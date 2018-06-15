package com.parassidhu.cdlumaths;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.parassidhu.cdlumaths.adapters.HomeAdapter;
import com.parassidhu.cdlumaths.models.AndroidVersion;
import com.parassidhu.cdlumaths.utils.ItemClickSupport;
import com.parassidhu.cdlumaths.utils.sidhu;

import java.util.ArrayList;

public class Syllabus2Year extends Fragment {
    HomeAdapter adapter;
    private Button finalsch;
    private Button DA2;
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
                                sidhu.startDownload("Semester "+pos +" (2-Year).pdf",add+"Semester%20-%20I.pdf",getActivity());
                                break;
                            case "2":
                                sidhu.startDownload("Semester "+pos +" (2-Year).pdf",add+"Semester%20-%20II.pdf",getActivity());
                                break;
                            case "3":
                                sidhu.startDownload("Semester "+pos +" (2-Year).pdf",add+"Semester%20-%20III.pdf",getActivity());
                                break;
                            default:
                                sidhu.startDownload("Semester "+pos +" (2-Year).pdf",add+"Semester%20-%20IV.pdf",getActivity());
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
        RecyclerView rcl = view.findViewById(R.id.card_recycler_view1);
        rcl.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        rcl.setLayoutManager(layoutManager);

        finalsch = getActivity().findViewById(R.id.finalsch);
        DA2 = getActivity().findViewById(R.id.DownloadAll2);
        final String add="CDLU/syllabus/2year/";
        finalsch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sidhu.startDownload("Final Scheme (2-Year).pdf",add+"FINAL-Scheme.pdf",getActivity());
            }
        });
        DA2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sidhu.startDownload("MSc Maths 2-Year.pdf",add+"MSc%20Maths%202-Year.pdf",getActivity());
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