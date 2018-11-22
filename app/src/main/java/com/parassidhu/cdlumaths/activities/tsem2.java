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

public class tsem2 extends AppCompatActivity {

    private final String subject_names[] = {
            "Advanced Abstract Algebra",
            "Measure and Integration Theory",
            "Mechanics of Solids",
            "System Of Differential Equations",
            "Computer Programming in Fortran 90 & 95",
            "Methods of Applied Mathematics"
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
        inflater.inflate(R.menu.tmay,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.download:
                downloadPaper("8");
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void downloadPaper(String year) {
        MyApp m = (MyApp) getApplicationContext();
        String add2="CDLU/2year/sem2/201".concat(year).concat("/");
        String yr = year.concat(").pdf");
        switch (m.hitit2()) {
            case 0:
                AppUtils.startDownload("Advanced Abstract Algebra (May 1".concat(yr),
                        add2+"AAA.pdf",this);
                break;
            case 1:
                AppUtils.startDownload("Measure and Integration Theory (May 1".concat(yr),
                        add2+"MaIT.pdf",this);
                break;
            case 2:
                AppUtils.startDownload("Mechanics of Solids (May 1".concat(yr),
                        add2+"MoS.pdf",this);
                break;
            case 3:
                AppUtils.startDownload("System Of Differential Equations (May 1".concat(yr),
                        add2+"SoDE.pdf",this);
                break;
            case 4:
                AppUtils.startDownload("Computer Programming in Fortran 90 & 95 (May 1".concat(yr),
                        add2+"CPiF.pdf",this);
                break;
            case 5:
                AppUtils.startDownload("Methods of Applied Mathematics (May 1".concat(yr),
                        add2+"MoAM.pdf",this);
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

        QueAdapter adapter = new QueAdapter(getApplicationContext(),
                AppUtils.prepareDataForQuePap(subject_names));
        recyclerView.setAdapter(adapter);
    }
}