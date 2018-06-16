package com.parassidhu.cdlumaths;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.parassidhu.cdlumaths.adapters.HomeAdapter;
import com.parassidhu.cdlumaths.models.AndroidVersion;
import com.parassidhu.cdlumaths.utils.ItemClickSupport;
import com.parassidhu.cdlumaths.utils.sidhu;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuestionPapers extends Fragment {

    @BindView(R.id.choose5Years) TextView choose5Years;
    @BindView(R.id.card_recycler_vie) RecyclerView rcl5Years;
    @BindView(R.id.apppromo) ImageView appPromo;
    @BindView(R.id.choose2Years) TextView choose2Years;
    @BindView(R.id.twoYearsRV) RecyclerView rcl2Years;
    @BindView(R.id.scrollViewQuePap) NestedScrollView scrollViewQuePap;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_question_papers, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        setListener5Years();
        setListener2Years();
    }

    private void initViews() {
        appPromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sidhu.openWebPage((AppCompatActivity) getActivity(),
                        "https://play.google.com/store/apps/details?id=com.parassidhu.pdfpin");
            }
        });

        rcl5Years.setHasFixedSize(true);
        rcl5Years.setLayoutManager(new GridLayoutManager
                (getActivity().getApplicationContext(), 4));
        HomeAdapter adapter = new HomeAdapter(getActivity(), prepareDataFor5Years());
        rcl5Years.setAdapter(adapter);
        sidhu.setFastScrolling(rcl5Years);


        rcl2Years.setHasFixedSize(true);
        rcl2Years.setLayoutManager(new GridLayoutManager
                (getActivity().getApplicationContext(), 4));

        ArrayList androidVersions = prepareDataFor2Years();
        HomeAdapter adapter2Years = new HomeAdapter(getActivity(), androidVersions);
        rcl2Years.setAdapter(adapter2Years);

        //Theme for Choose
        choose2Years.setTextColor(Color.rgb(Home.r, Home.g, Home.b));

        //Theme for Choose
        choose5Years.setTextColor(Color.rgb(Home.r, Home.g, Home.b));

        getActivity().setTitle("Question Papers");
        setHasOptionsMenu(true);
    }

    private ArrayList prepareDataFor5Years() {
        ArrayList android_version = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
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


    private ArrayList prepareDataFor2Years() {
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

    private void setListener5Years() {
        ItemClickSupport.addTo(rcl5Years).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
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
    }

    private void setListener2Years() {
        ItemClickSupport.addTo(rcl2Years).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
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
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.sort);
        sidhu.setOptVisibility(menu, false, true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }
}

