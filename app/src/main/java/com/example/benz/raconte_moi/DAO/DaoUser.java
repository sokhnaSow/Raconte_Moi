package com.example.benz.raconte_moi.DAO;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nadia on 28/02/2017.
 */

public class DaoUser {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference refData = database.getReference();

    List<User> users = new ArrayList<User>();



    public String addUser(User user){

        String key = refData.child("user").push().getKey();
        user.setIdUser(key);
        refData.child("user").child(key).setValue(user);
        users.add(user);
        return key;
    }

    public List<User> getUsers() {
        return users;
    }

}
