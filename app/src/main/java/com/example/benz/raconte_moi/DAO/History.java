package com.example.benz.raconte_moi.DAO;

import static android.R.attr.id;

/**
 * Created by nadia on 17/02/2017.
 */

public class History {
    private int idHistory;
    private String title;

    public History() {
    }

    public History(int idHistory, String title) {

        this.idHistory = idHistory;
        this.title = title;
    }

    public int getIdHistory() {
        return idHistory;
    }

    public void setIdHistory(int idHistory) {
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
