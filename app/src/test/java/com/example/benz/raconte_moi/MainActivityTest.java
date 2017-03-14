package com.example.benz.raconte_moi;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by mouna on 05/03/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class MainActivityTest{

    @Mock
    FirebaseDatabase database;

    @Mock
    DatabaseReference databaseReference;

    @Test
    public void test1(){
        //when(database.push())
    }


/*
@RunWith(MockitoJUnitRunner.class)
public class MainActivityTest {

    @Test
    public void test1(){
        //create mock
        MainActivity test = Mockito.mock(MainActivity.class);

        //define return value for method getUsername()
        when(test.getUsername()).thenReturn(toString());

        // use mock in test
        assertEquals(test.getUsername(),toString());

    }
*/



    /**
     * @Test
    public void test1()  {
    //  create mock
    MyClass test = Mockito.mock(MyClass.class);

    // define return value for method getUniqueId()
    when(test.getUniqueId()).thenReturn(43);

    // use mock in test....
    assertEquals(test.getUniqueId(), 43);
    }
     */


/*
    @Mock
    private MainView view;
    @Mock
    private MainService service;
    private MainPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new MainPresenter(view, service);

    }

    @Test
    public void shouldUserNameNotIsEmpty() throws Exception {
        when(view.getUsername()).thenReturn("");
        presenter.onMainClicked();

        verify(view).showUserNameError(R.id.etMail);

    }

    @Test
    public void shouldPwdNotIsEmpty() throws Exception {
        when(view.getUsername()).thenReturn("doudou");
        when(view.getPwd()).thenReturn("");
        presenter.onMainClicked();

        verify(view).showPwdError(R.id.etMotDePasse);

    }
    */
}
