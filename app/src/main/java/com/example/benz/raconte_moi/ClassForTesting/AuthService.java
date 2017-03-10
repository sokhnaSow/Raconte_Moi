package com.example.benz.raconte_moi.ClassForTesting;

import com.example.benz.raconte_moi.DAO.User;

/**
 * Created by mouna on 06/03/2017.
 */
public class AuthService implements AuthPresenter {

    User user;

    @Override
    public String getUsername () {
        user = new User();
        return user.getMail();
    }

    @Override
    public String getPwd() {

        user = new User();
        return toString(); //à ameliorer
    }

}
