package com.ciandt.gcc;

import com.google.appengine.api.datastore.*;

public class User {
 
    public void setFlagImportedContacts(Key key, boolean valueFlag){
       
        try {
            
            DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
            
            Entity user = datastore.get(key);
            user.setProperty("imported", valueFlag);
            datastore.put(user);
        
        } catch (EntityNotFoundException e) {

            e.printStackTrace();
        }

        
    }
    
    public void setAcessToken(Key key, String newToken, long newTime){

        try {
            
            DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
            
            Entity user = datastore.get(key);
            user.setProperty("accessToken", newToken);
            user.setProperty("expireTime", newTime);
            datastore.put(user);
        
        } catch (EntityNotFoundException e) {

            e.printStackTrace();
        }

        
    }
    


}
