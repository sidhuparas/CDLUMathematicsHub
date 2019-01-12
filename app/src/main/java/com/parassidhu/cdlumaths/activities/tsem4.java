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

public class tsem4 extends AppCompatActivity {

    private final String subject_names[] = {
            "Functional Analysis",
            "Partial Differential Equations",
            "Mechanics Of Solids-II",
            "Operations Research",
            "Methods Of Applied Mathematics",
            "Download All"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tsem4);
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
                    m.getHitSem4(position);
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
        inflater.inflate(R.menu.tmayonly,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        MyApp m = (MyApp) getApplicationContext();
        String add="CDLU/2year/sem4/2016/";
        switch (item.getItemId()) {
            case R.id.download:
                switch (m.hitit4()) {
                    case 0:
                        AppUtils.startDownload("Functional Analysis (May 16).pdf",add+"Functional%20Analysis.pdf",this);
                        break;
                    case 1:
                        AppUtils.startDownload("Partial Differential Equations (May 16).pdf",add+"PDE.pdf",this);
                        break;
                    case 2:
                        AppUtils.startDownload("Mechanics Of Solids-II (May 16).pdf",add+"Mechanics%20of%20Solids-II.pdf",this);
                        break;
                    case 3:
                        AppUtils.startDownload("Operations Research (May 16).pdf",add+"Operations%20Research.pdf",this);
                        break;
                    case 4:
                        AppUtils.startDownload("Methods Of Applied Mathematics (May 16).pdf",
                                add+"Methods%20of%20Applied%20Maths.pdf",this);
                        break;
                    case 5:
                        AppUtils.startDownload("MSc Maths 2-Year 4th Sem (May 16).pdf",
                                add+"msc%20maths%202%20years%204th%20sem%2005%202016.pdf",this);
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