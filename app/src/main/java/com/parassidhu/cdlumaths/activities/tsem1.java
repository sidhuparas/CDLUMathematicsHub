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

public class tsem1 extends AppCompatActivity {

    private final String subject_names[] = {
            "Abstract Algebra",
            "Real Analysis",
            "Mechanics",
            "Complex Analysis",
            "Ordinary Differential Equations"
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
        switch (item.getItemId()) {
            case R.id.download:
                downloadPaper("7");
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void downloadPaper(String year) {
        MyApp m = (MyApp) getApplicationContext();
        String add= "CDLU/2year/sem1/201".concat(year).concat("/");
        String yr = year.concat(").pdf");
        switch (m.hitit1()) {
            case 0:
                AppUtils.startDownload("Abstract Algebra (Dec 1".concat(yr),
                        add+"AA.pdf",this);
                break;
            case 1:
                AppUtils.startDownload("Real Analysis (Dec 1".concat(yr),
                        add+"RA.pdf",this);
                break;
            case 2:
                AppUtils.startDownload("Mechanics (Dec 1".concat(yr),
                        add+"M.pdf",this);
                break;
            case 3:
                AppUtils.startDownload("Complex Analysis (Dec 1".concat(yr),
                        add+"CA.pdf",this);
                break;
            case 4:
                AppUtils.startDownload("Ordinary Differential Equations (Dec 1".concat(yr),
                        add+"ODE.pdf",this);
                break;
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