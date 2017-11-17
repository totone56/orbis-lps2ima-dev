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
package org.orbisgis.orbisserver.api.service;

import org.orbisgis.orbisserver.api.model.*;

import java.util.List;
import java.util.Map;

/**
 * Interface to define a service which can be plugged to the server
 * Each Session will contains a Service instance.
 *
 * @author Sylvain PALOMINOS
 */
public interface Service {

    /**
     * Starts the service and configure it with the given property Map.
     *
     * @param propertyMap Map containing the properties to set the service.
     */
    void start(Map<String, Object> propertyMap);

    /**
     * Shutdown the service and free resources.
     */
    void shutdown();

    /**
     * Execute an operation with the given data
     *
     * @param request Request containing all the data for the execution
     *
     * @return A statusInfo object containing all the information about the execution
     */
    StatusInfo executeOperation(ExecuteRequest request);

    /**
     * Gets the status of an execution
     *
     * @param request Request containing the identifier of an execution
     *
     * @return A statusInfo object containing all the information about the execution
     */
    StatusInfo getStatus(StatusRequest request);

    /**
     * Returns the result according to the data contained in the StatusRequest.
     *
     * @param request Object containing all the data to get the result.
     *
     * @return Result object containing the results.
     */
    Result getResult(StatusRequest request);

    /**
     * Returns all the operation available throw the Service.
     *
     * @return List of all the available operations.
     */
    List<Operation> getAllOperation();

    /**
     * Test if the service offer an operation with the given id.
     *
     * @param id Id of the operation to find.
     *
     * @return True if the service contains the operation, false otherwise.
     */
    boolean hasOperation(String id);

    /**
     * Returns the operation with the given id. If no operation is found, returns null;
     *
     * @param id Id of the operation to get.
     *
     * @return An Operation object if it is found, null otherwise.
     */
    Operation getOperation(String id);
}
