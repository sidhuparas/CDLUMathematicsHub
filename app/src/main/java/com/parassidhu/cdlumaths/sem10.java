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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sem10);
        setupView();
        initViews();

        sidhu.renderTheme(this);
        AdView adView = this.findViewById(R.id.adView);
        sidhu.displayAds(this,adView);
        final RecyclerView rcl = findViewById(R.id.card_recycler_view);
        ItemClickSupport.addTo(rcl).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                try {
                    MyApp m = (MyApp) getApplicationContext();
                    m.getClickSem10(position);
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
        inflater.inflate(R.menu.sem10,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        MyApp m = (MyApp) getApplicationContext();
        String add[]={
                "CDLU/sem10/2016/",
                "CDLU/sem10/2017/"};
        switch (item.getItemId()) {
            case R.id.download:
                switch (m.getit10()) {
                    case 0:
                        sidhu.startDownload("Functional Analysis (May 16).pdf",
                                add[0]+"FA%28May16%29.pdf",this);
                        break;
                    case 1:
                        sidhu.startDownload("Partial Differential Equations-10 (May 16).pdf",
                                add[0]+"PDE%28May16%29.pdf",this);
                        break;
                    case 2:
                        sidhu.startDownload("Mechanics of Solids-II (May 16).pdf",
                                add[0]+"MoS%28May16%29.pdf",this);
                        break;
                    case 3:
                        sidhu.startDownload("Boundary Value Problem (May 16).pdf",
                                add[0]+"BVP%28May16%29.pdf",this);
                        break;
                    case 4:
                        sidhu.startDownload("Mathematical Aspect of Seismology (May 16).pdf",
                                add[0]+"MaOS%28May16%29.pdf",this);
                        break;
                    case 5:
                        sidhu.startDownload("MSc Maths 5-Year 10th Sem (May 16).pdf",
                                add[0]+"MSc%20Maths%2010th%20Sem%20%28May%2016%29.pdf",this);
                        break;
                }
                return true;
            case R.id.may17:
                switch (m.getit10()) {
                    case 0:
                        sidhu.startDownload("Functional Analysis (May 17).pdf",
                                add[1]+"FA.pdf",this);
                        break;
                    case 1:
                        sidhu.startDownload("Partial Differential Equations-10 (May 17).pdf",
                                add[1]+"PDE.pdf",this);
                        break;
                    case 2:
                        sidhu.startDownload("Mechanics of Solids-II (May 17).pdf",
                                add[1]+"MoS.pdf",this);
                        break;
                    case 3:
                        sidhu.startDownload("Boundary Value Problem (May 17).pdf",
                                add[1]+"BVP.pdf",this);
                        break;
                    case 4:
                        sidhu.startDownload("Mathematical Aspect of Seismology (May 17).pdf",
                                add[1]+"MaOS.pdf",this);
                        break;
                    case 5:
                        sidhu.startDownload("MSc Maths 5-Year 10th Sem (May 17).pdf",
                                add[1]+"ALL.pdf",this);
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