package com.example.benz.raconte_moi.DAO;


import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by nadia on 17/02/2017.
 */




public class DAO {



    History h;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference refData = database.getReference();

    public void addHistory(History history){

        refData.child("history").child(String.valueOf(history.getIdHistory())).setValue(history);

    }

    public void removeHistory(int idHistory){

        refData.child("history/"+idHistory+"").removeValue();

    }

    public void updateHistory(int idHistory , History newHistory){

    }

    public History searchHistory(int idHistory){
        refData.child("history/"+idHistory+"").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                 h = dataSnapshot.getValue(History.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return  h;
    }


}
