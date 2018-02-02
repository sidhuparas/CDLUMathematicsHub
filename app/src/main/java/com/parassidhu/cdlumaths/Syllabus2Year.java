package com.parassidhu.cdlumaths;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

public class Syllabus2Year extends Fragment {
    HomeAdapter adapter;
    private Button finalsch;
    private Button DA2;

    public void Start(){
        Snackbar.make(getActivity().findViewById(R.id.syllabi2year),"Download has started. Please check notification for progress.",4000).show();
    }

    final String add="CDLU/syllabus/2year/";

    public void x(final String pos){
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Start Download?")
                .setMessage("Do you want to download Semester "+pos+" syllabus?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (pos){
                            case "1":
                                startDownload("Semester "+pos +" (2-Year).pdf",add+"Semester%20-%20I.pdf",10001);
                                Start();
                                break;
                            case "2":
                                startDownload("Semester "+pos +" (2-Year).pdf",add+"Semester%20-%20II.pdf",10002);
                                Start();
                                break;
                            case "3":
                                startDownload("Semester "+pos +" (2-Year).pdf",add+"Semester%20-%20III.pdf",10003);
                                Start();
                                break;
                            default:
                                startDownload("Semester "+pos +" (2-Year).pdf",add+"Semester%20-%20IV.pdf",10004);
                                Start();
                                break;
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rcl =(RecyclerView)view.findViewById(R.id.card_recycler_view1);
        rcl.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        rcl.setLayoutManager(layoutManager);

        finalsch = (Button) getActivity().findViewById(R.id.finalsch);
        DA2 = (Button) getActivity().findViewById(R.id.DownloadAll2);
        final String add="CDLU/syllabus/2year/";
        finalsch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDownload("Final Scheme (2-Year).pdf",add+"FINAL-Scheme.pdf",10005);
            }
        });
        DA2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDownload("MSc Maths 2-Year.pdf",add+"MSc%20Maths%202-Year.pdf",10006);
            }
        });
        ArrayList androidVersions;
        androidVersions = new ArrayList<>(prepareData());
        adapter = new HomeAdapter(getActivity(),androidVersions);
        rcl.setAdapter(adapter);

        ItemClickSupport.addTo(rcl).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                x(String.valueOf(position+1));
            }
        });
    }

    public void startDownload(String filename, String url,int i){
        MyApp x =  (MyApp)getActivity().getApplicationContext();
        x.getUrl(url);
        x.getFilename(filename);
        x.getID(i);
        switch (x.ID){
            case 10001:
                Intent intent1 = new Intent(getActivity(),DownloadService.class);
                getActivity().startService(intent1);
                break;
            case 10002:
                Intent intent2 = new Intent(getActivity(),DownloadService.class);
                getActivity().startService(intent2);
                break;
            case 10003:
                Intent intent3 = new Intent(getActivity(),DownloadService.class);
                getActivity().startService(intent3);
                break;
            case 10004:
                Intent intent4 = new Intent(getActivity(),DownloadService.class);
                getActivity().startService(intent4);
                break;
            case 10005:
                Intent intent5 = new Intent(getActivity(),DownloadService.class);
                getActivity().startService(intent5);
                break;
            case 10006:
                Intent intent6 = new Intent(getActivity(),DownloadService.class);
                getActivity().startService(intent6);
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.syllabus2year, container, false);
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