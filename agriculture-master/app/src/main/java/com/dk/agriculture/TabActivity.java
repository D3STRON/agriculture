package com.dk.agriculture;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;

public class TabActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener{
    //This is our tablayout
    private TabLayout tabLayout;

    //This is our viewPager
    private ViewPager viewPager;
    static  AdapterView.OnItemClickListener mCropListener;
    static  AdapterView.OnItemClickListener mFertilizerlistener;
    static  AdapterView.OnItemClickListener mPesticidesListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        //Adding toolbar to the activity
        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mCropListener = new CropOnItemClickListener(this);
        mFertilizerlistener = new FertilizerOnItemClickListener(this);
        mPesticidesListener= new PesticdesOnItemClickListener(this);

        //Initializing the tablayout
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("Crops"));
        tabLayout.addTab(tabLayout.newTab().setText("Fertilizers"));
        tabLayout.addTab(tabLayout.newTab().setText("Pesticides"));
        //tabLayout.addTab(tabLayout.newTab().setText("Tab3"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.pager);

        //Creating our pager adapter
        Pager adapter = new Pager(getSupportFragmentManager(), tabLayout.getTabCount());

        //Adding adapter to pager
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(this);

    }
    private class CropOnItemClickListener implements AdapterView.OnItemClickListener{
        private final Context context;

        private CropOnItemClickListener(Context context) {
            this.context = context;
        }
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            //On click Work
            String cropSelect = (String) adapterView.getItemAtPosition(i);

            Intent videoIntent = new Intent(TabActivity.this,VideoActivity.class);
            videoIntent.putExtra("crop",cropSelect);
            startActivity(videoIntent);
        }
    }
    private class FertilizerOnItemClickListener implements AdapterView.OnItemClickListener{
        private final Context context;

        private FertilizerOnItemClickListener(Context context) {
            this.context = context;
        }
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
          //On click work
        }
    }

    private class PesticdesOnItemClickListener implements AdapterView.OnItemClickListener{
        private final Context context;

        private PesticdesOnItemClickListener(Context context) {
            this.context = context;
        }
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            //On click work
        }
    }
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


}
