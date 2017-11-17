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

import java.util.Map;

/**
 * Object containing data for an Operation Input
 *
 * @author Sylvain PALOMINOS
 */
public class Input {
    /** Title of the input. */
    private String title;
    /** Name of the input. */
    private String name;
    /** Id of the input. */
    private String id;
    /** Type of the input. */
    private String type;
    /** Attributes of the input. */
    private Map<String, Object> attributes;
    /** the input is optional or not. */
    private Boolean optional;
    /** Unique id used in the html code. */
    private String htmlId;

    public Input(String title, String name, String id, String type, Map<String, Object> attributes,Boolean optional){
        this.title = title;
        this.name = name;
        this.id = id;
        this.type = type;
        this.attributes = attributes;
        this.optional = optional;
        htmlId = id.replaceAll("[ :/.\\\\]", "");
    }

    /**
     * Returns the title of the input.
     * @return The title of the input.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the name of the input.
     * @return The name of the input.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the id of the input.
     * @return The id of the input.
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the html id of the input.
     * @return The html id of the input.
     */
    public String getHtmlId() {
        return htmlId;
    }

    /**
     * Returns the type of the input.
     * @return The type of the input.
     */
    public String getType() {
        return type;
    }

    /**
     * Return the map od the attributes of the input. The attributes defines additional information defined in the
     * extensions of the complex data input.
     * @return The attributes of the input.
     */
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    /**
     * Tells if the input is optional or not.
     * @return True if the input is optional, false otherwise.
     */
    public Boolean getOptional() { return optional; }
}
