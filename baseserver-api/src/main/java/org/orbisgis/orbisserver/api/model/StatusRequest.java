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

/**
 * Request for the status of the execution of an operation
 *
 * @author Sylvain PALOMINOS
 */
public class StatusRequest {

    /** Identifier of the running job. */
    private String id;
    /** Title of the process executed. */
    private String processTitle;
    /** Id of the process executed. */
    private String processId;

    /**
     * Main constructor.
     * @param id Identifier of the running job.
     */
    public StatusRequest(String id){
        this.id = id;
    }

    /**
     * Returns the identifier.
     * @return The identifier.
     */
    public String getId(){
        return id;
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
     * Returns the process identifier.
     * @return The process identifier.
     */
    public String getProcessId() {
        return processId;
    }

    /**
     * Sets the process identifier.
     * @param processId Process identifier.
     */
    public void setProcessId(String processId) {
        this.processId = processId;
    }
}
