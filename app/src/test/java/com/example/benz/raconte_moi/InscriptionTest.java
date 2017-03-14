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
public class InscriptionTest {

    @Test
    public void shouldFirstNameNotIsEmpty() throws Exception {

        User firstname = Mockito.mock(User.class);
        when(firstname.getFirstnameUser()).thenReturn(toString());
        assertEquals(firstname.getFirstnameUser(),toString());
    }

    @Test
    public void shouldLastNameNotIsEmpty()throws Exception{

        User lastName = Mockito.mock(User.class);
        when(lastName.getLastnameUser()).thenReturn(toString());
        assertEquals(lastName.getLastnameUser(),toString());

    }

    @Test
    public void shouldMailNotIsEmpty() throws Exception {

        User mail = Mockito.mock(User.class);
        when(mail.getMail()).thenReturn(toString());
        assertEquals(mail.getMail(), toString());

    }

    @Test
    public void shouldIdUserIsCorrect() throws Exception {

        User idUser = Mockito.mock(User.class);
        when(idUser.getIdUser()).thenReturn(toString());
        assertEquals(idUser.getIdUser(), toString());

    }

}
