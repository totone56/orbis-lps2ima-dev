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
package org.orbisgis.orbisserver.baseserver;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.orbisgis.orbisserver.api.BaseServer;
import org.orbisgis.orbisserver.api.service.ServiceFactory;
import org.orbisgis.orbisserver.baseserver.model.Session;
import org.orbisgis.orbisserver.baseserver.utils.SessionInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wisdom.api.DefaultController;
import org.wisdom.api.annotations.Controller;
import org.wisdom.api.concurrent.ManagedExecutorService;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.concurrent.ExecutorService;

/**
 * Main class of the module. This class is the core managing the user auth and the sessions.
 *
 * @author Sylvain PALOMINOS
 */

@Controller
@Provides(specifications={BaseServerImpl.class})
@Instantiate
public class BaseServerImpl extends DefaultController implements BaseServer {

    /** Logger of the class. */
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseServerImpl.class);

    /** Cache list of the opened sessions. */
    private List<Session> openSessionList;
    /** Cache list of the alive session. */
    private List<Session> aliveSessionList;

    /** List of the service factory registered. */
    private List<ServiceFactory> serviceFactoryList;

    /** Wisdom executor service, used for the session initialisation. */
    @Requires(filter = "(name=" + ManagedExecutorService.SYSTEM + ")", proxy = false)
    ExecutorService executor;

    /** Administration database. */
    @Requires DataSource ds;

    /**
     * Main Constructor. It initiate the administration database.
     */
    public BaseServerImpl(){
        openSessionList = new ArrayList<>();
        aliveSessionList = new ArrayList<>();
        serviceFactoryList = new ArrayList<>();
        //Read the resource sql script and execute it
        try {
            Statement st = ds.getConnection().createStatement();
            InputStream inStream = this.getClass().getResourceAsStream("db_script.sql");
            BufferedReader br = new BufferedReader(new InputStreamReader(inStream));
            String line = br.readLine();
            int queryCount = 1;
            while(line != null) {
                st.addBatch(line);
                line = br.readLine();
                queryCount++;
                if(queryCount == 100){
                    st.executeBatch();
                    queryCount = 0;
                }
            }
            if(queryCount != 0){
                st.executeBatch();
            }
            LOGGER.info("Database script loaded");
        } catch (SQLException e) {
            LOGGER.error("Unable to start the database\n"+e.getMessage());
        } catch (IOException e) {
            LOGGER.error("Unable to read the database initiation script\n"+e.getMessage());
        }
    }

    @Override
    public void registerServiceFactory(ServiceFactory serviceFactory){
        serviceFactoryList.add(serviceFactory);
    }

    @Override
    public void unregisterServiceFactory(ServiceFactory serviceFactory) {
        for(Session session : openSessionList){
            session.shutdownService(serviceFactory.getServiceClass());
        }
    }

    /**
     * Get the session  corresponding to the given username and password.
     * @param username Username to use to log in.
     * @param password Password to use to log in.
     * @return The session of the user.
     */
    public Session getSession(String username, String password){
        //Check if the combo user/password is valid
        if(!testUser(username, password)){
            return null;
        }
        //If the session is already open, return it
        for(Session s : openSessionList){
            if(s.getUsername().equals(username)){
                return s;
            }
        }
        //If the session is already open, return it
        for(Session s : aliveSessionList){
            if(s.getUsername().equals(username)){
                return s;
            }
        }
        //Otherwise create a new session and return it
        Session session = buildSession(username);
        openSessionList.add(session);
        return session;
    }

    /**
     * Instantiate a session with the user name.
     * @param username User name.
     * @return An instantiated session.
     */
    private Session buildSession(String username){
        UUID token = UUID.randomUUID();

        //Instantiate the session and initialize it
        Session session = new Session(username, token, this);
        SessionInitializer init = new SessionInitializer(
                session, getSessionOptions(session), token, serviceFactoryList);
        executor.submit(init);

        return session;
    }

    /**
     * Gets the properties of a session in the administration database and give it to the session.
     * @param session Session to set.
     */
    private Map<String, Object> getSessionOptions(Session session){
        Map<String, Object> optionMap = new HashMap<>();
        try {
            PreparedStatement ps = ds.getConnection().prepareStatement(
                    "SELECT expirationTime, poolSize FROM session_table WHERE username LIKE ?;");
            ps.setString(1, session.getUsername());
            ResultSet rs = ps.executeQuery();
            rs.first();
            optionMap.put(Session.PROPERTY_EXPIRATION_TIME_MILLIS, rs.getLong(1));
            optionMap.put(Session.JOB_POOL_SIZE, rs.getInt(2));
            rs.close();
        } catch (SQLException e) {
            LOGGER.error("Unable to request the database in order to get the session options.\n"+e.getMessage());
        }
        return optionMap;
    }

    /**
     * Test if the user name and password are correct.
     * @param username Name of the user.
     * @param password Password of the user.
     * @return True if the user is correct, false otherwise.
     */
    private boolean testUser(String username, String password){
        try {
            PreparedStatement ps = ds.getConnection().prepareStatement(
                    "SELECT COUNT(username) FROM session_table WHERE username LIKE ? AND password LIKE ?;");
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            rs.first();
            boolean isUser = rs.getInt(1) != 0;
            rs.close();
            return isUser;
        } catch (SQLException e) {
            LOGGER.error("Unable to request the database in order to test username/password.\n"+e.getLocalizedMessage());
            for(StackTraceElement el : e.getStackTrace()){
                LOGGER.error(el.toString());
            }
        }
        return false;
    }

    /**
     * Test if the user name is correct.
     * @param username Name of the user.
     * @return True if the user is correct, false otherwise.
     */
    private boolean testUser(String username){
        try {
            PreparedStatement ps = ds.getConnection().prepareStatement(
                    "SELECT COUNT(username) FROM session_table WHERE username LIKE ?;");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            rs.first();
            boolean isUser = rs.getInt(1) != 0;
            rs.close();
            return isUser;
        } catch (SQLException e) {
            LOGGER.error("Unable to request the database in order to test username.\n"+e.getLocalizedMessage());
            for(StackTraceElement el : e.getStackTrace()){
                LOGGER.error(el.toString());
            }
        }
        return false;
    }

    /**
     * Create a session for the given user.
     * @param username Name of the user.
     * @return The user session.
     */
    public Session createSession(String username, String password){
        if(!testUser(username, password)) {
            try {
                PreparedStatement ps = ds.getConnection().prepareStatement(
                        "INSERT INTO session_table VALUES (?,?,?,?);");
                ps.setString(1, username);
                ps.setString(2, password);
                ps.setString(3, "999999");
                ps.setString(4, "10");
                ps.execute();
            } catch (SQLException e) {
                LOGGER.error("Unable to add a user.\n" + e.getMessage());
            }
        }
        Session session = getSession(username, password);
        openSessionList.add(session);
        return session;
    }

    /**
     * Updates the password of a user
     * @param username User name.
     * @param newPassword New password.
     */
    public void changePassword(String username, String newPassword) {
        if(testUser(username)) {
            try {
                PreparedStatement ps = ds.getConnection().prepareStatement(
                        "UPDATE user_table SET password = ? WHERE username=?;");
                ps.setString(1, newPassword);
                ps.setString(2, username);
                ps.execute();
            } catch (SQLException e) {
                LOGGER.error("Unable to change password.\n" + e.getMessage());
            }
        }
    }

    /**
     * Returns the open session list.
     * @return The open session list.
     */
    public List<Session> getOpenSessionList() {
        return openSessionList;
    }

    /**
     * Close the session with the given id
     * @param id Id of the session to close
     */
    public void closeSession(String id) {
        //Get the session
        Session session = null;
        for(Session s : openSessionList){
            if(s.getToken().toString().equals(id)){
                session = s;
            }
        }
        if(session != null){
            //If the session is active (some process or result are running), move it to the alive session list
            if(session.isActive()) {
                aliveSessionList.add(session);
            }
            //Else (nor more results or process), shutdown the session
            else{
                killSession(session);
            }
        }
    }

    /**
     * Method called when a session became inactive. If the session is in the alive list, the session should be killed.
     * @param session Session which became inactive.
     */
    public void inactiveSession(Session session) {
        if(!aliveSessionList.contains(session)){
            return;
        }
        killSession(session);
    }

    /**
     * Kill a session and free resources.
     * @param session Session to kill.
     */
    public void killSession(Session session){
        session.shutdown();
        openSessionList.remove(session);
        aliveSessionList.remove(session);
    }
}
