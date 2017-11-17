/*
 * OrbisServer is an OSGI web application to expose OGC services.
 *
 * OrbisServer is part of the OrbisGIS platform
 *
 * OrbisGIS is a java GIS application dedicated to research in GIScience.
 * OrbisGIS is developed by the GIS group of the DECIDE team of the
 * Lab-STICC CNRS laboratory, see <http://www.lab-sticc.fr/>.
 *
 * The GIS group of the DECIDE team is located at :
 *
 * Laboratoire Lab-STICC – CNRS UMR 6285
 * Equipe DECIDE
 * UNIVERSITÉ DE BRETAGNE-SUD
 * Institut Universitaire de Technologie de Vannes
 * 8, Rue Montaigne - BP 561 56017 Vannes Cedex
 *
 * OrbisServer is distributed under LGPL 3 license.
 *
 * Copyright (C) 2017 CNRS (Lab-STICC UMR CNRS 6285)
 *
 *
 * OrbisServer is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * OrbisServer is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with
 * OrbisServer. If not, see <http://www.gnu.org/licenses/>.
 *
 * For more information, please consult: <http://www.orbisgis.org/>
 * or contact directly:
 * info_at_ orbisgis.org
 */
package org.orbisgis.orbisserver.baseserver.web;

import org.apache.felix.ipojo.annotations.Requires;
import org.orbisgis.orbisserver.api.model.Operation;
import org.orbisgis.orbisserver.api.model.StatusInfo;
import org.orbisgis.orbisserver.baseserver.BaseServerImpl;
import org.orbisgis.orbisserver.baseserver.model.DatabaseContent;
import org.orbisgis.orbisserver.baseserver.model.DatabaseTable;
import org.orbisgis.orbisserver.baseserver.model.Session;
import org.wisdom.api.DefaultController;
import org.wisdom.api.annotations.Controller;
import org.wisdom.api.annotations.Parameter;
import org.wisdom.api.annotations.Route;
import org.wisdom.api.annotations.View;
import org.wisdom.api.annotations.scheduler.Async;
import org.wisdom.api.http.FileItem;
import org.wisdom.api.http.HttpMethod;
import org.wisdom.api.http.Result;
import org.wisdom.api.templates.Template;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Main orbisserver controller containing all the route for the web wps client.
 *
 * @author Sylvain PALOMINOS
 */
@Controller
public class MainController extends DefaultController {

    @Requires
    private BaseServerImpl coreServerController;

    @View("Home")
    private Template home;

    @View("HomeContent")
    private Template homeContent;

    @View("ProcessList")
    private Template processListTemplate;

    @View("Describe")
    private Template describeProcess;

    @View("Jobs")
    private Template jobs;

    @View("Workspace")
    private Template workspace;

    @View("DataLeftNav")
    private Template dataLeftNav;

    @View("Data")
    private Template data;

    @View("Import")
    private Template tImport;

    @View("Export")
    private Template export;

    @View("Process")
    private Template process;

    @View("ProcessLeftNav")
    private Template leftNavContent;

    @View("User")
    private Template user;

    @View("UserSettings")
    private Template userSettings;

    @View("DatabaseView")
    private Template databaseView;

    @Route(method = HttpMethod.GET, uri = "/")
    public Result home() {
        return ok(render(home));
    }

    @Route(method = HttpMethod.GET, uri = "/home")
    public Result homeContent() {
        return ok(render(homeContent));
    }

    @Route(method = HttpMethod.GET, uri = "/user/logOut")
    public Result logOut() {
        String token = context().cookieValue("token");
        coreServerController.closeSession(token);
        return ok();
    }

    @Route(method = HttpMethod.POST, uri = "/login")
    @Async
    public Result login() throws IOException {
        String urlContent = URLDecoder.decode(context().reader().readLine(), "UTF-8");
        String[] split = urlContent.split("&");
        Session session = coreServerController.getSession(split[0].replaceAll(".*=", ""),
                split[1].replaceAll(".*=", ""));
        if(session != null) {
            return ok(session.getToken().toString());
        }
        else {
            return badRequest("Unrecognized credits.");
        }
    }

    @Route(method = HttpMethod.GET, uri = "/process/processList")
    public Result processList(@Parameter("filters") String filters) throws IOException {
        String token = context().cookieValue("token");
        for(Session session : coreServerController.getOpenSessionList()) {
            if (session.getToken().toString().equals(token)) {
                List<Operation> processList = session.getOperationList();
                List<Operation> importExportList = new ArrayList<>();
                List<Operation> filteredList = new ArrayList<>();

                for(Operation op : processList){
                    for(String keyword :  op.getKeyWord()){
                        if(keyword.equals("Export") || keyword.equals("Import")){
                            importExportList.add(op);
                        }
                    }
                }
                processList.removeAll(importExportList);
                for(Operation op : processList){
                    if(op.getTitle().toLowerCase().contains(filters.toLowerCase())) {
                        filteredList.add(op);
                    }
                }
                return ok(render(processListTemplate, "processList", filteredList));
            }
        }
        return badRequest(render(processListTemplate));
    }

    @Route(method = HttpMethod.GET, uri = "/describeProcess")
    public Result describeProcess(@Parameter("id") String id) throws IOException {
        String token = context().cookieValue("token");
        Session session = null;
        for(Session s : coreServerController.getOpenSessionList()) {
            if (s.getToken().toString().equals(token)) {
                session = s;
                Operation op = session.getOperation(id);
                return ok(render(describeProcess, "operation", op, "session", session));
            }
        }
        return badRequest(render(homeContent));
    }

    @Route(method = HttpMethod.POST, uri = "/execute")
    public Result execute() throws IOException {
        for(Session session : coreServerController.getOpenSessionList()) {
            String urlContent = URLDecoder.decode(context().reader().readLine(), "UTF-8");
            String[] split = urlContent.split("&");
            String token = context().cookieValue("token");
            if (session.getToken().toString().equals(token)) {
                Map<String, String> inputData = new HashMap<>();
                String id = "";
                for (String str : split) {
                    String[] val = str.split("=");
                    if (val[0].equals("processId")) {
                        id = val[1];
                    } else {
                        if (val.length == 1) {
                            inputData.put(val[0], "");
                        } else {
                            inputData.put(val[0], val[1]);
                        }
                    }
                }
                session.executeOperation(id, inputData);
                return ok();
            }
        }
        return badRequest();
    }

    @Route(method = HttpMethod.POST, uri = "/uploading")
    public Result upload() throws IOException {
        String cookie = context().cookieValue("token");
        for(Session session : coreServerController.getOpenSessionList()) {
            if(session.getToken().toString().equalsIgnoreCase(cookie)){

                if(!context().files().isEmpty()){
                    for (FileItem fileItem : context().files()) {
                        if(fileItem!=null){
                            byte[] buffer = new byte[8 * 1024];
                            FileOutputStream out = new FileOutputStream(new File(session.getWorkspaceFolder(),
                                    fileItem.name()));
                            BufferedInputStream in = new BufferedInputStream(fileItem.stream());
                            while (in.read(buffer) != -1) {
                                out.write(buffer);
                            }
                            in.close();
                            out.close();
                        }
                    }
                }
                return  ok();
            }
        }
        return badRequest(render(homeContent));
    }

    @Route(method = HttpMethod.GET, uri = "/jobs")
    public Result jobs() throws IOException {
        String token = context().cookieValue("token");
        for(Session session : coreServerController.getOpenSessionList()) {
            if (session.getToken().toString().equals(token)) {
                List<StatusInfo> statusInfoToRefreshList = session.getAllStatusInfoToRefresh();
                List<StatusInfo> statusInfoList = session.getAllStatusInfo();
                long minRefresh = Long.MAX_VALUE;
                for (StatusInfo statusInfo : statusInfoToRefreshList) {
                    minRefresh = Math.min(session.refreshStatus(statusInfo), minRefresh);
                }

                for (StatusInfo statusInfo : statusInfoList) {
                    if (statusInfo.getNextRefreshMillis() >= 0) {
                        minRefresh = Math.min(statusInfo.getNextRefreshMillis(), minRefresh);
                    }
                }

                if (minRefresh == Long.MAX_VALUE) {
                    minRefresh = -1;
                }
                return ok(render(jobs, "jobList", session.getAllStatusInfo(), "nextRefresh", minRefresh));
            }
        }
        return ok(render(homeContent));
    }

    @Route(method = HttpMethod.POST, uri = "/register")
    public Result signIn() throws IOException {
        String urlContent = URLDecoder.decode(context().reader().readLine(), "UTF-8");
        String[] split = urlContent.split("&");
        Session session = coreServerController.createSession(split[0].replaceAll(".*=", ""),
                split[1].replaceAll(".*=", ""));
        if(session != null) {
            return ok(session.getToken().toString());
        }
        else {
            return badRequest("Can not create user.");
        }
    }

    @Route(method = HttpMethod.GET, uri = "/workspace")
    public Result workspace() {
        return ok(render(workspace));
    }

    @Route(method = HttpMethod.GET, uri = "/data")
    public Result data() {
        String token = context().cookieValue("token");
        for (Session session : coreServerController.getOpenSessionList()) {
            if (session.getToken().toString().equals(token)) {
                return ok(render(data));
            }
        }
        return badRequest(render(data));
    }

    @Route(method = HttpMethod.GET, uri = "/dataleftnav")
    public Result dataLeftNav() {
        String token = context().cookieValue("token");
        for (Session session : coreServerController.getOpenSessionList()) {
            if (session.getToken().toString().equals(token)) {
                return ok(render(dataLeftNav));
            }
        }
        return badRequest(render(data));
    }

    @Route(method = HttpMethod.GET, uri = "/data/import")
    public Result Import(@Parameter("filters") String filters) {
        String token = context().cookieValue("token");
        for(Session session : coreServerController.getOpenSessionList()) {
            if (session.getToken().toString().equals(token)) {
                List<Operation> opList = session.getOperationList();
                List<Operation> importList = new ArrayList<>();
                List<Operation> filteredList = new ArrayList<>();

                for(Operation op : opList){
                    for(String keyword :  op.getKeyWord()){
                        if(keyword.equals("Import")){
                            importList.add(op);
                        }
                    }
                }

                for(Operation op : importList){
                    if(op.getTitle().toLowerCase().contains(filters.toLowerCase())) {
                        filteredList.add(op);
                    }
                }
                return ok(render(tImport, "processList", filteredList));
            }
        }

        return badRequest(render(homeContent));
    }

    @Route(method = HttpMethod.GET, uri = "/data/export")
    public Result export(@Parameter("filters") String filters) {
        String token = context().cookieValue("token");
        for(Session session : coreServerController.getOpenSessionList()) {
            if (session.getToken().toString().equals(token)) {
                List<Operation> opList = session.getOperationList();
                List<Operation> exportList = new ArrayList<>();
                List<Operation> filteredList = new ArrayList<>();

                for(Operation op : opList){
                    for(String keyword :  op.getKeyWord()){
                        if(keyword.equals("Export")){
                            exportList.add(op);
                        }
                    }
                }
                for(Operation op : exportList){
                    if(op.getTitle().toLowerCase().contains(filters.toLowerCase())) {
                        filteredList.add(op);
                    }
                }
                return ok(render(export, "processList", filteredList));
            }
        }

        return badRequest(render(homeContent));
    }

    @Route(method = HttpMethod.GET, uri = "/process")
    public Result process() {
        String token = context().cookieValue("token");
        for(Session session : coreServerController.getOpenSessionList()) {
            if (session.getToken().toString().equals(token)) {
                return ok(render(process));
            }
        }
        return badRequest(render(process));
    }


    @Route(method = HttpMethod.GET, uri = "/process/leftNavContent")
    public Result leftNavContent() {
        String token = context().cookieValue("token");
        for(Session session : coreServerController.getOpenSessionList()) {
            if (session.getToken().toString().equals(token)) {
                return ok(render(leftNavContent));
            }
        }
        return badRequest(render(process));
    }

    @Route(method = HttpMethod.GET, uri = "/user")
    public Result user() {
        String token = context().cookieValue("token");
        for(Session session : coreServerController.getOpenSessionList()) {
            if (session.getToken().toString().equals(token)) {
                return ok(render(user, "session", session));
            }
        }
        return ok(render(user, "session", null));
    }

    @Route(method = HttpMethod.POST, uri = "/user/changePwd")
    public Result changePwd() throws IOException {
        String urlContent = URLDecoder.decode(context().reader().readLine(), "UTF-8");
        String newPassword = "";
        String newPasswordRepeat = null;
        String token = null;
        String[] split = urlContent.split("&");
        for(String argument : split){
            String[] splitArg = argument.split("=");
            switch (splitArg[0]) {
                case "pwd":
                    newPassword = splitArg[1];
                    break;
                case "pwd_repeat":
                    newPasswordRepeat = splitArg[1];
                    break;
                case "token":
                    token = splitArg[1];
                    break;
            }
        }
        if(newPassword.equals(newPasswordRepeat) && token != null) {
            coreServerController.changePassword(token, newPassword);
            return ok("Password changed.");
        }
        else{
            return badRequest("The two passwords are not the same.");
        }
    }

    @Route(method = HttpMethod.GET, uri = "/user/settings")
    public Result settings() {
        String token = context().cookieValue("token");
        Session session = null;
        for(Session s : coreServerController.getOpenSessionList()){
            if(s.getToken().toString().equals(token)){
                session = s;
            }
        }
        if(session != null) {
            return ok(render(userSettings, "session", session));
        }
        else {
            return badRequest("Unexisting session.");
        }
    }

    @Route(method = HttpMethod.GET, uri = "/data/database")
    public Result database() {
        String token = context().cookieValue("token");
        Session session = null;
        for(Session s : coreServerController.getOpenSessionList()){
            if(s.getToken().toString().equals(token)){
                session = s;
            }
        }
        if(session != null) {
            DatabaseContent dbContent = session.getDatabaseContent();
            int maxSize = 0;
            for(DatabaseTable dbTable : dbContent.getTableList()){
                maxSize = Math.max(maxSize, dbTable.getFieldList().size()+1);
            }
            return ok(render(databaseView,
                    "databaseContent", dbContent,
                    "cell_width_percent", (float)(100)/maxSize));
        }
        else {
            return badRequest("Unexisting session.");
        }
    }

    @Route(method = HttpMethod.GET, uri = "/createArchive")
    public Result createArchive(@Parameter("jobId") String jobId) {
        String token = context().cookieValue("token");
        Session session = null;
        for(Session s : coreServerController.getOpenSessionList()){
            if(s.getToken().toString().equals(token)){
                session = s;
            }
        }
        if(session != null) {
            File file = session.getResultAchive(jobId);
            if(file != null) {
                return ok(file, true);
            }
            return badRequest("Unable to create the result archive.");
        }
        else{
            return badRequest("Unexisting session.");
        }
    }
}
