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

public class sem5 extends AppCompatActivity {

    private final String subject_names[] = {
            "Real Analysis",
            "Groups and Rings",
            "Numerical Methods",
            "Methods of Applied Mathematics",
            "Computer Networks and Data Communication",
            "Object-Oriented Programming With C++"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sem5);
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
                    m.getClickSem5(position);
                    registerForContextMenu(rcl.findFocus());
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
        inflater.inflate(R.menu.sem5, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        MyApp m = (MyApp) getApplicationContext();
        String add = "CDLU/sem5/2015/";
        int timeStamp = (int) System.currentTimeMillis();
        switch (item.getItemId()) {
            case R.id.download:
                switch (m.getit5()) {
                    case 0:
                        AppUtils.startDownload("Real Analysis (Dec 15).pdf",
                                add + "Real%20Analysis%20%28Dec%2015%29.pdf", this);
                        break;
                    case 1:
                        AppUtils.startDownload("Groups and Rings (Dec 15).pdf",
                                add + "Groups%20And%20Rings%20(Dec%2015).pdf", this);
                        break;
                    case 2:
                        AppUtils.startDownload("Numerical Methods (Dec 15).pdf",
                                add + "Numerical%20Methods%20(Dec%2015).pdf", this);
                        break;
                    case 3:
                        AppUtils.startDownload("Methods of Applied Mathematics (Dec 15).pdf",
                                add + "Methods%20of%20Applied%20Mathematics%20(Dec%2015).pdf", this);
                        break;
                    case 4:
                        AppUtils.startDownload("Computer Networks and Data Communication (Dec 15).pdf",
                                add + "Computer%20Networks%20and%20Data%20Communication%20(Dec%2015).pdf", this);
                        break;
                    case 5:
                        AppUtils.startDownload("Object-Oriented Programming With C++ (Dec 15).pdf",
                                add + "Object-Oriented%20Programming%20With%20C++%20(Dec%2015).pdf", this);
                        break;
                    case 6:
                        AppUtils.startDownload("MSc Maths 5-Year 5th Sem (Dec 15).pdf",
                                add + "MSc%20Maths%205-Year%205th%20Sem.pdf", this);
                        break;
                }
                return true;

            case R.id.download2:
                downloadQuestionPapers(m, "6"); //2016
                return true;

            case R.id.download3:
                downloadQuestionPapers(m, "7");
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void downloadQuestionPapers(MyApp m, String year) {
        String path = "CDLU/sem5/201" + year + "/";

        switch (m.getit5()) {
            case 0:
                AppUtils.startDownload("Real Analysis (Dec 1"+ year + ").pdf", path + "RA.pdf", this);
                break;
            case 1:
                AppUtils.startDownload("Groups and Rings (Dec 1"+ year + ").pdf", path + "GR.pdf", this);
                break;
            case 2:
                AppUtils.startDownload("Numerical Methods (Dec 1"+ year + ").pdf", path + "NM.pdf", this);
                break;
            case 3:
                AppUtils.startDownload("Methods of Applied Mathematics (Dec 1"+ year + ").pdf", path + "MAM.pdf", this);
                break;
            case 4:
                AppUtils.startDownload("Computer Networks and Data Communication (Dec 1"+ year + ").pdf", path + "CNDC.pdf", this);
                break;
            case 5:
                AppUtils.startDownload("Object-Oriented Programming With C++ (Dec 1"+ year + ").pdf", path + "OOPC.pdf", this);
                break;
            case 6:
                AppUtils.startDownload("MSc Maths 5-Year 5th Sem (Dec 1"+ year + ").pdf", path + "ALL.pdf", this);
                break;
        }
    }

    public void setupView() {
        android.support.v7.app.ActionBar acb = getSupportActionBar();
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
