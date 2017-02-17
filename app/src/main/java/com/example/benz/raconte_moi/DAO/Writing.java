package com.example.benz.raconte_moi.DAO;

/**
 * Created by nadia on 17/02/2017.
 */

public class Writing {

    private int idChild;
    private int idHistory;
    private int nbrError;
    private int score;


    public Writing() {
    }

    public Writing(int idChild, int idHistory, int nbrError, int score) {

        this.idChild = idChild;
        this.idHistory = idHistory;
        this.nbrError = nbrError;
        this.score = score;
    }

    public int getIdChild() {
        return idChild;
    }

    public void setIdChild(int idChild) {
        this.idChild = idChild;
    }

    public int getIdHistory() {
        return idHistory;
    }

    public void setIdHistory(int idHistory) {
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
}
