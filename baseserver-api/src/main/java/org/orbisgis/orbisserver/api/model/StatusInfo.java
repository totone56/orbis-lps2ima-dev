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
package org.orbisgis.orbisserver.api.model;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Information about the status of the operation execution.
 *
 * @author Sylvain PALOMINOS
 */
public class StatusInfo {

    /** Identifier of the job.*/
    private String jobId;
    /** Identifier of the operation or process. */
    private String processID;
    /** Title of the process or operation. */
    private String processTitle;
    /** Status of the job. */
    private String status;
    /** Percent complete of the job. */
    private Integer percent = 0;
    /** Estimated date of completion. */
    private XMLGregorianCalendar estimatedCompletion;
    /** The next poll date. */
    private XMLGregorianCalendar nextPoll;
    /** Time in millis when the refresh should be done. */
    private long nextRefreshMillis = 0;
    /** Job result.*/
    private Result result;

    /**
     * Main constructor.
     * @param jobId Identifier of the job.
     */
    public StatusInfo(String jobId){
        this.jobId = jobId;
    }

    /**
     * Returns the job identifier.
     * @return The job identifier.
     */
    public String getJobId() {
        return jobId;
    }

    /**
     * Returns the process identifier.
     * @return The process identifier.
     */
    public String getProcessID() {
        return processID;
    }

    /**
     * Sets the identifier of the process.
     * @param processID Identifier of the process.
     */
    public void setProcessID(String processID) {
        this.processID = processID;
    }

    /**
     * Returns the process title.
     * @return The process title.
     */
    public String getProcessTitle() {
        return processTitle;
    }

    /**
     * Sets the process title.
     * @param processTitle The process title.
     */
    public void setProcessTitle(String processTitle) {
        this.processTitle = processTitle;
    }

    /**
     * Sets the status of the job.
     * @param status The job status.
     */
    public void setStatus(String status){
        this.status = status;
    }

    /**
     * Returns the job status.
     * @return The job status.
     */
    public String getStatus(){
        return status;
    }

    /**
     * Sets the percent complete.
     * @param percent The percent complete.
     */
    public void setPercentCompleted(int percent){
        this.percent = percent;
    }

    /**
     * Returns the percent complete.
     * @return The percent complete.
     */
    public Integer getPercentCompleted(){
        return percent;
    }

    /**
     * Sets the estimated completion date.
     * @param date The estimated completion date.
     */
    public void setEstimatedCompletion(XMLGregorianCalendar date){
        this.estimatedCompletion = date;
    }

    /**
     * Returns the estimated completion date.
     * @return The estimated completion date.
     */
    public XMLGregorianCalendar getEstimatedCompletion(){
        return estimatedCompletion;
    }

    /**
     * Sets the next poll date.
     * @param nextPoll The next poll date.
     */
    public void setNextPoll(XMLGregorianCalendar nextPoll) {
        this.nextPoll = nextPoll;
    }

    /**
     * returns the next poll date.
     * @return The next poll date.
     */
    public XMLGregorianCalendar getNextPoll() {
        return nextPoll;
    }

    /**
     * Sets the next refresh time in millis.
     * @param nextRefreshMillis the next refresh time in millis.
     */
    public void setNextRefreshMillis(long nextRefreshMillis) {
        this.nextRefreshMillis = nextRefreshMillis;
    }

    /**
     * Returns the next refresh time in millis.
     * @return The next refresh time in millis.
     */
    public long getNextRefreshMillis(){
        return nextRefreshMillis;
    }

    /**
     * Sets the job result.
     * @param result Job result.
     */
    public void setResult(Result result) {
        this.result = result;
    }

    /**
     * Returns true if there is a result, false otherwise.
     * @return True if there is a result, false otherwise.
     */
    public boolean hasResult(){
        return result!=null;
    }

    /**
     * Returns the Result.
     * @return The Result.
     */
    public Result getResult() {
        return result;
    }
}
