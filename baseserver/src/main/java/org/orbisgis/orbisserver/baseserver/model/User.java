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
 * Laboratoire Lab-STICC 窶� CNRS UMR 6285
 * Equipe DECIDE
 * UNIVERSITﾃ� DE BRETAGNE-SUD
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

import java.util.Map;

/**
 * User registered in the server
 *
 * @author Sylvain PALOMINOS
 */
public class User {

  private int id;
  private String username;
  private String password;
  private Map<Integer, Boolean> listGroups;
  private Boolean superAdmin;

  /**
   * @param id
   * @param username
   * @param password
   * @param listGroups
   * @param superAdmin
   */
  public User(Integer id, String username, String password, Map<Integer, Boolean> listGroups,
      Boolean superAdmin) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.listGroups = listGroups;
    this.superAdmin = superAdmin;
  }

  public User(int id) {
    this.id = id;
  }

  /**
   * @return
   */
  public String getUsername() {
    return username;
  }

  /**
   * @param username
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * @return
   */
  public String getPassword() {
    return password;
  }

  /**
   * @param password
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * @return
   */
  public Map<Integer, Boolean> getListGroups() {
    return listGroups;
  }

  /**
   * @param listGroups
   */
  public void setListGroups(Map<Integer, Boolean> listGroups) {
    this.listGroups = listGroups;
  }

  /**
   * @return
   */
  public Boolean getSuperAdmin() {
    return superAdmin;
  }

  /**
   * @param superAdmin
   */
  public void setSuperAdmin(Boolean superAdmin) {
    this.superAdmin = superAdmin;
  }

  /**
   * @return
   */
  public Integer getId() {
    return id;
  }

  /**
   * @param id
   */
  public void setId(Integer id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object user) {
    if (!(user instanceof User)) {
      return false;
    }

    if (((User) user).id == id) {
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
