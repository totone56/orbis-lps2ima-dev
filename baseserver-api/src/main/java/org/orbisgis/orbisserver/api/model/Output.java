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
 * Output of a process.
 *
 * @author Sylvain PALOMINOS
 */
public class Output {

    /** Unique identifier of the output.*/
    private String id;
    /** The data of the output.*/
    private Data data;
    /** The reference of the output.*/
    private String reference;
    /** Title of the output. */
    private String title;

    /**
     * Main constructor.
     * @param id Unique id of the output.
     */
    public Output(String id) {
        this.id = id;
    }

    /**
     * Constructor with the title of the output.
     * @param title Title of the output.
     * @param id Unique id of the output.
     */
    public Output(String title, String id) {
        this.id = id;
        this.title = title;
    }

    /**
     * Sets the data of the output.
     * @param data The output data.
     */
    public void setData(Data data) {
        this.data = data;
    }

    /**
     * Sets the reference of the output data.
     * @param reference Output data reference.
     */
    public void setReference(String reference) {
        this.reference = reference;
    }

    /**
     * Returns the Data of the Output.
     * @return The Data of the Output
     */
    public Data getData(){
        return data;
    }

    /**
     * Returns the reference of the Output.
     * @return The reference of the Output.
     */
    public String getReference(){
        return reference;
    }

    /**
     * Returns the unique id of the Output.
     * @return The unique id of the Output.
     */
    public String getId(){
        return id;
    }

    /**
     * Returns the title of the Output.
     * @return The title of the Output.
     */
    public String getTitle(){
        return title ==null?id: title;
    }

    /**
     * Sets the title of the Output.
     */
    public void setTitle(String title) {
        this.title = title;
    }
}
