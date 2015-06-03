package com.ciandt.gcc.entities;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Date;
import java.util.logging.Logger;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.ciandt.gcc.auth.OAuthUtils;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Credential {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
    
    @Persistent
    private String accessToken;
    
    @Persistent
    private String refreshToken;
    
    @Persistent
    private long expirationTime;
    
    @NotPersistent
    private static final Logger log = Logger.getLogger(Credential.class.getName());
    
    public Credential(com.google.api.client.auth.oauth2.Credential credential) {  
        this.accessToken = credential.getAccessToken();
        this.refreshToken = credential.getRefreshToken();
        this.expirationTime = (System.currentTimeMillis() / 1000L) + credential.getExpiresInSeconds();
    }

    public String getAccessToken() {
        if (hasExpired()) {
          try {
              refreshAccessToken();
          }
          catch (IOException exception) {
              log.severe("Credential is expired and unable to refresh access token.");
          }
        }
        return accessToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
    }
    
    public boolean hasExpired() {
        return new Date(this.expirationTime).before(new Date());
    }
    
    public void refreshAccessToken() throws IOException {
      HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
      JsonFactory JSON_FACTORY = new JacksonFactory();
      Reader reader = new InputStreamReader(OAuthUtils.class.getResourceAsStream(OAuthUtils.CLIENTSECRETS));
      GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(new JacksonFactory(), reader);
  
      GoogleCredential credential = new GoogleCredential.Builder()
          .setTransport(HTTP_TRANSPORT)
          .setJsonFactory(JSON_FACTORY)
          .setClientSecrets(clientSecrets)
          .build();
      
      credential.setRefreshToken(this.refreshToken);
      credential.refreshToken();
      this.accessToken = credential.getAccessToken();
    }
}
