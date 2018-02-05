package org.orbisgis.orbisserver.baseserver.test;

import org.orbisgis.orbisserver.baseserver.model.Hachage;

public class TestHachage {

    public static void main(String [] args){
        testSha256();
    }

    public static void testSha256(){

        System.out.println("[----------------- Test de la méthode sha256 -----------------]\n");
        System.out.println("Hachage de la chaîne de caractère : password");
        System.out.println("Hash : "+Hachage.sha256("password")+"\n");
        String passwordSha256 = "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8";
        if(passwordSha256.equals(Hachage.sha256("password"))){
            System.out.println("TEST OK");
        } else{
            System.out.println("TEST PAS OK");
        }
    }
}
