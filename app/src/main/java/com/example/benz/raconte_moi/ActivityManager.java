package com.example.benz.raconte_moi;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.mikepenz.iconics.utils.Utils;

import java.util.ArrayList;
import java.util.Random;

import devlight.io.library.ntb.NavigationTabBar;

public class ActivityManager extends AppCompatActivity implements View.OnClickListener {
    ArrayList<Fragment> fr ;
    FragmentManager fm;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        String idchild = getIntent().getExtras().getString("idChild");
        Bundle bundle = new Bundle();
        bundle.putString("idChild",idchild );


        ReadingFragment fra1 = new ReadingFragment();
        fra1.setArguments(bundle);

        wrinting_from_drawing_fragment fra2 = new  wrinting_from_drawing_fragment();
        fra2.setArguments(bundle);

        writing_from_categorie fra3 = new  writing_from_categorie();
        fra3.setArguments(bundle);
        fr = new ArrayList<Fragment>();
        fr.add(fra1);
        fr.add(fra2);
        fr.add(fra3);
        initUI();
    }

    private void initUI() {
       /* final ViewPager viewPager = (ViewPager) findViewById(R.id.vp_horizontal_ntb);
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 5;
            }

            @Override
            public boolean isViewFromObject(final View view, final Object object) {
                return view.equals(object);
            }

            @Override
            public void destroyItem(final View container, final int position, final Object object) {
                ((ViewPager) container).removeView((View) object);
            }

            @Override
            public Object instantiateItem(final ViewGroup container, final int position) {
                final View view = LayoutInflater.from(
                        getBaseContext()).inflate(R.layout.item_vp_list, null, false);

                final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(
                                getBaseContext(), LinearLayoutManager.VERTICAL, false
                        )
                );
                recyclerView.setAdapter(new RecycleAdapter());

                container.addView(view);
                return view;
            }
        });*/

        final String[] colors = getResources().getStringArray(R.array.default_preview);

        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb_horizontal);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.read),
                        Color.parseColor(colors[0]))
                        .title("Reading")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.draw),
                        Color.parseColor(colors[1]))
                        .title("Drawing")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.write),
                        Color.parseColor(colors[2]))
                        .title("Images")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.home),
                        Color.parseColor(colors[3]))
                        .title("Main")
                        .build()
        );
        /*models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_fifth),
                        Color.parseColor(colors[4]))
                        .title("Medal")
                        .build()
        );*/
        navigationTabBar.setModels(models);
        //navigationTabBar.setViewPager(viewPager, 2);

        /*navigationTabBar.post(new Runnable() {
            @Override
            public void run() {
                final View viewPager = findViewById(R.id.vp_horizontal_ntb);
                ((ViewGroup.MarginLayoutParams) viewPager.getLayoutParams()).topMargin =
                        (int) -navigationTabBar.getBadgeMargin();
                viewPager.requestLayout();
            }
        });*/

        navigationTabBar.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
            @Override
            public void onStartTabSelected(final NavigationTabBar.Model model, final int index) {



               // Toast.makeText(ActivityManager.this, model.getTitle()+""+index, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onEndTabSelected(final NavigationTabBar.Model model, final int index) {
                if (index==3) // bug il faut recuperer nom, preni, iduser du parent
                {
                    Intent intent = new Intent(ActivityManager.this, PageAccueil.class);
                    startActivity(intent);
                }
                else {
                    fm = getFragmentManager();

                    final FragmentTransaction ft = fm.beginTransaction();

                    ft.replace(R.id.fragmentContainer, fr.get(index));
                    ft.commit();
                    model.hideBadge();
                }
            }
        });

        findViewById(R.id.mask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                for (int i = 0; i < navigationTabBar.getModels().size(); i++) {
                    final NavigationTabBar.Model model = navigationTabBar.getModels().get(i);
                    navigationTabBar.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            final String title = String.valueOf(new Random().nextInt(15));
                            if (!model.isBadgeShowed()) {
                                model.setBadgeTitle(title);
                                model.showBadge();
                            } else model.updateBadgeTitle(title);
                        }
                    }, i * 100);
                }
            }
        });
    }
    public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
            final View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.item_list, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.txt.setText(String.format("Navigation Item #%d", position));
        }

        @Override
        public int getItemCount() {
            return 20;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView txt;

            public ViewHolder(final View itemView) {
                super(itemView);
                txt = (TextView) itemView.findViewById(R.id.txt_vp_item_list);
            }
        }
    }
    @Override
    public void onClick(View view) {
        Intent intent2 = getIntent();
        String idChild = intent2.getStringExtra("idChild");

        switch (view.getId()) {

        }
    }


}