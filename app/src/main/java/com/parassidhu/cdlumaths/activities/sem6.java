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

public class sem6 extends AppCompatActivity {

    private final String subject_names[] = {
            "Real and Complex Analysis",
            "Linear Algebra",
            "Dynamics",
            "Mathematical Modelling",
            "Mechanics",
            "Computer Graphics",
            "Programming In Java",
            "Download All"
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sem6);
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
                    m.getClickSem6(position);
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
        inflater.inflate(R.menu.sem6,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        MyApp m = (MyApp) getApplicationContext();
        String add="CDLU/sem6/2016/";
        String n="CDLU/sem6/2017/";
        switch (item.getItemId()) {
            case R.id.download:
                switch (m.getit6()) {
                    case 0:
                        AppUtils.startDownload("Real and Complex Analysis (May 16).pdf",
                                add+"RACA%28May16%29.pdf",this);
                        break;
                    case 1:
                        AppUtils.startDownload("Linear Algebra (May 16).pdf",
                                add+"LA%28May16%29.pdf",this);
                        break;
                    case 2:
                        AppUtils.startDownload("Dynamics (May 16).pdf",
                                add+"D%28May16%29.pdf",this);
                        break;
                    case 3:
                        AppUtils.startDownload("Mathematical Modelling (May 16).pdf",
                                add+"MM%28May16%29.pdf",this);
                        break;
                    case 4:
                        AppUtils.startDownload("Mechanics (May 16).pdf",
                                add+"Mech%28May16%29.pdf",this);
                        break;
                    case 5:
                        AppUtils.startDownload("Computer Graphics (May 16).pdf",
                                add+"CG%28May16%29.pdf",this);
                        break;
                    case 6:
                        AppUtils.startDownload("Programming In Java (May 16).pdf",
                                add+"PiJ%28May16%29.pdf",this);
                        break;
                    case 7:
                        AppUtils.startDownload("MSc Maths 5-Year 6th Sem (May 16).pdf",
                                add+"MSc%20Maths%206th%20Sem%20%28May%2016%29.pdf",this);
                        break;
                }
                return true;

            case R.id.download2:
                switch (m.getit6()){
                    case 0:
                        AppUtils.startDownload("Real and Complex Analysis (May 17).pdf",n+"RCA.pdf",this);
                        break;
                    case 1:
                        AppUtils.startDownload("Linear Algebra (May 17).pdf",n+"LA.pdf",this);
                        break;
                    case 2:
                        AppUtils.startDownload("Dynamics (May 17).pdf",n+"D.pdf",this);
                        break;
                    case 3:
                        AppUtils.startDownload("Mathematical Modelling (May 17).pdf",n+"MM.pdf",this);
                        break;
                    case 4:
                        AppUtils.startDownload("Mechanics (May 17).pdf",n+"Mech.pdf",this);
                        break;
                    case 5:
                        AppUtils.startDownload("Computer Graphics (May 17).pdf",n+"CG.pdf",this);
                        break;
                    case 6:
                        AppUtils.startDownload("Programming In Java (May 17).pdf",n+"PJ.pdf",this);
                        break;
                    case 7:
                        AppUtils.startDownload("MSc Maths 5-Year 6th Sem (May 17).pdf",n+"ALL.pdf",this);
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
