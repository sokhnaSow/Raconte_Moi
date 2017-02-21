package com.example.benz.raconte_moi.DAO;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by nadia on 20/02/2017.
 */

public class DaoManager extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DAO D = new DAO();
        History h = new History(1,"alice");
        D.addHistory(h);

    }
}
