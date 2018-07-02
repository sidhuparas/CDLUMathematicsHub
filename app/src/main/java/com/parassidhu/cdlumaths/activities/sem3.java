package com.parassidhu.cdlumaths.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdView;
import com.parassidhu.cdlumaths.R;
import com.parassidhu.cdlumaths.adapters.QueAdapter;
import com.parassidhu.cdlumaths.models.AndroidVersion;
import com.parassidhu.cdlumaths.utils.ItemClickSupport;
import com.parassidhu.cdlumaths.utils.MyApp;
import com.parassidhu.cdlumaths.utils.sidhu;

import java.util.ArrayList;

public class sem3 extends AppCompatActivity {

    private final String subject_names[] = {
            "Advanced Calculus",
            "Partial Differential Equations",
            "Statics",
            "Differential Geometry",
            "Probability Distributions",
            "Structured System Analysis and Design",
            "Internet And Web Designing",
            "Hindi",
            "Download All"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sem3);
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
                   m.getClickSem3(position);
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
        inflater.inflate(R.menu.sem3,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        MyApp m = (MyApp) getApplicationContext();
        String add="CDLU/sem3/2015/";
        String n="CDLU/sem3/2016/";

        switch (item.getItemId()) {
            case R.id.download:    //December 2015
                switch (m.getit3()) {
                    case 0:
                        sidhu.startDownload("Advanced Calculus (Dec 15).pdf",
                                add+"AdvancedCalculus(Dec14).pdf",this);
                        break;
                    case 1:
                        sidhu.startDownload("Partial Differential Equations (Dec 15).pdf",
                                add+"Partial%20Differential%20Equations%20(Dec%2014).pdf",this);
                        break;
                    case 2:
                        sidhu.startDownload("Statics (Dec 15).pdf",add+"Statics%20(Dec%2014).pdf",this);
                        break;
                    case 3:
                        sidhu.startDownload("Differential Geometry (Dec 15).pdf",
                                add+"Differential%20Geometry%20(Dec%2014).pdf",this);
                        break;
                    case 4:
                        sidhu.startDownload("Probability Distributions (Dec 15).pdf",
                                add+"Probability%20Distributions%20(Dec%2014).pdf",this);
                        break;
                    case 5:
                        sidhu.startDownload("Structured System Analysis and Design (Dec 15).pdf",
                                add+"Structured%20System%20Analysis%20And%20Design%20(Dec%2014).pdf",this);
                        break;
                    case 6:
                        sidhu.startDownload("Internet And Web Designing (Dec 15).pdf",
                                add+"Internet%20And%20Web%20Designing%20(Dec%2014).pdf",this);
                        break;
                    case 7:
                        sidhu.startDownload("Hindi-I (Dec 15).pdf",
                                add+"Hindi-I%20(Dec%2014).pdf",this);
                        break;
                    case 8:
                        sidhu.startDownload("Complete Sem 3 (Dec 15).pdf",
                                add+"Complete%20Sem%203%20(Dec%2014).pdf",this);
                        break;
                }
                return true;
            case R.id.download2:
                
                switch (m.getit3()) {
                    case 0:
                        sidhu.startDownload("Advanced Calculus (Dec 16).pdf",n+"AC.pdf",this);
                        break;
                    case 1:
                        sidhu.startDownload("Partial Differential Equations (Dec 16).pdf",n+"PDE.pdf",this);
                        break;
                    case 2:
                        sidhu.startDownload("Statics (Dec 16).pdf",n+"S.pdf",this);
                        break;
                    case 3:
                        sidhu.startDownload("Differential Geometry (Dec 16).pdf",n+"DG.pdf",this);
                        break;
                    case 4:
                        sidhu.startDownload("Probability Distributions (Dec 16).pdf",n+"PD.pdf",this);
                        break;
                    case 5:
                        sidhu.startDownload("Structured System Analysis and Design (Dec 16).pdf",n+"SSAD.pdf",this);
                        break;
                    case 6:
                        sidhu.startDownload("Internet And Web Designing (Dec 16).pdf",n+"IWD.pdf",this);
                        break;
                    case 7:
                        sidhu.startDownload("Hindi-I (Dec 16).pdf",n+"H.pdf",this);
                        break;
                    case 8:
                        sidhu.startDownload("Complete Sem 3 (Dec 16).pdf",n+"ALL.pdf",this);
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
        Button download = findViewById(R.id.downloadLiveHTML);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sidhu.openWebPage(sem3.this,"http://www.downloadinformer.com/2016/07/live-html-makes-html-coding-easier.html");
            }
        });
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
