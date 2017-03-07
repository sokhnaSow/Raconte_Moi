package com.example.benz.raconte_moi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.benz.raconte_moi.DAO.DAO;
import com.example.benz.raconte_moi.DAO.DaoUser;
import com.example.benz.raconte_moi.DAO.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Inscription extends AppCompatActivity implements View.OnClickListener{

    EditText etNom, etPrenom, etNbreEnfant, etMail, etNomUtilisateur, etMotDePasse;
    TextInputLayout inputLayoutName, inputLayoutEmail,  inputLayoutPassword, inputLayoutPrenom;
    Button bInscription;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    DaoUser d;

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

        etNom.addTextChangedListener(new MyTextWatcher(etNom));
        etPrenom.addTextChangedListener(new MyTextWatcher(etPrenom));
        etMail.addTextChangedListener(new MyTextWatcher(etMail));
        etMotDePasse.addTextChangedListener(new MyTextWatcher(etMotDePasse));

        inputLayoutPrenom = (TextInputLayout) findViewById(R.id.input_Prenom);
        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_psw);

        bInscription = (Button)findViewById(R.id.bInscription);
        d=new DaoUser();
        bInscription.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bInscription:
                //redirection vers le menu
                if (!validateName() || !validateEmail() || !validatePassword()|| !validatePrenom()) {
                        return;
                } else {
                    User u = new User(etPrenom.getText().toString(), etNom.getText().toString(), etMail.getText().toString());
                    String key = d.addUser(u);

                    final ProgressDialog progressDialog = ProgressDialog.show(Inscription.this, "please wait ..", "Processing...", true);
                    firebaseAuth.createUserWithEmailAndPassword(etMail.getText().toString(), etMotDePasse.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressDialog.dismiss();
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Inscription.this, "Inscription reussite", Toast.LENGTH_LONG).show();
                                        Intent i = new Intent(Inscription.this, MainActivity.class);
                                        startActivity(i);
                                    } else {
                                        Toast.makeText(Inscription.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                    break;
                }
        }
    }

    private boolean validatePrenom() {
        if (etPrenom.getText().toString().trim().isEmpty()) {
            inputLayoutPrenom.setError(getString(R.string.err_msg_name));
            requestFocus(etPrenom);
            return false;
        } else {
            inputLayoutPrenom.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateEmail() {
        String email = etMail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
           requestFocus(etMail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateName() {
        if (etNom.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_msg_name));
            requestFocus(etNom);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validatePassword() {
        if (etMotDePasse.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password));
            requestFocus(etMotDePasse);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }
    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.etNom:
                    validateName();
                    break;
                case R.id.etMail:
                    validateEmail();
                    break;
                case R.id.etMotDePasse:
                    validatePassword();
                    break;
                case R.id.etPrenom:
                    validatePrenom();
                    break;
            }
        }
    }
}