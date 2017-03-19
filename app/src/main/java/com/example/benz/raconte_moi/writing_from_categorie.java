package com.example.benz.raconte_moi;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;

import android.widget.TextView;

import java.util.ArrayList;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link writing_from_categorie.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link writing_from_categorie#newInstance} factory method to
 * create an instance of this fragment.
 */
public class writing_from_categorie extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayoutAndroid;
    CoordinatorLayout rootLayoutAndroid;
    GridView gridView;
    Context context;
    ArrayList arrayList;
    public static String[] gridViewStrings = {
            "Manga",
            "Astronomie",
            "FootBall"
    };
    public static int[] gridViewImages = {
            R.drawable.manga2,
            R.drawable.astronimical,
            R.drawable.football
    };
    private String idChild;

    View viewFromImages;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public writing_from_categorie() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment writing_from_categorie.
     */
    // TODO: Rename and change types and number of parameters
    public static writing_from_categorie newInstance(String param1, String param2) {
        writing_from_categorie fragment = new writing_from_categorie();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       viewFromImages  = inflater.inflate(R.layout.fragment_writing_from_categorie, container, false);
        toolbar = (Toolbar) viewFromImages.findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        gridView = (GridView) viewFromImages.findViewById(R.id.grid);
        idChild =  getArguments().getString("idChild");
        gridView.setAdapter( new writing_from_categorie.CustomAndroidGridViewAdapter(getActivity(), gridViewStrings, gridViewImages,idChild));

        initInstances();
        return viewFromImages;
    }
    private void initInstances() {
        rootLayoutAndroid = (CoordinatorLayout) viewFromImages.findViewById(R.id.android_coordinator_layout);
        collapsingToolbarLayoutAndroid = (CollapsingToolbarLayout) viewFromImages.findViewById(R.id.collapsing_toolbar_android_layout);
        collapsingToolbarLayoutAndroid.setTitle("Images Categories");
    }

    public class CustomAndroidGridViewAdapter extends BaseAdapter {
        private final String idChild;
        private Context mContext;
        private final String[] string;
        private final int[] Imageid;

        public CustomAndroidGridViewAdapter(Context c,String[] string,int[] Imageid, String idChild ) {
            mContext = c;
            this.Imageid = Imageid;
            this.string = string;
            this.idChild=idChild;
        }



        //@Override
        public int getCount() {
            return string.length;
        }

        //@Override
        public Object getItem(int p) {
            return null;
        }

        //@Override
        public long getItemId(int p) {
            return 0;
        }



        // @Override
        public View getView(final int p, View convertView, ViewGroup parent) {
            View grid;
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {

                grid = new View(mContext);
                grid = inflater.inflate(R.layout.gridview_custom_layout, null);
                TextView textView = (TextView) grid.findViewById(R.id.gridview_text);
                ImageButton imageView = (ImageButton)grid.findViewById(R.id.gridview_image);
                textView.setText(string[p]);
                final String cat = string[p];
                imageView.setImageResource(Imageid[p]);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    /// go to activity manager
                    public void onClick(View view) {

                        Intent intent = new Intent(getActivity(), WritingFromCategories.class);
                        intent.putExtra("categorie", cat);
                        intent.putExtra("idChild",  idChild);
                        startActivity(intent);
                        System.out.println("yahoo");

                    }
                });
            } else {
                grid = (View) convertView;
            }

            return grid;
        }


    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
}
