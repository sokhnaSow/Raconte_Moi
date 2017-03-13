package com.example.benz.raconte_moi;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.benz.raconte_moi.DAO.Child;
import com.example.benz.raconte_moi.DAO.DAO;
import com.example.benz.raconte_moi.DAO.User;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.ContextMenu.ContextMenuInfo;
import java.util.ArrayList;
import java.util.prefs.Preferences;


public class PageAccueil extends AppCompatActivity implements View.OnClickListener {
    View rowView = null;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference refData = database.getReference();
    EditText etPrenom, etAge, etSex;
    Button bActivite, bDeconnecter;
    FloatingActionButton bAjoutEnfant;
    TextView mail, prenom, nom;
    static String idUser = null;
    DAO d;
    ArrayList<Child> items;
    ArrayList<String> itemsKeys;
    ChildAdapter itemsAdapter;
    ListView listView;
    static int positionItem;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

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
        registerForContextMenu(listView);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        // reference to listviex



    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        // TODO Auto-generated method stub
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        switch (item.getItemId()) {
            case R.id.delete_item:
                AlertDialog.Builder alert = new AlertDialog.Builder(
                        PageAccueil.this);
                alert.setTitle("Alert!!");
                alert.setMessage("Are you sure to delete record");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println(itemsKeys.get(positionItem));
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
                Toast.makeText(PageAccueil.this, "not implemented yet", Toast.LENGTH_LONG).show();
                return true;
            case R.id.seeReport_item:
                Toast.makeText(PageAccueil.this, "not implemented yet", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(PageAccueil.this, Report.class);
                intent.putExtra("idChild", itemsKeys.get(positionItem));
                System.out.println(itemsKeys.get(positionItem));
                startActivity(intent);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bAjoutEnfant:
                final Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.activity_ajout_enfant);
                dialog.setTitle("add Kids");
                dialog.show();
                etPrenom = (EditText) dialog.findViewById(R.id.etPrenom);
                etAge = (EditText) dialog.findViewById(R.id.etAge);
                etSex = (EditText) dialog.findViewById(R.id.etSex);
                Button dialogButtonAdd = (Button) dialog.findViewById(R.id.bValider);
                dialogButtonAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d = new DAO();
                        Child c = new Child(etPrenom.getText().toString(), Integer.parseInt(etAge.getText().toString()), etSex.getText().toString(), idUser);
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





    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("PageAccueil Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
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
                rowView.setBackgroundColor(Color.LTGRAY);
            else rowView.setBackgroundColor(Color.WHITE);
            // Lookup view for data population
            TextView tvName = (TextView) rowView.findViewById(R.id.itemNom);
            // Populate the data into the template view using the data object
            tvName.setText(kid.getNameChild());

            TextView tvAge = (TextView) rowView.findViewById(R.id.itemAge);
            // Populate the data into the template view using the data object
            tvName.setText(kid.getNameChild());
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