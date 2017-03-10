package com.example.benz.raconte_moi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.benz.raconte_moi.DAO.History;
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
        gridView = (GridView) findViewById(R.id.gridHistory);
        imageItems = new ArrayList<>();


        refData.child("history").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                gridAdapter = new GridViewAdapter(ChoiceDrawing.this, R.layout.grid_history,imageItems);
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    ImageItem it ;
                    History hist = child.getValue(History.class);
                    it=new ImageItem(hist.getTitle(),child.getKey());
                    imageItems.add(it);
                    gridAdapter.setData(imageItems);
                    gridView.setAdapter(gridAdapter);
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
                intent.putExtra("title", item.getTitle());
                intent.putExtra("idHistory",item.getIdHistory());

                startActivity(intent);
            }
        });

    }
}

