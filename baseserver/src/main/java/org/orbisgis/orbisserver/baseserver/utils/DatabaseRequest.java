package org.orbisgis.orbisserver.baseserver.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.sql.PreparedStatement;
import javax.sql.DataSource;

public class DatabaseRequest {

    //connection
    private static DatabaseRequest dao;

    private DataSource ds;

    public final static DatabaseRequest getInstance() {

        if (DatabaseRequest.dao == null) {
            DatabaseRequest.dao = new DatabaseRequest();
        }
        return DatabaseRequest.dao;
    }

    public final ResultSet find(String table, String idName, String idValue, HashMap<String, String> parameters) {

        ResultSet result = null;
        String params = "";

        try {
            if (parameters == null) {
                PreparedStatement ps = ds.getConnection().prepareStatement("SELECT * FROM " + table + " WHERE ? = ?;");
                ps.setString(1, idName);
                ps.setString(2, idValue);
                result = ps.executeQuery();
                System.out.println(result);
            } else {
                for (String k : parameters.keySet()) {
                    params += k + ", ";
                }

                Statement select = ds.getConnection().createStatement();
                result = select.executeQuery("select " + params + " from " + table + " where ? = ?");
            }

        } catch (SQLException sqle) {
            System.out.println("Erreur : " + sqle.getMessage());
        }
        return result;
    }


    public final void insert(String table, String[] parameters) {
        try {
            java.sql.Statement select = ds.getConnection().createStatement();
            select.executeUpdate(" ");
        } catch (SQLException sqle) {
            System.out.println("Erreur : " + sqle.getMessage());
        }
    }


    public void remove(String table, String idName, String idValue) {
        try {
            java.sql.Statement select = ds.getConnection().createStatement();
            select.executeUpdate("delete from " + table + " where id_name = " + idName + " and id_value = " + idValue);
        } catch (SQLException sqle) {
            System.out.println("Erreur : " + sqle.getMessage());
        }
    }


    public void update(String table, HashMap<Integer, Integer> parameters) {
        try {
            java.sql.Statement select = ds.getConnection().createStatement();
            select.executeUpdate(" ");
        } catch (SQLException sqle) {
            System.out.println("Erreur : " + sqle.getMessage());
        }
    }

    public void setDataSource(DataSource ds) {
        this.ds = ds;
    }
}