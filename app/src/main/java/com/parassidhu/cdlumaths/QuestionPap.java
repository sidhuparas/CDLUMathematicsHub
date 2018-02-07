package com.parassidhu.cdlumaths;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import static android.content.Context.MODE_PRIVATE;

public class QuestionPap extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_question_pap, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            getActivity().setTitle("Question Papers");
            setup();
        }
        catch (Exception ex){

        }
    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.sort);
        sidhu.setOptVisibility(menu,false,true);
    }

  public void setup(){
        setHasOptionsMenu(true);
        TabLayout tabLayout = getActivity().findViewById(R.id.tab_layout1);
        tabLayout.addTab(tabLayout.newTab().setText("5-Years"));
        tabLayout.addTab(tabLayout.newTab().setText("2-Years"));
        tabLayout.addTab(tabLayout.newTab().setText("Misc"));
        int r,g,b;
        r = Home.r;
        g = Home.g;
        b = Home.b;
        tabLayout.setBackgroundColor(Color.rgb(r,g,b));
        tabLayout.setSelectedTabIndicatorColor(Color.rgb(r,g,b));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = getActivity().findViewById(R.id.pager1);
        final PagerAdapter adapter = new PagerAdapter
                (getActivity().getSupportFragmentManager(), tabLayout.getTabCount(),"QuestionPap");
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            public void onTabUnselected(TabLayout.Tab tab) {}
            public void onTabReselected(TabLayout.Tab tab) {}
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
}
