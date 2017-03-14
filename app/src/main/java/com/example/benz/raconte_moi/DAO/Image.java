package com.example.benz.raconte_moi.DAO;

/**
 * Created by nadia on 17/02/2017.
 */
public class Image {

    private String pathImage;
    private String idCategory;

    public Image(String pathImage, String idCategory) {

        this.pathImage = pathImage;
        this.idCategory = idCategory;
    }

    public Image(String pathImage) {
        this.pathImage = pathImage;
    }

    public Image() {

    }

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }

    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }
}