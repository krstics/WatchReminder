package com.krstics.watchreminder;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.krstics.watchreminder.Adapters.PagerAdapter;
import com.krstics.watchreminder.DB.ShowsDB;
import com.krstics.watchreminder.Fragments.FragmentFive;
import com.krstics.watchreminder.Fragments.FragmentFour;
import com.krstics.watchreminder.Fragments.FragmentOne;
import com.krstics.watchreminder.Fragments.FragmentThree;
import com.krstics.watchreminder.Fragments.FragmentTwo;
import com.krstics.watchreminder.Loaders.EpisodeLoad;
import com.krstics.watchreminder.Loaders.UpdateLoad;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    PagerAdapter adapter;
    SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(viewPager);
        viewPager.setCurrentItem(0);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                View focus = getCurrentFocus();
                if(focus != null)
                    hideSoftInput(focus);

                int position = tab.getPosition();
                viewPager.setCurrentItem(position);
                Fragment fragment = adapter.getItem(viewPager.getCurrentItem());

                if(fragment instanceof FragmentTwo)
                    ((FragmentTwo)fragment).refresh();

                if(fragment instanceof FragmentThree)
                    ((FragmentThree)fragment).refresh();

                if(fragment instanceof FragmentFour)
                    ((FragmentFour)fragment).refresh();

                if(fragment instanceof FragmentFive)
                    ((FragmentFive)fragment).refresh();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

                View focus = getCurrentFocus();
                if(focus != null)
                    hideSoftInput(focus);

                int position = tab.getPosition();
                viewPager.setCurrentItem(position);
                Fragment fragment = adapter.getItem(viewPager.getCurrentItem());

                if(fragment instanceof FragmentTwo)
                    ((FragmentTwo)fragment).refresh();

                if(fragment instanceof FragmentThree)
                    ((FragmentThree)fragment).refresh();

                if(fragment instanceof FragmentFour)
                    ((FragmentFour)fragment).refresh();

                if(fragment instanceof FragmentFive)
                    ((FragmentFive)fragment).refresh();
            }
        });

        prefs = getSharedPreferences("com.krstics.watchreminder", MODE_PRIVATE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        getSupportActionBar().setHomeAsUpIndicator(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);


        UpdateLoad updateLoad = new UpdateLoad();
        if(prefs.getBoolean("firstRun", true)){
            updateLoad.getPreviousTime(getApplicationContext());

            prefs.edit().putBoolean("firstRun", false).apply();
        }

        if(shouldUpdate()){
            UpdateLoad ul = new UpdateLoad();
            ul.updateDataInDB(getApplicationContext());
        }

        EpisodeLoad episodeLoad = new EpisodeLoad();

        episodeLoad.loadEpisodeByAirDate(getApplicationContext());

        episodeLoad.loadEpisodeByAirDateForNext4Weeks(getApplicationContext());
    }

    private boolean shouldUpdate() {
        ShowsDB db = new ShowsDB(getApplicationContext());
        String previousTimeUnix = db.getPreviousUpdateTime();
        Long difference = 0L;

        if(!previousTimeUnix.isEmpty()) {
            long dv = Long.valueOf(previousTimeUnix);
            Log.e("MainAct", Long.toString(dv));

            long unixTime = System.currentTimeMillis() / 1000L;
            Log.e("MainAct", Long.toString(unixTime));

            difference = unixTime - dv;

            Log.e("MainAct", "diff " + difference);
        }

        return difference >= 43200; //12 hours in unix time


    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new PagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new FragmentOne(), "Search");
        adapter.addFrag(new FragmentTwo(), "Added Shows");
        adapter.addFrag(new FragmentThree(), "Today premier");
        adapter.addFrag(new FragmentFour(), "Not Watched");
        adapter.addFrag(new FragmentFive(), "Next 4 weeks premiers");
        viewPager.setAdapter(adapter);
    }

    private void hideSoftInput(View v) {
        InputMethodManager keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        keyboard.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}