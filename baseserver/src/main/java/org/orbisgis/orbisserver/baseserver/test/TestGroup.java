package org.orbisgis.orbisserver.baseserver.test;

import org.orbisgis.orbisserver.baseserver.model.Group;
import org.orbisgis.orbisserver.baseserver.model.NoRightException;
import org.orbisgis.orbisserver.baseserver.model.User;

import java.util.ArrayList;

public class TestGroup{


    private static ArrayList<User> usersList;
    private static ArrayList<User> adminGroup;
    private static ArrayList<BundleOrbisServer> bundleList;

    private Group testGroup;
    private User user = new User();

    
    
    public void main(String[] args){
    	//Création des utilisateurs necessaire au test
    	User superAdmin = new User(1, "superAdmin", null, null, true);
    	
    	User adminGroup = new User(2, "admin", null, null, false);
    	User adminGroup2 = new User(21, "admin", null, null, false);
    	
    	User user = new User(3, "user", null, null, false);
    	User user2 = new User(31, "user", null, null, false);
    	User user3 = new User(32, "user", null, null, false);
    	
    	User currentUser;
    	
    	System.out.println("Test des constructeurs :");
        testConstructor();
        
        System.out.println("Test des getters :");
        testGetters();

        System.out.println("Test des setters :");
        try {
            testSetters();
        } catch (NoRightException e) {
            e.printStackTrace();
        }

        //test Methode

        System.out.println("Test des méthodes :");
        testAddUser();
        testAddAdmin();
        testAddBundle();
        testRemoveUser();
        testRemoveAdmin();
        testRemoveBundle();
        testLeaveGroupe();
    }

    public void setUp() {
    	
        usersList.add(user);
        usersList.add(user2);
        usersList.add(user3);


        adminGroup.add(adminGroup);
        adminGroup.add(adminGroup2);

        testGroup = new Group(01,"testGroup",user,true);
        
        bundleList.add(new BundleOrbisServer(/*bundle1*/));
        bundleList.add(new BundleOrbisServer(/*bundle2*/));

        testGroup.setBundles(bundleList);
        
        currentUser = user;
    }
    
    
    private void testConstructor(){
    	setUp();
    	
        if(testGroup!=null){
            //System.out.println("test constructeur réussi.");
        }else{
            System.out.println("Echec test constructeur.");
        }
    }

    private void testGetters(){
    	setUp();
    	
        //System.out.println("Test des Getters");
        //System.out.println("getId() : ");

        if(testGroup.getId()==01){
           // System.out.println("test getId() réussi.");
        }else{
            System.out.println("Echec test getId().");
        }

        //System.out.println("getGroupName() : ");

        if(testGroup.getGroupName().equals("testGroup")){
            //System.out.println("test getGroupName() réussi.");
        }else{
            System.out.println("Echec test getGroupName().");
        }
        
        //System.out.println("getUsers() : ");

        if(testGroup.getUsers()!=null){
           // System.out.println("test getUsers() réussi.");
        }else{
            System.out.println("Echec test getUsers().");
        }

        //System.out.println("getAdmins() : ");

        if(testGroup.getAdmins()!=null){
            //System.out.println("test getAdmins() réussi.");
        }else{
            System.out.println("Echec test getAdmins().");
        }

        //System.out.println("getBundles() : ");

        if(testGroup.getBundles()!=null){
            //System.out.println("test getBundles() réussi.");
        }else{
            System.out.println("Echec test getBundles().");
        }
    }

    private void testSetters() throws NoRightException {
    	setUp();
    	
    	//System.out.println("Test des Setters");
        //System.out.println("setId() : ");

        testGroup.setId(02);

        if(testGroup.getId()==02){
            //System.out.println("test setId() réussi.");
        }else{
            System.out.println("Echec test setId().");
        }

        //System.out.println("setGroupName() : ");

        testGroup.setGroupName("testSetGroupName");

        if(testGroup.getGroupName().equals("testSetGroupName")){
            //System.out.println("test setGroupName() réussi.");
        }else{
            System.out.println("Echec test setGroupName().");
        }

        //System.out.println("setUsers() : ");

        testGroup.setUsers(usersList);

        if(testGroup.getUsers().size()==3){
            //System.out.println("test setUsers() réussi.");
        }else{
            System.out.println("Echec test setUsers().");
        }

        //System.out.println("setAdmins() : ");

        testGroup.setAdmins(adminGroup);

        if(testGroup.getAdmins().size()==2){
            //System.out.println("test setAdmins() réussi.");
        }else{
            System.out.println("Echec test setAdmins().");
        }

        //System.out.println("setBundles() : ");

        testGroup.setBundles(new ArrayList<BundleOrbisServer>());

        if(testGroup.getBundles()!=null){
            //System.out.println("test getBundles() réussi.");
        }else{
            System.out.println("Echec test getBundles().");
        }

        //System.out.println("setBundles() : ");

        testGroup.setPersistence(false);

        if(testGroup.isPersistent()==false){
            //System.out.println("test setPersistence() et isPersistent() réussi.");
        }else{
            System.out.println("Echec test setPersistence().");
        }
    }



    private void testAddUser() {
    	setUp();
    	
    	User userTest = new User(100, "user", null, null, false);
		
    	currentUser = adminGroup;
    	
        try {
            testGroup.addUser(currentUser, userTest);
            if(testGroup.getUsers().size() != usersList.size()+1) {
                System.out.println("Erreur AddUser quand admin, taille liste");
            }
            if(testGroup.getUsers().get(testGroup.getUsers().size()-1) != userTest) {
                System.out.println("Erreur AddUser quand admin, Objet ajouté");
            }
        }
        catch(Exception e) {
            System.out.println("Erreur addUser quand admin, exception recu :"+e);
        }
		
		
		
        currentUser= user;
        try {
            testGroup.addUser(currentUser, userTest);
            System.out.println("Erreur AddUser quand non admin, exception non recu");

        }
        catch (NoRightException e) {
            /*Résultat attendu*/
        }
        catch(Exception e) {
            System.out.println("Erreur AddUser quand non admin, mauvaise exception recu");
        }
    }
    
    private void testAddAdmin() {
    	setUp();
    	
    	User adminTest = new User(100, "user", null, null, false);
		
    	currentUser= superAdmin;
    	
        try {
            testGroup.addAdmin(currentUser, adminTest);
            if(testGroup.getAdmins().size() != adminGroup.size()+1) {
                System.out.println("Erreur AddAdmins quand SuperAdmin, taille liste");
            }
            if(testGroup.getAdmins().get(testGroup.getAdmins().size()-1) != adminTest) {
                System.out.println("Erreur AddAdmins quand SuperAdmin, Objet ajouté");
            }
        }
        catch(Exception e) {
            System.out.println("Erreur AddAdmins quand SuperAdmin, exception recu :"+e);
        }
		
		currentUser = adminGroup;
		
        try {
            testGroup.addAdmin(currentUser, adminTest);
            System.out.println("Erreur AddAdmins quand admin, exception non recu");
        }
        catch (NoRightException e) {
            /*Résultat attendu*/
        }
        catch(Exception e) {
            System.out.println("Erreur AddAdmins quand admin, mauvaise exception recu");
        }
    }
    
    private void testAddBundle() {
    	setUp();

        BundleOrbisServer bundleTest = new BundleOrbisServer(/*bundle*/);
		
		currentUser = adminGroup;
        try {
            testGroup.addBundle(currentUser, bundleTest);
            if(testGroup.getBundles().size() != bundleList.size()+1) {
                System.out.println("Erreur addBundle quand admin, taille liste");
            }
            if(testGroup.getBundles().get(testGroup.getBundles().size()-1) != bundleTest) {
                System.out.println("Erreur addBundle quand admin, Objet ajouté");
            }
        }
        catch(Exception e) {
            System.out.println("Erreur addBundle quand admin, exception recu :"+e);
        }
		
		currentUser = user;
        try {
            testGroup.addBundle(currentUser, bundleTest);
            System.out.println("Erreur addBundle quand user, exception non recu");
        }
        catch (NoRightException e) {
            /*Résultat attendu*/
        }catch(Exception e) {
            System.out.println("Erreur addBundle quand user, mauvaise exception recu");
        }
    }
    
    
    private void testRemoveUser() {
		
    	setUp();
    	currentUser = adminGroup;
    	User userSuppr = user2;
    	
        try {
            testGroup.removeUser(currentUser, userSuppr);
            if(testGroup.getUsers().size() != usersList.size()-1) {
                System.out.println("Erreur removeUser quand admin, taille liste");
            }
            if(testGroup.getUsers().contains(userSuppr)) {
                System.out.println("Erreur removeUser quand admin, Objet ajouté");
            }
        }
        catch(Exception e) {
            System.out.println("Erreur removeUser quand admin, exception recu :"+e);
        }
		
		
		
        setUp();
        currentUser = user;
        
        try {
            testGroup.removeUser(currentUser, userSuppr);
            System.out.println("Erreur removeUser quand non admin, exception non recu");

        }catch (NoRightException e) {
            /*Résultat attendu*/
        }
        catch(Exception e) {
            System.out.println("Erreur removeUser quand non admin, mauvaise exception recu");
        }
    }
    
    
    private void testRemoveAdmin() {
    	setUp();
    	currentUser = superAdmin;
    	User adminSuppr = adminGroup2;
    	
        try {
            testGroup.removeUser(currentUser, adminSuppr);
            if(testGroup.getAdmin().size() != adminGroup.size()-1) {
                System.out.println("Erreur removeAdmin quand SuperAdmin, taille liste");
            }
            if(testGroup.getAdmin().contains(adminSuppr)) {
                System.out.println("Erreur removeAdmin quand SuperAdmin, Objet supprimé");
            }
        }
        catch(Exception e) {
            System.out.println("Erreur removeAdmin quand SuperAdmin, exception recu :"+e);
        }
		
        
        setUp();
        currentUser = addminGroup;
        
        try {
            testGroup.removeUser(currentUser, adminSuppr);
            System.out.println("Erreur removeAdmin quand admin, exception non recu");

        }
        catch (NoRightException e) {
            /*Résultat attendu*/
        }
        catch(Exception e) {
            System.out.println("Erreur removeAdmin quand admin, mauvaise exception recu");
        }
    }
    
    
    private void testRemoveBundle() {
    	setUp();
    	currentUser = adminGroup;
    	BundleOrbisServer bundleSuppr = new BundleOrbisServer(/*bundle1*/);
    	
        try {
            testGroup.removeBundle(currentUser, adminSuppr);
            if(testGroup.getBundles().size() != bundleList.size()-1) {
                System.out.println("Erreur removeBundle quand admin, taille liste");
            }
            if(testGroup.getBundles().contains(adminSuppr)) {
                System.out.println("Erreur removeBundle quand admin, Objet supprimé");
            }
        }
        catch(Exception e) {
            System.out.println("Erreur removeBundle quand admin, exception recu :"+e);
        }
		
        
        setUp();
        currentUser = user;
        
        try {
            testGroup.removeBundle(currentUser, adminSuppr);
            System.out.println("Erreur removeBundle quand non admin, exception non recu");

        }catch (NoRightException e) {
            
        }
        catch(Exception e) {
            System.out.println("Erreur removeBundle quand non admin, mauvaise exception recu");
        }
    }
    
    private void testLeaveGroupe() {
    	setUp();
    	currentUser = adminGroup;
    	
        try {
            testGroup.leaveGroupe(currentUser);
            if(testGroup.getAdmin().size() != adminGroup.size()-1) {
                System.out.println("Erreur leaveGroupe quand admin, taille liste");
            }
            if(testGroup.getAdmin().contains(adminSuppr)) {
                System.out.println("Erreur leaveGroupe quand admin, Objet supprimé");
            }
        }
        catch(Exception e) {
            System.out.println("Erreur leaveGroupe quand admin, exception recu :"+e);
        }
        
        //L'adminTest2 ets le dernier admin du groupe, il ne peu donc pas le quitter
        User adminSuppr = new User(/*adminTest2*/);
		
        try {
            testGroup.leaveGroupe(currentUser);
            System.out.println("Erreur leaveGroupe quand dernier admin du groupe, exception non recu");

        }catch (AdminException e) {
            
        }
        catch(Exception e) {
            System.out.println("Erreur leaveGroupe quand dernier admin du groupe, mauvaise exception recu");
        } 
        
        
		/*SetSession user*/
        setUp();
    	
        try {
            testGroup.leaveGroupe(currentUser);
            if(testGroup.getAdmin().size() != adminGroup.size()-1) {
                System.out.println("Erreur leaveGroupe quand user, taille liste");
            }
            if(testGroup.getAdmin().contains(adminSuppr)) {
                System.out.println("Erreur leaveGroupe quand user, Objet supprimé");
            }
        }
        catch(Exception e) {
            System.out.println("Erreur leaveGroupe quand user, exception recu :"+e);
        }
    }

}