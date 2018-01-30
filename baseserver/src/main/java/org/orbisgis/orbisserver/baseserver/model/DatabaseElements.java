package org.orbisgis.orbisserver.baseserver.model;

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
 * @see Group
 */
public final class DatabaseElements {
  /**
   * The name of the table containing the groups in the database.
   */
  public static final String GROUP_TABLE = "Group";
  /**
   * The name of the table containing the users in the database.
   */
  public static final String USER_TABLE = "User";
  /**
   * The name of the table containing the links between the users and the groups
   * in the database.
   */
  public static final String LINK_TABLE = "LinkUserGroup";
  /**
   * The name of the IDs in the table of the groups.
   */
  public static final String ID_GROUP = "idGroup";
  /**
   * The name of the names in the table of the groups.
   */
  public static final String GROUP_NAME = "nom";
  /**
   * The name of the persistence in the table of the groups.
   */
  public static final String GROUP_PERSISTENCE = "persistance";
  /**
   * The name of the groups in the table of the links.
   */
  public static final String LINK_GROUP = "idGroup";
  /**
   * The name of the users in the table of the links.
   */
  public static final String LINK_USER = "idUser";
  /**
   * The name of the administrators in the table of the links.
   */
  public static final String LINK_ADMIN = "admin";
  /**
   * The name of the IDs in the table of the users.
   */
  public static final String USER_ID = "idUser";
  /**
   * The name of the user names in the table of the users.
   */
  public static final String USER_USERNAME = "pseudo";
  /**
   * The name of the passwords in the table of the users.
   */
  public static final String USER_PASSWORD = "password";
  /**
   * The name of the super administrators in the table of the users.
   */
  public static final String USER_SUPER_ADMIN = "superAdmin";
}
