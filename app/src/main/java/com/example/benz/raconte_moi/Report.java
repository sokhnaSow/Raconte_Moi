package com.example.benz.raconte_moi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.benz.raconte_moi.DAO.Child;
import com.example.benz.raconte_moi.DAO.Reading;
import com.example.benz.raconte_moi.DAO.Writing;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class Report extends AppCompatActivity {

    FirebaseStorage storage;
    StorageReference storageRef;
    FirebaseDatabase database;
    DatabaseReference refData;
    float nbReading = 0;
    float nbWriting = 0;
    WebView webView;
    float pourcLecture;
    float pourcWriting;
    TextView txtname ,nbR,nbW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        webView = (WebView) findViewById(R.id.webView);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        database = FirebaseDatabase.getInstance();
        refData = database.getReference();
        txtname= (TextView) findViewById(R.id.txtName);
        nbR= (TextView) findViewById(R.id.txtR);
        nbW= (TextView) findViewById(R.id.txtw);

        Intent intent2 = getIntent();
        final String idChild = intent2.getStringExtra("idChild");

        System.out.println(idChild);


        refData.child("children").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Child u = child.getValue(Child.class);
                    if (child.getKey().equals(idChild)){
                    txtname.setText(u.getNameChild());
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        refData.child("Reading").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Reading r = child.getValue(Reading.class);
                    if (r.getIdChild().equals(idChild)) {
                        nbReading += 1;
                    }
                }
                System.out.println("nbr lecture : " + nbReading);
               refData.child("Writing").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            Writing w = child.getValue(Writing.class);
                            if (w.getIdChild().equals(idChild)) {
                                nbWriting += 1;
                            }
                        }
                        System.out.println("nbr lecture : " + nbReading);
                        System.out.println("nbr ecriture : "+ nbWriting);

                        nbW.setText(String.valueOf(nbWriting));
                        nbR.setText(String.valueOf(nbReading));

                        float total = nbReading+nbWriting;
                        System.out.println("total "+total);
                         pourcLecture= nbReading/total *100;
                         pourcWriting= (nbWriting/total)*100 ;
                        System.out.println("pw:"+pourcWriting);
                        System.out.println("pr:"+pourcLecture);
                        webView.loadUrl("http://chart.apis.google.com/chart?cht=p3&chs=250x100&chd=t:"+pourcWriting+","+pourcLecture+"&chl=writing|reading");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}
