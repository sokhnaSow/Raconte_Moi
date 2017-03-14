package com.example.benz.raconte_moi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class WritingImageManager extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing_image_manager);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        gridView = (GridView) findViewById(R.id.grid);
        Intent intent = getIntent();
        idChild = intent.getStringExtra("idChild");
        gridView.setAdapter( new CustomAndroidGridViewAdapter(this, gridViewStrings, gridViewImages,idChild));

        initInstances();
    }

    private void initInstances() {
        rootLayoutAndroid = (CoordinatorLayout) findViewById(R.id.android_coordinator_layout);
        collapsingToolbarLayoutAndroid = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_android_layout);
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
                ImageView imageView = (ImageView)grid.findViewById(R.id.gridview_image);
                textView.setText(string[p]);
                final String cat = string[p];
                imageView.setImageResource(Imageid[p]);
                grid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    /// go to activity manager
                    public void onClick(View view) {

                        Intent intent = new Intent(WritingImageManager.this, WritingFromCategories.class);
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
}