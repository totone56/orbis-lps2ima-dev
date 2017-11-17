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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Object containing the representation of the result of a process execution (job).
 *
 * @author Sylvain PALOMINOS
 */
public class Result {

    /** Unique id of the job linked to the result.*/
    private String jobId;
    /** Expiration date of the data of the result.*/
    private XMLGregorianCalendar expirationDate;
    /** List of the outputs of the result.*/
    private List<Output> outputList;

    /**
     * Main Constructor.
     * @param jobId Unique id of the job linked to the result.
     */
    public Result(String jobId){
        this.jobId = jobId;
    }

    /**
     * Sets the expiration date of the data of the result.
     * @param expirationDate The expiration date.
     */
    public void setExpirationDate(XMLGregorianCalendar expirationDate){
        this.expirationDate = expirationDate;
    }

    /**
     * Sets the list of the output of the result.
     * @param outputList The list of output.
     */
    public void setOutputList(List<Output> outputList){
        this.outputList = outputList;
    }

    /**
     * Gets the string representation of time remaining before the destruction of the Result.
     * The string is structured this way : MM/dd/yyyy hh:mm.
     * @return The string representation of the remaining time before destruction.
     */
    public String getRemainTime(){
        Date date = new Date();
        Date expDate = expirationDate.toGregorianCalendar().getTime();

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm");
        formatter.setTimeZone(expirationDate.toGregorianCalendar().getTimeZone());
        long diff = expDate.getTime()-date.getTime();
        diff /= 1000;
        long days = diff/(60*60*24);
        long hours = (diff-days*(60*60*24))/(60*60);
        long minutes = (diff-days*(60*60*24)-hours*(60*60))/60;
        return "" + days + "j" + hours + ":" + minutes;
    }

    /**
     * Get the time remaining before the destruction of the Result in milliseconds.
     * @return the time remaining before the destruction.
     */
    public long getRemainTimeMillis(){
        Date date = new Date();
        Date expDate = expirationDate.toGregorianCalendar().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm");
        formatter.setTimeZone(expirationDate.toGregorianCalendar().getTimeZone());
        return expDate.getTime()-date.getTime();
    }

    /**
     * Returns the output list.
     * @return The output list.
     */
    public List<Output> getOutputList(){
        return outputList;
    }

    /**
     * Returns the XMLGregorianCalendar object of the expiration date.
     * @return The XMLGregorianCalendar object.
     */
    public XMLGregorianCalendar getExpirationDate() {
        return expirationDate;
    }

    /**
     * Returns the unique id of the Job linked to the result.
     * @return The unique id of the Job.
     */
    public String getJobId(){
        return jobId;
    }
}
