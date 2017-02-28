package com.example.benz.raconte_moi.DAO;

/**
 * Created by nadia on 17/02/2017.
 */

public class Illustration  {

    private  String idImage;
    private  String idHistory;
    private String paragraphe;
    private String idEnfant;

    public Illustration( String idImage,  String idHistory, String paragraphe, String idEnfant) {

        this.idImage = idImage;
        this.idHistory = idHistory;
        this.paragraphe = paragraphe;
        this.idEnfant= idEnfant;
    }

    public Illustration() {
    }

    public  String getIdEnfant() {
        return idEnfant;
    }

    public void setIdEnfant( String idEnfant) {
        this.idEnfant = idEnfant;
    }

    public  String getIdImage() {
        return idImage;
    }

    public void setIdImage( String idImage) {
        this.idImage = idImage;
    }

    public  String getIdHistory() {
        return idHistory;
    }

    public void setIdHistory( String idHistory) {
        this.idHistory = idHistory;
    }

    public String getParagraphe() {
        return paragraphe;
    }

    public void setParagraphe(String paragraphe) {
        this.paragraphe = paragraphe;
    }
}
