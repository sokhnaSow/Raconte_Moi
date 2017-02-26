package com.example.benz.raconte_moi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PageAccueil extends AppCompatActivity implements View.OnClickListener {


    Button bAjoutEnfant, bActivite, bDeconnecter;
    TextView mail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_accueil);

        bAjoutEnfant = (Button)findViewById(R.id.bAjoutEnfant);
        bActivite = (Button)findViewById(R.id.bActivite);
        bDeconnecter = (Button)findViewById(R.id.bDeconnecter);

        mail = (TextView) findViewById(R.id.tePrenomP);
        mail.setText(getIntent().getExtras().getString("mail"));

        bAjoutEnfant.setOnClickListener(this);
        bActivite.setOnClickListener(this);
        bDeconnecter.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bAjoutEnfant:
                startActivity(new Intent(this, AjoutEnfant.class));
                break;

            case R.id.bActivite:
                startActivity(new Intent(this, ActivityManager.class));
                break;

            case R.id.bDeconnecter:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}
