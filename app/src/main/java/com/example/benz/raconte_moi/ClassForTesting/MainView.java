package com.example.benz.raconte_moi.ClassForTesting;

/**
 * Created by mouna on 06/03/2017.
 */
public interface MainView {
    String getUsername();
    void showUserNameError(int idMail);

    String getPwd();
    void showPwdError(int idPwd);

}
