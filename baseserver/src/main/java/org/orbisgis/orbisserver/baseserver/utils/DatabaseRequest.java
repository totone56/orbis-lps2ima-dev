package org.orbisgis.orbisserver.baseserver.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.orbisgis.orbisserver.baseserver.exception.NoDataSourceException;

/**
 * <b>This class help the others to quickly make database requests.</b>
 * <p>
 * The different methods will work only after the data source has been defined.
 * Every attempt to use them before will result in the throw of an exception.
 * </p>
 * 
 * @author LP S2IMa - 2017/2018
 * 
 * @version 1.0
 * 
 * @see DatabaseElements
 */
public final class DatabaseRequest {
  /**
   * Contains the unique instance of the class.
   * 
   * @see DatabaseRequest#getInstance()
   */
  private static volatile DatabaseRequest instance;
  /**
   * Corresponds to the source of the data, used to alter the database.
   * 
   * @see DatabaseRequest#setDataSource(DataSource)
   */
  private DataSource source;

  /**
   * The constructor of the class.
   * <p>
   * This class is a singleton, so the constructor is set to private. It
   * initializes the source to null, so it requires to set the data source.
   * </p>
   * 
   * @see DatabaseRequest#source
   */
  private DatabaseRequest() {
    source = null;
  }

  /**
   * Used to get the instance of the class.
   * 
   * @return the unique instance
   * 
   * @see DatabaseRequest#instance
   */
  public static DatabaseRequest getInstance() {
    if (instance == null) {
      instance = new DatabaseRequest();
    }
    return instance;
  }

  /**
   * Allows to make a SELECT request to the database.
   * <p>
   * If you want to get information for each element in the result, you can ignore
   * results. And if you want to get every element in the table, you can put
   * idName and idValue to null.
   * </p>
   * 
   * @param table
   *          the name of the table where you want to search
   * @param idName
   *          the name of the column to use to find the result
   * @param idValue
   *          the value to put in the column idName
   * @param results
   *          the names of the columns you want in the result
   * 
   * @return the result of the request
   * 
   * @throws NoDataSourceException
   *           when the data source has not been defined beforehand
   */
  public ResultSet find(DatabaseElements table, DatabaseElements idName, String idValue,
      DatabaseElements... results) throws NoDataSourceException {
    if (source == null) {
      throw new NoDataSourceException(
          "Aucune source n'a été définie, il est impossible de faire la requête.");
    }

    Connection co = null;
    PreparedStatement insert = null;
    ResultSet result = null;

    try {
      String params = null;
      if (results.length > 0) {
        StringBuilder paramsBuilder = new StringBuilder();

        for (int i = 0; i < results.length; i++) {
          paramsBuilder.append(results[i]);
          paramsBuilder.append(", ");
        }

        params = paramsBuilder.substring(0, paramsBuilder.length() - 2);
      } else {
        params = "*";
      }

      String statement = "SELECT " + params + " FROM " + table;

      if (idName != null && idValue != null) {
        statement += " WHERE " + idName + " = ?;";
      } else {
        statement += ";";
      }

      co = source.getConnection();
      insert = co.prepareStatement(statement);

      insert.setString(1, idValue);

      result = insert.executeQuery();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (co != null) {
        try {
          co.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }

      if (insert != null) {
        try {
          insert.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }

    return result;
  }

  /**
   * Allows to make an INSERT request to the database.
   * 
   * @param table
   *          the name of the table where you want to insert
   * @param parameters
   *          the elements to insert
   * 
   * @return true if the request worked as intended, false if an error occurred
   * 
   * @throws NoDataSourceException
   *           when the data source has not been defined beforehand
   */
  public boolean insert(DatabaseElements table, HashMap<DatabaseElements, String> parameters)
      throws NoDataSourceException {
    if (source == null) {
      throw new NoDataSourceException(
          "Aucune source n'a été définie, il est impossible de faire la requête.");
    }

    Connection co = null;
    PreparedStatement insert = null;
    boolean ret = false;

    try {
      String names = "";
      StringBuilder dataBuilder = new StringBuilder();
      String[] elements = new String[parameters.size()];

      int i = 0;
      Iterator<Entry<DatabaseElements, String>> it = parameters.entrySet().iterator();
      while (it.hasNext()) {
        Entry<DatabaseElements, String> element = it.next();

        names += element.getKey() + ", ";
        dataBuilder.append("?, ");

        elements[i++] = element.getValue();
      }

      names = names.substring(0, names.length() - 2);
      String data = dataBuilder.substring(0, dataBuilder.length() - 2);
      String statement = "INSERT INTO " + table + "(" + names + ") VALUES (" + data + ");";

      co = source.getConnection();
      insert = co.prepareStatement(statement);

      for (i = 1; i <= elements.length; i++) {
        insert.setString(i++, elements[i - 1]);
      }

      insert.executeUpdate();

      ret = true;
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (co != null) {
        try {
          co.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }

      if (insert != null) {
        try {
          insert.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }

    return ret;
  }

  /**
   * Allows to make a DELETE request to the database.
   * 
   * @param table
   *          the name of the table where you want to delete
   * @param parameters
   *          the elements allowing to determine the row(s) to delete
   * 
   * @return true if the request worked as intended, false if an error occurred
   * 
   * @throws NoDataSourceException
   *           when the data source has not been defined beforehand
   */
  public boolean remove(DatabaseElements table, HashMap<DatabaseElements, String> parameters)
      throws NoDataSourceException {
    if (source == null) {
      throw new NoDataSourceException(
          "Aucune source n'a été définie, il est impossible de faire la requête.");
    }

    Connection co = null;
    PreparedStatement delete = null;
    boolean ret = false;

    try {
      StringBuilder dataBuilder = new StringBuilder();
      String[] elements = new String[parameters.size()];

      int i = 0;
      Iterator<Entry<DatabaseElements, String>> it = parameters.entrySet().iterator();
      while (it.hasNext()) {
        Entry<DatabaseElements, String> element = it.next();

        dataBuilder.append(element.getKey() + " = ? AND ");

        elements[i++] = element.getValue();
      }

      String data = dataBuilder.substring(0, dataBuilder.length() - 5);
      String statement = "DELETE FROM " + table + " WHERE " + data + ";";

      co = source.getConnection();
      delete = co.prepareStatement(statement);

      for (i = 1; i <= elements.length; i++) {
        delete.setString(i++, elements[i - 1]);
      }

      delete.executeUpdate();

      ret = true;
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (co != null) {
        try {
          co.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }

      if (delete != null) {
        try {
          delete.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }

    return ret;
  }

  /**
   * Allows to make an UPDATE request to the database.
   * 
   * @param table
   *          the name of the table where you want to update
   * @param set
   *          the elements to change in the selected row(s)
   * @param parameters
   *          the elements allowing to determine the row(s) to update
   * 
   * @return true if the request worked as intended, false if an error occurred
   * 
   * @throws NoDataSourceException
   *           when the data source has not been defined beforehand
   */
  public boolean update(DatabaseElements table, HashMap<DatabaseElements, String> set,
      HashMap<DatabaseElements, String> parameters) throws NoDataSourceException {
    if (source == null) {
      throw new NoDataSourceException(
          "Aucune source n'a été définie, il est impossible de faire la requête.");
    }

    Connection co = null;
    PreparedStatement update = null;
    boolean ret = false;

    try {
      StringBuilder stringBuilder = new StringBuilder();
      String[] elements = new String[set.size() + parameters.size()];

      int i = 0;
      Iterator<Entry<DatabaseElements, String>> it = set.entrySet().iterator();
      while (it.hasNext()) {
        Entry<DatabaseElements, String> element = it.next();

        stringBuilder.append(element.getKey() + " = ?, ");

        elements[i++] = element.getValue();
      }

      String change = stringBuilder.substring(0, stringBuilder.length() - 2);

      it = parameters.entrySet().iterator();
      while (it.hasNext()) {
        Entry<DatabaseElements, String> element = it.next();

        stringBuilder.append(element.getKey() + " = ? AND ");

        elements[i++] = element.getValue();
      }

      String data = stringBuilder.substring(0, stringBuilder.length() - 5);

      String statement = "UPDATE " + table + " SET " + change + " WHERE " + data + ";";

      co = source.getConnection();
      update = co.prepareStatement(statement);

      for (i = 1; i <= elements.length; i++) {
        update.setString(i++, elements[i - 1]);
      }

      update.executeUpdate();

      ret = true;
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (co != null) {
        try {
          co.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }

      if (update != null) {
        try {
          update.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }

    return ret;
  }

  /**
   * Used to define the data source.
   * 
   * @param source
   *          the data source to use
   * 
   * @see DatabaseRequest#source
   */
  public void setDataSource(DataSource source) {
    this.source = source;
  }
}