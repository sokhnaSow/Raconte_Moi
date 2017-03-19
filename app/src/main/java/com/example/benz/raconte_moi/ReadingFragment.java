package com.example.benz.raconte_moi;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.example.benz.raconte_moi.DAO.History;
import com.example.benz.raconte_moi.DAO.Writing;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReadingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReadingFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private GridView gridView;
    private GridViewAdapter gridAdapter;

    FirebaseStorage storage;
    StorageReference storageRef;
    FirebaseDatabase database;
    DatabaseReference refData;
    Button myStories;
    ArrayList<ImageItem> imageItems;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public Button btnImage, btnDrawing;
    private OnFragmentInteractionListener mListener;

    public ReadingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReadingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReadingFragment newInstance(String param1, String param2) {
        ReadingFragment fragment = new ReadingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View v = inflater.inflate(R.layout.fragment_reading, container, false);

        storage = FirebaseStorage.getInstance();
        //storageRef = storage.getReference();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        refData = database.getReference();
        gridView = (GridView) v.findViewById(R.id.gridHistory);
        imageItems = new ArrayList<>();
        myStories= (Button) v.findViewById(R.id.btnMyStory);

        final ArrayList<String> idstories= new ArrayList<>();

        refData.child("Writing").addListenerForSingleValueEvent(new ValueEventListener() {
            ImageItem it ;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final GridViewAdapter gridAdapter;

                gridAdapter = new GridViewAdapter(getActivity(), R.layout.grid_history,imageItems);
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Writing w = child.getValue(Writing.class);
                    if (w.isValide()) {
                        idstories.add(w.getIdHistory());
                    }
                }

                refData.child("history").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            for (String s : idstories) {
                                History h = child.getValue(History.class);
                                if (child.getKey().equals(s)) {
                                    it = new ImageItem(h.getTitle(), child.getKey());
                                    imageItems.add(it);
                                    gridAdapter.setData(imageItems);
                                    gridView.setAdapter(gridAdapter);
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                /*
                Code....
                 */

            }
        });


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Intent intent = new Intent(getActivity(), ReadingDrawing.class);
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {

                String idChild = getArguments().getString(("idChild"));

                ImageItem item = (ImageItem) parent.getItemAtPosition(position);
                //Create intent
                intent.putExtra("title", item.getTitle());
                intent.putExtra("idHistory",item.getIdHistory());
                intent.putExtra("idChild",idChild);

                startActivity(intent);
            }
        });
        myStories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String idChild = getArguments().getString(("idChild"));

                Intent intent = new Intent(getActivity(), ChoiceMyStorie.class);
                intent.putExtra("idChild",idChild);
                startActivity(intent);

            }
        });
       return v;
    }


}
