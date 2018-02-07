package com.parassidhu.cdlumaths;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.yarolegovich.lovelydialog.LovelyInfoDialog;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

public class sidhu {

    public static String releaseNotes ="- More Question Papers\n" +
            "- Study Material feature will provide important study contents\n"+
            "- New design for Offline feature\n" +
            "- Digital Timetable for Sem 6th and 8th\n" +
            "- New theme options in menu to customize the look and feel of the app\n"+
            "- New and old syllabus for semesters 6th to 10th\n"+
            "- New App Icon and lots of bug fixes";

    public static void setOptVisibility(Menu menu, boolean sorted,boolean defaultview){
        MenuItem sort = menu.findItem(R.id.sort);
        MenuItem defaultView = menu.findItem(R.id.defaultView);

        sort.setVisible(sorted);
        defaultView.setVisible(defaultview);
    }

    public static boolean isLollipop(){
        return Build.VERSION.SDK_INT >= 21;
    }
    public static void setFastScrolling(RecyclerView rcl){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
            rcl.setNestedScrollingEnabled(false);
        else
            ViewCompat.setNestedScrollingEnabled(rcl,false);
    }

    public static void openWebPage(AppCompatActivity app, String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(app.getPackageManager()) != null) {
            app.startActivity(intent);
        }
    }

    public static void setFont(Context context, TextView textView, String fontName){
        AssetManager am = context.getApplicationContext().getAssets();
        Typeface custom_font = Typeface.createFromAsset(am, "fonts/"+fontName);
        textView.setTypeface(custom_font);
    }

    public static void MsgBox(Context context, String title, String message){
        new LovelyStandardDialog(context)
                .setTopColorRes(R.color.colorAccent)
                .setTitle(title)
                .setPositiveButton("Ok", null)
                .setMessage(message)
                .show();
    }

    public static void permBox(Context context, String message, View.OnClickListener onClickListener){
        new LovelyStandardDialog(context)
                .setTopColorRes(R.color.colorAccent)
                .setTitle("Permissions Required!")
                .setPositiveButton("Grant Permissions", onClickListener)
                .setMessage(message)
                .show();
    }

    public static void tipMsg(Context context, String message, int id){
        new LovelyInfoDialog(context)
                .setTopColorRes(R.color.Orange)
                .setTitle("Did you know?")
                .setNotShowAgainOptionEnabled(id)
                .setMessage(message)
                .show();
    }

    public static void startDownload(String filename, String url,int i, Context context, Context appContext){
        MyApp x =  (MyApp)appContext;
        x.getUrl(url);
        x.getFilename(filename);
        x.getID(i);

        Intent intent = new Intent(context,DownloadService.class);
        context.startService(intent);
    }

    public static void ActionBarClr(int r, int g, int b, AppCompatActivity activity){
        activity.getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.rgb(r,g,b)));
    }

    public static void StatusBarClr(int r, int g, int b, AppCompatActivity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(r,g,b));
            window.setNavigationBarColor(Color.rgb(r,g,b));
        }
    }

    public static void renderTheme(AppCompatActivity activity){
        int r,g,b,e,f,v;
        r=Home.r;
        g=Home.g;
        b=Home.b;
        e=Home.e;
        f=Home.f;
        v=Home.v;

        ActionBarClr(r,g,b,activity);
        StatusBarClr(e,f,v,activity);
    }
}
