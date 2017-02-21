package com.example.benz.raconte_moi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PageAccueil extends AppCompatActivity implements View.OnClickListener {

    //just commit GestionParent
    Button bActivites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_accueil);

        bActivites = (Button)findViewById(R.id.bActivites);

        bActivites.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bActivites:
                startActivity(new Intent(this, ActivityManager.class));
                break;
        }
    }
}
