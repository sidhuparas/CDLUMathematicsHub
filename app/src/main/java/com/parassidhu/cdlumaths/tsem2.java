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

public class tsem2 extends AppCompatActivity {

    private final String subject_names[] = {
            "Advanced Abstract Algebra-II",
            "Measure and Integration Theory",
            "Computer Programming",
            "Complex Analysis-II",
            "Ordinary Differential Equations-II",
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
        setContentView(R.layout.activity_tsem2);
        setupView();
        initViews();
        registerReceiver();
        sidhu.renderTheme(this);
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

        final RecyclerView rcl = (RecyclerView) findViewById(R.id.card_recycler_view);
        ItemClickSupport.addTo(rcl).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                try {
                    MyApp m = (MyApp) getApplicationContext();
                    switch (position) {
                        case 0:     //Subject 1
                            m.getHitSem2(0);
                            registerForContextMenu(rcl.findFocus());
                            openContextMenu(v);
                            break;
                        case 1:     //Subject 2
                            m.getHitSem2(1);
                            registerForContextMenu(rcl.findFocus());
                            openContextMenu(v);
                            break;
                        case 2:
                            m.getHitSem2(2);
                            registerForContextMenu(rcl.findFocus());
                            openContextMenu(v);
                            break;
                        case 3:
                            m.getHitSem2(3);
                            registerForContextMenu(rcl.findFocus());
                            openContextMenu(v);
                            break;
                        case 4:
                            m.getHitSem2(4);
                            registerForContextMenu(rcl.findFocus());
                            openContextMenu(v);
                            break;
                        case 5:
                            m.getHitSem2(5);
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
        inflater.inflate(R.menu.tmay,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        MyApp m = (MyApp) getApplicationContext();
        String add="CDLU/2year/sem2/2015/";
        String add2="CDLU/2year/sem2/2016/";
        switch (item.getItemId()) {
            case R.id.download:
                switch (m.hitit2()) {
                    case 0:
                        startDownload("Advanced Abstract Algebra-II (May 15).pdf",add+"AAA-II.pdf",21521);
                        starting();
                        break;
                    case 1:
                        startDownload("Measure and Integration Theory (May 15).pdf",add+"MaIT.pdf",21522);
                        starting();
                        break;
                    case 2:
                        startDownload("Computer Programming (May 15).pdf",add+"Computer%20Programming.pdf",21523);
                        starting();
                        break;
                    case 3:
                        startDownload("Complex Analysis-II (May 15).pdf",add+"Complex%20Analyis-II.pdf",21524);
                        starting();
                        break;
                    case 4:
                        startDownload("Ordinary Differential Equations-II (May 15).pdf",add+"ODE-II.pdf",21525);
                        starting();
                        break;
                    case 5:
                        startDownload("MSc Maths 2-Year 2nd Sem (May 15).pdf",add+"MSc%20maths%202-year%202nd%20sem%20%2815%29.pdf",21526);
                        starting();
                        break;
                }
                return true;
            case R.id.download2:
                switch (m.hitit2()) {
                    case 0:
                        startDownload("Advanced Abstract Algebra-II (May 16).pdf",add2+"AAA-II.pdf",21621);
                        starting();
                        break;
                    case 1:
                        startDownload("Measure and Integration Theory (May 16).pdf",add2+"MaIT.pdf",21622);
                        starting();
                        break;
                    case 2:
                        startDownload("Computer Programming (May 16).pdf",add2+"CP.pdf",21623);
                        starting();
                        break;
                    case 3:
                        startDownload("Complex Analysis-II (May 16).pdf",add2+"CA.pdf",21624);
                        starting();
                        break;
                    case 4:
                        startDownload("Ordinary Differential Equations-II (May 16).pdf",add2+"ODE-II.pdf",21625);
                        starting();
                        break;
                    case 5:
                        startDownload("MSc Maths 2-Year 2nd Sem (May 16).pdf",add2+"MSc%20maths%202-year%202nd%20sem.pdf",21626);
                        starting();
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