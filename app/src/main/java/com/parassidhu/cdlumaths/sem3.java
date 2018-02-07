package com.parassidhu.cdlumaths;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

public class sem3 extends AppCompatActivity {

    private final String subject_names[] = {
            "Advanced Calculus",
            "Partial Differential Equations",
            "Statics",
            "Differential Geometry",
            "Probability Distributions",
            "Structured System Analysis and Design",
            "Internet And Web Designing",
            "Hindi",
            "Download All"
    };

    public boolean showAds() {
        SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean showAd = getPrefs.getBoolean("showAd", true);
        return showAd;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sem3);
        setupView();
        initViews();
        sidhu.renderTheme(this);
        final AdView adView = this.findViewById(R.id.adView);
        if (showAds()){
            MobileAds.initialize(getApplicationContext(),"ca-app-pub-6089158898128407/9919503008");
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .addTestDevice("73CC8EA0F398EEC21B718FF0F9EB507A")
                    .addTestDevice("39C695F82AC6C82B1C9874FBBDCC2D46")
                    .build();

            adView.setAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(int i) {
                    super.onAdFailedToLoad(i);
                    adView.setVisibility(View.GONE);
                }

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    adView.setVisibility(View.VISIBLE);
                }
            });

            adView.loadAd(adRequest);
        } else {
            adView.setVisibility(View.GONE);
        }

        final RecyclerView rcl = findViewById(R.id.card_recycler_view);
        ItemClickSupport.addTo(rcl).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
               try {
                   MyApp m = (MyApp) getApplicationContext();
                   switch (position) {
                       case 0:     //Subject 1
                           m.getClickSem3(0);
                           registerForContextMenu(rcl.findFocus());
                           openContextMenu(v);
                           break;
                       case 1:     //Subject 2
                           m.getClickSem3(1);
                           registerForContextMenu(rcl.findFocus());
                           openContextMenu(v);
                           break;
                       case 2:
                           m.getClickSem3(2);
                           registerForContextMenu(rcl.findFocus());
                           openContextMenu(v);
                           break;
                       case 3:
                           m.getClickSem3(3);
                           registerForContextMenu(rcl.findFocus());
                           openContextMenu(v);
                           break;
                       case 4:
                           m.getClickSem3(4);
                           registerForContextMenu(rcl.findFocus());
                           openContextMenu(v);
                           break;
                       case 5:
                           m.getClickSem3(5);
                           registerForContextMenu(rcl.findFocus());
                           openContextMenu(v);
                           break;
                       case 6:
                           m.getClickSem3(6);
                           registerForContextMenu(rcl.findFocus());
                           openContextMenu(v);
                           break;
                       case 7:
                           m.getClickSem3(7);
                           registerForContextMenu(rcl.findFocus());
                           openContextMenu(v);
                           break;
                       case 8:
                           m.getClickSem3(8);
                           registerForContextMenu(rcl.findFocus());
                           openContextMenu(v);
                           break;
                   }
               }catch (Exception ex){

               }
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sem3,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        MyApp m = (MyApp) getApplicationContext();
        String add="CDLU/sem3/2015/";
        String n="CDLU/sem3/2016/";
        int timeStamp = (int) System.currentTimeMillis();
        switch (item.getItemId()) {
            case R.id.download:    //December 2015
                switch (m.getit3()) {
                    case 0:
                        startDownload("Advanced Calculus (Dec 15).pdf",add+"AdvancedCalculus(Dec14).pdf",31);
                        starting();
                        break;
                    case 1:
                        startDownload("Partial Differential Equations (Dec 15).pdf",add+"Partial%20Differential%20Equations%20(Dec%2014).pdf",32);
                        starting();
                        break;
                    case 2:
                        startDownload("Statics (Dec 15).pdf",add+"Statics%20(Dec%2014).pdf",33);
                        starting();
                        break;
                    case 3:
                        startDownload("Differential Geometry (Dec 15).pdf",add+"Differential%20Geometry%20(Dec%2014).pdf",34);
                        starting();
                        break;
                    case 4:
                        startDownload("Probability Distributions (Dec 15).pdf",add+"Probability%20Distributions%20(Dec%2014).pdf",35);
                        starting();
                        break;
                    case 5:
                        startDownload("Structured System Analysis and Design (Dec 15).pdf",add+"Structured%20System%20Analysis%20And%20Design%20(Dec%2014).pdf",36);
                        starting();
                        break;
                    case 6:
                        startDownload("Internet And Web Designing (Dec 15).pdf",add+"Internet%20And%20Web%20Designing%20(Dec%2014).pdf",37);
                        starting();
                        break;
                    case 7:
                        startDownload("Hindi-I (Dec 15).pdf",add+"Hindi-I%20(Dec%2014).pdf",38);
                        starting();
                        break;
                    case 8:
                        startDownload("Complete Sem 3 (Dec 15).pdf",add+"Complete%20Sem%203%20(Dec%2014).pdf",39);
                        starting();
                        break;
                }
                return true;
            case R.id.download2:
                starting();
                switch (m.getit3()) {
                    case 0:
                        startDownload("Advanced Calculus (Dec 16).pdf",n+"AC.pdf",timeStamp);
                        break;
                    case 1:
                        startDownload("Partial Differential Equations (Dec 16).pdf",n+"PDE.pdf",timeStamp);
                        break;
                    case 2:
                        startDownload("Statics (Dec 16).pdf",n+"S.pdf",timeStamp);
                        break;
                    case 3:
                        startDownload("Differential Geometry (Dec 16).pdf",n+"DG.pdf",timeStamp);
                        break;
                    case 4:
                        startDownload("Probability Distributions (Dec 16).pdf",n+"PD.pdf",timeStamp);
                        break;
                    case 5:
                        startDownload("Structured System Analysis and Design (Dec 16).pdf",n+"SSAD.pdf",timeStamp);
                        break;
                    case 6:
                        startDownload("Internet And Web Designing (Dec 16).pdf",n+"IWD.pdf",timeStamp);
                        break;
                    case 7:
                        startDownload("Hindi-I (Dec 16).pdf",n+"H.pdf",timeStamp);
                        break;
                    case 8:
                        startDownload("Complete Sem 3 (Dec 16).pdf",n+"ALL.pdf",timeStamp);
                        break;
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void starting() {
        Toast.makeText(this, "Starting download...Please check notifications panel for progress", Toast.LENGTH_SHORT).show();
    }

    public void setupView() {
        android.support.v7.app.ActionBar acb = getSupportActionBar();
        acb.setHomeButtonEnabled(true);
        acb.setDisplayHomeAsUpEnabled(true);
        acb.setTitle("Choose Subject");
    }

    private void initViews(){
        RecyclerView recyclerView = findViewById(R.id.card_recycler_view);
        Button download = findViewById(R.id.downloadLiveHTML);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sidhu.openWebPage(sem3.this,"http://www.downloadinformer.com/2016/07/live-html-makes-html-coding-easier.html");
            }
        });
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList androidVersions = prepareData();
        QueAdapter adapter = new QueAdapter(getApplicationContext(),androidVersions);
        recyclerView.setAdapter(adapter);
    }

    private ArrayList prepareData(){
        ArrayList android_version = new ArrayList<>();
        for(int i=0;i<subject_names.length;i++){
            AndroidVersion androidVersion = new AndroidVersion();
            androidVersion.setAndroid_version_name(subject_names[i]);
            androidVersion.setAndroid_image_url(R.drawable.materialq);
            android_version.add(androidVersion);
        }
        return android_version;
    }

    public void startDownload(String filename, String url,int i){
        MyApp x =  (MyApp)getApplicationContext();
        x.getUrl(url);
        x.getFilename(filename);
        x.getID(i);
        Intent intent = new Intent(this,DownloadService.class);
        startService(intent);
    }
}
