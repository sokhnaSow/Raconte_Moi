package com.example.benz.raconte_moi.DAO;

/**
 * Created by nadia on 17/02/2017.
 */

public class User {

    private int idUser ;
    private String firstnameUser;
    private String lastnameUser;
    private String login;
    private String password;

    public User(int idUser, String firstnameUser, String lastnameUser, String login, String password) {
        this.idUser = idUser;
        this.firstnameUser = firstnameUser;
        this.lastnameUser = lastnameUser;
        this.login = login;
        this.password = password;
    }

    public User() {
    }

    public int getIdUser() {
        return idUser;
    }

    public String getFirstnameUser() {
        return firstnameUser;
    }

    public String getLastnameUser() {
        return lastnameUser;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setFirstnameUser(String firstnameUser) {
        this.firstnameUser = firstnameUser;
    }

    public void setLastnameUser(String lastnameUser) {
        this.lastnameUser = lastnameUser;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
