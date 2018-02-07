package org.orbisgis.orbisserver.baseserver.utils;

import java.security.MessageDigest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.orbisgis.orbisserver.baseserver.exception.NoDataSourceException;

public class Hachage {

  public static String sha256(String aHash) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] hash = digest.digest(aHash.getBytes("UTF-8"));
      StringBuffer hexString = new StringBuffer();

      for (int i = 0; i < hash.length; i++) {
        String hex = Integer.toHexString(0xff & hash[i]);
        if (hex.length() == 1)
          hexString.append('0');
        hexString.append(hex);
      }

      return hexString.toString();
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  public static boolean checkPassword(String password, String pseudo) {

    boolean ret = false;

    DatabaseRequest bddRequest = DatabaseRequest.getInstance();
    ResultSet result = null;

    // On crﾃｩer le hashMap contenant les paramﾃｨtres la requﾃｪte ﾃ� effectuer
    HashMap data = new HashMap();
    data.put("idUser", null);
    // Ici le pseudo passﾃｩ en paramﾃｨtre
    data.put("pseudo", pseudo);
    data.put("password", null);
    data.put("image", null);
    data.put("superAdmin", false);

    // En attente de la mﾃｩthode find du groupe BDD
    // On passe en paramﾃｨtre le hashMap crﾃｩﾃｩ prﾃｩcedemment
    try {
      result = bddRequest.find(DatabaseElements.USER_TABLE, null, null);
    } catch (NoDataSourceException e1) {
      // TODO Bloc catch généré automatiquement
      e1.printStackTrace();
    }

    // On vﾃｩrifie qu'on rﾃｩcupﾃｨre bien un utilisateur
    try {
      if (result.next()) {
        String hash = result.getString(DatabaseElements.USER_PASSWORD.toString());

        if (hash == sha256(password)) {
          ret = true;
        } else {
          ret = false;
        }
      }
    } catch (SQLException e) {
      // TODO Bloc catch g駭駻� automatiquement
      e.printStackTrace();
    }

    return ret;
  }
}
