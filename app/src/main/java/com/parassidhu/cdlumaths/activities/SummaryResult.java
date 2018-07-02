package com.parassidhu.cdlumaths.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parassidhu.cdlumaths.BuildConfig;
import com.parassidhu.cdlumaths.R;
import com.parassidhu.cdlumaths.adapters.SummaryAdapter;
import com.parassidhu.cdlumaths.models.GetSetGo;
import com.parassidhu.cdlumaths.utils.AppUtils;
import com.parassidhu.cdlumaths.utils.ItemClickSupport;
import com.parassidhu.cdlumaths.utils.ServiceHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SummaryResult extends AppCompatActivity {

    private String lastroll, which, semester,firstroll="";
    private Long val, val2;
    private TextView processtext;
    private LinearLayout processing;
    private ProgressBar progressBar;
    private boolean active = false;
    private boolean free = true;
    private int total;
    private int pro = 0;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.result, menu);
        MenuItem item = menu.findItem(R.id.Share);
        return true;
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void onStop() {
        super.onStop();
        active = false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Share) {
            try {
                saveToInternalStorage(getRecyclerViewScreenshot(resultdata));
            } catch (Exception e) {
                Toast.makeText(this, "Please load a result first!", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_result);
        active = true;
        setTitle("Results");
        String sem = getIntent().getStringExtra("sem");
        int semster = Integer.parseInt(sem);
        semester =sem;
        String seme = semster + "th";
        switch (semster) {
            case 1:
                seme = "1st";
                break;
            case 2:
                seme = "2nd";
                break;
            case 3:
                seme = "3rd";
                break;
        }
        setTitle("Results (" + seme + " Sem)");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        resultdata = findViewById(R.id.resultdatalv);
        val = getIntent().getLongExtra("start", 10);
        val2 = getIntent().getLongExtra("end", 10);
        which = getIntent().getStringExtra("which");
        processing = findViewById(R.id.processing);
        processtext = findViewById(R.id.processtext);
        processing.setVisibility(View.VISIBLE);
        progressBar = findViewById(R.id.progressShow);


        resultlist = new ArrayList();
        enquiry = new ArrayList();
        geturlcode = getIntent().getStringExtra("code");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        resultdata.setLayoutManager(layoutManager);
        try {
            url = url.replace("entercoursecode", geturlcode);
        } catch (Exception e) {
        }
        total = (int) (val2 - val);
        checkifFree();
        setListener(sem,geturlcode);
        AppUtils.tipMsg(this,"Tap on any name to view individual result",2001);
    }

    private void setListener(final String sem, final String code) {
        ItemClickSupport.addTo(resultdata).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                int progress =pro * 100 / total;
                if(progress>97){
                    Intent i = new Intent(SummaryResult.this,MyResult.class);
                    i.putExtra("rollno",enquiry.get(position).getRollno());
                    i.putExtra("sem",sem);
                    i.putExtra("code",code);
                    i.putExtra("activity","sum");
                    startActivity(i);

                }else{
                    Toast.makeText(SummaryResult.this, "Please wait until all the results are fetched...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void checkifFree() {
        if (active) {
            if (!free) {
                checkifFree();
            } else {
                if (lastroll == null) {
                    url = url.replace("enterroll", String.valueOf(val));
                    lastroll = String.valueOf(val);
                } else {
                    url = url.replace(lastroll, String.valueOf(val));
                    lastroll = String.valueOf(val);
                }

                if (val < val2) {
                    new getresult(SummaryResult.this).execute(String.valueOf(val));
                    pro = pro + 1;
                } else {
                    try {processing.setVisibility(View.GONE);} catch (Exception e) {}

                    if (val.equals(val2)) {
                        new getresult(SummaryResult.this).execute(String.valueOf(val));
                        try {
                            if (resultdata.getAdapter().getItemCount() == 0)
                                Toast.makeText(this, "No result found!", Toast.LENGTH_SHORT).show();
                            else {
                                Toast.makeText(this, "Successfully completed the operation!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(this, "No result found!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                if (val <= val2)
                    val = val + 1;
                free = false;
            }
        }
    }

    class a implements ContainerFactory {
        a() {
        }

        public List creatArrayContainer() {
            return new LinkedList();
        }

        public Map createObjectContainer() {
            return new LinkedHashMap();
        }
    }

    private static final String TAG_DATA = "data";
    private static final String TAG_NAME = "name";
    private static final String TAG_ROLL = "roll";
    public static ArrayList<GetSetGo> enquiry;
    String Resultstr;
    String data;
    String getRoll;
    String geturlcode;
    String name;
    RecyclerView resultdata;
    ArrayList<HashMap<String, String>> resultlist;
    String roll;
    private String url;

    public class getresult extends AsyncTask<String, Void, String> {

        private SummaryResult myResult;

        public getresult(SummaryResult mr) {
            myResult = mr;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                String progress = String.valueOf(pro * 100 / total);
                myResult.processtext.setText("Processing... " + progress + "%");
            } catch (Exception e) {
            }
        }

        public String doInBackground(String... urls) {
            String lala = BuildConfig.FLAVOR;
            ServiceHandler sh = new ServiceHandler();
            Resultstr = sh.makeServiceCall(url, 1);
            if (Resultstr != null) {
                if (Resultstr.equalsIgnoreCase("[]")) {
                    return "blank";
                }
            } else {
                return "blank";
            }
            try {
                JSONArray jsonArray = new JSONArray(Resultstr);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject c = jsonArray.getJSONObject(i);
                    name = c.getString(TAG_NAME);
                    roll = c.getString(TAG_ROLL);
                    data = c.getString(TAG_DATA);
                }
                return lala;
            } catch (JSONException e) {

                e.printStackTrace();
                return lala;
            }
        }
        public void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result.equalsIgnoreCase("[]") || result.equalsIgnoreCase("blank")) {
                free = true;
                checkifFree();
                return;
            }
            try {
                for (Map.Entry entry : ((Map<Map.Entry, Map.Entry>) new JSONParser().parse(data, new a())).entrySet()) {
                    if ((!entry.toString().contains("null")) && ((entry.getKey().equals(which)))) {
                        enquiry.add(new GetSetGo("" + name , "" + entry.getValue(),String.valueOf(roll)));
                    }
                }

                if (resultdata.getAdapter()==null)
                resultdata.setAdapter(new SummaryAdapter(SummaryResult.this, enquiry, SummaryResult.this));
                else
                 resultdata.getAdapter().notifyDataSetChanged();
                    if (firstroll.equals(""))
                        firstroll=String.valueOf(roll);
            } catch (Exception e) {
                e.printStackTrace();
            }
            free = true;
            checkifFree();

        }
    }

    public SummaryResult() {
        this.url = "http://cdlu.maibiz.in/cdlu/api/mobileroll/enquiry/enterroll/*/*/entercoursecode/mobileapi";
    }

    public static Bitmap getRecyclerViewScreenshot(RecyclerView view) {
        int size = view.getAdapter().getItemCount();
        RecyclerView.ViewHolder holder = view.getAdapter().createViewHolder(view, 0);
        view.getAdapter().onBindViewHolder(holder, 0);
        holder.itemView.measure(View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        holder.itemView.layout(0, 0, holder.itemView.getMeasuredWidth(), holder.itemView.getMeasuredHeight());
        Bitmap bigBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), holder.itemView.getMeasuredHeight() * size,
                Bitmap.Config.ARGB_8888);
        Canvas bigCanvas = new Canvas(bigBitmap);
        bigCanvas.drawColor(Color.parseColor("#006400"));
        Paint paint = new Paint();
        int iHeight = 0;
        holder.itemView.setDrawingCacheEnabled(true);
        holder.itemView.buildDrawingCache();
        bigCanvas.drawBitmap(holder.itemView.getDrawingCache(), 0f, iHeight, paint);
        holder.itemView.setDrawingCacheEnabled(false);
        holder.itemView.destroyDrawingCache();
        iHeight += holder.itemView.getMeasuredHeight();
        try {
            for (int i = 1; i < size; i++) {
                view.getAdapter().onBindViewHolder(holder, i);
                holder.itemView.setDrawingCacheEnabled(true);
                holder.itemView.buildDrawingCache();
                bigCanvas.drawBitmap(holder.itemView.getDrawingCache(), 0f, iHeight, paint);
                iHeight += holder.itemView.getMeasuredHeight();
                holder.itemView.setDrawingCacheEnabled(false);
                holder.itemView.destroyDrawingCache();
            }
        } catch (Exception e) {
        }

        return bigBitmap;
    }

    private String getName(){
        return firstroll+"_"+val2.toString().substring(val2.toString().length()-2,val2.toString().length())+"_SEM"+ semester;
    }

    private String saveToInternalStorage(Bitmap bitmapImage) {
        File directory = new File(Environment.getExternalStorageDirectory().toString() +"/CDLU Results");
        directory.mkdirs();
        File mypath = new File(directory, getName() + ".png");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            Toast.makeText(this, "Result image successfully saved to CDLU Results folder!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {try {fos.close();} catch (IOException e) {e.printStackTrace();}}
        return directory.getAbsolutePath();
    }
}
