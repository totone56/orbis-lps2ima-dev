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

import java.util.Map;

/**
 * Interface for the definition of the factory creating and configuring a service.
 *
 * @author Sylvain PALOMINOS
 */
public interface ServiceFactory {

    String DATA_SOURCE_PROP = "DATA_SOURCE_PROP";
    String EXECUTOR_SERVICE_PROP = "EXECUTOR_SERVICE_PROP";
    String WORKSPACE_FOLDER_PROP = "WORKSPACE_FOLDER_PROP";
    String USERNAME_PROP = "USERNAME_PROP";
    String TOKEN_PROP = "TOKEN_PROP";

    /**
     * Instantiate, set and returns the service
     * @param properties Map of the properties to set the service with a String as key and an Object as Value.
     * @return A service instance
     */
    Service createService(Map<String, Object> properties);

    /**
     * Returns the Class object of the class implementing the Service interface and instantiated by the factory.
     * @return The class object of the instantiated Service.
     */
    Class getServiceClass();
}
