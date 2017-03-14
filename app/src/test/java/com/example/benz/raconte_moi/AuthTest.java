package com.example.benz.raconte_moi;

import com.example.benz.raconte_moi.DAO.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by mouna on 05/03/2017.
 */


@RunWith(MockitoJUnitRunner.class)
public class AuthTest {

    @Test
    public void shouldUserNameNotIsEmpty() throws Exception {

        //create mock
        User userName = Mockito.mock(User.class);

        //define return value for method getUsername()
        when(userName.getMail()).thenReturn(toString());

        // use mock in test
        assertEquals(userName.getMail(),toString());


    }

    @Test
    public void shouldPwdNameNotIsEmpty()throws Exception{

        User pwd = Mockito.mock(User.class);
        when(pwd.toString()).thenReturn(toString());
        assertEquals(pwd.toString(),toString());

    }
}
