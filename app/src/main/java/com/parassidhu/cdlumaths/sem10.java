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

public class sem10 extends AppCompatActivity {

    private final String subject_names[] = {
            "Functional Analysis",
            "Partial Differential Equations",
            "Mechanics of Solids-II",
            "Boundary Value Problem",
            "Mathematical Aspect of Seismology",
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
        setContentView(R.layout.activity_sem10);
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
                        case 0:     //Subject 1
                            m.getClickSem10(0);
                            registerForContextMenu(rcl.findFocus());
                            openContextMenu(v);
                            break;
                        case 1:     //Subject 2
                            m.getClickSem10(1);
                            registerForContextMenu(rcl.findFocus());
                            openContextMenu(v);
                            break;
                        case 2:
                            m.getClickSem10(2);
                            registerForContextMenu(rcl.findFocus());
                            openContextMenu(v);
                            break;
                        case 3:
                            m.getClickSem10(3);
                            registerForContextMenu(rcl.findFocus());
                            openContextMenu(v);
                            break;
                        case 4:
                            m.getClickSem10(4);
                            registerForContextMenu(rcl.findFocus());
                            openContextMenu(v);
                            break;
                        case 5:
                            m.getClickSem10(5);
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
        inflater.inflate(R.menu.sem10,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        MyApp m = (MyApp) getApplicationContext();
        String add="CDLU/sem10/2016/";
        switch (item.getItemId()) {
            case R.id.download:
                switch (m.getit10()) {
                    case 0:
                        startDownload("Functional Analysis (May 16).pdf",add+"FA%28May16%29.pdf",16101);
                        starting();
                        break;
                    case 1:
                        startDownload("Partial Differential Equations-10 (May 16).pdf",add+"PDE%28May16%29.pdf",16102);
                        starting();
                        break;
                    case 2:
                        startDownload("Mechanics of Solids-II (May 16).pdf",add+"MoS%28May16%29.pdf",16103);
                        starting();
                        break;
                    case 3:
                        startDownload("Boundary Value Problem (May 16).pdf",add+"BVP%28May16%29.pdf",16104);
                        starting();
                        break;
                    case 4:
                        startDownload("Mathematical Aspect of Seismology (May 16).pdf",add+"MaOS%28May16%29.pdf",16105);
                        starting();
                        break;
                    case 5:
                        startDownload("MSc Maths 5-Year 10th Sem (May 16).pdf",add+"MSc%20Maths%2010th%20Sem%20%28May%2016%29.pdf",16106);
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