package com.example.benz.raconte_moi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AjoutEnfant extends AppCompatActivity implements View.OnClickListener{

    EditText etNom, etPrenom, etAge, etNiveau;
    Button bValider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_enfant);

        etNom = (EditText)findViewById(R.id.etNom);
        etPrenom = (EditText)findViewById(R.id.etPrenom);
        etAge = (EditText)findViewById(R.id.etAge);
        etNiveau = (EditText)findViewById(R.id.etNiveau);
        bValider = (Button)findViewById(R.id.bValider);

        bValider.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bValider:
                startActivity(new Intent(this, PageAccueil.class));
                break;
        }
    }
}
