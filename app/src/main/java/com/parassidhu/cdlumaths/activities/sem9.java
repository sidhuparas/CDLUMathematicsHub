package com.parassidhu.cdlumaths.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.ads.AdView;
import com.parassidhu.cdlumaths.R;
import com.parassidhu.cdlumaths.adapters.QueAdapter;
import com.parassidhu.cdlumaths.models.AndroidVersion;
import com.parassidhu.cdlumaths.utils.AppUtils;
import com.parassidhu.cdlumaths.utils.ItemClickSupport;
import com.parassidhu.cdlumaths.utils.MyApp;

import java.util.ArrayList;

public class sem9 extends AppCompatActivity {

    private final String subject_names[] = {
            "Topology",
            "Advanced Mathematical Methods",
            "Mechanics of Solids-I",
            "Integral Equations",
            "Operations Research",
            "Download All"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sem9);
        setupView();
        initViews();

        AppUtils.renderTheme(this);
        AdView adView = this.findViewById(R.id.adView);
        AppUtils.displayAds(this, adView);

        final RecyclerView rcl = findViewById(R.id.card_recycler_view);
        ItemClickSupport.addTo(rcl).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                try {
                    MyApp m = (MyApp) getApplicationContext();
                    m.getClickSem9(position);
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
        inflater.inflate(R.menu.sem9,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        MyApp m = (MyApp) getApplicationContext();
        String add="CDLU/sem9/2015/";
        String a ="CDLU/sem9/2016/";
        switch (item.getItemId()) {
            case R.id.download:    //December 2015
                switch (m.getit9()) {
                    case 0:
                        AppUtils.startDownload("Topology (Dec 15).pdf",
                                add+"To%28Dec15%29.pdf",this);
                        break;
                    case 1:
                        AppUtils.startDownload("Advanced Mathematical Methods (Dec 15).pdf",
                                add+"AMM%28Dec15%29.pdf",this);
                        break;
                    case 2:
                        AppUtils.startDownload("Mechanics of Solids-I (Dec 15).pdf",
                                add+"MoS1%28Dec15%29.pdf",this);
                        break;
                    case 3:
                        AppUtils.startDownload("Integral Equations (Dec 15).pdf",
                                add+"IE%28Dec15%29.pdf",this);
                        break;
                    case 4:
                        AppUtils.startDownload("Operations Research (Dec 15).pdf",
                                add+"OR%28Dec15%29.pdf",this);
                        break;
                    case 5:
                        AppUtils.startDownload("MSc Maths 5-Year Sem 9th (Dec 15).pdf",
                                add+"Complete%20Sem%209%20%28Dec%2015%29.pdf",this);
                        break;
                }
                return true;
            case R.id.download2:
                switch (m.getit9()) {
                    case 0:
                        AppUtils.startDownload("Topology (Dec 16).pdf",a+"T.pdf",this);
                        break;
                    case 1:
                        AppUtils.startDownload("Advanced Mathematical Methods (Dec 16).pdf",a+"AMM.pdf",this);
                        break;
                    case 2:
                        AppUtils.startDownload("Mechanics of Solids-I (Dec 16).pdf",a+"MS.pdf",this);
                        break;
                    case 3:
                        AppUtils.startDownload("Integral Equations (Dec 16).pdf",a+"IE.pdf",this);
                        break;
                    case 4:
                        AppUtils.startDownload("Operations Research (Dec 16).pdf",a+"OR.pdf",this);
                        break;
                    case 5:
                        AppUtils.startDownload("MSc Maths 5-Year Sem 9th (Dec 16).pdf",a+"ALL.pdf",this);
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
