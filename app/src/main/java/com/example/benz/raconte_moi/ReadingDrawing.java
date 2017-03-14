package com.example.benz.raconte_moi;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

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

    public String idImage ;
    public FirebaseStorage storage;
    public StorageReference storageRef;
    public FirebaseDatabase database;
    public DatabaseReference refData;
    public ImageView imageView;
    public TextView titleTextView;
    public ArrayList<String> l ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_drawing);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        database = FirebaseDatabase.getInstance();
        refData = database.getReference();


         String title = getIntent().getStringExtra("title");
         final String idHistory = getIntent().getStringExtra("idHistory");

        imageView = (ImageView) findViewById(R.id.image);
        titleTextView = (TextView) findViewById(R.id.title);
        l=new ArrayList<String>();

        System.out.println(title);

        refData.child("Illustration").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Illustration u = child.getValue(Illustration.class);
                    if(u.getIdHistory().equals(idHistory)){
                        idImage=u.getIdImage();
                        titleTextView.setText(u.getParagraphe());
                        System.out.println("id; " + idImage);
                        refData.child("Images").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot child : dataSnapshot.getChildren()) {
                                    Image i = child.getValue(Image.class);
                                    System.out.println("idimage : "+ idImage);
                                    System.out.println("getIdimage :" + i.getPathImage());
                                    if(child.getKey().equals(idImage)){
                                        StorageReference ref = storageRef.child(i.getPathImage());
                                        final long ONE_MEGABYTE = 1024 * 1024;
                                        ref.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                            @Override
                                            public void onSuccess(byte[] bytes) {
                                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                                imageView.setImageBitmap(bitmap);
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
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                /*
                ****Code*****
                 */

            }

        });




    }
}
