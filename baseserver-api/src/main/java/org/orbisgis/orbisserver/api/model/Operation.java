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

import java.util.ArrayList;
import java.util.List;

/**
 * Operation performed by the server.
 *
 * @author Sylvain PALOMINOS
 */
public class Operation {

    /** Title. */
    private String title;
    /** Unique identifier. */
    private String id;
    /** Abstract.*/
    private String abstr;
    /** List of the inputs. */
    private List<Input> inputList;
    /** List of the outputs. */
    private List<Output> outputList;
    /** List of the keywords. */
    private List<String> keyWord;

    /**
     * Main constructor.
     * @param title Title of the operation.
     * @param id Identifier of the operation.
     */
    public Operation(String title, String id){
        this.title = title;
        this.id = id;
        inputList = new ArrayList<>();
        outputList = new ArrayList<>();
        keyWord = new ArrayList();
    }

    /**
     * Returns the title.
     * @return The title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title.
     * @param title The title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the identifier.
     * @return The identifier.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the identifier.
     * @param id The identifier.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns the abstract.
     * @return The abstract.
     */
    public String getAbstr() {
        return abstr;
    }

    /**
     * Sets the abstract.
     * @param abstr The abstract.
     */
    public void setAbstr(String abstr) {
        this.abstr = abstr;
    }

    /**
     * Returns the list of the inputs.
     * @return The input list.
     */
    public List<Input> getInputList() {
        return inputList;
    }

    /**
     * Adds an Input.
     * @param input Input to add.
     */
    public void addInput(Input input) {
        this.inputList.add(input);
    }

    /**
     * Returns the list of the inputs.
     * @return The input List.
     */
    public List<Output> getOutputList() {
        return outputList;
    }

    /**
     * Adds an output.
     * @param output Output to add.
     */
    public void addOutput(Output output) {
        this.outputList.add(output);
    }

    /**
     * Return the list of keywords.
     * @return The keyword list.
     */
    public List<String> getKeyWord() {return keyWord;}

    /**
     * Adds a keywords.
     * @param keyWord Keyword to add.
     */
    public void setKeyWord(List<String> keyWord) {this.keyWord = keyWord;}

    /**
     * Sets the list of Input of the Operation.
     * @param inputList List of the Input.
     */
    public void setInputList(List<Input> inputList) {
        this.inputList = inputList;
    }


    /**
     * Sets the list of Output of the Operation.
     * @param outputList List of the Output.
     */
    public void setOutputList(List<Output> outputList) {
        this.outputList = outputList;
    }
}
