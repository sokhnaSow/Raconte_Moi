package com.example.benz.raconte_moi;

import com.example.benz.raconte_moi.ClassForTesting.AjoutEnfantService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;
import static junit.framework.Assert.assertEquals;

/**
 * Created by mouna on 05/03/2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class AjoutEnfantTest {

    @Test
    public void shouldNameChildNotIsEmpty() throws Exception {
        AjoutEnfantService nameChild = Mockito.mock(AjoutEnfantService.class);
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
        AjoutEnfantService sex = Mockito.mock(AjoutEnfantService.class);
        when(sex.getSex()).thenReturn(toString());
        assertEquals(sex.getSex(),toString());
    }


    @Test
    public void shouldIdUserNotIsExist() throws Exception {
        AjoutEnfantService idUser = Mockito.mock(AjoutEnfantService.class);
        when(idUser.getIdUser()).thenReturn(toString());
        assertEquals(idUser.getIdUser(),toString());
    }

}
