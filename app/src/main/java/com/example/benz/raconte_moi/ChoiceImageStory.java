package com.example.benz.raconte_moi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.example.benz.raconte_moi.DAO.History;
import com.example.benz.raconte_moi.DAO.Writing;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ChoiceImageStory extends AppCompatActivity {

    private GridView gridView;
    private GridViewAdapter gridAdapter;

    FirebaseStorage storage;
    StorageReference storageRef;
    FirebaseDatabase database;
    DatabaseReference refData;

    Button myStories;


    ImageItem it ;
    ArrayList<ImageItem> imageItems;
    int i = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_image_story);



        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        database = FirebaseDatabase.getInstance();
        refData = database.getReference();
        gridView = (GridView) findViewById(R.id.gridHistory);
        imageItems = new ArrayList<>();
        myStories= (Button) findViewById(R.id.btnMyStory);

        final ArrayList<String> idstories= new ArrayList<>();

        refData.child("Writing").addListenerForSingleValueEvent(new ValueEventListener() {
            ImageItem it ;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                gridAdapter = new GridViewAdapter(ChoiceImageStory.this, R.layout.grid_history,imageItems);
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Writing w = child.getValue(Writing.class);
                    if (w.isValide() && !(w.getCategorie().equals("Drawing"))) {
                        idstories.add(w.getIdHistory());
                    }
                }

                refData.child("history").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            for (String s : idstories) {
                                History h = child.getValue(History.class);
                                if (child.getKey().equals(s)) {
                                    it = new ImageItem(h.getTitle(), child.getKey());
                                    imageItems.add(it);
                                    gridAdapter.setData(imageItems);
                                    gridView.setAdapter(gridAdapter);
                                }
                            }
                        }
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


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Intent intent = new Intent(ChoiceImageStory.this, ReadingDrawing.class);
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {

                Intent intent2 = getIntent();
                String idChild = intent2.getStringExtra("idChild");

                ImageItem item = (ImageItem) parent.getItemAtPosition(position);
                //Create intent
                intent.putExtra("title", item.getTitle());
                intent.putExtra("idHistory",item.getIdHistory());
                intent.putExtra("idChild",idChild);




                startActivity(intent);
            }
        });
        myStories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent2 = getIntent();
                String idChild = intent2.getStringExtra("idChild");

                Intent intent = new Intent(ChoiceImageStory.this, ChoiseMyImagestorie.class);
                intent.putExtra("idChild",idChild);
                startActivity(intent);

            }
        });

    }
}
