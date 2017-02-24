package com.example.benz.raconte_moi.dao;

/**
 * Created by nadia on 17/02/2017.
 */

public class Illustration  {

    private int idImage;
    private int idHistory;
    private String paragraphe;

    public Illustration( int idImage, int idHistory, String paragraphe) {

        this.idImage = idImage;
        this.idHistory = idHistory;
        this.paragraphe = paragraphe;
    }

    public Illustration() {
    }

    public int getIdImage() {
        return idImage;
    }

    public void setIdImage(int idImage) {
        this.idImage = idImage;
    }

    public int getIdHistory() {
        return idHistory;
    }

    public void setIdHistory(int idHistory) {
        this.idHistory = idHistory;
    }

    public String getParagraphe() {
        return paragraphe;
    }

    public void setParagraphe(String paragraphe) {
        this.paragraphe = paragraphe;
    }
}
