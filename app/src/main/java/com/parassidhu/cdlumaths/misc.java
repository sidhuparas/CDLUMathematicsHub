package com.parassidhu.cdlumaths;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

public class misc extends Fragment {

    private final String class_name[] = {
            "Download Ph.D (Jan-2016)",
            "Download M.Phil (July-2015)"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.misc, container, false);
        return rootView;
    }

    public void startDownload(String filename, String url,int i){
        MyApp x =  (MyApp)getActivity().getApplicationContext();
        x.getUrl(url);
        x.getFilename(filename);
        x.getID(i);
        Intent intent = new Intent(getActivity(),DownloadService.class);
        getActivity().startService(intent);
    }

    public void starting() {
        Toast.makeText(getActivity(), "Starting download...Please check notifications panel for progress", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        final String add="CDLU/misc/";

        RecyclerView rcl = getActivity().findViewById(R.id.card_recycler_viewee);
        ItemClickSupport.addTo(rcl).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
       @Override
       public void onItemClicked(RecyclerView recyclerView, int position, View v) {
           switch (position) {
               case 0:
                   startDownload("PhD (Jan 2016).pdf",add+"phd%2001%202016.pdf",1000001);
                   starting();
                   break;
               case 1:
                   startDownload("MPhil (July 2015).pdf",add+"Mphil%2007%202015.pdf",1000002);
                   starting();
                   break;
           }
       }
   });
    }

    private void initViews(){
        try {
            RecyclerView recyclerView = getActivity().findViewById(R.id.card_recycler_viewee);
            recyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);

            ArrayList androidVersions = prepareData();
            DataAdapter adapter = new DataAdapter(getActivity(), androidVersions);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL);
            recyclerView.addItemDecoration(dividerItemDecoration);
            recyclerView.setAdapter(adapter);
        }catch (Exception ex){

        }
    }
    private ArrayList prepareData(){

        ArrayList android_version = new ArrayList<>();
        for(int i=0;i<class_name.length;i++){
            AndroidVersion androidVersion = new AndroidVersion();
            androidVersion.setAndroid_version_name(class_name[i]);
            androidVersion.setAndroid_image_url(R.drawable.contri);
            android_version.add(androidVersion);
        }
        return android_version;
    }
}

