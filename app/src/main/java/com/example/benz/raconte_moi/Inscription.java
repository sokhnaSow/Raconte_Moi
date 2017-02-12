package com.example.benz.raconte_moi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Inscription extends AppCompatActivity implements View.OnClickListener{

    EditText etNom, etPrenom, etNbreEnfant, etMail, etNomUtilisateur, etMotDePasse;
    Button bInscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        etNom = (EditText)findViewById(R.id.etNom);
        etPrenom = (EditText)findViewById(R.id.etPrenom);
        etNbreEnfant = (EditText)findViewById(R.id.etNbreEnfant);
        etMail = (EditText)findViewById(R.id.etMail);
        etNomUtilisateur = (EditText)findViewById(R.id.etNomUtilisateur);
        etMotDePasse = (EditText)findViewById(R.id.etMotDePasse);
        bInscription = (Button)findViewById(R.id.bInscription);

        bInscription.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bInscription:
                //redirection vers le menu
                break;
        }
    }
}
