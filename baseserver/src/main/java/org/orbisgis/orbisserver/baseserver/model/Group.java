package org.orbisgis.orbisserver.baseserver.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.orbisgis.orbisserver.baseserver.exception.AdminException;
import org.orbisgis.orbisserver.baseserver.exception.DatabaseException;
import org.orbisgis.orbisserver.baseserver.exception.NoDataSourceException;
import org.orbisgis.orbisserver.baseserver.exception.NoRightException;
import org.orbisgis.orbisserver.baseserver.utils.DatabaseElements;
import org.orbisgis.orbisserver.baseserver.utils.DatabaseRequest;
import org.wisdom.api.DefaultController;

/**
 * <b>This class represent a group in the application.</b>
 * <p>
 * It allows to create and modify a group, which will be stored in the database.
 * You can create a new Group in two ways :
 * <ul>
 * <li>using the constructor with an integer as a parameter to load the group
 * from the database with the ID corresponding to the parameter</li>
 * <li>using the other constructor to create a new group which will be
 * automatically stored in the database</li>
 * </ul>
 * </p>
 * 
 * @author Group A - 2017/2018
 * 
 * @version 1.0
 *
 * @see Session
 * @see User
 * @see BundleOrbisserver
 * @see NoRightException
 * @see AdminException
 */
public class Group extends DefaultController {
  /**
   * Corresponds to the id of the group in the database.
   * 
   * @see Group#Group(int)
   * @see Group#Group(int, String, User, boolean, User)
   * @see Group#getId()
   */
  private int id;
  /**
   * Contains the name of the group.
   * 
   * @see Group#Group(int, String, User, boolean, User)
   * @see Group#getGroupName()
   * @see Group#setGroupName(String, User)
   */
  private String groupName;
  /**
   * True if the data of the group are persistent, else false.
   * 
   * @see Group#Group(int, String, User, boolean, User)
   * @see Group#isPersistent()
   * @see Group#setPersistence(boolean, User)
   */
  private boolean persistence;
  /**
   * Contains the list of the administrators of the group.
   * 
   * @see Group#getAdmins()
   * @see Group#addAdmin(User, User)
   * @see Group#removeAdmin(User, User)
   */
  private ArrayList<User> admins = new ArrayList<User>();
  /**
   * Contains the list of all the users of the group, which must not be
   * administrators.
   * 
   * @see Group#getUsers()
   * @see Group#addUser(User, User)
   * @see Group#removeUser(User, User)
   */
  private ArrayList<User> users = new ArrayList<User>();
  /**
   * Contains the list of all the bundles of the group.
   * 
   * @see Group#getBundles()
   * @see Group#addBundle(BundleOrbisserver, User)
   * @see Group#removeBundle(BundleOrbisserver, User)
   */
  private ArrayList<BundleOrbisserver> bundles = new ArrayList<BundleOrbisserver>();

  /**
   * This constructor load the group from the database.
   * 
   * @param id
   *          the ID of the group
   * 
   * @throws DatabaseException
   *           thrown when an error occur with the database, or if the group is
   *           not found
   * 
   * @see Group#id
   */
  public Group(int id) throws DatabaseException {
    try {
      ResultSet group = DatabaseRequest.getInstance().find(DatabaseElements.GROUP_TABLE,
          DatabaseElements.ID_GROUP, Integer.toString(id));

      if (group == null) {
        throw new DatabaseException(
            "Une erreur est survenue dans la base de données, le groupe n'a pas pu être récupéré.");
      }

      if (!group.next()) {
        throw new DatabaseException(
            "Aucun groupe correspondant à l'ID fourni n'a été trouvé dans la base de données.");
      }

      this.id = id;
      groupName = group.getString(DatabaseElements.GROUP_NAME.toString());
      persistence = group.getBoolean(DatabaseElements.GROUP_PERSISTENCE.toString());

      ResultSet users = DatabaseRequest.getInstance().find(DatabaseElements.LINK_USER_TABLE,
          DatabaseElements.LINK_USER_GROUP, Integer.toString(id));

      if (users == null) {
        throw new DatabaseException(
            "Une erreur est survenue dans la base de données, les utilisateurs du groupe"
                + " n'ont pas pu être récupérés.");
      }

      while (users.next()) {
        User newUser = new User(users.getInt(DatabaseElements.LINK_USER.toString()));

        if (users.getBoolean(DatabaseElements.LINK_ADMIN.toString())) {
          admins.add(newUser);
        } else {
          this.users.add(newUser);
        }
      }

      ResultSet bundles = DatabaseRequest.getInstance().find(DatabaseElements.LINK_BUNDLE_TABLE,
          DatabaseElements.LINK_BUNDLE_GROUP, Integer.toString(id));

      if (bundles == null) {
        throw new DatabaseException(
            "Une erreur est survenue dans la base de données, les bundles du groupe n'ont pas"
                + " pu être récupérés.");
      }

      while (bundles.next()) {
        BundleOrbisserver newBundle = new BundleOrbisserver(bundles.getInt(
            DatabaseElements.LINK_BUNDLE.toString()));

        this.bundles.add(newBundle);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DatabaseException(
          "Une erreur est survenue lors du traitement des résultats de la requête.");
    } catch (NoDataSourceException e) {
      throw new DatabaseException(
          "La source des données n'a pas été définie, aucune requête ne peut être effectuée.");
    }
  }

  /**
   * This constructor create the group in the database.
   * 
   * @param id
   *          the ID of the new group
   * @param groupName
   *          the name of the new group
   * @param admin
   *          the user that will be administrator of the group at the creation
   * @param persistence
   *          the persistence of the data of the new group
   * @param currentUser
   *          the user currently connected
   * 
   * @throws NoRightException
   *           thrown when the user is not a super administrator
   * @throws DatabaseException
   *           thrown when an error occur with the database
   * 
   * @see Group#id
   * @see Group#groupName
   * @see Group#persistence
   */
  public Group(int id, String groupName, User admin, boolean persistence, User currentUser)
      throws NoRightException, DatabaseException {
    if (currentUser.getSuperAdmin()) {
      admins.add(admin);
      this.id = id;
      this.groupName = groupName;
      this.persistence = persistence;

      HashMap<DatabaseElements, String> parameters = new HashMap<DatabaseElements, String>(3);
      parameters.put(DatabaseElements.ID_GROUP, Integer.toString(id));
      parameters.put(DatabaseElements.GROUP_NAME, groupName);
      parameters.put(DatabaseElements.GROUP_PERSISTENCE, Boolean.toString(persistence));

      try {
        if (DatabaseRequest.getInstance().insert(DatabaseElements.GROUP_TABLE, parameters)) {
          parameters = new HashMap<DatabaseElements, String>(3);
          parameters.put(DatabaseElements.LINK_USER_GROUP, Integer.toString(id));
          parameters.put(DatabaseElements.LINK_USER, Integer.toString(admin.getId()));
          parameters.put(DatabaseElements.LINK_ADMIN, "true");

          if (!DatabaseRequest.getInstance().insert(DatabaseElements.LINK_USER_TABLE, parameters)) {
            parameters = new HashMap<DatabaseElements, String>(1);
            parameters.put(DatabaseElements.ID_GROUP, Integer.toString(id));
            if (DatabaseRequest.getInstance().remove(DatabaseElements.GROUP_TABLE, parameters)) {
              throw new DatabaseException(
                  "Une erreur est survenue dans la base de données, le groupe n'a pas été créé.");
            } else {
              throw new DatabaseException(
                  "Une erreur est survenue dans la base de données, le groupe a été créé,"
                      + " mais pas le lien entre le groupe et l'administrateur, créant un"
                      + " état instable. Il est conseillé de restaurer manuellement"
                      + " l'intégrité de la base de données.");
            }
          }
        } else {
          throw new DatabaseException(
              "Une erreur est survenue dans la base de données, le groupe n'a pas été créé.");
        }
      } catch (NoDataSourceException e) {
        throw new DatabaseException(
            "La source des données n'a pas été définie, aucune requête ne peut être effectuée.");
      }
    } else {
      throw new NoRightException("Vous n'avez pas les droits pour créer un groupe.");
    }
  }

  /**
   * Return the ID of the group in the database.
   * 
   * @return the ID of the group
   * 
   * @see Group#id
   */
  public int getId() {
    return id;
  }

  /**
   * Return the name of the group.
   * 
   * @return the name of the group
   * 
   * @see Group#groupName
   */
  public String getGroupName() {
    return groupName;
  }

  /**
   * Allows to change the name of the group.
   * 
   * @param groupName
   *          the new name of the group
   * @param currentUser
   *          the user currently connected
   * 
   * @throws NoRightException
   *           thrown when the user is not a super administrator
   * @throws DatabaseException
   *           thrown when an error occur with the database
   * 
   * @see Group#groupName
   */
  public void setGroupName(String groupName, User currentUser) throws NoRightException,
      DatabaseException {
    if (currentUser.getSuperAdmin()) {
      HashMap<DatabaseElements, String> set = new HashMap<DatabaseElements, String>(1);
      set.put(DatabaseElements.GROUP_NAME, groupName);

      HashMap<DatabaseElements, String> parameters = new HashMap<DatabaseElements, String>(1);
      parameters.put(DatabaseElements.ID_GROUP, Integer.toString(id));

      try {
        if (DatabaseRequest.getInstance().update(DatabaseElements.GROUP_TABLE, set, parameters)) {
          this.groupName = groupName;
        } else {
          throw new DatabaseException(
              "Une erreur est survenue avec la base de données, le nom du groupe n'a pas"
                  + " été changé.");
        }
      } catch (NoDataSourceException e) {
        throw new DatabaseException(
            "La source des données n'a pas été définie, aucune requête ne peut être effectuée.");
      }
    } else {
      throw new NoRightException("Vous n'avez pas les droits pour modifier le nom d'un groupe.");
    }
  }

  /**
   * Return the persistence of the data of the group.
   * 
   * @return the persistence of the data of the group
   * 
   * @see Group#persistence
   */
  public boolean isPersistent() {
    return persistence;
  }

  /**
   * Allows to change the persistence of the data of the group.
   * 
   * @param persistence
   *          the new persistence of the data of the group
   * @param currentUser
   *          the user currently connected
   * 
   * @throws NoRightException
   *           thrown when the user is not a super administrator
   * @throws DatabaseException
   *           thrown when an error occur with the database
   * 
   * @see Group#persistence
   */
  public void setPersistence(boolean persistence, User currentUser) throws NoRightException,
      DatabaseException {
    if (currentUser.getSuperAdmin()) {
      HashMap<DatabaseElements, String> set = new HashMap<DatabaseElements, String>(1);
      set.put(DatabaseElements.GROUP_PERSISTENCE, Boolean.toString(persistence));

      HashMap<DatabaseElements, String> parameters = new HashMap<DatabaseElements, String>(1);
      parameters.put(DatabaseElements.ID_GROUP, Integer.toString(id));

      try {
        if (DatabaseRequest.getInstance().update(DatabaseElements.GROUP_TABLE, set, parameters)) {
          this.persistence = persistence;
        } else {
          throw new DatabaseException(
              "Une erreur est survenue avec la base de données, la persistence des données"
                  + " n'a pas été changée.");
        }
      } catch (NoDataSourceException e) {
        throw new DatabaseException(
            "La source des données n'a pas été définie, aucune requête ne peut être effectuée.");
      }
    } else {
      throw new NoRightException(
          "Vous n'avez pas les droits pour modifier la persistence des données d'un groupe.");
    }
  }

  /**
   * Return the users of the group, without the administrators.
   * 
   * @return the users of the group
   * 
   * @see Group#users
   */
  public ArrayList<User> getUsers() {
    return users;
  }

  /**
   * Return the administrators of the group.
   * 
   * @return the administrators of the group
   * 
   * @see Group#admins
   */
  public ArrayList<User> getAdmins() {
    return admins;
  }

  /**
   * Return the bundles of the group.
   * 
   * @return the bundles of the group
   * 
   * @see Group#bundles
   */
  public ArrayList<BundleOrbisserver> getBundles() {
    return bundles;
  }

  /**
   * Allows to add an user to the group.
   * <p>
   * If the user is already an administrator of the group, then it will become a
   * simple user, unless there is no other administrator.
   * </p>
   * 
   * @param user
   *          the user to add to the group
   * @param currentUser
   *          the user currently connected
   * 
   * @throws NoRightException
   *           thrown when the user is not an administrator (or a super
   *           administrator if it strips an administrator from its privileges)
   * @throws DatabaseException
   *           thrown when an error occur with the database
   * @throws AdminException
   *           thrown when the user is the last administrator of the group
   * 
   * @see Group#users
   */
  public void addUser(User user, User currentUser) throws NoRightException, DatabaseException,
      AdminException {
    try {
      if (!users.contains(user)) {
        if (admins.contains(user)) {
          if (currentUser.getSuperAdmin()) {
            if (admins.size() > 1) {
              HashMap<DatabaseElements, String> set = new HashMap<DatabaseElements, String>(1);
              set.put(DatabaseElements.LINK_ADMIN, "false");

              HashMap<DatabaseElements, String> parameters = new HashMap<DatabaseElements, String>(
                  2);
              parameters.put(DatabaseElements.LINK_USER_GROUP, Integer.toString(id));
              parameters.put(DatabaseElements.LINK_USER, Integer.toString(user.getId()));

              if (DatabaseRequest.getInstance().update(DatabaseElements.LINK_USER_TABLE, set,
                  parameters)) {
                admins.remove(user);
                users.add(user);
              } else {
                throw new DatabaseException(
                    "Une erreur est survenue dans la base de donnée, l'utilisateur est"
                        + " toujours administrateur du groupe.");
              }
            } else {
              throw new AdminException(
                  "L'utilisateur est le dernier administrateur du groupe, il ne peut être"
                      + " rétrogradé.");
            }
          } else {
            throw new NoRightException(
                "Vous n'avez pas les droits pour rétrograder l'administrateur d'un groupe.");
          }
        } else {
          if (admins.contains(currentUser)) {
            HashMap<DatabaseElements, String> parameters = new HashMap<DatabaseElements, String>(3);
            parameters.put(DatabaseElements.LINK_ADMIN, "false");
            parameters.put(DatabaseElements.LINK_USER_GROUP, Integer.toString(id));
            parameters.put(DatabaseElements.LINK_USER, Integer.toString(user.getId()));

            if (DatabaseRequest.getInstance().insert(DatabaseElements.LINK_USER_TABLE,
                parameters)) {
              users.add(user);
            } else {
              throw new DatabaseException(
                  "Une erreur est survenue dans la base de données, l'utilisateur n'a pas"
                      + " été ajouté au groupe.");
            }
          } else {
            throw new NoRightException(
                "Vous n'avez pas les droits par ajouter un utilisateur au groupe.");
          }
        }
      }
    } catch (NoDataSourceException e) {
      throw new DatabaseException(
          "La source des données n'a pas été définie, aucune requête ne peut être effectuée.");
    }
  }

  /**
   * Allows to add an administrator to the group.
   * 
   * @param admin
   *          the user to add as an administrator
   * @param currentUser
   *          the user currently connected
   * 
   * @throws NoRightException
   *           thrown when the user is not a super administrator
   * @throws DatabaseException
   *           thrown when an error occur with the database
   * 
   * @see Group#admins
   */
  public void addAdmin(User admin, User currentUser) throws NoRightException, DatabaseException {
    try {
      if (currentUser.getSuperAdmin()) {
        if (!admins.contains(admin)) {
          if (users.contains(admin)) {
            HashMap<DatabaseElements, String> set = new HashMap<DatabaseElements, String>(1);
            set.put(DatabaseElements.LINK_ADMIN, "true");

            HashMap<DatabaseElements, String> parameters = new HashMap<DatabaseElements, String>(2);
            parameters.put(DatabaseElements.LINK_USER_GROUP, Integer.toString(id));
            parameters.put(DatabaseElements.LINK_USER, Integer.toString(admin.getId()));

            if (DatabaseRequest.getInstance().update(DatabaseElements.LINK_USER_TABLE, set,
                parameters)) {
              users.remove(admin);
              admins.add(admin);
            } else {
              throw new DatabaseException(
                  "Une erreur est survenue dans la base de données, l'utilisateur n'est pas"
                      + " devenu administrateur.");
            }
          } else {
            HashMap<DatabaseElements, String> parameters = new HashMap<DatabaseElements, String>(3);
            parameters.put(DatabaseElements.LINK_ADMIN, "true");
            parameters.put(DatabaseElements.LINK_USER_GROUP, Integer.toString(id));
            parameters.put(DatabaseElements.LINK_USER, Integer.toString(admin.getId()));

            if (DatabaseRequest.getInstance().insert(DatabaseElements.LINK_USER_TABLE,
                parameters)) {
              admins.add(admin);
            } else {
              throw new DatabaseException(
                  "Une erreur est survenue dans la base de données, l'utilisateur n'a pas"
                      + " été ajouté au groupe.");
            }
          }
        }
      } else {
        throw new NoRightException(
            "Vous n'avez pas les droits pour ajouter un administrateur au groupe.");
      }
    } catch (NoDataSourceException e) {
      throw new DatabaseException(
          "La source des données n'a pas été définie, aucune requête ne peut être effectuée.");
    }
  }

  /**
   * Allows to add a bundle to the group.
   * 
   * @param bundle
   *          the bundle to add
   * @param currentUser
   *          the user currently connected
   * 
   * @throws NoRightException
   *           thrown when the user is not an administrator
   * @throws DatabaseException
   *           thrown when an error occur with the database
   * 
   * @see Group#bundles
   */
  public void addBundle(BundleOrbisserver bundle, User currentUser) throws NoRightException,
      DatabaseException {
    try {
      if (!bundles.contains(bundle)) {
        if (admins.contains(currentUser)) {
          HashMap<DatabaseElements, String> parameters = new HashMap<DatabaseElements, String>(2);
          parameters.put(DatabaseElements.LINK_BUNDLE_GROUP, Integer.toString(id));
          parameters.put(DatabaseElements.LINK_BUNDLE, Integer.toString(bundle.getId()));

          if (DatabaseRequest.getInstance().insert(DatabaseElements.LINK_BUNDLE_TABLE,
              parameters)) {
            bundles.add(bundle);
          } else {
            throw new DatabaseException(
                "Une erreur est survenue dans la base de données, le bundle n'a pas été ajouté au"
                    + " groupe.");
          }
        }
      } else {
        throw new NoRightException("Vous n'avez pas les droits pour ajouter un bundle au groupe.");
      }
    } catch (NoDataSourceException e) {
      throw new DatabaseException(
          "La source des données n'a pas été définie, aucune requête ne peut être effectuée.");
    }
  }

  /**
   * Allows to remove an user of the group.
   * <p>
   * If the user is not in the group, it will not change anything.
   * </p>
   * 
   * @param user
   *          the user to remove
   * @param currentUser
   *          the user currently connected
   * 
   * @throws NoRightException
   *           thrown when the user is not an administrator
   * @throws DatabaseException
   *           thrown when an error occur with the database
   * 
   * @see Group#users
   */
  public void removeUser(User user, User currentUser) throws NoRightException, DatabaseException {
    if (users.contains(user)) {
      if (admins.contains((currentUser))) {
        try {
          HashMap<DatabaseElements, String> parameters = new HashMap<DatabaseElements, String>(2);

          parameters.put(DatabaseElements.LINK_USER_GROUP, Integer.toString(id));
          parameters.put(DatabaseElements.LINK_USER, Integer.toString(user.getId()));

          if (DatabaseRequest.getInstance().remove(DatabaseElements.LINK_USER_TABLE, parameters)) {
            users.remove(user);
          } else {
            throw new DatabaseException(
                "Une erreur est survenue dans la base de données, l'utilisateur n'a pas été"
                    + " supprimé.");
          }
        } catch (NoDataSourceException e) {
          throw new DatabaseException(
              "La source des données n'a pas été définie, aucune requête ne peut être effectuée.");
        }
      } else {
        throw new NoRightException(
            "Vous n'avez pas les droits pour supprimer un utilisateur du groupe.");
      }
    }
  }

  /**
   * Allows to remove an administrator of the group.
   * <p>
   * If the administrator is not in the group, it will not change anything.
   * </p>
   * 
   * @param admin
   *          the administrator to remove
   * @param currentUser
   *          the user currently connected
   * 
   * @throws NoRightException
   *           thrown when the user is not a super administrator
   * @throws AdminException
   *           thrown when the administrator is the last in the group
   * @throws DatabaseException
   *           thrown when an error occur with the database
   * 
   * @see Group#admins
   */
  public void removeAdmin(User admin, User currentUser) throws NoRightException, AdminException,
      DatabaseException {
    if (currentUser.getSuperAdmin()) {
      if (admins.size() > 1) {
        try {
          HashMap<DatabaseElements, String> parameters = new HashMap<DatabaseElements, String>(2);

          parameters.put(DatabaseElements.LINK_USER_GROUP, Integer.toString(id));
          parameters.put(DatabaseElements.LINK_USER, Integer.toString(admin.getId()));

          if (DatabaseRequest.getInstance().remove(DatabaseElements.LINK_USER_TABLE, parameters)) {
            admins.remove(admin);
          } else {
            throw new DatabaseException(
                "Une erreur est survenue dans la base de données, l'administrateur n'a pas été"
                    + " supprimé.");
          }
        } catch (NoDataSourceException e) {
          throw new DatabaseException(
              "La source des données n'a pas été définie, aucune requête ne peut être effectuée.");
        }
      } else {
        throw new AdminException(
            "Vous ne pouvez pas partir du groupe, vous êtes le seul administrateur.");
      }
    } else {
      throw new NoRightException(
          "Vous n'avez pas les droits pour supprimer un administrateur du groupe.");
    }
  }

  /**
   * Allows an user to leave the group.
   * 
   * @param user
   *          the user that wants to leave, is also the user currently connected
   * 
   * @throws AdminException
   *           thrown when the user is the last administrator of the group
   * @throws DatabaseException
   *           thrown when an error occur with the database
   * 
   * @see Group#users
   * @see Group#admins
   */
  public void leaveGroup(User user) throws AdminException, DatabaseException {
    if (admins.contains(user)) {
      if (admins.size() > 1) {
        try {
          HashMap<DatabaseElements, String> parameters = new HashMap<DatabaseElements, String>(2);

          parameters.put(DatabaseElements.LINK_USER_GROUP, Integer.toString(id));
          parameters.put(DatabaseElements.LINK_USER, Integer.toString(user.getId()));

          if (DatabaseRequest.getInstance().remove(DatabaseElements.LINK_USER_TABLE, parameters)) {
            admins.remove(user);
          } else {
            throw new DatabaseException(
                "Une erreur est survenue dans la base de données, vous n'avez pas été retiré du"
                    + " groupe.");
          }
        } catch (NoDataSourceException e) {
          throw new DatabaseException(
              "La source des données n'a pas été définie, aucune requête ne peut être effectuée.");
        }
      } else {
        throw new AdminException(
            "Vous ne pouvez pas partir du groupe, vous êtes le seul administrateur.");
      }
    } else {
      try {
        HashMap<DatabaseElements, String> parameters = new HashMap<DatabaseElements, String>(2);

        parameters.put(DatabaseElements.LINK_USER_GROUP, Integer.toString(id));
        parameters.put(DatabaseElements.LINK_USER, Integer.toString(user.getId()));

        if (DatabaseRequest.getInstance().remove(DatabaseElements.LINK_USER_TABLE, parameters)) {
          users.remove(user);
        } else {
          throw new DatabaseException(
              "Une erreur est survenue dans la base de données, vous n'avez pas été retiré du"
                  + " groupe.");
        }
      } catch (NoDataSourceException e) {
        throw new DatabaseException(
            "La source des données n'a pas été définie, aucune requête ne peut être effectuée.");
      }
    }
  }

  /**
   * Allows to remove a bundle from the group.
   * <p>
   * If the bundle is not in the group, it will not change anything.
   * </p>
   * 
   * @param bundle
   *          the bundle to remove
   * @param currentUser
   *          the user currently connected
   * 
   * @throws NoRightException
   *           thrown when the user is not an administrator
   * @throws DatabaseException
   *           thrown when an error occur with the database
   * 
   * @see Group#bundles
   */
  public void removeBundle(BundleOrbisserver bundle, User currentUser) throws NoRightException,
      DatabaseException {
    if (admins.contains(currentUser)) {
      try {
        HashMap<DatabaseElements, String> parameters = new HashMap<DatabaseElements, String>(2);

        parameters.put(DatabaseElements.LINK_BUNDLE_GROUP, Integer.toString(id));
        parameters.put(DatabaseElements.LINK_BUNDLE, Integer.toString(bundle.getId()));

        if (DatabaseRequest.getInstance().remove(DatabaseElements.LINK_BUNDLE_TABLE, parameters)) {
          bundles.remove(bundle);
        } else {
          throw new DatabaseException(
              "Une erreur est survenue dans la base de données, le bundle n'a pas été"
                  + " supprimé.");
        }
      } catch (NoDataSourceException e) {
        throw new DatabaseException(
            "La source des données n'a pas été définie, aucune requête ne peut être effectuée.");
      }
    } else {
      throw new NoRightException("Vous n'avez pas les droits pour supprimer un bundle du groupe.");
    }
  }

  /**
   * Allows to get all the groups currently in the database.
   * 
   * @return all the groups in the database
   * 
   * @throws DatabaseException
   *           DatabaseException thrown when an error occur with the database
   */
  public static ArrayList<Group> getListGroups() throws DatabaseException {
    ResultSet result = null;
    try {
      result = DatabaseRequest.getInstance().find(DatabaseElements.GROUP_TABLE, null, null,
          DatabaseElements.ID_GROUP);
    } catch (NoDataSourceException e) {
      throw new DatabaseException(
          "La source des données n'a pas été définie, aucune requête ne peut être effectuée.");
    }
    ArrayList<Group> groups = new ArrayList<Group>();

    try {
      while (result.next()) {
        groups.add(new Group(result.getInt(DatabaseElements.ID_GROUP.toString())));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DatabaseException(
          "Une erreur est survenue lors du traitement des résultats de la requête.");
    }

    return groups;
  }

  /**
   * Allows to get all the groups in which a user is.
   * 
   * @param user
   *          the user whose groups you want to get
   * 
   * @return all the groups in which a user is
   * 
   * @throws DatabaseException
   *           DatabaseException thrown when an error occur with the database
   */
  public static ArrayList<Group> getGroupsUser(User user) throws DatabaseException {
    ResultSet result = null;
    try {
      result = DatabaseRequest.getInstance().find(DatabaseElements.LINK_USER_TABLE,
          DatabaseElements.LINK_USER, Integer.toString(user.getId()),
          DatabaseElements.LINK_USER_GROUP);
    } catch (NoDataSourceException e) {
      throw new DatabaseException(
          "La source des données n'a pas été définie, aucune requête ne peut être effectuée.");
    }
    ArrayList<Group> groups = new ArrayList<Group>();

    try {
      while (result.next()) {
        groups.add(new Group(result.getInt(DatabaseElements.ID_GROUP.toString())));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DatabaseException(
          "Une erreur est survenue lors du traitement des résultats de la requête.");
    }

    return groups;
  }
}