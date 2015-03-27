package br.com.ciandt.gcc.controller.model;

import java.util.Date;

public class Ancestor {

    private String name;
    private String lastName;
    private Date birth;
    private String mail;
    private String userId;
    private String domain;
    private String extendDomain;

    public Ancestor() {

    }

    public Ancestor(String name, String lastName, Date birth, String mail,
            String userId, String domain, String extendDomain) {

        this.name = name;
        this.lastName = lastName;
        this.birth = birth;
        this.mail = mail;
        this.userId = userId;
        this.domain = domain;
        this.extendDomain = extendDomain;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getExtendDomain() {
        return extendDomain;
    }

    public void setExtendDomain(String extendDomain) {
        this.extendDomain = extendDomain;
    }

}
