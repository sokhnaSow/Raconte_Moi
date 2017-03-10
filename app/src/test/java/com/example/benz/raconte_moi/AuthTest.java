package com.example.benz.raconte_moi;

import com.example.benz.raconte_moi.ClassForTesting.AuthService;

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
        AuthService username = Mockito.mock(AuthService.class);

        //define return value for method getUsername()
        when(username.getUsername()).thenReturn(toString());

        // use mock in test
        assertEquals(username.getUsername(), toString());

    }

    @Test
    public void shouldPwdNameNotIsEmpty()throws Exception{

        AuthService pwd = Mockito.mock(AuthService.class);
        when(pwd.getPwd()).thenReturn(toString());
        assertEquals(pwd.getPwd(), toString());

    }
}
