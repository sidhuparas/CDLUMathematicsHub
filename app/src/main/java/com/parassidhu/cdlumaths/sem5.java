package com.parassidhu.cdlumaths;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

public class sem5 extends AppCompatActivity {

    private final String subject_names[] = {
            "Real Analysis",
            "Groups and Rings",
            "Numerical Methods",
            "Methods of Applied Mathematics",
            "Computer Networks and Data Communication",
            "Object-Oriented Programming With C++",
            "Download All"
    };
    private float mActionBarHeight;
    private ActionBar actionBar;

    public boolean showAds() {

        SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        boolean showAd = getPrefs.getBoolean("showAd", true);

        return showAd;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sem5);
        setupView();
        initViews();
        registerReceiver();
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
                        case 0:
                            m.getClickSem5(0);
                            registerForContextMenu(rcl.findFocus());
                            openContextMenu(v);
                            break;
                        case 1:     //Subject 1
                            m.getClickSem5(1);
                            registerForContextMenu(rcl.findFocus());
                            openContextMenu(v);
                            break;
                        case 2:     //Subject 2
                            m.getClickSem5(2);
                            registerForContextMenu(rcl.findFocus());
                            openContextMenu(v);
                            break;
                        case 3:
                            m.getClickSem5(3);
                            registerForContextMenu(rcl.findFocus());
                            openContextMenu(v);
                            break;
                        case 4:
                            m.getClickSem5(4);
                            registerForContextMenu(rcl.findFocus());
                            openContextMenu(v);
                            break;
                        case 5:
                            m.getClickSem5(5);
                            registerForContextMenu(rcl.findFocus());
                            openContextMenu(v);
                            break;
                        case 6:
                            m.getClickSem5(6);
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
        inflater.inflate(R.menu.sem5,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        MyApp m = (MyApp) getApplicationContext();
        String add="CDLU/sem5/2015/";
        String n = "CDLU/sem5/2016/";
        int timeStamp = (int)System.currentTimeMillis();
        switch (item.getItemId()) {
            case R.id.download:
                switch (m.getit5()) {
                    case 0:
                        startDownload("Real Analysis (Dec 15).pdf", add + "Real%20Analysis%20%28Dec%2015%29.pdf", 51);
                        starting();
                        break;
                    case 1:
                        startDownload("Groups and Rings (Dec 15).pdf", add + "Groups%20And%20Rings%20(Dec%2015).pdf", 52);
                        starting();
                        break;
                    case 2:
                        startDownload("Numerical Methods (Dec 15).pdf", add + "Numerical%20Methods%20(Dec%2015).pdf", 53);
                        starting();
                        break;
                    case 3:
                        startDownload("Methods of Applied Mathematics (Dec 15).pdf", add + "Methods%20of%20Applied%20Mathematics%20(Dec%2015).pdf", 54);
                        starting();
                        break;
                    case 4:
                        startDownload("Computer Networks and Data Communication (Dec 15).pdf", add + "Computer%20Networks%20and%20Data%20Communication%20(Dec%2015).pdf", 55);
                        starting();
                        break;
                    case 5:
                        startDownload("Object-Oriented Programming With C++ (Dec 15).pdf", add + "Object-Oriented%20Programming%20With%20C++%20(Dec%2015).pdf", 56);
                        starting();
                        break;
                    case 6:
                        startDownload("MSc Maths 5-Year 5th Sem (Dec 15).pdf", add + "MSc%20Maths%205-Year%205th%20Sem.pdf", 57);
                        starting();
                        break;
                }
                return true;
            case R.id.download2:
                starting();
                switch (m.getit5()) {
                    case 0:
                        startDownload("Real Analysis (Dec 16).pdf", n + "RA.pdf", timeStamp);
                        break;
                    case 1:
                        startDownload("Groups and Rings (Dec 16).pdf", n + "GR.pdf", timeStamp);
                        break;
                    case 2:
                        startDownload("Numerical Methods (Dec 16).pdf", n + "NM.pdf", timeStamp);
                        break;
                    case 3:
                        startDownload("Methods of Applied Mathematics (Dec 16).pdf", n + "MAM.pdf", timeStamp);
                        break;
                    case 4:
                        startDownload("Computer Networks and Data Communication (Dec 16).pdf", n + "CNDC.pdf", timeStamp);
                        break;
                    case 5:
                        startDownload("Object-Oriented Programming With C++ (Dec 16).pdf", n + "OOPC.pdf", timeStamp);
                        break;
                    case 6:
                        startDownload("MSc Maths 5-Year 5th Sem (Dec 16).pdf", n + "ALL.pdf", timeStamp);
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
