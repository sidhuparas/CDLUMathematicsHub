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
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.app.ActionBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.net.URLConnection;
import java.util.ArrayList;
public class sem1 extends AppCompatActivity {

    private final String subject_names[] = {
            "Algebra",
            "Calculus",
            "Solid Geometry",
            "Discrete Mathematics",
            "Descriptive Statistics",
            "Computer Fundamentals",
            "Introduction To Windows Software",
            "English",
            "Download All"
    };

    public static final String MESSAGE_PROGRESS = "message_progress";
    private static final int PERMISSION_REQUEST_CODE = 1;

    public boolean showAds() {
        SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean showAd = getPrefs.getBoolean("showAd", true);
        return showAd;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sem1);

        final AdView adView = (AdView) this.findViewById(R.id.adView);
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
        final RecyclerView rcl = (RecyclerView) findViewById(R.id.card_recycler_view);
        ItemClickSupport.addTo(rcl).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                try{
                MyApp m = (MyApp) getApplicationContext();
                switch (position) {

                    case 0:     //Subject 1
                        m.getClickSem1(0);
                        registerForContextMenu(rcl.findFocus());
                        openContextMenu(v);
                        break;
                    case 1:     //Subject 2
                        m.getClickSem1(1);
                        registerForContextMenu(rcl.findFocus());
                        openContextMenu(v);
                        break;
                    case 2:
                        m.getClickSem1(2);
                        registerForContextMenu(rcl.findFocus());
                        openContextMenu(v);
                        break;
                    case 3:
                        m.getClickSem1(3);
                        registerForContextMenu(rcl.findFocus());
                        openContextMenu(v);
                        break;
                    case 4:
                        m.getClickSem1(4);
                        registerForContextMenu(rcl.findFocus());
                        openContextMenu(v);
                        break;
                    case 5:
                        m.getClickSem1(5);
                        registerForContextMenu(rcl.findFocus());
                        openContextMenu(v);
                        break;
                    case 6:
                        m.getClickSem1(6);
                        registerForContextMenu(rcl.findFocus());
                        openContextMenu(v);
                        break;
                    case 7:
                        m.getClickSem1(7);
                        registerForContextMenu(rcl.findFocus());
                        openContextMenu(v);
                        break;
                    case 8:
                        m.getClickSem1(8);
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

    public void starting() {
        Toast.makeText(sem1.this, "Starting download...Please check notifications panel for progress", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sem1,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        MyApp m = (MyApp) getApplicationContext();
        item.setEnabled(false);
        String add="CDLU/sem1/2014/";
        String asd="CDLU/sem1/2015/";
        String n="CDLU/sem1/2016/";
        int timeStamp = (int) System.currentTimeMillis();
        switch (item.getItemId()) {
            case R.id.download:     //December 2014
                switch (m.getit1()) {
                    case 0:
                       startDownload("Algebra (Dec 14).pdf",add + "Algebra%20(Dec%2014).pdf",11);
                        starting();
                        break;
                    case 1:
                        startDownload("Calculus (Dec 14).pdf",add+"Calculus%20(Dec%2014).pdf",12);
                        starting();
                        break;
                    case 2:
                        startDownload("Solid Geometry (Dec 14).pdf",add+"Solid%20Geometry%20(Dec%2014).pdf",13);
                        starting();
                        break;
                    case 3:
                        startDownload("Discrete Mathematics-I (Dec 14).pdf",add+"Discrete%20Mathematics-I%20(Dec%2014).pdf",14);
                        starting();
                        break;
                    case 4:
                        startDownload("Descriptive Statistics (Dec 14).pdf",add+"Descriptive%20Statistics%20(Dec%2014).pdf",15);
                        starting();
                        break;
                    case 5:
                        startDownload("Computer Fundamentals (Dec 14).pdf",add+"Computer%20Fundamentals%20(Dec%2014).pdf",16);
                        starting();
                        break;
                    case 6:
                        startDownload("Introduction To Window Softwares (Dec 14).pdf",add+"Introduction%20To%20Window%20Softwares%20(Dec%2014).pdf",17);
                        starting();
                        break;
                    case 7:
                        startDownload("English-I (Dec 14).pdf",add+"English-I%20(Dec%2014).pdf",18);
                        starting();
                        break;
                    case 8:
                        startDownload("Complete Sem 1 (Dec 14).pdf",add+"Complete%20Sem%201%20(Dec%2014).pdf",19);
                        starting();
                        break;
                }
                return true;
            case R.id.download2://December 2015
                switch (m.getit1()) {
                    case 0:
                        startDownload("Algebra (Dec 15).pdf",asd+"Al%28Dec15%29.pdf",1511);
                        starting();
                        break;
                    case 1:
                        startDownload("Calculus (Dec 15).pdf",asd+"Ca%28Dec15%29.pdf",1512);
                        starting();
                        break;
                    case 2:
                        startDownload("Solid Geometry (Dec 15).pdf",asd+"SG%28Dec15%29.pdf",1513);
                        starting();
                        break;
                    case 3:
                        startDownload("Discrete Mathematics-I (Dec 15).pdf",asd+"DM1%28Dec15%29.pdf",1514);
                        starting();
                        break;
                    case 4:
                        startDownload("Descriptive Statistics (Dec 15).pdf",asd+"DS%28Dec15%29.pdf",1515);
                        starting();
                        break;
                    case 5:
                        startDownload("Computer Fundamentals (Dec 15).pdf",asd+"CF%28Dec15%29.pdf",1516);
                        starting();
                        break;
                    case 6:
                        startDownload("Introduction To Window Softwares (Dec 15).pdf",asd+"ItWS%28Dec15%29.pdf",1517);
                        starting();
                        break;
                    case 7:
                        startDownload("English-I (Dec 15).pdf",asd+"E%28Dec15%29.pdf",1518);
                        starting();
                        break;
                    case 8:
                        startDownload("Complete Sem 1 (Dec 15).pdf",asd+"Complete%20Sem%201%20%28Dec15%29.pdf",1519);
                        starting();
                        break;
                }
                return true;
            case R.id.download3:
                starting();
                switch (m.getit1()) {
                    case 0:
                        startDownload("Algebra (Dec 16).pdf",n+"A.pdf",timeStamp);
                        break;
                    case 1:
                        startDownload("Calculus (Dec 16).pdf",n+"C.pdf",timeStamp);
                        break;
                    case 2:
                        startDownload("Solid Geometry (Dec 16).pdf",n+"SG.pdf",timeStamp);
                        break;
                    case 3:
                        startDownload("Discrete Mathematics-I (Dec 16).pdf",n+"DM.pdf",timeStamp);
                        break;
                    case 4:
                        startDownload("Descriptive Statistics (Dec 16).pdf",n+"DS.pdf",timeStamp);
                        break;
                    case 5:
                        startDownload("Computer Fundamentals (Dec 16).pdf",n+"CF.pdf",timeStamp);
                        break;
                    case 6:
                        startDownload("Introduction To Window Softwares (Dec 16).pdf",n+"IWS.pdf",timeStamp);
                        break;
                    case 7:
                        startDownload("English-I (Dec 16).pdf",n+"E.pdf",timeStamp);
                        break;
                    case 8:
                        startDownload("Complete Sem 1 (Dec 16).pdf",n+"ALL.pdf",timeStamp);
                        break;
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void startDownload(String filename, String url,int i){
        MyApp x =  (MyApp)getApplicationContext();
        x.getUrl(url);
        x.getFilename(filename);
        x.getID(i);

        Intent intent = new Intent(this,DownloadService.class);
        startService(intent);
    }

    public void setupView() {
        android.support.v7.app.ActionBar acb = getSupportActionBar();
        acb.setHomeButtonEnabled(true);
        acb.setDisplayHomeAsUpEnabled(true);
        acb.setTitle("Choose Subject");
    }
    private void initViews(){
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
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

    private void registerReceiver(){
        LocalBroadcastManager bManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MESSAGE_PROGRESS);
        bManager.registerReceiver(broadcastReceiver, intentFilter);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(MESSAGE_PROGRESS)){
                Download download = intent.getParcelableExtra("download");
                if(download.getProgress() == 100){

                } else {

                }
            }
        }
    };
}
