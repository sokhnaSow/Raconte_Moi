package com.example.benz.raconte_moi.DAO;

/**
 * Created by nadia on 17/02/2017.
 */

public class Image {
    private int idImage;
    private String pathImage ;
    private  int idCategory;

    public Image(int idImage, String pathImage, int idCategory) {
        this.idImage = idImage;
        this.pathImage = pathImage;
        this.idCategory = idCategory;
    }

    public Image() {

    }

    public int getIdImage() {
        return idImage;
    }

    public void setIdImage(int idImage) {
        this.idImage = idImage;
    }

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }
}
