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
import com.parassidhu.cdlumaths.utils.ItemClickSupport;
import com.parassidhu.cdlumaths.utils.MyApp;
import com.parassidhu.cdlumaths.utils.AppUtils;

import java.util.ArrayList;

public class sem4 extends AppCompatActivity {

    private final String subject_names[] = {
            "Sequences and Series",
            "Special Functions and Integral Transforms",
            "Numerical Analysis",
            "Hydrostatics",
            "Elementary Inference",
            "Operating Systems",
            "Data Structures Using C",
            "Hindi",
            "Download All"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sem4);
        setupView();
        initViews();

        AppUtils.renderTheme(this);
        AdView adView = this.findViewById(R.id.adView);
        AppUtils.displayAds(this,adView);

        final RecyclerView rcl = findViewById(R.id.card_recycler_view);
        ItemClickSupport.addTo(rcl).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                try {
                    MyApp m = (MyApp) getApplicationContext();
                    m.getClickSem4(position);
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
        inflater.inflate(R.menu.sem4,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        MyApp m = (MyApp) getApplicationContext();
        String add[]={
                "CDLU/sem4/2016/",
                "CDLU/sem4/2017/"
        };

        switch (item.getItemId()) {
            case R.id.download:    //December 2015
                switch (m.getit4()) {
                    case 0:
                        AppUtils.startDownload("Sequences and Series (May 16).pdf",
                                add[0]+"Sequence%20And%20Series%20(Dec%2014).pdf",this);
                        break;
                    case 1:
                        AppUtils.startDownload("Special Functions and Integral Transforms (May 16).pdf",
                                add[0]+"Special%20Functions%20And%20Integral%20Transforms%20(Dec%2014).pdf",this);
                        break;
                    case 2:
                        AppUtils.startDownload("Numerical Analysis (May 16).pdf",
                                add[0]+"Numerical%20Analysis%20(Dec%2014).pdf",this);
                        break;
                    case 3:
                        AppUtils.startDownload("Hydrostatics (May 16).pdf",
                                add[0]+"Hydrostatics%20(Dec%2014).pdf",this);
                        break;
                    case 4:
                        AppUtils.startDownload("Elementary Inference (May 16).pdf",
                                add[0]+"Elementary%20Inference%20(Dec%2014).pdf",this);
                        break;
                    case 5:
                        AppUtils.startDownload("Operating Systems (May 16).pdf",
                                add[0]+"Operating%20System%20(Dec%2014).pdf",this);
                        break;
                    case 6:
                        AppUtils.startDownload("Data Structures Using C (May 16).pdf",
                                add[0]+"Data%20Structure%20Using%20C%20(Dec%2014).pdf",this);
                        break;
                    case 7:
                        AppUtils.startDownload("Hindi-II (May 16).pdf",
                                add[0]+"Hindi-II%20(Dec%2014).pdf",this);
                        break;
                    case 8:
                        AppUtils.startDownload("Complete Sem 4 (May 16).pdf",
                                add[0]+"Complete%20Sem%204%20(Dec%2014).pdf",this);
                        break;
                }
                return true;

            case R.id.may17:
                switch (m.getit4()) {
                    case 0:
                        AppUtils.startDownload("Sequences and Series (May 17).pdf",
                                add[1]+"SaS.pdf",this);
                        break;
                    case 1:
                        AppUtils.startDownload("Special Functions and Integral Transforms (May 17).pdf",
                                add[1]+"SFaIT.pdf",this);
                        break;
                    case 2:
                        AppUtils.startDownload("Numerical Analysis (May 17).pdf",
                                add[1]+"NA.pdf",this);
                        break;
                    case 3:
                        AppUtils.startDownload("Hydrostatics (May 17).pdf",
                                add[1]+"H.pdf",this);
                        break;
                    case 4:
                        AppUtils.startDownload("Elementary Inference (May 17).pdf",
                                add[1]+"EI.pdf",this);
                        break;
                    case 5:
                        AppUtils.startDownload("Operating Systems (May 17).pdf",
                                add[1]+"OS.pdf",this);
                        break;
                    case 6:
                        AppUtils.startDownload("Data Structures Using C (May 17).pdf",
                                add[1]+"DSUC.pdf",this);
                        break;
                    case 7:
                        AppUtils.startDownload("Hindi-II (May 17).pdf",
                                add[1]+"Hin.pdf",this);
                        break;
                    case 8:
                        AppUtils.startDownload("Complete Sem 4 (May 17).pdf",
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

