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
import com.parassidhu.cdlumaths.models.OldItem;
import com.parassidhu.cdlumaths.utils.AppUtils;
import com.parassidhu.cdlumaths.utils.ItemClickSupport;
import com.parassidhu.cdlumaths.utils.MyApp;

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
            "Hindi"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sem3);
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
                   m.getClickSem3(position);
                   registerForContextMenu(recyclerView);
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
                        AppUtils.startDownload("Advanced Calculus (Dec 15).pdf",
                                add+"AdvancedCalculus(Dec14).pdf",this);
                        break;
                    case 1:
                        AppUtils.startDownload("Partial Differential Equations (Dec 15).pdf",
                                add+"Partial%20Differential%20Equations%20(Dec%2014).pdf",this);
                        break;
                    case 2:
                        AppUtils.startDownload("Statics (Dec 15).pdf",add+"Statics%20(Dec%2014).pdf",this);
                        break;
                    case 3:
                        AppUtils.startDownload("Differential Geometry (Dec 15).pdf",
                                add+"Differential%20Geometry%20(Dec%2014).pdf",this);
                        break;
                    case 4:
                        AppUtils.startDownload("Probability Distributions (Dec 15).pdf",
                                add+"Probability%20Distributions%20(Dec%2014).pdf",this);
                        break;
                    case 5:
                        AppUtils.startDownload("Structured System Analysis and Design (Dec 15).pdf",
                                add+"Structured%20System%20Analysis%20And%20Design%20(Dec%2014).pdf",this);
                        break;
                    case 6:
                        AppUtils.startDownload("Internet And Web Designing (Dec 15).pdf",
                                add+"Internet%20And%20Web%20Designing%20(Dec%2014).pdf",this);
                        break;
                    case 7:
                        AppUtils.startDownload("Hindi-I (Dec 15).pdf",
                                add+"Hindi-I%20(Dec%2014).pdf",this);
                        break;
                    case 8:
                        AppUtils.startDownload("Complete Sem 3 (Dec 15).pdf",
                                add+"Complete%20Sem%203%20(Dec%2014).pdf",this);
                        break;
                }
                return true;
            case R.id.download2:
                downloadQuestionPapers("6");
                return true;
            case R.id.download3:
                downloadQuestionPapers("7");
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void downloadQuestionPapers(String y) {
        MyApp m = (MyApp)getApplicationContext();
        String URL = "CDLU/sem3/201" + y + "/";

        switch (m.getit3()) {
            case 0:
                AppUtils.startDownload("Advanced Calculus (Dec 1" + y + ").pdf",URL+"AC.pdf",this);
                break;
            case 1:
                AppUtils.startDownload("Partial Differential Equations (Dec 1" + y + ").pdf",URL+"PDE.pdf",this);
                break;
            case 2:
                AppUtils.startDownload("Statics (Dec 1" + y + ").pdf",URL+"S.pdf",this);
                break;
            case 3:
                AppUtils.startDownload("Differential Geometry (Dec 1" + y + ").pdf",URL+"DG.pdf",this);
                break;
            case 4:
                AppUtils.startDownload("Probability Distributions (Dec 1" + y + ").pdf",URL+"PD.pdf",this);
                break;
            case 5:
                AppUtils.startDownload("Structured System Analysis and Design (Dec 1" + y + ").pdf",URL+"SSAD.pdf",this);
                break;
            case 6:
                AppUtils.startDownload("Internet And Web Designing (Dec 1" + y + ").pdf",URL+"IWD.pdf",this);
                break;
            case 7:
                AppUtils.startDownload("Hindi-I (Dec 1" + y + ").pdf",URL+"H.pdf",this);
                break;
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
                AppUtils.openWebPage(sem3.this,
                        "http://www.downloadinformer.com/2016/07/live-html-makes-html-coding-easier.html");
            }
        });

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(layoutManager);

        QueAdapter adapter = new QueAdapter(getApplicationContext(),
                AppUtils.prepareDataForQuePap(subject_names));

        recyclerView.setAdapter(adapter);
    }
}
