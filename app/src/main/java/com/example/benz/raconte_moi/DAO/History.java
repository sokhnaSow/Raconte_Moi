package com.example.benz.raconte_moi.DAO;

import static android.R.attr.id;

/**
 * Created by nadia on 17/02/2017.
 */

public class History {

    private String title;

    public History() {
    }

    public History( String title) {

        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
