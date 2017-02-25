package com.example.benz.raconte_moi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle; import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.benz.raconte_moi.DAO.DAO;
import com.example.benz.raconte_moi.DAO.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Inscription extends AppCompatActivity implements View.OnClickListener{

    EditText etNom, etPrenom, etNbreEnfant, etMail, etNomUtilisateur, etMotDePasse;
    Button bInscription;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    DAO d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        etNom = (EditText)findViewById(R.id.etNom);
        etPrenom = (EditText)findViewById(R.id.etPrenom);
        //etNbreEnfant = (EditText)findViewById(R.id.etNbreEnfant);
        etMail = (EditText)findViewById(R.id.etMail);
        //etNomUtilisateur = (EditText)findViewById(R.id.etNomUtilisateur);
        etMotDePasse = (EditText)findViewById(R.id.etMotDePasse);
        bInscription = (Button)findViewById(R.id.bInscription);
        d=new DAO();
        bInscription.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bInscription:
                //redirection vers le menu

                User u = new User(etPrenom.getText().toString(),etNom.getText().toString(),etMail.getText().toString());
                d.addUser(u);
                final ProgressDialog progressDialog= ProgressDialog.show(Inscription.this,"please wait ..","Processing...",true);
                firebaseAuth.createUserWithEmailAndPassword(etMail.getText().toString(),etMotDePasse.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();
                                if(task.isSuccessful()){
                                    Toast.makeText(Inscription.this,"Inscription reussite",Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(Inscription.this,MainActivity.class);
                                    startActivity(i);
                                }
                                else{
                                    Toast.makeText(Inscription.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                }
                            }
                        });
              //  User u = new User(etPrenom.getText().toString(),etNom.getText().toString(),etMail.getText().toString());

               // d.addUser(u);
                break;
        }
    }
}