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

public class sem7 extends AppCompatActivity {

    private final String subject_names[] = {
            "Advanced Abstract Algebra",
            "Real Analysis",
            "Mechanics",
            "Complex Analysis-I",
            "Ordinary Differential Equations",
            "Download All"
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sem7);
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
                    m.getClickSem7(position);
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
        inflater.inflate(R.menu.sem7,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        MyApp m = (MyApp) getApplicationContext();
        switch (item.getItemId()) {
            case R.id.download:    //December 2015
                downloadOldPaper("5");
                return true;
            case R.id.dec17:
                downloadPaper("7");
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void downloadPaper(String year){
        MyApp m = (MyApp) getApplicationContext();
        String add= "CDLU/sem7/201".concat(year).concat("/");
        String yr = year.concat(").pdf");
        switch (m.getit7()) {
            case 0:
                AppUtils.startDownload("Abstract Algebra (Dec 1".concat(yr),
                        add+"AA.pdf",this);
                break;
            case 1:
                AppUtils.startDownload("Real Analysis 5 (Dec 1".concat(yr),
                        add+"RA.pdf",this);
                break;
            case 2:
                AppUtils.startDownload("Mechanics (Dec 1".concat(yr),
                        add+"M.pdf",this);
                break;
            case 3:
                AppUtils.startDownload("Complex Analysis-I (Dec 1".concat(yr),
                        add+"CA.pdf",this);
                break;
            case 4:
                AppUtils.startDownload("Ordinary Differential Equations (Dec 1".concat(yr),
                        add+"ODE.pdf",this);
                break;
            case 5:
                AppUtils.startDownload("Complete Sem 7th (Dec 1".concat(yr),
                        add+"ALL.pdf",this);
                break;
        }
    }

    private void downloadOldPaper(String year){
        MyApp m = (MyApp) getApplicationContext();
        String add= "CDLU/sem7/201".concat(year).concat("/");
        switch (m.getit7()) {
            case 0:
                AppUtils.startDownload("Advanced Abstract Algebra (Dec 1".concat(").pdf"),
                        add+"1AAA%28Dec15%29.pdf",this);
                break;
            case 1:
                AppUtils.startDownload("Real Analysis 5 (Dec 1".concat(").pdf"),
                        add+"2RA5%28Dec15%29.pdf",this);
                break;
            case 2:
                AppUtils.startDownload("Mechanics (Dec 1".concat(").pdf"),
                        add+"3Mech%28Dec15%29.pdf",this);
                break;
            case 3:
                AppUtils.startDownload("Complex Analysis-I (Dec 1".concat(").pdf"),
                        add+"4CA1%28Dec15%29.pdf",this);
                break;
            case 4:
                AppUtils.startDownload("Ordinary Differential Equations (Dec 1".concat(").pdf"),
                        add+"5ODE5%28Dec15%29.pdf",this);
                break;
            case 5:
                AppUtils.startDownload("Complete Sem 7th (Dec 1".concat(").pdf"),
                        add+"Complete%20Sem%207%20%28Dec15%29.pdf",this);
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
