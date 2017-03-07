package com.example.benz.raconte_moi.DAO;

/**
 * Created by nadia on 17/02/2017.
 */

public class Child {

    private String nameChild;
    private int age ;
    private String sex;
    private String idUser;


    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }



    public Child( String nameChild, int age, String sex, String idUser) {


        this.nameChild = nameChild;
        this.age = age;
        this.sex = sex;
        this.idUser = idUser;
    }

    public Child() {
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

    public String getSex() {

        return sex;
    }

    public void setSex(String Sex) {

        this.sex = Sex;
    }
}



