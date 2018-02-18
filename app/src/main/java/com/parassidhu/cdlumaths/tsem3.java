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

public class tsem3 extends AppCompatActivity {

    private final String subject_names[] = {
            "Topology",
            "Integral Equations",
            "Mechanics Of Solids-I",
            "Mathematical Statistics",
            "Advanced Discrete Mathematics",
            "Ordinary Differential Equations",
            "Download All"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tsem3);
        setupView();
        initViews();

        sidhu.renderTheme(this);
        final AdView adView = this.findViewById(R.id.adView);
        sidhu.displayAds(this,adView);

        final RecyclerView rcl = findViewById(R.id.card_recycler_view);
        ItemClickSupport.addTo(rcl).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                try {
                    MyApp m = (MyApp) getApplicationContext();
                    m.getHitSem3(position);
                    registerForContextMenu(rcl.findFocus());
                    openContextMenu(v);
                }catch (Exception ex){}
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tdec,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        MyApp m = (MyApp) getApplicationContext();
        String add="CDLU/2year/sem3/2015/";
        switch (item.getItemId()) {
            case R.id.download:
                switch (m.hitit3()) {
                    case 0:
                        sidhu.startDownload("Topology (Dec 15).pdf",add+"Topology.pdf",this);
                        break;
                    case 1:
                        sidhu.startDownload("Integral Equations (Dec 15).pdf",add+"Integral%20Equations.pdf",this);
                        break;
                    case 2:
                        sidhu.startDownload("Mechanics Of Solids-I (Dec 15).pdf",add+"Mechanics%20of%20Solids-I.pdf",this);
                        break;
                    case 3:
                        sidhu.startDownload("Mathematical Statistics (Dec 15).pdf",add+"Mathematical%20Statistics.pdf",this);
                        break;
                    case 4:
                        sidhu.startDownload("Advanced Discrete Mathematics (Dec 15).pdf",add+"Advanced%20Discrete%20Maths.pdf",this);
                        break;
                    case 5:
                        sidhu.startDownload("Ordinary Differential Equations (Dec 15).pdf",add+"ODE.pdf",this);
                        break;
                    case 6:
                        sidhu.startDownload("MSc Maths 2-Year 3rd Sem (Dec 15).pdf",
                                add+"msc%20maths%202%20years%203rd%20sem%2012%202015.pdf",this);
                        break;
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
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
}