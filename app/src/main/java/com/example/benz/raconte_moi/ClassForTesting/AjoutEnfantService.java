package com.example.benz.raconte_moi.ClassForTesting;

import com.example.benz.raconte_moi.DAO.Child;

/**
 * Created by mouna on 06/03/2017.
 */

public class AjoutEnfantService implements AjoutEnfantPresenter{

    private String nameChild;
    private int age ;
    private String sex;
    private String idUser;
    private Child child = new Child(nameChild, age, sex, idUser);

    @Override
    public String getNameChild() {
        return child.getNameChild();
    }

    @Override
    public int getAge() {
        return child.getAge();
    }

    @Override
    public String getSex() {
        return child.getSex();
    }

    @Override
    public String getIdUser() {
        return child.getIdUser();
    }
}
