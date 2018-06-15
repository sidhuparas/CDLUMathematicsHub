package com.parassidhu.cdlumaths;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.ads.AdView;
import com.parassidhu.cdlumaths.adapters.QueAdapter;
import com.parassidhu.cdlumaths.models.AndroidVersion;
import com.parassidhu.cdlumaths.utils.ItemClickSupport;
import com.parassidhu.cdlumaths.utils.sidhu;

import java.util.ArrayList;
public class sem1 extends AppCompatActivity {

    private final String subject_names[] = {
            "Algebra",
            "Calculus",
            "Solid Geometry",
            "Discrete Mathematics",
            "Descriptive Statistics",
            "Computer Fundamentals",
            "Introduction To Windows Software",
            "English",
            "Download All"
    };

    public static final String MESSAGE_PROGRESS = "message_progress";
    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sem1);

        AdView adView = this.findViewById(R.id.adView);

        sidhu.displayAds(this,adView);
        setupView();
        initViews();
        final RecyclerView rcl = findViewById(R.id.card_recycler_view);
        ItemClickSupport.addTo(rcl).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                try{
                MyApp m = (MyApp) getApplicationContext();
                m.getClickSem1(position);
                registerForContextMenu(rcl.findFocus());
                openContextMenu(v);
                }catch (Exception ex){}
            }
        });
         sidhu.renderTheme(this);
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sem1,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        MyApp m = (MyApp) getApplicationContext();
        item.setEnabled(false);
        String add[]={
                "CDLU/sem1/2014/",
                "CDLU/sem1/2015/",
                "CDLU/sem1/2016/",
                "CDLU/sem1/2017/"
        };

        switch (item.getItemId()) {
            case R.id.download:     //December 2014
                switch (m.getit1()) {
                    case 0:
                       sidhu.startDownload("Algebra (Dec 14).pdf",
                               add[0] + "Algebra%20(Dec%2014).pdf",this);
                        break;
                    case 1:
                        sidhu.startDownload("Calculus (Dec 14).pdf",
                                add[0]+"Calculus%20(Dec%2014).pdf",this);
                        break;
                    case 2:
                        sidhu.startDownload("Solid Geometry (Dec 14).pdf",
                                add[0]+"Solid%20Geometry%20(Dec%2014).pdf",this);
                        break;
                    case 3:
                        sidhu.startDownload("Discrete Mathematics-I (Dec 14).pdf",
                                add[0]+"Discrete%20Mathematics-I%20(Dec%2014).pdf",this);
                        break;
                    case 4:
                        sidhu.startDownload("Descriptive Statistics (Dec 14).pdf",
                                add[0]+"Descriptive%20Statistics%20(Dec%2014).pdf",this);
                        break;
                    case 5:
                        sidhu.startDownload("Computer Fundamentals (Dec 14).pdf",
                                add[0]+"Computer%20Fundamentals%20(Dec%2014).pdf",this);
                        break;
                    case 6:
                        sidhu.startDownload("Introduction To Window Softwares (Dec 14).pdf",
                                add[0]+"Introduction%20To%20Window%20Softwares%20(Dec%2014).pdf",this);
                        break;
                    case 7:
                        sidhu.startDownload("English-I (Dec 14).pdf",
                                add[0]+"English-I%20(Dec%2014).pdf",this);
                        break;
                    case 8:
                        sidhu.startDownload("Complete Sem 1 (Dec 14).pdf",
                                add[0]+"Complete%20Sem%201%20(Dec%2014).pdf",this);
                        break;
                }
                return true;
            case R.id.download2://December 2015
                switch (m.getit1()) {
                    case 0:
                        sidhu.startDownload("Algebra (Dec 15).pdf",
                                add[1]+"Al%28Dec15%29.pdf",this);
                        break;
                    case 1:
                        sidhu.startDownload("Calculus (Dec 15).pdf",
                                add[1]+"Ca%28Dec15%29.pdf",this);
                        break;
                    case 2:
                        sidhu.startDownload("Solid Geometry (Dec 15).pdf",
                                add[1]+"SG%28Dec15%29.pdf",this);
                        break;
                    case 3:
                        sidhu.startDownload("Discrete Mathematics-I (Dec 15).pdf",
                                add[1]+"DM1%28Dec15%29.pdf",this);
                        break;
                    case 4:
                        sidhu.startDownload("Descriptive Statistics (Dec 15).pdf",
                                add[1]+"DS%28Dec15%29.pdf",this);
                        break;
                    case 5:
                        sidhu.startDownload("Computer Fundamentals (Dec 15).pdf",
                                add[1]+"CF%28Dec15%29.pdf",this);
                        break;
                    case 6:
                        sidhu.startDownload("Introduction To Window Softwares (Dec 15).pdf",
                                add[1]+"ItWS%28Dec15%29.pdf",this);
                        break;
                    case 7:
                        sidhu.startDownload("English-I (Dec 15).pdf",
                                add[1]+"E%28Dec15%29.pdf",this);
                        break;
                    case 8:
                        sidhu.startDownload("Complete Sem 1 (Dec 15).pdf",
                                add[1]+"Complete%20Sem%201%20%28Dec15%29.pdf",this);
                        break;
                }
                return true;

            case R.id.download3:
                switch (m.getit1()) {
                    case 0:
                        sidhu.startDownload("Algebra (Dec 16).pdf",
                                add[2]+"A.pdf",this);
                        break;
                    case 1:
                        sidhu.startDownload("Calculus (Dec 16).pdf",
                                add[2]+"C.pdf",this);
                        break;
                    case 2:
                        sidhu.startDownload("Solid Geometry (Dec 16).pdf",
                                add[2]+"SG.pdf",this);
                        break;
                    case 3:
                        sidhu.startDownload("Discrete Mathematics-I (Dec 16).pdf",
                                add[2]+"DM.pdf",this);
                        break;
                    case 4:
                        sidhu.startDownload("Descriptive Statistics (Dec 16).pdf",
                                add[2]+"DS.pdf",this);
                        break;
                    case 5:
                        sidhu.startDownload("Computer Fundamentals (Dec 16).pdf",
                                add[2]+"CF.pdf",this);
                        break;
                    case 6:
                        sidhu.startDownload("Introduction To Window Softwares (Dec 16).pdf",
                                add[2]+"IWS.pdf",this);
                        break;
                    case 7:
                        sidhu.startDownload("English-I (Dec 16).pdf",
                                add[2]+"E.pdf",this);
                        break;
                    case 8:
                        sidhu.startDownload("Complete Sem 1 (Dec 16).pdf",
                                add[2]+"ALL.pdf",this);
                        break;
                }
                return true;

            case R.id.dec17:
                switch (m.getit1()) {
                    case 0:
                        sidhu.startDownload("Algebra (Dec 17).pdf",
                                add[3]+"A.pdf",this);
                        break;
                    case 1:
                        sidhu.startDownload("Calculus (Dec 17).pdf",
                                add[3]+"C.pdf",this);
                        break;
                    case 2:
                        sidhu.startDownload("Solid Geometry (Dec 17).pdf",
                                add[3]+"SG.pdf",this);
                        break;
                    case 3:
                        sidhu.startDownload("Discrete Mathematics-I (Dec 17).pdf",
                                add[3]+"DM.pdf",this);
                        break;
                    case 4:
                        sidhu.startDownload("Descriptive Statistics (Dec 17).pdf",
                                add[3]+"DS.pdf",this);
                        break;
                    case 5:
                        sidhu.startDownload("Computer Fundamentals (Dec 17).pdf",
                                add[3]+"CF.pdf",this);
                        break;
                    case 6:
                        sidhu.startDownload("Introduction To Window Softwares (Dec 17).pdf",
                                add[3]+"ItWS.pdf",this);
                        break;
                    case 7:
                        sidhu.startDownload("English-I (Dec 17).pdf",
                                add[3]+"E.pdf",this);
                        break;
                    case 8:
                        sidhu.startDownload("Complete Sem 1 (Dec 17).pdf",
                                add[3]+"ALL.pdf",this);
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
