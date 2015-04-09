package com.ciandt.gcc;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;

public class User {

    public void setFlagImportedContacts(Key key, boolean valueFlag) {

        try {

            DatastoreService datastore = DatastoreServiceFactory
                    .getDatastoreService();

            Entity user = datastore.get(key);
            user.setProperty("imported", valueFlag);
            datastore.put(user);

        } catch (EntityNotFoundException e) {

            e.printStackTrace();
        }

    }

    public void setAcessToken(Key key, String newToken, long newTime) {

        try {

            DatastoreService datastore = DatastoreServiceFactory
                    .getDatastoreService();

            Entity user = datastore.get(key);
            user.setProperty("accessToken", newToken);
            user.setProperty("expireTime", newTime);
            datastore.put(user);

        } catch (EntityNotFoundException e) {

            e.printStackTrace();
        }

    }

    public JSONArray QueryContactsAncestor(String userEmail) {

        DatastoreService datastore = DatastoreServiceFactory
                .getDatastoreService();

        Key requestUser = KeyFactory.createKey("User", userEmail);

        Query ancestorQuery = new Query("Contact").setAncestor(requestUser);
        List<Entity> results = datastore.prepare(ancestorQuery).asList(
                FetchOptions.Builder.withDefaults());

        JSONArray jsonResponseArray = new JSONArray();
        jsonResponseArray.put(results);

        return jsonResponseArray;

    }

    public JSONArray QueryContacts() throws JSONException {

        DatastoreService datastore = DatastoreServiceFactory
                .getDatastoreService();

        Query ancestorQuery = new Query("Contact");
        List<Entity> results = datastore.prepare(ancestorQuery).asList(
                FetchOptions.Builder.withDefaults());

        JSONArray jsonResponseArray = new JSONArray();
        jsonResponseArray.put(results);

        return jsonResponseArray;

    }

}
