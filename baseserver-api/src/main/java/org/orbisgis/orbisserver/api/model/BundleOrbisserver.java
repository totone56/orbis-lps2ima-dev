package org.orbisgis.orbisserver.api.model;
import java.util.List;

/**
 * The purpose of this class is to represent a bundle of scripts
 */
public class BundleOrbisserver {
    private List<Operation> listScript ;
    private String name;

    /**
     * Setter of the list of scripts
     * @param listScript - List
     */
    public void setListScript(List<Operation> listScript) {
        this.listScript = listScript;
    }

    /**
     * Setter of the name of the bundle
     * @param name
     */
    public void setName(String name ){
        this.name = name ;
    }

    /**
     * Getter of the list
     * @return list<Operation>
     */
    public List<Operation> getListScript() {
        return listScript;
    }

    /**
     * Getter of the name
     * @return String
     */
    public String getName() {
        return name;
    }
}