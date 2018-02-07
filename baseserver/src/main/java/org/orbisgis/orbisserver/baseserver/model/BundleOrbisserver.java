package org.orbisgis.orbisserver.baseserver.model;

import java.util.List;

import org.orbisgis.orbisserver.api.model.Operation;

/**
 * The purpose of this class is to represent a bundle of scripts
 */
public class BundleOrbisserver {
  private List<Operation> listScript;
  private String name;
  private int id;

  public BundleOrbisserver(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  /**
   * Setter of the list of scripts
   * 
   * @param listScript
   *          - List
   */
  public void setListScript(List<Operation> listScript) {
    this.listScript = listScript;
  }

  /**
   * Setter of the name of the bundle
   * 
   * @param name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Getter of the list
   * 
   * @return list<Operation>
   */
  public List<Operation> getListScript() {
    return listScript;
  }

  /**
   * Getter of the name
   * 
   * @return String
   */
  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object bundle) {
    if (!(bundle instanceof BundleOrbisserver)) {
      return false;
    }

    if (((BundleOrbisserver) bundle).id == id) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return 42;
  }
}