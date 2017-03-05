package com.example.benz.raconte_moi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class PageAccueil extends AppCompatActivity implements View.OnClickListener {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference refData = database.getReference();

    Button bAjoutEnfant, bActivite, bDeconnecter;

    TextView mail , prenom, nom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_accueil);

        bAjoutEnfant = (Button)findViewById(R.id.bAjoutEnfant);
        bActivite = (Button)findViewById(R.id.bActivite);
        bDeconnecter = (Button)findViewById(R.id.bDeconnecter);

        prenom = (TextView) findViewById(R.id.tePrenomP);
        nom = (TextView) findViewById(R.id.teNomP);
        mail = (TextView) findViewById(R.id.teMail);

        nom.setText(getIntent().getExtras().getString("nom"));
        prenom.setText(getIntent().getExtras().getString("prenom"));
        mail.setText(getIntent().getExtras().getString("mail"));

        String idUser = getIntent().getExtras().getString("id");


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
                String idChild = "KdytbjZGQcZb3QU4Yz6";
                Intent intent = new Intent(this, ActivityManager.class);
                intent.putExtra("idChild",idChild);
                startActivity(intent);
                break;

            case R.id.bDeconnecter:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}