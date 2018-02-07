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
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

public class sem2 extends AppCompatActivity {

    private final String subject_names[] = {
            "Number Theory and Trigonometry",
            "Ordinary Differential Equations",
            "Vector Calculus",
            "Discrete Mathematics",
            "Regression Analysis And Probability",
            "Digital Logic and Computer Design",
            "Problem Solving Through C",
            "English",
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
        setContentView(R.layout.activity_sem2);

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

        setupView();
        initViews();
        registerReceiver();
        final RecyclerView rcl = findViewById(R.id.card_recycler_view);
        ItemClickSupport.addTo(rcl).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                try {
                    MyApp m = (MyApp) getApplicationContext();
                    switch (position) {
                        case 0:     //Subject 1
                            m.getClickSem2(0);
                            registerForContextMenu(rcl.findFocus());
                            openContextMenu(v);
                            break;
                        case 1:     //Subject 2
                            m.getClickSem2(1);
                            registerForContextMenu(rcl.findFocus());
                            openContextMenu(v);
                            break;
                        case 2:
                            m.getClickSem2(2);
                            registerForContextMenu(rcl.findFocus());
                            openContextMenu(v);
                            break;
                        case 3:
                            m.getClickSem2(3);
                            registerForContextMenu(rcl.findFocus());
                            openContextMenu(v);
                            break;
                        case 4:
                            m.getClickSem2(4);
                            registerForContextMenu(rcl.findFocus());
                            openContextMenu(v);
                            break;
                        case 5:
                            m.getClickSem2(5);
                            registerForContextMenu(rcl.findFocus());
                            openContextMenu(v);
                            break;
                        case 6:
                            m.getClickSem2(6);
                            registerForContextMenu(rcl.findFocus());
                            openContextMenu(v);
                            break;
                        case 7:
                            m.getClickSem2(7);
                            registerForContextMenu(rcl.findFocus());
                            openContextMenu(v);
                            break;
                        case 8:
                            m.getClickSem2(8);
                            registerForContextMenu(rcl.findFocus());
                            openContextMenu(v);
                            break;
                    }
                }catch (Exception ex){

                }
            }
        });
        sidhu.renderTheme(this);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sem2,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        MyApp m = (MyApp) getApplicationContext();
        String add="CDLU/sem2/2015/";
        String add2="CDLU/sem2/2016/";
        String n="CDLU/sem2/2017/";
        int timeStamp = (int) System.currentTimeMillis();
        switch (item.getItemId()) {
            case R.id.download:     //May 2015
                switch (m.getit2()) {
                    case 0:
                        startDownload("Number Theory and Trigonometry (May 15).pdf",add+"Number%20Theory%20And%20Trigonometry%20(Dec%2014).pdf",21);
                        starting();
                        break;
                    case 1:
                        startDownload("Ordinary Differential Equations (May 15).pdf",add+"Ordinary%20Differential%20Equations%20(Dec%2014).pdf",22);
                        starting();
                        break;
                    case 2:
                        startDownload("Vector Calculus (May 15).pdf",add+"Vector%20Calculus%20(Dec%2014).pdf",23);
                        starting();
                        break;
                    case 3:
                        startDownload("Discrete Mathematics-II (May 15).pdf",add+"Discrete%20Mathematics-II%20(Dec%2014).pdf",24);
                        starting();
                        break;
                    case 4:
                        startDownload("Regression Analysis And Probability (May 15).pdf",add+"Regression%20Analysis%20And%20Probability%20(Dec%2014).pdf",25);
                        starting();
                        break;
                    case 5:
                        startDownload("Digital Logic and Computer Design (May 15).pdf",add+"Digital%20Logic%20And%20Computer%20Design%20(Dec%2014).pdf",26);
                        starting();
                        break;
                    case 6:
                        startDownload("Problem Solving Through C (May 15).pdf",add+"Problem%20Solving%20Through%20C%20(Dec%2014).pdf",27);
                        starting();
                        break;
                    case 7:
                        startDownload("English-II (May 15).pdf",add+"English-II%20(Dec%2014).pdf",28);
                        starting();
                        break;
                    case 8:
                        startDownload("Complete Sem 2 (May 15).pdf",add+"Complete%20Sem%202%20(Dec%2014).pdf",29);
                        starting();
                        break;
                }
                return true;
            case R.id.download2:    //May 2016
                switch (m.getit2()) {
                    case 0:
                        startDownload("Number Theory and Trigonometry (May 16).pdf",add2+"Number%20Theory%20And%20Trigonometry.pdf",1621);
                        starting();
                        break;
                    case 1:
                        startDownload("Ordinary Differential Equations (May 16).pdf",add2+"Ordinary%20Differential%20Equations.pdf",1622);
                        starting();
                        break;
                    case 2:
                        startDownload("Vector Calculus (May 16).pdf",add2+"Vector%20Calculus.pdf",1623);
                        starting();
                        break;
                    case 3:
                        startDownload("Discrete Mathematics-II (May 16).pdf",add2+"Discrete%20Mathematics-II.pdf",1624);
                        starting();
                        break;
                    case 4:
                        startDownload("Regression Analysis And Probability (May 16).pdf",add2+"Regression%20Analysis%20And%20Probability.pdf",1625);
                        starting();
                        break;
                    case 5:
                        startDownload("Digital Logic and Computer Design (May 16).pdf",add2+"Digital%20Logic%20And%20Computer%20Design.pdf",1626);
                        starting();
                        break;
                    case 6:
                        startDownload("Problem Solving Through C (May 16).pdf",add2+"Problem%20Solving%20Through%20C.pdf",1627);
                        starting();
                        break;
                    case 7:
                        startDownload("English-II (May 16).pdf",add2+"English-II.pdf",1628);
                        starting();
                        break;
                    case 8:
                        startDownload("MSc Maths 5-Year 2nd Sem (May 16).pdf",add2+"MSc%20Maths%202nd%20Sem%20%28May%202016%29.pdf",1629);
                        starting();
                        break;
                }
                return true;
            case R.id.download3:
                starting();
                switch (m.getit2()) {
                    case 0:
                        startDownload("Number Theory and Trigonometry (May 17).pdf",n+"NTT.pdf",timeStamp);
                        break;
                    case 1:
                        startDownload("Ordinary Differential Equations (May 17).pdf",n+"ODE.pdf",timeStamp);
                        break;
                    case 2:
                        startDownload("Vector Calculus (May 17).pdf",n+"VC.pdf",timeStamp);
                        break;
                    case 3:
                        startDownload("Discrete Mathematics-II (May 17).pdf",n+"DM.pdf",timeStamp);
                        break;
                    case 4:
                        startDownload("Regression Analysis And Probability (May 17).pdf",n+"RAP.pdf",timeStamp);
                        break;
                    case 5:
                        startDownload("Digital Logic and Computer Design (May 17).pdf",n+"DLCD.pdf",timeStamp);
                        break;
                    case 6:
                        startDownload("Problem Solving Through C (May 17).pdf",n+"PSTC.pdf",timeStamp);
                        break;
                    case 7:
                        startDownload("English-II (May 17).pdf",n+"E.pdf",timeStamp);
                        break;
                    case 8:
                        startDownload("MSc Maths 5-Year 2nd Sem (May 17).pdf",n+"ALL.pdf",timeStamp);
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

    private void registerReceiver(){
        LocalBroadcastManager bManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("message_progress");
        bManager.registerReceiver(broadcastReceiver, intentFilter);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("message_progress")){
                Download download = intent.getParcelableExtra("download");
                if(download.getProgress() == 100){

                } else {

                }
            }
        }
    };
}
