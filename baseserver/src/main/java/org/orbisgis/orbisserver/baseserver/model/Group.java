package org.orbisgis.orbisserver.baseserver.model;

import org.osgi.framework.Bundle;
import org.wisdom.api.DefaultController;

import java.util.ArrayList;

public class Group extends DefaultController {
  private int id;
  private String groupName;
  private boolean persistence;
  private ArrayList<User> admins;
  private ArrayList<User> users;
  private ArrayList<BundleOrbisServer> bundles;


  public Group(int id, String groupName, boolean persistence){
    this.adminGroup = admins;
    this.id = id;
    this.usersList = users;
    this.groupName = groupName;
    this.persistence = persistence;
    this.bundlesList = bundles;
  }

  public int getId() {
    return id;
  }

  private void setId(int id) throws NoRightException {
    // ajouter le contrôle de super admin via la session
    if(isSuperAdmin()) {
      //BDD -> changer l'id
      this.id = id;
    } else {
      throw new NoRightException("Vous n'avez pas les droits.");
    }
  }

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) throws NoRightException {
    // ajouter le contrôle de super admin via la session
    if(isSuperAdmin()) {
      //BDD -> changer le nom du groupe
      this.nomGroup = nomGroup;
    } else {
      throw new NoRightException("Vous n'avez pas les droits.");
    }
  }

  public boolean isPersistent() {
    return persistence;
  }

  public void setPersistence(boolean persistence) throws NoRightException {
    // ajouter le contrôle de super admin via la session
    if(isSuperAdmin()) {
      //BDD -> changer la persitance
      this.persistence = persistence;
    } else {
      throw new NoRightException("Vous n'avez pas les droits.");
    }
  }

  public ArrayList<User> getUsers() {
    return users;
  }

  public void setUsers(ArrayList<User> users) {
    this.users = users;
  }

  public ArrayList<User> getAdmins() {
    return admins;
  }

  public ArrayList<BundleOrbisServer> getBundles() {
    return bundles;
  }

  public void setBundles(ArrayList<BundleOrbisServer>> bundles) {
    this.bundles = bundles;
  }

  public void setAdmins(ArrayList<User> admins) {
    this.admins = admins;
  }

  public void addUser(User user) throws NoRightException {
    // ajouter le contrôle de admin via la session
    if(isAdmin()) {
      //ajout à la BDD
      this.getUsers().add(user);
    } else {
      throw new noRightException("Vous n'avez pas les droits.");
    }
  }

  public void addAdmin(User admin) throws NoRightException {
    // ajouter le contrôle de super admin via la session
    if(isSuperAdmin()) {
      //ajout à la bdd
      this.getAdmins().add(admin);
    } else {
      throw new NoRightException("Vous n'avez pas les droits.");
    }
  }

  public void addBundle(BundleOrbisServer bundle) throws NoRightException {
    // ajouter le contrôle de admin via la session
    if(isAdmin()) {
      //ajout à la BDD
      this.getBundles().add(bundle);
    } else {
      throw new NoRightException("Vous n'avez pas les droits.");
    }
  }

  public void removeUser(User user) throws NoRightException {
    // ajouter le contrôle de admin via la session
    if(isAdmin()) {
      //BDD
      this.getUsers().remove(user);
    } else {
      throw new NoRightException("Vous n'avez pas les droits.");
    }
  }

  public void removeAdmin(User admin) throws NoRightException {
    // ajouter le contrôle de admin via la session
    if(isSuperAdmin()) {
      //BDD
      this.getAdmins().remove(admin);
    } else {
      throw new NoRightException("Vous n'avez pas les droits.");
    }
  }

  public void removeBundle(BundleOrbisServer bundle) throws NoRightException {
    // ajouter le contrôle de admin via la session
    if(isAdmin()) {
      //BDD
      this.getBundles().remove(bundle);
    } else {
      throw new NoRightException("Vous n'avez pas les droits.");
    }
  }

  public void leaveGroupe(User user) throws AdminException {
    if(user.isAdmin()) {
      if(getAdmins().size() > 1) {
        this.getAdmins().remove(user);
      } else {
        throw new AdminException("Vous ne pouvez pas partir du groupe, vous êtes le seul admin.");
      }
    } else {
        this.getUsers().remove(user);
    }
  }
}