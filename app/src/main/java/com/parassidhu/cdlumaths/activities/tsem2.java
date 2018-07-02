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

public class tsem2 extends AppCompatActivity {

    private final String subject_names[] = {
            "Advanced Abstract Algebra-II",
            "Measure and Integration Theory",
            "Computer Programming",
            "Complex Analysis-II",
            "Ordinary Differential Equations-II",
            "Download All"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tsem2);
        setupView();
        initViews();

        AppUtils.renderTheme(this);
        final AdView adView = this.findViewById(R.id.adView);
        AppUtils.displayAds(this,adView);

        final RecyclerView rcl = findViewById(R.id.card_recycler_view);
        ItemClickSupport.addTo(rcl).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                try {
                    MyApp m = (MyApp) getApplicationContext();
                    m.getHitSem2(position);
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
                        AppUtils.startDownload("Advanced Abstract Algebra-II (May 15).pdf",add+"AAA-II.pdf",this);
                        break;
                    case 1:
                        AppUtils.startDownload("Measure and Integration Theory (May 15).pdf",add+"MaIT.pdf",this);
                        break;
                    case 2:
                        AppUtils.startDownload("Computer Programming (May 15).pdf",add+"Computer%20Programming.pdf",this);
                        break;
                    case 3:
                        AppUtils.startDownload("Complex Analysis-II (May 15).pdf",add+"Complex%20Analyis-II.pdf",this);
                        break;
                    case 4:
                        AppUtils.startDownload("Ordinary Differential Equations-II (May 15).pdf",add+"ODE-II.pdf",this);
                        break;
                    case 5:
                        AppUtils.startDownload("MSc Maths 2-Year 2nd Sem (May 15).pdf",
                                add+"MSc%20maths%202-year%202nd%20sem%20%2815%29.pdf",this);
                        break;
                }
                return true;

            case R.id.download2:
                switch (m.hitit2()) {
                    case 0:
                        AppUtils.startDownload("Advanced Abstract Algebra-II (May 16).pdf",add2+"AAA-II.pdf",this);
                        break;
                    case 1:
                        AppUtils.startDownload("Measure and Integration Theory (May 16).pdf",add2+"MaIT.pdf",this);
                        break;
                    case 2:
                        AppUtils.startDownload("Computer Programming (May 16).pdf",add2+"CP.pdf",this);
                        break;
                    case 3:
                        AppUtils.startDownload("Complex Analysis-II (May 16).pdf",add2+"CA.pdf",this);
                        break;
                    case 4:
                        AppUtils.startDownload("Ordinary Differential Equations-II (May 16).pdf",
                                add2+"ODE-II.pdf",this);
                        break;
                    case 5:
                        AppUtils.startDownload("MSc Maths 2-Year 2nd Sem (May 16).pdf",
                                add2+"MSc%20maths%202-year%202nd%20sem.pdf",this);
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