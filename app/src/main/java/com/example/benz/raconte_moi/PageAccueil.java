package com.example.benz.raconte_moi;



import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ikimuhendis.ldrawer.ActionBarDrawerToggle;
import com.ikimuhendis.ldrawer.DrawerArrowDrawable;
import android.app.Dialog;

import android.content.Context;
import android.content.DialogInterface;

import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;

import android.view.MenuInflater;

import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.benz.raconte_moi.DAO.Child;
import com.example.benz.raconte_moi.DAO.DAO;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.view.ContextMenu;
import java.util.ArrayList;
import java.util.List;


public class PageAccueil extends AppCompatActivity implements View.OnClickListener ,AdapterView.OnItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerArrowDrawable drawerArrow;
    private boolean drawerArrowColor;

    View rowView = null;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference refData = database.getReference();
    EditText etPrenom, etAge;
    FloatingActionButton bAjoutEnfant;
    TextView mail, prenom, nom;
    static String idUser = null;
    DAO d;
    ArrayList<Child> items;
    ArrayList<String> itemsKeys;
    ChildAdapter itemsAdapter;
    ListView listView;
    static int positionItem;

    private String item = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_accueil);

        bAjoutEnfant = (FloatingActionButton) findViewById(R.id.bAjoutEnfant);

        prenom = (TextView) findViewById(R.id.tePrenomP);
        nom = (TextView) findViewById(R.id.teNomP);
        mail = (TextView) findViewById(R.id.teMail);

        nom.setText(getIntent().getExtras().getString("nom"));
        prenom.setText(getIntent().getExtras().getString("prenom"));
        mail.setText(getIntent().getExtras().getString("mail"));

        idUser = getIntent().getExtras().getString("id");


        bAjoutEnfant.setOnClickListener(this);

        // kids list
        items = new ArrayList<Child>();
        itemsKeys = new ArrayList<String>();
        // get all kids of id parent
        refData.child("children").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("count", "" + dataSnapshot.getChildrenCount());

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Child u = child.getValue(Child.class);
                    if (u.getIdUser().toString().equals(idUser)) {
                        items.add(u);
                        itemsKeys.add(child.getKey());

                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        itemsAdapter = new ChildAdapter(this, R.layout.item_kid, items);
        listView = (ListView) findViewById(R.id.listKids);
        listView.setAdapter(itemsAdapter);
        itemsAdapter.notifyDataSetChanged();
        registerForContextMenu(listView);

        // drawerView

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.navdrawer);


        drawerArrow = new DrawerArrowDrawable(this) {
            @Override
            public boolean isLayoutRtl() {
                return false;
            }
        };
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                drawerArrow, R.string.drawer_open,
                R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();


        String[] values = new String[]{
                "Edit Profil",
                "Log out"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position) {
                    case 0:

                        mDrawerToggle.setAnimateEnabled(false);
                        drawerArrow.setProgress(0f);
                        break;
                    case 1:
                        mDrawerToggle.setAnimateEnabled(false);
                        drawerArrow.setProgress(1f);
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

                        break;

                }

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
                mDrawerLayout.closeDrawer(mDrawerList);
            } else {
                mDrawerLayout.openDrawer(mDrawerList);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onContextItemSelected(final MenuItem item) {

        // TODO Auto-generated method stub
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        switch (item.getItemId()) {
            case R.id.delete_item:
                AlertDialog.Builder alert = new AlertDialog.Builder(
                        PageAccueil.this);
                alert.setTitle("Alert!!");
                alert.setMessage("Are you sure to delete kid");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DAO d = new DAO();
                        d.deleteKid(itemsKeys.get(positionItem));
                        items.remove(positionItem);
                        itemsKeys.remove(positionItem);
                        itemsAdapter.notifyDataSetChanged();
                        Toast.makeText(PageAccueil.this, "Kid deleted", Toast.LENGTH_LONG).show();
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
            case R.id.edit_item:
                     /**/
                // Spinner element
                final Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.activity_ajout_enfant);
                Spinner spinner = (Spinner) dialog.findViewById(R.id.spinnerSex);

                // Spinner click listener
                spinner.setOnItemSelectedListener(PageAccueil.this);

                // Spinner Drop down elements
                List<String> categories = new ArrayList<String>();
                categories.add("Male");
                categories.add("Female");

                // Creating adapter for spinner
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

                // Drop down layout style - list view with radio button
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // attaching data adapter to spinner
                spinner.setAdapter(dataAdapter);


                dialog.setTitle("Edit Kids");
                dialog.show();
                etPrenom = (EditText) dialog.findViewById(R.id.etPrenom);
                etAge = (EditText) dialog.findViewById(R.id.etAge);
                etPrenom.setText(items.get(positionItem).getNameChild());
                etAge.setText(String.valueOf(items.get(positionItem).getAge()));
                Button dialogButtonAdd = (Button) dialog.findViewById(R.id.bValider);
                dialogButtonAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d = new DAO();
                        Child c = new Child(etPrenom.getText().toString(), Integer.parseInt(etAge.getText().toString()), "Male", idUser);
                        d.editChildren(c, itemsKeys.get(positionItem));
                        items.removeAll(items);
                        itemsKeys.removeAll(itemsKeys);
                        Toast.makeText(PageAccueil.this, "Kid successfully edited", Toast.LENGTH_LONG).show();
                        refData.child("children").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Log.e("count", "" + dataSnapshot.getChildrenCount());

                                for (DataSnapshot child : dataSnapshot.getChildren()) {
                                    Child u = child.getValue(Child.class);
                                    if (u.getIdUser().toString().equals(idUser)) {
                                        items.add(u);
                                        itemsKeys.add(child.getKey());

                                    }

                                }
                                itemsAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }

                        });
                        itemsAdapter.notifyDataSetChanged();
                        listView.setAdapter(itemsAdapter);
                        //itemsAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

                Button dialogButtonCancel = (Button) dialog.findViewById(R.id.annuler);
                dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                //startActivity(new Intent(this, t.class));
                break;
            //Toast.makeText(PageAccueil.this, "not implemented yet", Toast.LENGTH_LONG).show();
            //return true;
            case R.id.seeReport_item:
                Toast.makeText(PageAccueil.this, "not implemented yet", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
        return  true;
    }

@Override
public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
       item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        // Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
        }

public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
        }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bAjoutEnfant:
                /**/
                 // Spinner element
                final Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.activity_ajout_enfant);
        Spinner spinner = (Spinner) dialog.findViewById(R.id.spinnerSex);

        // Spinner click listener
        spinner.setOnItemSelectedListener(PageAccueil.this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Male");
        categories.add("Female");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);


                dialog.setTitle("add Kids");
                dialog.show();
                etPrenom = (EditText) dialog.findViewById(R.id.etPrenom);
                etAge = (EditText) dialog.findViewById(R.id.etAge);
                Button dialogButtonAdd = (Button) dialog.findViewById(R.id.bValider);
                dialogButtonAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d = new DAO();
                        Child c = new Child(etPrenom.getText().toString(), Integer.parseInt(etAge.getText().toString()), item, idUser);
                        d.addChildren(c);
                        Toast.makeText(PageAccueil.this, "Kid successfully added", Toast.LENGTH_LONG).show();
                        items.add(c);
                        itemsAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

                Button dialogButtonCancel = (Button) dialog.findViewById(R.id.annuler);
                dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                //startActivity(new Intent(this, t.class));
                break;

        }
    }


    // override methode for contextuel


    // adtateur paersonalizer
    public class ChildAdapter extends ArrayAdapter<Child> {


        public ChildAdapter(Context context, int resource, ArrayList<Child> items) {
            super(context, resource, items);
        }

        public ChildAdapter(Context context, ArrayList<Child> users) {
            super(context, 0, users);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // Get the data item for this position

            Child kid = getItem(position);
            LayoutInflater inflater = (LayoutInflater) parent.getContext().
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //View v= convertView;
            // Check if an existing view is being reused, otherwise inflate the view

            if (null == convertView) {
                rowView = inflater.inflate(R.layout.item_kid, parent, false);
            } else {
                // PAS TOUJOURS ADAPTE AUX BESOINS //
                // IL FAUDRA PARFOIS TOUJOURS JETER LE convertViewFOURNI
                rowView = convertView;
            }
            if (position % 2 == 0)
                rowView.setBackgroundColor(Color.WHITE);
            else rowView.setBackgroundColor(Color.WHITE);
            // Lookup view for data population
            TextView tvName = (TextView) rowView.findViewById(R.id.itemNom);
            ImageView sex = (ImageView) rowView.findViewById(R.id.itemSex);
            // Populate the data into the template view using the data object
            tvName.setText(kid.getNameChild());

            TextView tvAge = (TextView) rowView.findViewById(R.id.itemAge);
            // Populate the data into the template view using the data object
            tvName.setText(kid.getNameChild());
            tvAge.setText(String.valueOf(kid.getAge()));
            if (kid.getSex().equals("Female")){
                sex.setImageResource(R.drawable.girl);
            }else
            {
                sex.setImageResource(R.drawable.boy);
            }
            // delete item whene click on it
            rowView.setTag(position);
            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                /// go to activity manager
                public void onClick(View view) {

                    int position = (Integer) view.getTag();

                    String idChild = itemsKeys.get(position);
                    Intent intent = new Intent(PageAccueil.this, ActivityManager.class);
                    intent.putExtra("idChild", idChild);
                    startActivity(intent);

                }
            });
            //rowView.setLongClickable(true);
           rowView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                @Override
                public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                    MenuInflater inflater = getMenuInflater();
                    positionItem = position;
                    inflater.inflate(R.menu.stock_item_menu, menu);


                }
            });

           /*rowView.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View arg0) {

                    positionItem=
                    return true;
                }
            });*/

            // Return the completed view to render on screen
            return rowView;
        }
    }

}