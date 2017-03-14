package com.example.benz.raconte_moi.DAO;

import android.provider.ContactsContract;
import java.util.Date;
/**
 * Created by nadia on 17/02/2017.
 */

public class Writing {

    private String idChild;
    private String idHistory;
    private int nbrError;
    private int score;
    private boolean valide;
    private String categorie;
    private Date date;

    public Writing() {
    }

    public Writing(String idChild, String idHistory, int nbrError, int score,boolean valide, String categorie, Date date  ) {

        this.idChild = idChild;
        this.idHistory = idHistory;
        this.nbrError = nbrError;
        this.score = score;
        this.valide=valide;
        this.categorie = categorie;
        this.date=date;
    }

    public String getIdChild() {
        return idChild;
    }

    public void setIdChild(String idChild) {
        this.idChild = idChild;
    }

    public String getIdHistory() {
        return idHistory;
    }

    public void setIdHistory(String idHistory) {
        this.idHistory = idHistory;
    }

    public int getNbrError() {
        return nbrError;
    }

    public void setNbrError(int nbrError) {
        this.nbrError = nbrError;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


    public boolean isValide() {
        return valide;
    }

    public void setValide(boolean valide) {
        this.valide = valide;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

