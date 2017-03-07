package com.example.benz.raconte_moi.ClassForTesting;

//class qui va interagir avec la vue

import com.example.benz.raconte_moi.R;

/**
 * Created by mouna on 05/03/2017.
 */
public class MainPresenter {
    private final MainView view;
    private MainService service;


    public MainPresenter(MainView view, MainService service ) {
        this.view = view;
        this.service = service;
    }


    public void onMainClicked() {
        String username = view.getUsername();
        if (username.isEmpty()){
            view.showUserNameError(R.id.etMail);
            return;
        }
        String pwd = view.getPwd();
        if (pwd.isEmpty()) {
            view.showPwdError(R.id.etMotDePasse);
            return;
        }
    }
}
