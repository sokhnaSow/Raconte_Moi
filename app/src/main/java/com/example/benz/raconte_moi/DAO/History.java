package com.example.benz.raconte_moi.DAO;

/**
 * Created by nadia on 17/02/2017.
 */

public class History {
    private String idHistory;
    private String title;

    public History() {

    }

    public History(String idHistory, String title) {

        this.idHistory = idHistory;
        this.title = title;
    }

    public String getIdHistory() {
        return idHistory;
    }

    public void setIdHistory(String idHistory) {
        this.idHistory = idHistory;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "History{" +
                "idHistory='" + idHistory + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
