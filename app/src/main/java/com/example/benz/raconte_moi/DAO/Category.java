package com.example.benz.raconte_moi.DAO;

/**
 * Created by nadia on 17/02/2017.
 */

public class Category {

    private String nameCategory;

    public Category( String nameCategory) {

        this.nameCategory = nameCategory;
    }

    public Category() {
    }


    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }
}
