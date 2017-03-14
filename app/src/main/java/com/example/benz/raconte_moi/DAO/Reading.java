package com.example.benz.raconte_moi.DAO;

/**
 * Created by nadia on 17/02/2017.
 */

public class Reading {

    private String idChild;
    private String idHistory;
    private double speed;
    private int score;

    public Reading() {
    }

    public Reading(String idChild, String idHistory, double speed, int score) {

        this.idChild = idChild;
        this.idHistory = idHistory;
        this.speed = speed;
        this.score = score;
    }

    public Reading(String idChild, String idHistory, int score) {
        this.idChild = idChild;
        this.idHistory = idHistory;
        this.score = score;
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