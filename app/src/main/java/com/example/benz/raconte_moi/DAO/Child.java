package com.example.benz.raconte_moi.DAO;

/**
 * Created by nadia on 17/02/2017.
 */

public class Child {

    private String nameChild;
    private int age ;
    private String levelStudy;
    //private String idUser;

    public Child( String nameChild, int age, String levelStudy) {


        this.nameChild = nameChild;
        this.age = age;
        this.levelStudy = levelStudy;
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

    public String getLevelStudy() {

        return levelStudy;
    }

    public void setLevelStudy(String levelStudy) {

        this.levelStudy = levelStudy;
    }
}


