package com.parassidhu.cdlumaths;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parassidhu.cdlumaths.adapters.enquiryadapter;
import com.parassidhu.cdlumaths.models.GetSetGo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;

public class MyResult extends AppCompatActivity {
    private String lastroll,semester;
    private LinearLayout extra, screenLayout;
    private Button findanother,next,plusone;
    RecyclerView resultdata;
    private android.support.v7.widget.ShareActionProvider mShareActionProvider;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.result,menu);
        MenuItem item = menu.findItem(R.id.Share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Share) {
            try {
                saveToInternalStorage(getRecyclerViewScreenshot(resultdata));
            } catch (Exception e) {
                Toast.makeText(this, "Please load a result first!", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void showit(){
        rollno.setVisibility(View.GONE);
        fabsubmit.setVisibility(View.GONE);
        rname.setVisibility(View.VISIBLE);
        plusone.setVisibility(View.GONE);
        rroll.setVisibility(View.VISIBLE);
        resultdata.setVisibility(View.VISIBLE);
        extra.setVisibility(View.VISIBLE);
    }

    public void hideit(){
        rname.setVisibility(View.GONE);
        plusone.setVisibility(View.VISIBLE);
        rroll.setVisibility(View.GONE);
        resultdata.setVisibility(View.GONE);
        extra.setVisibility(View.GONE);
        fabsubmit.setVisibility(View.VISIBLE);
        rollno.setVisibility(View.VISIBLE);
        if (enquiry!=null)
            enquiry.clear();
        if(rname.getText()!=null)
            rname.setText(null);
        if(rroll.getText()!=null) rroll.setText(null);
        if (data!=null) data=null;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Results");
        sharedPreferences = getSharedPreferences("Values",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String sem = getIntent().getStringExtra("sem");
        semester=sem;
        int semster = Integer.parseInt(sem);
        String seme=semster+"th";
        switch (semster){
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
        setTitle("Results ("+ seme +" Sem)");
        String activity = getIntent().getStringExtra("activity");
        if (activity==null)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        else getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        setContentView(R.layout.activity_my_result);
        plusone = findViewById(R.id.plusone);
        next = findViewById(R.id.nextresult);
        rroll = findViewById(R.id.txtresultroll);
        rollno = findViewById(R.id.txtrollno);
        resultdata = findViewById(R.id.resultdatalv);
        fabsubmit = findViewById(R.id.submit);
        screenLayout = findViewById(R.id.screenLayout);
        extra = findViewById(R.id.extra);
        findanother = findViewById(R.id.findanother);

        rname = findViewById(R.id.txtresultname);
        hideit();
        resultlist = new ArrayList();
        enquiry = new ArrayList();
        geturlcode = getIntent().getStringExtra("code");
        if (geturlcode.equals("34010")) geturlcode="3410";
        fabsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    InputMethodManager keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    keyboard.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }catch (Exception e){}
                MyResult.this.getRoll = MyResult.this.rollno.getText().toString();
                if (MyResult.this.getRoll.equalsIgnoreCase("")) {
                    MyResult.this.rollno.setError("Don't leave it blank :)");
                    return;
                }
                try {
                    url = url.replace("entercoursecode",geturlcode);
                }catch (Exception e) {}

                if (lastroll==null) {
                    url = url.replace("enterroll", rollno.getText());
                    lastroll=rollno.getText().toString();
                } else {
                    url = url.replace(lastroll, rollno.getText());
                    lastroll=rollno.getText().toString();
                }
                showit();
                new getresult(MyResult.this).execute(getRoll);
                editor.putString("rollno",rollno.getText().toString());
                editor.apply();
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        resultdata.setLayoutManager(layoutManager);
        plusone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rollno.getText()!=null){
                    try {
                        Long val = Long.valueOf(rollno.getText().toString())+1;
                        rollno.setText(val.toString());
                    }catch (Exception e){
                    }
                }
            }
        });
        findanother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              hideit();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Long val = Long.valueOf(rollno.getText().toString())+1;
                rollno.setText(val.toString());
                hideit();
                fabsubmit.callOnClick();
            }
        });
        rollno.setText(sharedPreferences.getString("rollno",""));

        String rollnum = getIntent().getStringExtra("rollno");
        if(rollnum!=null){
            rollno.setText(getIntent().getStringExtra("rollno"));
            fabsubmit.callOnClick();
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
    Button fabsubmit;
    String getRoll;
    String geturlcode;
    String name;
    private ProgressDialog pDialog;

    ArrayList<HashMap<String, String>> resultlist;
    TextView rname;
    String roll;
    EditText rollno;
    TextView rroll;
    private String url;
    String urlroll;


    public void show(){
        CoordinatorLayout co = findViewById(R.id.coordi);
        co.setBackgroundColor(Color.parseColor("#ffffff"));
    }
    public class getresult extends AsyncTask<String, Void, String> {
        MyResult myResult;
        public getresult(MyResult mr){
            myResult=mr;
        }
        protected void onPreExecute() {
            super.onPreExecute();
          MyResult.this.pDialog = new ProgressDialog(MyResult.this);
          MyResult.this.pDialog.setMessage("Getting your marks...");
          MyResult.this.pDialog.setCancelable(false);
          MyResult.this.pDialog.show();
        }

        public String doInBackground(String... urls) {
            String lala = BuildConfig.FLAVOR;
            ServiceHandler sh = new ServiceHandler();
            MyResult.this.Resultstr = sh.makeServiceCall(MyResult.this.url, 1);
            if(Resultstr!=null){
                if (MyResult.this.Resultstr.equalsIgnoreCase("[]")) {
                    return "blank";
                }
            }  else{
                pDialog.dismiss();
                return "blank";
            }
            try {
                JSONArray jsonArray = new JSONArray(MyResult.this.Resultstr);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject c = jsonArray.getJSONObject(i);
                    MyResult.this.name = c.getString(TAG_NAME);
                    MyResult.this.roll = c.getString(TAG_ROLL);
                    MyResult.this.data = c.getString(TAG_DATA);
                }
                return lala;
            } catch (JSONException e) {

                e.printStackTrace();
                return lala;
            }
        }

        public void onPostExecute(String result) {
            super.onPostExecute(result);
            if (MyResult.this.pDialog.isShowing()) {
                MyResult.this.pDialog.dismiss();
            }
            if (result.equalsIgnoreCase("[]") || result.equalsIgnoreCase("blank")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MyResult.this);
                builder.setMessage("No Result Found");
                builder.setPositiveButton("OK",null);
                builder.setCancelable(false);
                hideit();
                builder.show();
                return;
            }
            try {
                for (Entry entry : ((Map<Entry,Entry>)new JSONParser().parse(MyResult.this.data, new a())).entrySet()) {
                    if (!entry.toString().contains("null")) {
                        MyResult.enquiry.add(new GetSetGo("" + entry.getKey(), "" + entry.getValue()));
                    }
                }
                enquiry.remove(0);
                MyResult.this.resultdata.setAdapter(new enquiryadapter(MyResult.this, MyResult.enquiry,MyResult.this));
            } catch (Exception e) {
                e.printStackTrace();
            }
            MyResult.this.rname.append("NAME  =  " + MyResult.this.name);
            MyResult.this.rroll.append("ROLL NO.  =  " + MyResult.this.roll);
            show();
        }
    }

    public MyResult() {
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
        bigCanvas.drawColor(Color.WHITE);
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
        }catch (Exception e){}

        return bigBitmap;
    }

    private String saveToInternalStorage(Bitmap bitmapImage){
        File directory = new File(Environment.getExternalStorageDirectory().toString() + "/CDLU Results");
        directory.mkdirs();

        File mypath=new File(directory,getRoll +"_"+semester+ ".png");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);

            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            Toast.makeText(this, "Result image successfully saved to CDLU Results folder!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {e.printStackTrace();} finally {try { fos.close(); } catch (Exception e) {}}

        return directory.getAbsolutePath();
    }
}
