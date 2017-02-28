package com.example.benz.raconte_moi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.benz.raconte_moi.DAO.DAO;
import com.example.benz.raconte_moi.DAO.DaoUser;
import com.example.benz.raconte_moi.DAO.History;
import com.example.benz.raconte_moi.DAO.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText etMail, etMotDePasse;
    Button bConnexion;
    TextView tvInscriptionLink, tvOubliInfoLink;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference refData = database.getReference();
    DaoUser du;
    String key ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etMail = (EditText)findViewById(R.id.etNomUtilisateur);
        etMotDePasse = (EditText)findViewById(R.id.etMotDePasse);
        bConnexion = (Button)findViewById(R.id.bConnexion);
        tvInscriptionLink = (TextView)findViewById(R.id.tvInscriptionLink);
        tvOubliInfoLink = (TextView)findViewById(R.id.tvOubliInfoLink);

        bConnexion.setOnClickListener(this);
        tvInscriptionLink.setOnClickListener(this);
        tvOubliInfoLink.setOnClickListener(this);


        /*for(User user : du.getUsers()){
            if(user.getMail().equals(etMail.getText().toString())){
                key=user.getIdUser();
                System.out.println(key);
            }
        }*/



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bConnexion:

                final ProgressDialog progressDialog= ProgressDialog.show(MainActivity.this,"please wait ..","Processing...",true);
                firebaseAuth.signInWithEmailAndPassword(etMail.getText().toString(),etMotDePasse.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();
                                if(task.isSuccessful()){
                                    Toast.makeText(MainActivity.this,"Connexion reussite",Toast.LENGTH_LONG).show();

                                    refData.child("user").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            Log.e("count",""+dataSnapshot.getChildrenCount());


                                            for(DataSnapshot child : dataSnapshot.getChildren()){
                                                User u = child.getValue(User.class);
                                                if(u.getMail().equals(etMail.getText().toString())){
                                                    Intent i = new Intent(MainActivity.this,PageAccueil.class);
                                                    i.putExtra("id",u.getIdUser());
                                                    i.putExtra("nom",u.getLastnameUser());
                                                    i.putExtra("prenom",u.getFirstnameUser());
                                                    i.putExtra("mail",u.getMail());

                                                    startActivity(i);
                                                }

                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }

                                    });



                                }
                                else{
                                    Toast.makeText(MainActivity.this,"Votre mail ou votre mot de passe est invalide ",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
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
