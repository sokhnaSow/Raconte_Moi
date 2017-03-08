package com.example.benz.raconte_moi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.benz.raconte_moi.DAO.Child;
import com.example.benz.raconte_moi.DAO.Illustration;
import com.example.benz.raconte_moi.DAO.Image;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class ReadingDrawing extends AppCompatActivity {

    String idImage ;
    FirebaseStorage storage;
    StorageReference storageRef;
    FirebaseDatabase database;
    DatabaseReference refData;
    ImageView imageView;
    TextView titleTextView;
    ArrayList<String> l ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_drawing);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        database = FirebaseDatabase.getInstance();
        refData = database.getReference();


        final String path = getIntent().getStringExtra("path");
        imageView = (ImageView) findViewById(R.id.image);
         titleTextView = (TextView) findViewById(R.id.title);
        l=new ArrayList<String>();


        StorageReference ref = storageRef.child(path);
        final long ONE_MEGABYTE = 1024 * 1024;
        ref.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imageView.setImageBitmap(bitmap);
            }
        });


        refData.child("Images").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Image i = child.getValue(Image.class);
                    if(i.getPathImage().equals(path)){
                        idImage=i.getIdImage();
                        System.out.println("id; " + idImage);
                        l.add(idImage);

                        refData.child("Illustration").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot child : dataSnapshot.getChildren()) {
                                    Illustration i = child.getValue(Illustration.class);
                                    if(i.getIdImage().equals(idImage)){
                                        titleTextView.setText(i.getParagraphe());
                                        System.out.println(i.getParagraphe());
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }

                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });




    }
}
