package com.example.benz.raconte_moi;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.benz.raconte_moi.DAO.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    public static final String TAG = "LoginActivity";
    public EditText etMail, etMotDePasse;
    public Button bConnexion;
    public TextView tvInscriptionLink, tvOubliInfoLink;
    public FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference refData = database.getReference();

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

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bConnexion:

                Log.d(TAG, "Login");
                if (!validate()) {
                        onLoginFailed();
                        return;
                }
                bConnexion.setEnabled(false);
                final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this, R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Authenticating...");
                progressDialog.show();
                firebaseAuth.signInWithEmailAndPassword(etMail.getText().toString(), etMotDePasse.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();
                                if (task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "Connexion succeed", Toast.LENGTH_LONG).show();

                                    refData.child("user").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            Log.e("count", "" + dataSnapshot.getChildrenCount());


                                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                                User u = child.getValue(User.class);
                                                if (u.getMail().equals(etMail.getText().toString())) {
                                                    Intent i = new Intent(MainActivity.this, PageAccueil.class);
                                                    i.putExtra("id", u.getIdUser());
                                                    i.putExtra("nom", u.getLastnameUser());
                                                    i.putExtra("prenom", u.getFirstnameUser());
                                                    i.putExtra("mail", u.getMail());

                                                    startActivity(i);
                                                }

                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            /*
                                            Code.......
                                             */
                                        }

                                    });


                                } else {
                                    Toast.makeText(MainActivity.this, "invalid email or password ", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                // On complete call either onLoginSuccess or onLoginFailed
                                onLoginSuccess();
                                // onLoginFailed();
                                progressDialog.dismiss();
                            }
                        }, 3000);

                break;

            case R.id.tvInscriptionLink:
                startActivity(new Intent(this, Inscription.class));
                break;
        }
    }

    public void onLoginSuccess() {
        bConnexion.setEnabled(true);
        //finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        bConnexion.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = etMail.getText().toString();
        String password = etMotDePasse.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etMail.setError("enter a valid email address");
            valid = false;
        } else {
            etMail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            etMotDePasse.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            etMotDePasse.setError(null);
        }

        return valid;
    }
}
