package com.example.benz.raconte_moi.DAO;

/**
 * Created by nadia on 17/02/2017.
 */

public class User {

    private String idUser;
    private String firstnameUser;
    private String lastnameUser;
    private String mail;

    public User(String firstnameUser, String lastnameUser, String mail) {
        this.firstnameUser = firstnameUser;
        this.lastnameUser = lastnameUser;
        this.mail = mail;
    }

    public User() {
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getFirstnameUser() {
        return firstnameUser;
    }

    public void setFirstnameUser(String firstnameUser) {
        this.firstnameUser = firstnameUser;
    }

    public String getLastnameUser() {
        return lastnameUser;
    }

    public void setLastnameUser(String lastnameUser) {
        this.lastnameUser = lastnameUser;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}