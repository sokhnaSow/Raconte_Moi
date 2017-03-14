package com.example.benz.raconte_moi.DAO;


import android.content.Intent;
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
import android.util.Log;

import com.example.benz.raconte_moi.MainActivity;
import com.example.benz.raconte_moi.PageAccueil;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by nadia on 17/02/2017.
 */




public class DAO {




    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference refData = database.getReference();
    HashMap<String, String> titles = new HashMap<String, String>();

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


    public String addImage(Bitmap bitmap, String path) {

      // store image on firebase storage
        System.out.println(path);
        StorageReference mountainsRef = storageRef.child(path);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        mountainsRef.putBytes(baos.toByteArray());

        // store images path on firebase database
        String key = refData.child("Images").push().getKey();
        Image i = new Image(path);
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

    public String addChildren(Child children){


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
        StorageReference ref = storageRef.child("Images/IdKids/4200e14b-77dd-4fb2-aa93-88193f3ee6c8.png");
        final Bitmap[] bitmap = new Bitmap[1];
        final long ONE_MEGABYTE = 1024 * 1024;
        ref.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                bitmap[0] = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

            }
        });
        return bitmap[0];
    }

    public String addWriting(Writing w) {
        String key = refData.child("Writing").push().getKey();
        refData.child("Writing").child(key).setValue(w);

        return key;
    }

    public HashMap<String, String> getTitlesHistory(final String idKids) {
       final ArrayList<String> titlesId = new ArrayList<String>();
        // HashMap<Titles, Keys>
       // final HashMap<String, String> titles = new HashMap<String, String>();
        refData.child("Writing").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("count",""+dataSnapshot.getChildrenCount());


                for(DataSnapshot writing : dataSnapshot.getChildren()){
                    Writing w = writing.getValue(Writing .class);
                    if(w.getIdChild().equals(idKids)){
                      titlesId.add(w.getIdHistory());
                        System.out.println(w.getIdHistory());
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        System.out.println();
        refData.child("history").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("count",""+dataSnapshot.getChildrenCount());


                for(DataSnapshot h : dataSnapshot.getChildren()){
                    String w = h.getKey();
                    History v = h.getValue(History.class);
                    for(String t : titlesId){
                        if(w.equals(t)){
                            titles.put(w,v.getTitle());
                            System.out.println(v.getTitle());

                            //return titles;
                        }

                    }


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        //System.out.println(titles.size());
        return titles;
    }


    public void deleteImage(final Bitmap b, final String pathImage) {
        // Create a storage reference from our app
       // StorageReference storageRef = storage.getReference();

// Create a reference to the file to delete
        StorageReference desertRef = storageRef.child(pathImage);

// Delete the file
        desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //addImage(b,pathImage);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Uh-oh, an error occurred!
            }
        });
    }

    public void deleteKid(String s) {
        // Create a storage reference from our app
        // StorageReference storageRef = storage.getReference();
// Create a reference to the file to delete
        Task desertRef = refData.child("children").child(s).removeValue();

    }

    public void editChildren(Child c, String s) {
        refData.child("children").child(s).setValue(c);
    }
}
