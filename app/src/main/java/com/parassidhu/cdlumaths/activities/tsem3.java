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

public class tsem3 extends AppCompatActivity {

    private final String subject_names[] = {
            "Topology",
            "Integral Equations",
            "Mechanics Of Solids-I",
            "Mathematical Statistics",
            "Advanced Discrete Mathematics",
            "Ordinary Differential Equations",
            "Download All"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tsem3);
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
                    m.getHitSem3(position);
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
        inflater.inflate(R.menu.tdec,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        MyApp m = (MyApp) getApplicationContext();
        String add="CDLU/2year/sem3/2015/";
        switch (item.getItemId()) {
            case R.id.download:
                switch (m.hitit3()) {
                    case 0:
                        AppUtils.startDownload("Topology (Dec 15).pdf",add+"Topology.pdf",this);
                        break;
                    case 1:
                        AppUtils.startDownload("Integral Equations (Dec 15).pdf",add+"Integral%20Equations.pdf",this);
                        break;
                    case 2:
                        AppUtils.startDownload("Mechanics Of Solids-I (Dec 15).pdf",add+"Mechanics%20of%20Solids-I.pdf",this);
                        break;
                    case 3:
                        AppUtils.startDownload("Mathematical Statistics (Dec 15).pdf",add+"Mathematical%20Statistics.pdf",this);
                        break;
                    case 4:
                        AppUtils.startDownload("Advanced Discrete Mathematics (Dec 15).pdf",add+"Advanced%20Discrete%20Maths.pdf",this);
                        break;
                    case 5:
                        AppUtils.startDownload("Ordinary Differential Equations (Dec 15).pdf",add+"ODE.pdf",this);
                        break;
                    case 6:
                        AppUtils.startDownload("MSc Maths 2-Year 3rd Sem (Dec 15).pdf",
                                add+"msc%20maths%202%20years%203rd%20sem%2012%202015.pdf",this);
                        break;
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void setupView() {
        androidx.appcompat.app.ActionBar acb = getSupportActionBar();
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