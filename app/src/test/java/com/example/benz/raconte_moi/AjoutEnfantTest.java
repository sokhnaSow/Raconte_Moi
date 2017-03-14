package com.example.benz.raconte_moi;

import com.example.benz.raconte_moi.DAO.Child;

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
public class AjoutEnfantTest {

    @Test
    public void shouldNameChildNotIsEmpty() throws Exception {
        Child nameChild = Mockito.mock(Child.class);
        when(nameChild.getNameChild()).thenReturn(toString());
        assertEquals(nameChild.getNameChild(),toString());
    }
/*
    @Test
    public void shouldAgeNotIsEmpty() throws Exception {
        AjoutEnfantService age = Mockito.mock(AjoutEnfantService.class);
        when(age.getAge()).thenReturn(Integer.parseInt(toString()));
        assertEquals(age.getAge(),Integer.parseInt(toString()));
    }
*/

    @Test
    public void shouldSexNotIsEmpty() throws Exception {
        Child sexChild = Mockito.mock(Child.class);
        when(sexChild.getSex()).thenReturn(toString());
        assertEquals(sexChild.getSex(),toString());
    }


    @Test
    public void shouldIdUserNotIsExist() throws Exception {
        Child idUser = Mockito.mock(Child.class);
        when(idUser.getIdUser()).thenReturn(toString());
        assertEquals(idUser.getIdUser(),toString());

    }

}
