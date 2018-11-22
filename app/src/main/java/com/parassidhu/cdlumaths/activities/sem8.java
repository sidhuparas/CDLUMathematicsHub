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
import com.parassidhu.cdlumaths.utils.ItemClickSupport;
import com.parassidhu.cdlumaths.utils.MyApp;
import com.parassidhu.cdlumaths.utils.AppUtils;

import java.util.ArrayList;

public class sem8 extends AppCompatActivity {

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
        setContentView(R.layout.activity_sem8);
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
                    m.getClickSem8(position);
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
        inflater.inflate(R.menu.sem8,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        MyApp m = (MyApp) getApplicationContext();
        String add="CDLU/sem8/2018/";
        switch (item.getItemId()) {
            case R.id.download:
                switch (m.getit8()) {
                    case 0:
                        AppUtils.startDownload("Advanced Abstract Algebra (May 18).pdf",
                                add+"AAA.pdf",this);
                        break;
                    case 1:
                        AppUtils.startDownload("Measure and Integration Theory (May 18).pdf",
                                add+"MaIT.pdf",this);
                        break;
                    case 2:
                        AppUtils.startDownload("Mechanics of Solids (May 18).pdf",
                                add+"MoS.pdf",this);
                        break;
                    case 3:
                        AppUtils.startDownload("System of Differential Equations (May 18).pdf",
                                add+"SoDE.pdf",this);
                        break;
                    case 4:
                        AppUtils.startDownload("Computer Programming in FORTRAN 90 & 95 (May 18).pdf",
                                add+"CPiF.pdf",this);
                        break;
                    case 5:
                        AppUtils.startDownload("Methods of Applied Mathematics (May 18).pdf",
                                add+"MoAM.pdf",this);
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
