package com.parassidhu.cdlumaths;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Result2 extends Fragment {
    HomeAdapter adapter;

    public Result2() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_result2, container, false);
    }

    public void openResult(int i){
        Intent intent = new Intent(getActivity(),MyResult.class);
        intent.putExtra("code","140" + (i+1));
        intent.putExtra("sem",String.valueOf(i+1));
        startActivity(intent);
    }
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rcl = (RecyclerView) view.findViewById(R.id.card_recycler_view1);
        rcl.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        rcl.setLayoutManager(layoutManager);
        ArrayList androidVersions;
        androidVersions = new ArrayList<>(prepareData());
        adapter = new HomeAdapter(getActivity(),androidVersions);
        rcl.setAdapter(adapter);
        sidhu.setFastScrolling(rcl);
        ItemClickSupport.addTo(rcl).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                openResult(position);
            }
        });
    }

    private ArrayList prepareData(){

        ArrayList android_version = new ArrayList<>();
        for(int i=0;i<4;i++){
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
            }
        }
        return android_version;
    }
}
