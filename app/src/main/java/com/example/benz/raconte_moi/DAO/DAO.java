package com.example.benz.raconte_moi.DAO;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by nadia on 17/02/2017.
 */




public class DAO {




    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference refData = database.getReference();

    // Ajouter histoire
    public String addHistory(final History history){

        // key contient la clé primaire de histoire généré automatiquement

        String key = refData.child("history").push().getKey();
        refData.child("history").child(key).setValue(history);

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





}
