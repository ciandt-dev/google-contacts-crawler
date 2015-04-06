package com.ciandt.gcc;

import com.google.appengine.api.datastore.*;

public class User {
 
    public void queryUser(String key){
        
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        
        Query userQuery = new Query(key);
        PreparedQuery preparedUserQuery = datastore.prepare(userQuery);
        
        System.out.println(preparedUserQuery);
        
    }

}
