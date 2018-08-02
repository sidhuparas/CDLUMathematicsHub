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

public class tsem1 extends AppCompatActivity {

    private final String subject_names[] = {
            "Advanced Abstract Algebra-I",
            "Real Analysis",
            "Mechanics",
            "Complex Analysis-I",
            "Ordinary Differential Equations-I",
            "Download All"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tsem1);
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
                    m.getHitSem1(position);
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
        inflater.inflate(R.menu.tdec,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        MyApp m = (MyApp) getApplicationContext();
        String add="CDLU/2year/sem1/2015/";
        switch (item.getItemId()) {
            case R.id.download:
                switch (m.hitit1()) {
                    case 0:
                        AppUtils.startDownload("Advanced Abstract Algebra-I (Dec 15).pdf",add+"AAA-I.pdf",this);
                        break;
                    case 1:
                        AppUtils.startDownload("Real Analysis (Dec 15).pdf",add+"Real%20Analysis.pdf",this);
                        break;
                    case 2:
                        AppUtils.startDownload("Mechanics (Dec 15).pdf",add+"Mechanics.pdf",this);
                        break;
                    case 3:
                        AppUtils.startDownload("Complex Analysis-I (Dec 15).pdf",add+"CA.pdf",this);
                        break;
                    case 4:
                        AppUtils.startDownload("Ordinary Differential Equations-I (Dec 15).pdf",add+"ODE.pdf",this);
                        break;
                    case 5:
                        AppUtils.startDownload("MSc Maths 2-Year 1st Sem (Dec 15).pdf",
                                add+"Msc%20maths%202-year%201st%20sem.pdf",this);
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

        QueAdapter adapter = new QueAdapter(getApplicationContext(),
                AppUtils.prepareDataForQuePap(subject_names));
        recyclerView.setAdapter(adapter);
    }
}