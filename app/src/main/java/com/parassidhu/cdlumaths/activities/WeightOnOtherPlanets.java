package com.parassidhu.cdlumaths.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parassidhu.cdlumaths.R;
import com.parassidhu.cdlumaths.adapters.WOOPAdapter;
import com.parassidhu.cdlumaths.models.OldItem;
import com.parassidhu.cdlumaths.utils.ItemClickSupport;

import java.util.ArrayList;

public class WeightOnOtherPlanets extends AppCompatActivity {

    String planets[]={"","","","","","","","","",""};

   double sun;
   double mercury;
   double venus;
   double moon;
   double mars;
   double jupiter;
   double saturn;
   double uranus;
   double neptune;
   double pluto;

    WOOPAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.woop);
        final RecyclerView rcl = findViewById(R.id.card_rc);
        rcl.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rcl.setLayoutManager(layoutManager);
        EditText editText = findViewById(R.id.editText);
        editText.clearFocus();
        final Button btn = findViewById(R.id.buttoncal);
        android.support.v7.app.ActionBar acb = getSupportActionBar();
        acb.setTitle("Weight On Other Planets");
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    btn.callOnClick();
                }
                return false;
            }
        });
        ArrayList<OldItem> androidVersions;
        androidVersions = new ArrayList<OldItem>(prepareData());
        adapter = new WOOPAdapter(this,androidVersions);
        rcl.setAdapter(adapter);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              rcl.setVisibility(View.VISIBLE);
                WOOP(v);
            }
        });

        ItemClickSupport.addTo(rcl).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

            }
        });
    }

    public void WOOP(final View view) {
        Thread start = new Thread(new Runnable() {
            @Override
            public void run() {
                EditText editText = findViewById(R.id.editText);
                if ((editText.getText().toString().equalsIgnoreCase(""))) {
                    //Snackbar.make(getBaseContext(),"Please enter the value!",Snackbar.LENGTH_SHORT).show();
                } else {
                    double w = Double.valueOf(editText.getText().toString());
                    double x = w / 9.8;
                    InputMethodManager keyboard = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    keyboard.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    sun = Math.round(x * 274.13);
                    moon = Math.round(x * 1.62);
                    mercury = Math.round(x * 3.59);
                    venus = Math.round(x * 8.87);
                    mars = Math.round(x * 3.77);
                    jupiter = Math.round(x * 25.95);
                    saturn = Math.round(x * 11.08);
                    uranus = Math.round(x * 10.67);
                    neptune = Math.round(x * 14.07);
                    pluto = Math.round(x * 0.42);
                    final RecyclerView rcl = findViewById(R.id.card_rc);
                    ArrayList<OldItem> androidVersions;
                    androidVersions = new ArrayList<OldItem>(prepareDataforPlanets());
                    adapter = new WOOPAdapter(getApplicationContext(), androidVersions);
                    rcl.setAdapter(adapter);
                }
            }
        });
        start.run();
    }

    private ArrayList<OldItem> prepareDataforPlanets(){
        ArrayList<OldItem> android_version = new ArrayList<>();
        for(int i=0;i<planets.length;i++){
            OldItem oldItem = new OldItem();
            switch (i){
                case 0:
                    planets[0]= "Sun = " + sun + " kg";
                    oldItem.setName(planets[0]);
                    break;

                case 1:
                    planets[1]= "Mercury = " + mercury + " kg";
                    oldItem.setName(planets[1]);
                    break;

                case 2:
                    planets[2]= "Venus = " + venus + " kg";
                    oldItem.setName(planets[2]);
                    break;

                case 3:
                    planets[3]= "Moon = " + moon + " kg";
                    oldItem.setName(planets[3]);
                    break;

                case 4:
                    planets[4]= "Mars = " + mars + " kg";
                    oldItem.setName(planets[4]);
                    break;

                case 5:
                    planets[5]= "Jupiter = " + jupiter + " kg";
                    oldItem.setName(planets[5]);
                    break;

                case 6:
                    planets[6]= "Saturn = " + saturn + " kg";
                    oldItem.setName(planets[6]);
                    break;

                case 7:
                    planets[7]= "Uranus = " + uranus + " kg";
                    oldItem.setName(planets[7]);
                    break;

                case 8:
                    planets[8]= "Neptune = " + neptune + " kg";
                    oldItem.setName(planets[8]);
                    break;

                case 9:
                    planets[9]= "Pluto = " + pluto + " kg";
                    oldItem.setName(planets[9]);
                    break;
            }
            android_version.add(oldItem);
        }
        return android_version;
    }

    private ArrayList<OldItem> prepareData(){
        ArrayList<OldItem> android_version = new ArrayList<OldItem>();
        for (String planet : planets) {
            OldItem oldItem = new OldItem();
            oldItem.setName(planet);
            oldItem.setImage_url(R.mipmap.ic_launcher);
            android_version.add(oldItem);
        }
        return android_version;
    }

}