package com.ciandt.gcc.entities;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Date;
import java.util.logging.Logger;

import com.ciandt.gcc.auth.OAuthUtils;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Parent;

@Cache
@Entity
public class Credential {
    @Id Long id;
    
    @Parent Ref<User> owner;
    
    String accessToken;
    
    String refreshToken;
    
    long expirationTime;
    
    @Ignore
    static final Logger log = Logger.getLogger(Credential.class.getName());
    
    @SuppressWarnings("unused")
    private Credential() {}
    
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
