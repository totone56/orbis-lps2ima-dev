package org.orbisgis.orbisserver.baseserver.utils;

/**
 * <b>Contains all the names related to the database.</b>
 * <p>
 * All classes can access this class to quickly get the latest name of the
 * different elements in the database.
 * </p>
 * 
 * @author Group A - 2017/2018
 * 
 * @version 1.0
 * 
 * @see DatabaseRequest
 */
public enum DatabaseElements {
  /**
   * The name of the table containing the groups in the database.
   */
  GROUP_TABLE("Group"),
  /**
   * The name of the table containing the users in the database.
   */
  USER_TABLE("User"),
  /**
   * The name of the table containing the links between the users and the groups
   * in the database.
   */
  LINK_USER_TABLE("LinkUserGroup"),
  /**
   * The name of the table containing the links between the bundles and the groups
   * in the database.
   */
  LINK_BUNDLE_TABLE("LinkBundleGroup"),
  /**
   * The name of the IDs in the table of the groups.
   */
  ID_GROUP("idGroup"),
  /**
   * The name of the names in the table of the groups.
   */
  GROUP_NAME("nom"),
  /**
   * The name of the persistence in the table of the groups.
   */
  GROUP_PERSISTENCE("persistance"),
  /**
   * The name of the groups in the table of the links with the users.
   */
  LINK_USER_GROUP("idGroup"),
  /**
   * The name of the users in the table of the links.
   */
  LINK_USER("idUser"),
  /**
   * The name of the administrators in the table of the links.
   */
  LINK_ADMIN("admin"),
  /**
   * The name of the groups in the table of the links with the bundles.
   */
  LINK_BUNDLE_GROUP("idGroup"),
  /**
   * The name of the bundles in the table of the links.
   */
  LINK_BUNDLE("idBundle"),
  /**
   * The name of the administrators in the table of the links.
   */
  /**
   * The name of the IDs in the table of the users.
   */
  USER_ID("idUser"),
  /**
   * The name of the user names in the table of the users.
   */
  USER_USERNAME("pseudo"),
  /**
   * The name of the passwords in the table of the users.
   */
  USER_PASSWORD("password"),
  /**
   * The name of the super administrators in the table of the users.
   */
  USER_SUPER_ADMIN("superAdmin");

  /**
   * Contains the name of the element in the database.
   */
  private String name;

  /**
   * To create an element of the enumeration.
   * 
   * @param name
   *          the name of the element in the database
   */
  DatabaseElements(String name) {
    this.name = name;
  }

  /**
   * To get the name of the element in the database.
   */
  @Override
  public String toString() {
    return name;
  }
}