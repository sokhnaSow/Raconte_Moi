package com.example.benz.raconte_moi;



import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ikimuhendis.ldrawer.DrawerArrowDrawable;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;


public class PageAccueil extends AppCompatActivity  {
    private static final int PROFILE_SETTING = 1;

    //save our header or result
    private AccountHeader headerResult = null;
    private Drawer result = null;

    private IProfile profile;



    private DrawerArrowDrawable drawerArrow;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference refData = database.getReference();

    String mail, prenom, nom;

    ArrayList<Fragment> fr ;
    private String idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        nom = getIntent().getExtras().getString("nom");
        prenom= getIntent().getExtras().getString("prenom");
        mail = getIntent().getExtras().getString("mail");

        idUser = getIntent().getExtras().getString("id");

        Bundle bundle = new Bundle();
        String myMessage = "Stackoverflow is cool!";
        bundle.putString("nom",nom );
        bundle.putString("prenom",prenom );
        bundle.putString("mail",mail );
        bundle.putString("id",idUser );

        FragmentHome fra1 = new FragmentHome();
        fra1.setArguments(bundle);
        validateStoryFragment fra2 = new validateStoryFragment();
        fra2.setArguments(bundle);
        fr = new ArrayList<Fragment>();
        fr.add(fra1);
        fr.add(fra2);
       // fr.add(new fragment2());
        final FragmentManager fm = getFragmentManager();

        final FragmentTransaction ft = fm.beginTransaction();

        ft.add(R.id.fragmentContainer,fr.get(0));
        ft.commit();
        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.drawer_item_advanced_drawer);

        // Create a few sample profile
        profile = new ProfileDrawerItem().withName(nom.toString()+prenom.toString()).withEmail(mail.toString()).withIcon(getResources().getDrawable(R.drawable.profile));

        // Create the AccountHeader
        buildHeader(false, savedInstanceState);

        //Create the drawer
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_home).withIcon(FontAwesome.Icon.faw_home)
                        .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener(){
                            @Override
                            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                final FragmentTransaction ft = fm.beginTransaction();

                                ft.replace(R.id.fragmentContainer,fr.get(0));
                                ft.commit();
                                return false;
                            }

        }),
                        new PrimaryDrawerItem().withName("Validate Story").withIcon(FontAwesome.Icon.faw_home)
                                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener(){
                                    @Override
                                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                        final FragmentTransaction ft = fm.beginTransaction();
                                        ft.replace(R.id.fragmentContainer,fr.get(1));
                                        //ft.add(R.id.fragmentContainer,fr.get(1));
                                        ft.commit();
                                        return false;
                                    }

                                })
                        //here we use a customPrimaryDrawerItem we defined in our sample app
                        //this custom DrawerItem extends the PrimaryDrawerItem so it just overwrites some methods
                ) // add the items we want to use with our Drawer
                .withOnDrawerNavigationListener(new Drawer.OnDrawerNavigationListener() {
                    @Override
                    public boolean onNavigationClickListener(View clickedView) {
                        //this method is only called if the Arrow icon is shown. The hamburger is automatically managed by the MaterialDrawer
                        //if the back arrow is shown. close the activity
                        PageAccueil.this.finish();
                        //return true if we have consumed the event
                        return true;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();
        result.openDrawer();

    }

    /**
     * small helper method to reuse the logic to build the AccountHeader
     * this will be used to replace the header of the drawer with a compact/normal header
     *
     * @param compact
     * @param savedInstanceState
     */
    private void buildHeader(boolean compact, Bundle savedInstanceState) {
        // Create the AccountHeader
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .withCompactStyle(compact)
                .addProfiles(
                        profile,
                        new ProfileSettingDrawerItem().withName("Manage Account").withIcon(GoogleMaterial.Icon.gmd_settings).withIdentifier(100001),
                new ProfileSettingDrawerItem().withName("Log out").withIcon(GoogleMaterial.Icon.gmd_settings).withIdentifier(100001)
                        .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener(){
                            @Override
                            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                AlertDialog.Builder alert = new AlertDialog.Builder(
                                        PageAccueil.this);
                                alert.setTitle("Alert!!");
                                alert.setMessage("Are you sure to loging out");

                                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        final ProgressDialog progressDialog = new ProgressDialog(PageAccueil.this, R.style.AppTheme_Dark_Dialog);
                                        progressDialog.setIndeterminate(true);
                                        progressDialog.setMessage("Login out...");
                                        progressDialog.show();
                                        Intent intent = new Intent(PageAccueil.this, MainActivity.class);
                                        startActivity(intent);
                                        dialog.dismiss();

                                    }
                                });
                                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        dialog.dismiss();
                                    }
                                });

                                alert.show();
                                return true;
                            }


                        })



                        //don't ask but google uses 14dp for the add account icon in gmail but 20dp for the normal icons (like manage account)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        //sample usage of the onProfileChanged listener
                        //if the clicked item has the identifier 1 add a new profile ;)
                        if (profile instanceof IDrawerItem && ((IDrawerItem) profile).getIdentifier() == PROFILE_SETTING) {
                            IProfile newProfile = new ProfileDrawerItem().withNameShown(true).withName("Batman").withEmail("batman@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile));
                            if (headerResult.getProfiles() != null) {
                                //we know that there are 2 setting elements. set the new profile above them ;)
                                headerResult.addProfile(newProfile, headerResult.getProfiles().size() - 2);
                            } else {
                                headerResult.addProfiles(newProfile);
                            }
                        }

                        //false if you have not consumed the event and it should close the drawer
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = result.saveInstanceState(outState);
        //add the values which need to be saved from the accountHeader to the bundle
        outState = headerResult.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    /// end profil


}