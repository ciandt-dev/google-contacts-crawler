package com.ciandt.gcc;

import com.google.appengine.api.datastore.*;

public class User {
 
    private static final boolean flagImported = true;
    
    public void setUser(Key key){
       
        try {
            
            DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
            
            Entity user = datastore.get(key);
            user = datastore.get(user.getKey());
            user.setProperty("imported", flagImported);
            datastore.put(user);
        
        } catch (EntityNotFoundException e) {

            e.printStackTrace();
        }

        
    }

}
