package com.example.benz.raconte_moi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText ettNomUtilisateur, etMotDePasse;
    Button bConnexion;
    TextView tvInscriptionLink, tvOubliInfoLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ettNomUtilisateur = (EditText)findViewById(R.id.etNomUtilisateur);
        etMotDePasse = (EditText)findViewById(R.id.etMotDePasse);
        bConnexion = (Button)findViewById(R.id.bConnexion);
        tvInscriptionLink = (TextView)findViewById(R.id.tvInscriptionLink);
        tvOubliInfoLink = (TextView)findViewById(R.id.tvOubliInfoLink);

        bConnexion.setOnClickListener(this);
        tvInscriptionLink.setOnClickListener(this);
        tvOubliInfoLink.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bConnexion:
                startActivity(new Intent(this, PageAccueil.class));
                break;

            case R.id.tvInscriptionLink:
                startActivity(new Intent(this, Inscription.class));
                break;

            /* info à traiter
            case R.id.tvOubliInfoLink:
                break;
                */
        }
    }
}
