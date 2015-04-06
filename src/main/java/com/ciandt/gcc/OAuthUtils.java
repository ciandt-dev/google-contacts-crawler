package com.ciandt.gcc;

import java.io.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.http.*;
import com.google.api.client.http.javanet.*;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.*;
import com.google.appengine.api.datastore.Entity;

public class OAuthUtils {
    
    private static final String CLIENTSECRETS_LOCATION = "/client_secrets.json";
    private static final String REDIRECT_URI = "/oauth2callback";
    private static final List<String> SCOPES = Arrays.asList(
        "https://www.googleapis.com/auth/contacts.readonly");
    private static GoogleCredential credential = new GoogleCredential();
    
    static String getRedirectUri(HttpServletRequest req) {
        GenericUrl url = new GenericUrl(req.getRequestURL().toString());
        url.setRawPath(REDIRECT_URI);
        return url.build();
    }
  
    static GoogleAuthorizationCodeFlow newFlow() throws IOException {
        HttpTransport httpTransport = new NetHttpTransport();
        JsonFactory jsonFactory = new JacksonFactory();
    
        Reader reader = new InputStreamReader(OAuthUtils.class.getResourceAsStream(CLIENTSECRETS_LOCATION));
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(new JacksonFactory(), reader);
        return
            new GoogleAuthorizationCodeFlow.Builder(httpTransport, jsonFactory, clientSecrets, SCOPES)
                .setAccessType("offline").setApprovalPrompt("force").build();
    }
    
    protected boolean accessTokenHasExpired(Entity user) {
        Date expireDate = new Date((Long) user.getProperty("expireTime"));
        return expireDate.before(new Date());
    }
    
    protected void refreshAccessToken(Entity user) throws IOException {
        HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
        JsonFactory JSON_FACTORY = new JacksonFactory();
        Reader reader = new InputStreamReader(OAuthUtils.class.getResourceAsStream(CLIENTSECRETS_LOCATION));
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(new JacksonFactory(), reader);
    
        OAuthUtils.credential = new GoogleCredential.Builder()
            .setTransport(HTTP_TRANSPORT)
            .setJsonFactory(JSON_FACTORY)
            .setClientSecrets(clientSecrets)
            .build();
       
        credential.setRefreshToken((String) user.getProperty("refreshToken"));
    }
    
    protected String getAccessToken(Entity user) throws IOException {
        if (this.accessTokenHasExpired(user)) {
            this.refreshAccessToken(user);
        }
        return OAuthUtils.credential.getAccessToken();
    }
}
