package com.example.benz.raconte_moi.DAO;


import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.util.Base64;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URI;

/**
 * Created by nadia on 17/02/2017.
 */




public class DAO {




    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference refData = database.getReference();


    // Ajouter histoire

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    static  Bitmap b = null;

    public String addHistory(final History history){

        // key contient la clé primaire de histoire généré automatiquement

        String key = refData.child("history").push().getKey();
        refData.child("history").child(key).setValue(history);

        return key;


    }
    public String addKid( Child c){

        // key contient la clé primaire de histoire généré automatiquement

        String key = refData.child("children").push().getKey();
        refData.child("children").child(key).setValue(c);

        return key;


    }

    public String addImage(Bitmap bitmap, String path, String idCategorie) {

      // store image on firebase storage
        StorageReference mountainsRef = storageRef.child("Images/"+path);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        mountainsRef.putBytes(baos.toByteArray());

        // store images path on firebase database
        String key = refData.child("Images").push().getKey();
        Image i = new Image("Images/"+path,idCategorie);
        refData.child("Images").child(key).setValue(i);
        return key;
    }

    public void removeHistory(String idHistory){

        refData.child("history/"+idHistory+"").removeValue();

    }

    public void updateHistory(String idHistory , History newHistory){

        refData.child("history").child(idHistory).setValue(newHistory);
    }


    public void searchHistory(String idHistory){

        refData.child("history/"+idHistory).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                History h = dataSnapshot.getValue(History.class);
                System.out.println("titre " +h.getTitle());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


    }

    public String addChildren(final Child children){


        String key = refData.child("children").push().getKey();
        refData.child("children").child(key).setValue(children);

        return key;
    }






    public String addIllustration(Illustration i) {

        String key = refData.child("Illustration").push().getKey();
        refData.child("Illustration").child(key).setValue(i);

        return key;
    }


    public Bitmap searchImage(String idImage) {
        //refData.child("history/"+idImage).
        StorageReference islandRef = storageRef.child("Images/IdKids/5621436a-73d4-4ac7-b8c8-8d05053b74fb.png");
        final Bitmap[] bitmap = new Bitmap[1];
        final long ONE_MEGABYTE = 1024 * 1024;
        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                bitmap[0] = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                // Data for "images/island.jpg" is returns, use this as needed
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
      return bitmap[0];
    }

    public String addWriting(Writing w) {
        String key = refData.child("Writing").push().getKey();
        refData.child("Writing").child(key).setValue(w);

        return key;
    }


}
