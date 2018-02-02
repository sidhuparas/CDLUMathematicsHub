package com.parassidhu.cdlumaths;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

public class Q5Year extends Fragment {

    private TextView choose;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_q5_year, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        try {
            RecyclerView rcl = (RecyclerView) getActivity().findViewById(R.id.card_recycler_vie);
            ItemClickSupport.addTo(rcl).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    switch (position) {
                        case 0:
                            final Intent i = new Intent(getActivity(), sem1.class);
                            getActivity().startActivity(i);
                            break;
                        case 1:
                            final Intent a = new Intent(getActivity(), sem2.class);
                            getActivity().startActivity(a);
                            break;
                        case 2:
                            final Intent b = new Intent(getActivity(), sem3.class);
                            getActivity().startActivity(b);
                            break;
                        case 3:
                            final Intent c = new Intent(getActivity(), sem4.class);
                            getActivity().startActivity(c);
                            break;
                        case 4:
                            final Intent d = new Intent(getActivity(), sem5.class);
                            getActivity().startActivity(d);
                            break;
                        case 5:
                            final Intent e = new Intent(getActivity(), sem6.class);
                            getActivity().startActivity(e);
                            break;
                        case 6:
                            final Intent f = new Intent(getActivity(), sem7.class);
                            getActivity().startActivity(f);
                            break;
                        case 7:
                            final Intent g = new Intent(getActivity(), sem8.class);
                            getActivity().startActivity(g);
                            break;
                        case 8:
                            final Intent h = new Intent(getActivity(), sem9.class);
                            getActivity().startActivity(h);
                            break;
                        case 9:
                            final Intent o = new Intent(getActivity(), sem10.class);
                            getActivity().startActivity(o);
                            break;
                    }
                }
            });
        }catch (Exception e){}
    }

    private void initViews(){
      try {
          RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.card_recycler_vie);
          ImageView apppromo = getActivity().findViewById(R.id.apppromo);
          apppromo.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  sidhu.openWebPage((AppCompatActivity) getActivity(),"https://play.google.com/store/apps/details?id=com.parassidhu.pdfpinner");
              }
          });
          recyclerView.setHasFixedSize(true);
          RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity().getApplicationContext(),2);
          recyclerView.setLayoutManager(layoutManager);
          ArrayList androidVersions = prepareData();
          HomeAdapter adapter = new HomeAdapter(getActivity(), androidVersions);
          recyclerView.setAdapter(adapter);
          sidhu.setFastScrolling(recyclerView);

          //Theme for Choose
          choose = getActivity().findViewById(R.id.choose);
          choose.setTextColor(Color.rgb(Home.r,Home.g,Home.b));
      }catch (Exception ex){}
    }

    private ArrayList prepareData(){
        ArrayList android_version = new ArrayList<>();
        for(int i=0;i<10;i++){
            AndroidVersion androidVersion = new AndroidVersion();
            switch (i){
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
                case 4:
                    androidVersion.setAndroid_image_url(R.drawable.a5);
                    android_version.add(androidVersion);
                    break;
                case 5:
                    androidVersion.setAndroid_image_url(R.drawable.a6);
                    android_version.add(androidVersion);
                    break;
                case 6:
                    androidVersion.setAndroid_image_url(R.drawable.a7);
                    android_version.add(androidVersion);
                    break;
                case 7:
                    androidVersion.setAndroid_image_url(R.drawable.a8);
                    android_version.add(androidVersion);
                    break;
                case 8:
                    androidVersion.setAndroid_image_url(R.drawable.a9);
                    android_version.add(androidVersion);
                    break;
                case 9:
                    androidVersion.setAndroid_image_url(R.drawable.a10);
                    android_version.add(androidVersion);
                    break;
            }

        }
        return android_version;
    }
}

