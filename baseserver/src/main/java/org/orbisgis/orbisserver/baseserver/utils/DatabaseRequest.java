package org.orbisgis.orbisserver.baseserver.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.mysql.jdbc.*;
import java.sql.PreparedStatement;
import javax.sql.DataSource;
import jdk.nashorn.internal.ir.Statement;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.sqlite.JDBC;

public class DatabaseRequest{

	  //connection
	  private static DatabaseRequest dao;
          
          private DataSource ds;

	  public final static DatabaseRequest getInstance() {

	    if (DatabaseRequest.dao == null) {
	        DatabaseRequest.dao = new DatabaseRequest();
	    }
	    return DatabaseRequest.dao;
	  }


	  public final ResultSet find(String table, String idName, String idValue, String[] parameters){

			ResultSet result = null;
			String params = "";
			
			try {
				if(parameters == null){
					PreparedStatement ps = ds.getConnection().prepareStatement("SELECT * FROM "+table+" WHERE ? = ?;");
                                        ps.setString(1, idName);
                                        ps.setString(2, idValue);
					result = ps.executeQuery();
                                        System.out.println(result);
				}
				else {
					/**for(int i = 0; i < parameters.length; i++){
						params+=parameters[i]+", ";
					}
					Statement select = ds.createStatement();
					result = select.executeQuery("select "+params+" from "+table+" where ? = ?");*/
				}

			} catch (SQLException sqle) {  
				System.out.println("Erreur : "+sqle.getMessage());
			}
			return result;
	  }
	  
	  
	  
	  public final void insert(String table, String[] parameters){
		   try {
		        Statement select = ds.createStatement();
		        select.executeUpdate(" ");
		   } catch (SQLException sqle) { 
			   System.out.println("Erreur : "+sqle.getMessage());
		   }
	  }
	  
	  

	  public void remove(String table, String idName, String idValue){
		    try {
		        Statement select = ds.createStatement();
		        select.executeUpdate("delete from "+table+" where id_name = "+idName+" and id_value = "+idValue);
		    } catch (SQLException sqle) { 
		    	System.out.println("Erreur : "+sqle.getMessage());
		    }
	  }
	  
	  

	  public void update(String table, HashMap<Integer, Integer> parameters){
		    try {
		        Statement select = ds.createStatement();
		        select.executeUpdate(" ");
		    } catch (SQLException sqle) {  
		    	System.out.println("Erreur : "+sqle.getMessage());
		    }
	  }
          
          public void setDataSource(DataSource ds){
              this.ds = ds;
          }
}