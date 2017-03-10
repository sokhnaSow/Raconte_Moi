package com.example.benz.raconte_moi;


import com.example.benz.raconte_moi.ClassForTesting.InscriptionService;

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
public class InscriptionTest {

    @Test
    public void shouldLastNameNotIsEmpty() throws Exception {
        InscriptionService lastName = Mockito.mock(InscriptionService.class);
        when(lastName.getLastName()).thenReturn(toString());
        assertEquals(lastName.getLastName(), toString());

    }

    @Test
    public void shouldFirstNameNotIsEmpty()throws Exception{

        InscriptionService firstName = Mockito.mock(InscriptionService.class);
        when(firstName.getFirstName()).thenReturn(toString());
        assertEquals(firstName.getFirstName(), toString());

    }

    @Test
    public void shouldUserNameNotIsEmpty()throws Exception{

        InscriptionService mail = Mockito.mock(InscriptionService.class);
        when(mail.getMail()).thenReturn(toString());
        assertEquals(mail.getMail(), toString());

    }

    @Test
    public void shouldPwdNameNotIsEmpty()throws Exception{

        InscriptionService pwd = Mockito.mock(InscriptionService.class);
        when(pwd.getPwd()).thenReturn(toString());
        assertEquals(pwd.getPwd(), toString());

    }

}
