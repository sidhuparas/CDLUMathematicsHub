package com.parassidhu.cdlumaths;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.parassidhu.cdlumaths.adapters.PagerAdapter;
import com.parassidhu.cdlumaths.utils.sidhu;

public class Syllabus extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_syllabus, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Syllabus");
        setHasOptionsMenu(true);
        setup();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.sort);
        sidhu.setOptVisibility(menu,false,true);
    }

    public void setup(){
       try {
           TabLayout tabLayout = getActivity().findViewById(R.id.tab_layout);
           tabLayout.addTab(tabLayout.newTab().setText("5-Years"));
           tabLayout.addTab(tabLayout.newTab().setText("2-Years"));
           tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
           int r,g,b;
           r = Home.r;
           g = Home.g;
           b = Home.b;
           tabLayout.setBackgroundColor(Color.rgb(r,g,b));
           tabLayout.setSelectedTabIndicatorColor(Color.rgb(r,g,b));

           final ViewPager viewPager = getActivity().findViewById(R.id.pager);
           final PagerAdapter adapter = new PagerAdapter
                   (getActivity().getSupportFragmentManager(), tabLayout.getTabCount(),"Syllabus");
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
       }catch (Exception ex){

       }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
}
