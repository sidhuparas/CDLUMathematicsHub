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

public class Syllabus5Year extends Fragment {

    HomeAdapter adapter;
    Button DownloadAll;
    final String add="CDLU/syllabus/5year/";

    public void downloadSyllabus(final String pos){
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Start Download?")
                .setMessage("Do you want to download Semester "+pos+" syllabus?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (pos){
                            case "1":
                                sidhu.startDownload("Semester "+pos +".pdf",add+"1st%20Semester.pdf",getActivity());
                                break;
                            case "2":
                                sidhu.startDownload("Semester "+pos +".pdf",add+"2nd%20Semester.pdf",getActivity());
                                break;
                            case "3":
                                sidhu.startDownload("Semester "+pos +".pdf",add+"3rd%20Semester.pdf",getActivity());
                                break;
                            case "7":
                                Sem7();
                                break;
                            case "8":
                                Sem8();
                                break;
                            case "9":
                                Sem9();
                                break;
                            case "10":
                                Sem10();
                                break;
                            default:
                                sidhu.startDownload("Semester "+pos +".pdf",add+pos+"th%20Semester.pdf",getActivity());
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

    private void Sem7(){
        downloadOldNew(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sidhu.startDownload("Semester 7th.pdf",add+"7th%20Semester.pdf",getActivity());
                ;
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sidhu.startDownload("Semester 7th Old.pdf",add+"7th%20SemesterO.pdf",getActivity());
                ;
            }
        });
    }

    private void Sem8(){
        downloadOldNew(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sidhu.startDownload("Semester 8th.pdf",add+"8th%20Semester.pdf",getActivity());
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sidhu.startDownload("Semester 8th Old.pdf",add+"8th%20SemesterO.pdf",getActivity());
            }
        });
    }

    private void Sem9(){
        downloadOldNew(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sidhu.startDownload("Semester 9th.pdf",add+"9th%20Semester.pdf",getActivity());
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sidhu.startDownload("Semester 9th Old.pdf",add+"9th%20SemesterO.pdf",getActivity());
            }
        });
    }

    private void Sem10(){
        downloadOldNew(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sidhu.startDownload("Semester 10th.pdf",add+"10th%20Semester.pdf",getActivity());
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sidhu.startDownload("Semester 10th Old.pdf",add+"10th%20SemesterO.pdf",getActivity());
            }
        });
    }

    private void downloadOldNew(DialogInterface.OnClickListener latest, DialogInterface.OnClickListener old){
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Start Download?")
                .setMessage("Do you want to download the new or old syllabus?")
                .setPositiveButton("Latest", latest)
                .setNegativeButton("Old", old).show();
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rcl = view.findViewById(R.id.card_recycler_view1);

        rcl.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        rcl.setLayoutManager(layoutManager);
        sidhu.setFastScrolling(rcl);
        DownloadAll= getActivity().findViewById(R.id.DownloadAll);
        DownloadAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String add="CDLU/syllabus/5year/";
                sidhu.startDownload("MSc Maths 5-Year.pdf",add+"M.Sc.%20Math%20(5%20years).pdf",getActivity());
            }
        });
        ArrayList androidVersions;
        androidVersions = new ArrayList<>(prepareData());
        adapter = new HomeAdapter(getActivity(),androidVersions);
        rcl.setAdapter(adapter);
        ItemClickSupport.addTo(rcl).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                if (position<6)
                downloadSyllabus(String.valueOf(position+1));
                else {
                    switch (String.valueOf(position)){
                        case "6":
                            Sem7();
                            break;
                        case "7":
                            Sem8();
                            break;
                        case "8":
                            Sem9();
                            break;
                        case "9":
                            Sem10();
                            break;
                    }
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.syllabus5year, container, false);
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