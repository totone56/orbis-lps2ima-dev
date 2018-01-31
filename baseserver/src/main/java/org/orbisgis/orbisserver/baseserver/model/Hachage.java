package org.orbisgis.orbisserver.baseserver.model;

import java.security.MessageDigest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.orbisgis.orbisserver.baseserver.utils.DatabaseElements;
import org.orbisgis.orbisserver.baseserver.utils.DatabaseRequest;

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

    DatabaseRequest bddRequest = new DatabaseRequest();
    ResultSet result;

    // On créer le hashMap contenant les paramètres la requête à effectuer
    HashMap data = new HashMap();
    data.put("idUser", null);
    // Ici le pseudo passé en paramètre
    data.put("pseudo", pseudo);
    data.put("password", null);
    data.put("image", null);
    data.put("superAdmin", false);

    // En attente de la méthode find du groupe BDD
    // On passe en paramètre le hashMap créé précedemment
    result = bddRequest.find("User", null, null, data);

    // On vérifie qu'on récupère bien un utilisateur
    try {
      if (result.next()) {
        String hash = result.getString(DatabaseElements.USER_PASSWORD);

        if (hash == sha256(password)) {
          ret = true;
        } else {
          ret = false;
        }
      }
    } catch (SQLException e) {
      // TODO Bloc catch g�n�r� automatiquement
      e.printStackTrace();
    }

    return ret;

  }
}
