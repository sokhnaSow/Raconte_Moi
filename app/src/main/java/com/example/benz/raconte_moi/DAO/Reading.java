package com.example.benz.raconte_moi.DAO;

/**
 * Created by nadia on 17/02/2017.
 */

public class Reading {

    private int idChild;
    private int idHistory;
    private double speed;
    private int score;

    public Reading() {
    }

    public Reading(int idChild, int idHistory, double speed, int score) {

        this.idChild = idChild;
        this.idHistory = idHistory;
        this.speed = speed;
        this.score = score;
    }

    public Reading(int idChild, int idHistory, int score) {
        this.idChild = idChild;
        this.idHistory = idHistory;
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

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
