package com.example.benz.raconte_moi;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

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

public class ChoiceDrawing extends AppCompatActivity {

    private GridView gridView;
    private GridViewAdapter gridAdapter;

    FirebaseStorage storage;
    StorageReference storageRef;
    FirebaseDatabase database;
    DatabaseReference refData;


    ImageItem it ;
     ArrayList<ImageItem> imageItems;
    int i = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_drawing);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        database = FirebaseDatabase.getInstance();
        refData = database.getReference();

        StorageReference ref = storageRef.child("Images/IdKids/4200e14b-77dd-4fb2-aa93-88193f3ee6c8.png");

        gridView = (GridView) findViewById(R.id.gridHistory);
        imageItems = new ArrayList<>();


        refData.child("Images").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                gridAdapter = new GridViewAdapter(ChoiceDrawing.this, R.layout.item_history,imageItems);

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    final Image image = child.getValue(Image.class);
                    StorageReference ref = storageRef.child(image.getPathImage());
                    final long ONE_MEGABYTE = 1024 * 1024;
                    ref.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            it=new ImageItem(bitmap,image.getPathImage());
                            imageItems.add(it);
                            gridAdapter.setData(imageItems);
                            gridView.setAdapter(gridAdapter);
                        }
                    });
                    i++;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            Intent intent = new Intent(ChoiceDrawing.this, ReadingDrawing.class);

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {

                ImageItem item = (ImageItem) parent.getItemAtPosition(position);
                //Create intent
                System.out.println(item.getTitle());
                intent.putExtra("path", item.getTitle());
                //intent.putExtra("bitmap", item.getImage());
                startActivity(intent);
            }
        });

    }
}

