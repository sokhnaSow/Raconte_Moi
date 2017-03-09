package com.example.benz.raconte_moi;

import android.graphics.Bitmap;

/**
 * Created by nadia on 08/03/2017.
 */

public class ImageItem {


    private String title;
    private String idHistory;

    public ImageItem(String title, String idHistory) {
        this.title = title;
        this.idHistory = idHistory;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIdHistory() {
        return idHistory;
    }

    public void setIdHistory(String idHistory) {
        this.idHistory = idHistory;
    }
}
