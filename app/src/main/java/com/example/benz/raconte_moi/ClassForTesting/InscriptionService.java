package com.example.benz.raconte_moi.ClassForTesting;

import com.example.benz.raconte_moi.DAO.User;

/**
 * Created by mouna on 07/03/2017.
 */

public class InscriptionService implements InscriptionPresenter{

    private String firstnameUser;
    private String lastnameUser;
    private String mail;
    private User user = new User(firstnameUser,lastnameUser,mail);

    @Override
    public String getLastName() {

        return user.getLastnameUser();
    }

    @Override
    public String getFirstName() {

        return user.getFirstnameUser();
    }

    @Override
    public String getMail() {

        return user.getMail();
    }

    @Override
    public String getPwd() {

        return toString();
    }
}
