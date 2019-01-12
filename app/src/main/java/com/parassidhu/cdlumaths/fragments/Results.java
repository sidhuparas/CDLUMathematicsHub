package com.parassidhu.cdlumaths.fragments;

import android.graphics.Color;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.parassidhu.cdlumaths.R;
import com.parassidhu.cdlumaths.activities.Home;
import com.parassidhu.cdlumaths.adapters.PagerAdapter;
import com.parassidhu.cdlumaths.utils.AppUtils;

public class Results extends Fragment {

    public Results() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_results, container, false);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.sort);
        AppUtils.setOptVisibility(menu, false, true);
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
        getActivity().setTitle("Results");
        setHasOptionsMenu(true);
        setup();
    }

    public void setup() {
        TabLayout tabLayout = getActivity().findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Individual"));
        tabLayout.addTab(tabLayout.newTab().setText("Summary"));

        int r, g, b;
        r = Home.r;
        g = Home.g;
        b = Home.b;
        tabLayout.setBackgroundColor(Color.rgb(r, g, b));
        tabLayout.setSelectedTabIndicatorColor(Color.rgb(r, g, b));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager = getActivity().findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getActivity().getSupportFragmentManager(), tabLayout.getTabCount(), "Result");
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            public void onTabUnselected(TabLayout.Tab tab) { }

            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }
}
