package com.parassidhu.cdlumaths;

import android.*;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.yarolegovich.lovelydialog.LovelyInfoDialog;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.io.File;
import java.util.ArrayList;

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

    public static void startDownload(String filename, String url, Context context){
        MyApp x =  (MyApp)context.getApplicationContext();
        x.getUrl(url);
        x.getFilename(filename);
        x.getID(getTimeStamp());
        String newName = filename.substring(0,filename.lastIndexOf("."));

        matchFileName(newName,context);
    }

    public static void matchFileName(String filename, Context context){
        String path = Environment.getExternalStorageDirectory().toString()+"/CDLU Mathematics Hub";

        File f = new File(path);
        File file[] = f.listFiles();
        ArrayList arrayFiles = new ArrayList<String>();
        if (file != null){
            for (int i = 0; i < file.length; i++) {
                String fileName = file[i].getName();
                //arrayFiles.add(file[i].getName().split("\\.")[count-1]);
                String newName = fileName.substring(0,fileName.lastIndexOf("."));
                arrayFiles.add(newName);
            }
        }

        if (arrayFiles.contains(filename))
            showAlertDialog(context,"Redownload?",
                    "It seems that you have already downloaded this file. Do you want to download it again?", filename);
        else {
            Intent intent = new Intent(context,DownloadService.class);
            context.startService(intent);
        }
    }

    public static void showAlertDialog(final Context context, String title, String msg, final String filename){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(msg)
                .setNeutralButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(context,DownloadService.class);
                        context.startService(intent);
                    }
                })
                .setNegativeButton("No",null)
                .setPositiveButton("Open File", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        openFile(context, filename + ".pdf");
                    }
                })
                .setIcon(R.drawable.icon)
                .show();
    }

    public static int getTimeStamp(){
        return (int)System.currentTimeMillis();
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

    private static boolean showAds(Context context) {
        SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return getPrefs.getBoolean("showAd", true);
    }

    public static void displayAds(Context context, final AdView adView){
        if (showAds(context)){
            MobileAds.initialize(context.getApplicationContext(),"ca-app-pub-6089158898128407/9919503008");
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .addTestDevice("73CC8EA0F398EEC21B718FF0F9EB507A")
                    .addTestDevice("39C695F82AC6C82B1C9874FBBDCC2D46")
                    .build();
            adView.setAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(int i) {
                    super.onAdFailedToLoad(i);
                    adView.setVisibility(View.GONE);
                }
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    adView.setVisibility(View.VISIBLE);
                }
            });

            adView.loadAd(adRequest);
        } else {
            adView.setVisibility(View.GONE);
        }
    }

    public static boolean checkPerm(Context context){
        hasPermissions(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        String[] PERMISSIONS = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (!hasPermissions(context, PERMISSIONS)) {
            Toast.makeText(context, "You haven't enabled Storage permission. Please go to Settings->Apps and enable the permission so that question paper can be" +
                            "downloaded and stored on the device."
                    , Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void openFile(Context context,String filename){
        File file = new File(Environment.getExternalStorageDirectory()+"/CDLU Mathematics Hub",
                filename);
        Uri path;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N) {
            path = FileProvider.getUriForFile(context,
                    context.getApplicationContext().getPackageName() + ".provider", file);
        }else {
            path = Uri.fromFile(file);
        }
        Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW,path);
        pdfOpenintent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        pdfOpenintent.setDataAndType(path, "application/pdf");
        try {
            context.startActivity(pdfOpenintent);
        }
        catch (ActivityNotFoundException e) {
            Toast.makeText(context, "No PDF reader installed. Please download from Play Store.", Toast.LENGTH_SHORT).show();
        }
    }
}
