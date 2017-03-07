package com.example.benz.raconte_moi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.benz.raconte_moi.DAO.Child;
import com.example.benz.raconte_moi.DAO.DAO;

public class AjoutEnfant extends AppCompatActivity implements View.OnClickListener{

    //pour commit
    EditText etPrenom, etAge, etNiveau;
    Button bValider;
    DAO d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_enfant);

        etPrenom = (EditText)findViewById(R.id.etPrenom);
        etAge = (EditText)findViewById(R.id.etAge);
        etNiveau = (EditText)findViewById(R.id.etSex);
        bValider = (Button)findViewById(R.id.bValider);

        bValider.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.bValider:
                DAO d = new DAO();
                Intent intent2 = getIntent();
                String idParent = intent2.getStringExtra("id");
                //Child c = new Child(etPrenom.getText().toString(),Integer.parseInt(etAge.getText().toString()),idParent);
                // d.addChildren(c);
                startActivity(new Intent(this, PageAccueil.class));
                break;
        }
    }

}
