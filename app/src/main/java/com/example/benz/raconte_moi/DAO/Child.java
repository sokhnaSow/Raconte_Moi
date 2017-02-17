package com.example.benz.raconte_moi.DAO;

/**
 * Created by nadia on 17/02/2017.
 */

public class Child {
    private int idChild;
    private String nameChild;
    private int age ;
    private String idUser;

    public Child(int idChild, String nameChild, int age, String idUser) {

        this.idChild = idChild;
        this.nameChild = nameChild;
        this.age = age;
        this.idUser = idUser;
    }

    public Child() {
    }

    public int getIdChild() {
        return idChild;
    }

    public void setIdChild(int idChild) {
        this.idChild = idChild;
    }

    public String getNameChild() {
        return nameChild;
    }

    public void setNameChild(String nameChild) {
        this.nameChild = nameChild;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}


