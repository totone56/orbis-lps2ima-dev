package org.orbisgis.orbisserver.baseserver.utils;

public class TestDatabaseRequest{



    static public void testDatabaseRequest(String ){
        @Requires DataSource ds;
        DatabaseRequest databaseRequest = DatabaseRequest.getInstance();
        databaseRequest.setDataSource(ds);
        testInsert(databaseRequest);
        testFind(databaseRequest);
        testUpdate(databaseRequest);
        testRemove(databaseRequest);
    }

    private static void testInsert(DatabaseRequest databaseRequest){
        //Test cas normal ou on insert un user valide
        if(databaseRequest.insert("User",new HashMap<St>))
    }


}