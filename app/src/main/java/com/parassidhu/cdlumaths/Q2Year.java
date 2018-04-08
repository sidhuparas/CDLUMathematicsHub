package com.parassidhu.cdlumaths;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

public class Q2Year extends Fragment {

    private TextView choose2year;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_q2_year, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    private void initViews() {
        try {
            RecyclerView recyclerView = getActivity().findViewById(R.id.card_recycler_view1);
            recyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 2);
            recyclerView.setLayoutManager(layoutManager);

            ArrayList androidVersions = prepareData();
            HomeAdapter adapter = new HomeAdapter(getActivity(), androidVersions);
            recyclerView.setAdapter(adapter);

            //Theme for Choose
            choose2year = getActivity().findViewById(R.id.choose2year);
            choose2year.setTextColor(Color.rgb(Home.r, Home.g, Home.b));

            ImageView apppromo = getActivity().findViewById(R.id.apppromo);
            apppromo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sidhu.openWebPage((AppCompatActivity) getActivity(), "https://play.google.com/store/apps/details?id=com.parassidhu.pdfpin");
                }
            });
            ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    switch (position) {
                        case 0:
                            final Intent i = new Intent(getActivity(), tsem1.class);
                            startActivity(i);
                            break;
                        case 1:
                            final Intent a = new Intent(getActivity(), tsem2.class);
                            startActivity(a);
                            break;
                        case 2:
                            final Intent b = new Intent(getActivity(), tsem3.class);
                            startActivity(b);
                            break;
                        case 3:
                            final Intent c = new Intent(getActivity(), tsem4.class);
                            startActivity(c);
                            break;
                    }
                }
            });
        } catch (Exception ex) {

        }
    }

    private ArrayList prepareData() {
        ArrayList android_version = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            AndroidVersion androidVersion = new AndroidVersion();
            switch (i) {
                case 0:
                    androidVersion.setAndroid_image_url(R.drawable.a1);
                    android_version.add(androidVersion);
                    break;
                case 1:
                    androidVersion.setAndroid_image_url(R.drawable.a2);
                    android_version.add(androidVersion);
                    break;
                case 2:
                    androidVersion.setAndroid_image_url(R.drawable.a3);
                    android_version.add(androidVersion);
                    break;
                case 3:
                    androidVersion.setAndroid_image_url(R.drawable.a4);
                    android_version.add(androidVersion);
                    break;
            }
        }
        return android_version;
    }
}

