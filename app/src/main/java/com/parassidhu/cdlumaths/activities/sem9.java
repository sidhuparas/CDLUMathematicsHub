package com.parassidhu.cdlumaths.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.ads.AdView;
import com.parassidhu.cdlumaths.R;
import com.parassidhu.cdlumaths.adapters.QueAdapter;
import com.parassidhu.cdlumaths.utils.AppUtils;
import com.parassidhu.cdlumaths.utils.ItemClickSupport;
import com.parassidhu.cdlumaths.utils.MyApp;

public class sem9 extends AppCompatActivity {

    private final String subject_names[] = {
            "Topology",
            "Fluid Mechanics",
            "Integral Equations",
            "Mathematical Statistics",
            "Advanced Mechanics Of Solids"
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
                    registerForContextMenu(recyclerView);
                    openContextMenu(v);
                } catch (Exception ex) {
                }
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sem9, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        MyApp m = (MyApp) getApplicationContext();
        String add = "CDLU/sem9/2015/";
        String a = "CDLU/sem9/2016/";
        switch (item.getItemId()) {
            case R.id.download:    //December 2015
                downloadPaper("8");
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void downloadPaper(String year) {
        MyApp m = (MyApp) getApplicationContext();
        String add = "CDLU/sem9/201".concat(year).concat("/");
        String yr = year.concat(").pdf");
        switch (m.getit9()) {
            case 0:
                AppUtils.startDownload("Topology (Dec 1".concat(yr),
                        add + "T.pdf", this);
                break;
            case 1:
                AppUtils.startDownload("Fluid Mechanics (Dec 1".concat(yr),
                        add + "FM.pdf", this);
                break;
            case 2:
                AppUtils.startDownload("Integral Equations (Dec 1".concat(yr),
                        add + "IE.pdf", this);
                break;
            case 3:
                AppUtils.startDownload("Mathematical Statistics (Dec 1".concat(yr),
                        add + "MS.pdf", this);
                break;
            case 4:
                AppUtils.startDownload("Advanced Mechanics Of Solids (Dec 1".concat(yr),
                        add + "AMoS.pdf", this);
                break;
        }
    }

    public void setupView() {
        androidx.appcompat.app.ActionBar acb = getSupportActionBar();
        acb.setHomeButtonEnabled(true);
        acb.setDisplayHomeAsUpEnabled(true);
        acb.setTitle("Choose Subject");
    }

    private void initViews() {
        RecyclerView recyclerView = findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(layoutManager);

        QueAdapter adapter = new QueAdapter(getApplicationContext(),
                AppUtils.prepareDataForQuePap(subject_names));
        recyclerView.setAdapter(adapter);
    }
}
