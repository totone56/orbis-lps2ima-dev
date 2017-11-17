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
package org.orbisgis.orbisserver.baseserver.model;

import org.h2gis.utilities.TableLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * Representation of a table from a database.
 *
 * @author Sylvain PALOMINOS
 */
public class DatabaseTable {

    /** TableLocation object of the table. */
    private TableLocation tableLocation;
    /** List of fields of the table. */
    private List<DatabaseField> fieldList;

    /**
     * Main constructor.
     * @param tableLocation TableLocation of the table.
     */
    public DatabaseTable(TableLocation tableLocation){
        this.tableLocation = tableLocation;
        this.fieldList = new ArrayList();
    }

    /**
     * Returns the name of the table.
     * @return The table name.
     */
    public String getName() {
        return tableLocation.getTable();
    }

    /**
     * Adds a field.
     * @param columnLabel Name of the field.
     * @param columnTypeName Type of the field.
     */
    public void addField(String columnLabel, String columnTypeName) {
        fieldList.add(new DatabaseField(columnLabel, columnTypeName));
    }

    /**
     * Returns the list of the fields.
     * @return The field list.
     */
    public List<DatabaseField> getFieldList(){
        return fieldList;
    }
}
