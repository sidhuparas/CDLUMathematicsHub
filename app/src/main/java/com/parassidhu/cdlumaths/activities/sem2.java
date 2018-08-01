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
import com.parassidhu.cdlumaths.models.OldItem;
import com.parassidhu.cdlumaths.utils.AppUtils;
import com.parassidhu.cdlumaths.utils.ItemClickSupport;
import com.parassidhu.cdlumaths.utils.MyApp;

import java.util.ArrayList;

public class sem2 extends AppCompatActivity {

    private final String subject_names[] = {
            "Number Theory and Trigonometry",
            "Ordinary Differential Equations",
            "Vector Calculus",
            "Discrete Mathematics",
            "Regression Analysis And Probability",
            "Digital Logic and Computer Design",
            "Problem Solving Through C",
            "English",
            "Download All"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sem2);

        AdView adView = this.findViewById(R.id.adView);
        AppUtils.displayAds(this,adView);

        setupView();
        initViews();
        final RecyclerView rcl = findViewById(R.id.card_recycler_view);
        ItemClickSupport.addTo(rcl).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                try {
                    MyApp m = (MyApp) getApplicationContext();
                    m.getClickSem2(position);
                    registerForContextMenu(rcl.findFocus());
                    openContextMenu(v);
                }catch (Exception ex){}
            }
        });
        AppUtils.renderTheme(this);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sem2,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        MyApp m = (MyApp) getApplicationContext();
        String add="CDLU/sem2/2015/";
        String add2="CDLU/sem2/2016/";
        String n="CDLU/sem2/2017/";
        switch (item.getItemId()) {
            case R.id.download:     //May 2015
                switch (m.getit2()) {
                    case 0:
                        AppUtils.startDownload("Number Theory and Trigonometry (May 15).pdf",
                                add+"Number%20Theory%20And%20Trigonometry%20(Dec%2014).pdf",this);
                        break;
                    case 1:
                        AppUtils.startDownload("Ordinary Differential Equations (May 15).pdf",
                                add+"Ordinary%20Differential%20Equations%20(Dec%2014).pdf",this);
                        break;
                    case 2:
                        AppUtils.startDownload("Vector Calculus (May 15).pdf",
                                add+"Vector%20Calculus%20(Dec%2014).pdf",this);
                        break;
                    case 3:
                        AppUtils.startDownload("Discrete Mathematics-II (May 15).pdf",
                                add+"Discrete%20Mathematics-II%20(Dec%2014).pdf",this);
                        break;
                    case 4:
                        AppUtils.startDownload("Regression Analysis And Probability (May 15).pdf",
                                add+"Regression%20Analysis%20And%20Probability%20(Dec%2014).pdf",this);
                        break;
                    case 5:
                        AppUtils.startDownload("Digital Logic and Computer Design (May 15).pdf",
                                add+"Digital%20Logic%20And%20Computer%20Design%20(Dec%2014).pdf",this);
                        break;
                    case 6:
                        AppUtils.startDownload("Problem Solving Through C (May 15).pdf",
                                add+"Problem%20Solving%20Through%20C%20(Dec%2014).pdf",this);
                        break;
                    case 7:
                        AppUtils.startDownload("English-II (May 15).pdf",
                                add+"English-II%20(Dec%2014).pdf",this);
                        break;
                    case 8:
                        AppUtils.startDownload("Complete Sem 2 (May 15).pdf",
                                add+"Complete%20Sem%202%20(Dec%2014).pdf",this);
                        break;
                }
                return true;
            case R.id.download2:    //May 2016
                switch (m.getit2()) {
                    case 0:
                        AppUtils.startDownload("Number Theory and Trigonometry (May 16).pdf",
                                add2+"Number%20Theory%20And%20Trigonometry.pdf",this);
                        break;
                    case 1:
                        AppUtils.startDownload("Ordinary Differential Equations (May 16).pdf",
                                add2+"Ordinary%20Differential%20Equations.pdf",this);
                        break;
                    case 2:
                        AppUtils.startDownload("Vector Calculus (May 16).pdf",
                                add2+"Vector%20Calculus.pdf",this);
                        break;
                    case 3:
                        AppUtils.startDownload("Discrete Mathematics-II (May 16).pdf",
                                add2+"Discrete%20Mathematics-II.pdf",this);
                        break;
                    case 4:
                        AppUtils.startDownload("Regression Analysis And Probability (May 16).pdf",
                                add2+"Regression%20Analysis%20And%20Probability.pdf",this);
                        break;
                    case 5:
                        AppUtils.startDownload("Digital Logic and Computer Design (May 16).pdf",
                                add2+"Digital%20Logic%20And%20Computer%20Design.pdf",this);
                        break;
                    case 6:
                        AppUtils.startDownload("Problem Solving Through C (May 16).pdf",
                                add2+"Problem%20Solving%20Through%20C.pdf",this);
                        break;
                    case 7:
                        AppUtils.startDownload("English-II (May 16).pdf",
                                add2+"English-II.pdf",this);
                        break;
                    case 8:
                        AppUtils.startDownload("MSc Maths 5-Year 2nd Sem (May 16).pdf",
                                add2+"MSc%20Maths%202nd%20Sem%20%28May%202016%29.pdf",this);
                        break;
                }
                return true;

            case R.id.download3:
                switch (m.getit2()) {
                    case 0:
                        AppUtils.startDownload("Number Theory and Trigonometry (May 17).pdf",
                                n+"NTT.pdf",this);
                        break;
                    case 1:
                        AppUtils.startDownload("Ordinary Differential Equations (May 17).pdf",
                                n+"ODE.pdf",this);
                        break;
                    case 2:
                        AppUtils.startDownload("Vector Calculus (May 17).pdf",
                                n+"VC.pdf",this);
                        break;
                    case 3:
                        AppUtils.startDownload("Discrete Mathematics-II (May 17).pdf",
                                n+"DM.pdf",this);
                        break;
                    case 4:
                        AppUtils.startDownload("Regression Analysis And Probability (May 17).pdf",
                                n+"RAP.pdf",this);
                        break;
                    case 5:
                        AppUtils.startDownload("Digital Logic and Computer Design (May 17).pdf",
                                n+"DLCD.pdf",this);
                        break;
                    case 6:
                        AppUtils.startDownload("Problem Solving Through C (May 17).pdf",
                                n+"PSTC.pdf",this);
                        break;
                    case 7:
                        AppUtils.startDownload("English-II (May 17).pdf",
                                n+"E.pdf",this);
                        break;
                    case 8:
                        AppUtils.startDownload("MSc Maths 5-Year 2nd Sem (May 17).pdf",
                                n+"ALL.pdf",this);
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
            OldItem oldItem = new OldItem();
            oldItem.setName(subject_names[i]);
            oldItem.setImage_url(R.drawable.materialq);
            android_version.add(oldItem);
        }
        return android_version;
    }
}
