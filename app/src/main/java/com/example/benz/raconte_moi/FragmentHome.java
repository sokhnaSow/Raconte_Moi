package com.example.benz.raconte_moi;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentHome.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentHome#newInstance} factory method to
 * create an instance of this fragment.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class FragmentHome extends Fragment implements View.OnClickListener ,AdapterView.OnItemSelectedListener {
    View rowView = null;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference refData = database.getReference();
    EditText etPrenom, etAge;
    FloatingActionButton bAjoutEnfant;
    String mail, prenom, nom;
    static String idUser = null;
    DAO d;
    ArrayList<Child> items;
    ArrayList<String> itemsKeys;
    ChildAdapter itemsAdapter;
    ListView listView;
    static int positionItem;
    ArrayList<Fragment> fr;
    private String item = "";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragmentHome() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentHome.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentHome newInstance(String param1, String param2) {
        FragmentHome fragment = new FragmentHome();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fragment_home, container, false);
        bAjoutEnfant = (FloatingActionButton) v.findViewById(R.id.bAjoutEnfant);

        /*prenom = (TextView) findViewById(R.id.tePrenomP);
        nom = (TextView) findViewById(R.id.teNomP);
        mail = (TextView) findViewById(R.id.teMail);*/

        nom = getArguments().getString(("nom"));
        prenom = getArguments().getString("prenom");
        mail = getArguments().getString("mail");

        idUser = getArguments().getString("id");


        bAjoutEnfant.setOnClickListener(this);

        // kids list
        items = new ArrayList<Child>();
        itemsKeys = new ArrayList<String>();

        // get all kids of id parent
        refData.child("children").addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
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
                itemsAdapter = new FragmentHome.ChildAdapter(getContext(), R.layout.item_kid, items);
                listView = (ListView) getView().findViewById(R.id.listKids);
                listView.setAdapter(itemsAdapter);
                itemsAdapter.notifyDataSetChanged();
                registerForContextMenu(listView);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bAjoutEnfant:
                /**/
                // Spinner element
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.activity_ajout_enfant);
                Spinner spinner = (Spinner) dialog.findViewById(R.id.spinnerSex);

                // Spinner click listener
                spinner.setOnItemSelectedListener(FragmentHome.this);

                // Spinner Drop down elements
                List<String> categories = new ArrayList<String>();
                categories.add("Male");
                categories.add("Female");

                // Creating adapter for spinner
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categories);

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
                        final Child c = new Child(etPrenom.getText().toString(), Integer.parseInt(etAge.getText().toString()), item, idUser);
                         //key = d.addChildren(c);
                        refData.child("children").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                int trouv = 0;
                                for(DataSnapshot child : dataSnapshot.getChildren()){
                                    Child c1 = child.getValue(Child.class);
                                    if(c1.getNameChild().equals(c.getNameChild())){
                                        trouv = 1;
                                        Toast.makeText(getContext(), "Kid already existing", Toast.LENGTH_LONG).show();
                                    }
                                }
                                if (trouv!=1)
                                {
                                    Toast.makeText(getContext(), "Kid successfully added", Toast.LENGTH_LONG).show();

                                   String key = refData.child("children").push().getKey();
                                    refData.child("children").child(key).setValue(c);
                                    items.add(c);
                                    itemsKeys.add(key);
                                    itemsAdapter.notifyDataSetChanged();
                                    dialog.dismiss();

                                }


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }

                        });




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
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onContextItemSelected(final MenuItem item) {

        // TODO Auto-generated method stub
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        switch (item.getItemId()) {
            case R.id.delete_item:
                AlertDialog.Builder alert = new AlertDialog.Builder(
                getContext());
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
                        Toast.makeText(getContext(), "Kid deleted", Toast.LENGTH_LONG).show();
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
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.activity_ajout_enfant);
                Spinner spinner = (Spinner) dialog.findViewById(R.id.spinnerSex);

                // Spinner click listener
                spinner.setOnItemSelectedListener(FragmentHome.this);

                // Spinner Drop down elements
                List<String> categories = new ArrayList<String>();
                categories.add("Male");
                categories.add("Female");

                // Creating adapter for spinner
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categories);

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
                        Toast.makeText(getContext(), "Kid successfully edited", Toast.LENGTH_LONG).show();
                        refData.child("children").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Log.e("count", "" + dataSnapshot.getChildrenCount());

                                for (DataSnapshot child : dataSnapshot.getChildren()) {
                                    // child.get
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

                Intent intent = new Intent(getContext(), Report.class);
                intent.putExtra("idChild", itemsKeys.get(positionItem));
                System.out.println(itemsKeys.get(positionItem));
                startActivity(intent);
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

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


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
                   // Toast.makeText(getContext(), "not implemented yet", Toast.LENGTH_LONG).show();
                    String idChild = itemsKeys.get(position);
                    Intent intent = new Intent(getContext(), ActivityManager.class);
                    intent.putExtra("idChild", idChild);
                    startActivity(intent);

                }
            });
            //rowView.setLongClickable(true);
            rowView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                @Override
                public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                    MenuInflater inflater = getActivity().getMenuInflater();
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
