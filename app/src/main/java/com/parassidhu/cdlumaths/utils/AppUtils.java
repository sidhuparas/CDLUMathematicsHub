package com.parassidhu.cdlumaths.utils;

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
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.core.view.ViewCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
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
import com.parassidhu.cdlumaths.BuildConfig;
import com.parassidhu.cdlumaths.R;
import com.parassidhu.cdlumaths.activities.Home;
import com.parassidhu.cdlumaths.models.OldItem;
import com.parassidhu.cdlumaths.services.DownloadService;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class AppUtils {

    public static String releaseNotes = "- Added Question Papers for 1st, 4th, 7th, 8th and 10th Semesters\n" +
            "- New Design For Home Screen, Syllabus and Results\n" +
            "- Added TimeTable for complete 5-Year course\n" +
            "- Two new Theme Options: Pink and Dark Blue\n" +
            "- Added Rename option in Offline\n" +
            "- Notifications can be expanded on Android 7.0+\n" +
            "- Some visual changes in TimeTable\n" +
            "- Under the hood performance improvements\n" +
            "- Press and hold offline items to bulk delete" ;

    public static void setOptVisibility(Menu menu, boolean sorted, boolean defaultview) {
        MenuItem sort = menu.findItem(com.parassidhu.cdlumaths.R.id.sort);
        MenuItem defaultView = menu.findItem(com.parassidhu.cdlumaths.R.id.defaultView);

        sort.setVisible(sorted);
        defaultView.setVisible(defaultview);
    }

    public static boolean isLollipop() {
        return Build.VERSION.SDK_INT >= 21;
    }

    public static void setFastScrolling(RecyclerView rcl) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            rcl.setNestedScrollingEnabled(false);
        else
            ViewCompat.setNestedScrollingEnabled(rcl, false);
    }

    public static void openWebPage(AppCompatActivity app, String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(app.getPackageManager()) != null) {
            app.startActivity(intent);
        }
    }

    public static void setFont(Context context, TextView textView, String fontName) {
        AssetManager am = context.getApplicationContext().getAssets();
        Typeface custom_font = Typeface.createFromAsset(am, "fonts/" + fontName);
        textView.setTypeface(custom_font);
    }

    public static void startDownload(String filename, String url, Context context) {
        MyApp x = (MyApp) context.getApplicationContext();
        x.getUrl(url);
        x.getFilename(filename);
        x.getID(getTimeStamp());
        String newName = filename.substring(0, filename.lastIndexOf("."));

        matchFileName(newName, context);
    }

    public static void matchFileName(String filename, Context context) {
        String path = Environment.getExternalStorageDirectory().toString() + "/CDLU Mathematics Hub";

        File f = new File(path);
        File file[] = f.listFiles();
        ArrayList<String> arrayFiles = new ArrayList<>();

        if (file != null) {
            for (File aFile : file) {
                String fileName = aFile.getName();
                String newName = fileName.substring(0, fileName.lastIndexOf("."));
                arrayFiles.add(newName);
            }
        }

        if (arrayFiles.contains(filename))
            showAlertDialog(context, "Redownload?",
                    "It seems that you have already downloaded this file. Do you want to download it again?", filename);
        else {
            Intent intent = new Intent(context, DownloadService.class);
            context.startService(intent);
        }
    }

    public static void showAlertDialog(final Context context, String title, String msg, final String filename) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(msg)
                .setNeutralButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(context, DownloadService.class);
                        context.startService(intent);
                    }
                })
                .setNegativeButton("No", null)
                .setPositiveButton("Open File", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        openFile(context, filename + ".pdf");
                    }
                })
                .setIcon(com.parassidhu.cdlumaths.R.drawable.icon)
                .show();
    }

    public static int getTimeStamp() {
        return (int) System.currentTimeMillis();
    }

    public static void ActionBarClr(int r, int g, int b, AppCompatActivity activity) {
        activity.getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.rgb(r, g, b)));
    }

    public static void StatusBarClr(int r, int g, int b, AppCompatActivity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(r, g, b));
            window.setNavigationBarColor(Color.rgb(r, g, b));
        }
    }

    public static void renderTheme(AppCompatActivity activity) {
        int r, g, b, e, f, v;
        r = Home.r;
        g = Home.g;
        b = Home.b;
        e = Home.e;
        f = Home.f;
        v = Home.v;

        ActionBarClr(r, g, b, activity);
        StatusBarClr(e, f, v, activity);
    }

    public static int getColor(){
        int r, g, b, e, f, v;
        r = Home.r;
        g = Home.g;
        b = Home.b;
        e = Home.e;
        f = Home.f;
        v = Home.v;

        return Color.rgb(r,g,b);
    }
    private static boolean showAds(Context context) {
        SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return getPrefs.getBoolean("showAd", true);
    }

    public static void displayAds(Context context, final AdView adView) {
        if (showAds(context)) {
            MobileAds.initialize(context.getApplicationContext(), BuildConfig.BANNER_AD_KEY);
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .addTestDevice("0A8FC7FC744BC150F4A34C86227EDD41")
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

    public static boolean checkPerm(Context context) {
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

    public static void openFile(Context context, String filename) {
        File file = new File(Environment.getExternalStorageDirectory() + "/CDLU Mathematics Hub",
                filename);
        Uri path;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            path = FileProvider.getUriForFile(context,
                    context.getApplicationContext().getPackageName() + ".provider", file);
        } else {
            path = Uri.fromFile(file);
        }
        Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW, path);
        pdfOpenintent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        pdfOpenintent.setDataAndType(path, "application/pdf");
        try {
            context.startActivity(pdfOpenintent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "No PDF reader installed. Please download from Play Store.", Toast.LENGTH_SHORT).show();
        }
    }

    public static ArrayList<OldItem> prepareDataFor5Years() {
        ArrayList<OldItem> android_version = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            OldItem oldItem = new OldItem();
            switch (i) {
                case 0:
                    oldItem.setImage_url(R.drawable.a1);
                    android_version.add(oldItem);
                    break;
                case 1:
                    oldItem.setImage_url(R.drawable.a2);
                    android_version.add(oldItem);
                    break;
                case 2:
                    oldItem.setImage_url(R.drawable.a3);
                    android_version.add(oldItem);
                    break;
                case 3:
                    oldItem.setImage_url(R.drawable.a4);
                    android_version.add(oldItem);
                    break;
                case 4:
                    oldItem.setImage_url(R.drawable.a5);
                    android_version.add(oldItem);
                    break;
                case 5:
                    oldItem.setImage_url(R.drawable.a6);
                    android_version.add(oldItem);
                    break;
                case 6:
                    oldItem.setImage_url(R.drawable.a7);
                    android_version.add(oldItem);
                    break;
                case 7:
                    oldItem.setImage_url(R.drawable.a8);
                    android_version.add(oldItem);
                    break;
                case 8:
                    oldItem.setImage_url(R.drawable.a9);
                    android_version.add(oldItem);
                    break;
                case 9:
                    oldItem.setImage_url(R.drawable.a10);
                    android_version.add(oldItem);
                    break;
            }

        }
        return android_version;
    }


    public static ArrayList<OldItem> prepareDataFor2Years() {
        ArrayList<OldItem> android_version = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            OldItem oldItem = new OldItem();
            switch (i) {
                case 0:
                    oldItem.setImage_url(R.drawable.a1);
                    android_version.add(oldItem);
                    break;
                case 1:
                    oldItem.setImage_url(R.drawable.a2);
                    android_version.add(oldItem);
                    break;
                case 2:
                    oldItem.setImage_url(R.drawable.a3);
                    android_version.add(oldItem);
                    break;
                case 3:
                    oldItem.setImage_url(R.drawable.a4);
                    android_version.add(oldItem);
                    break;
            }
        }
        return android_version;
    }

    public static ArrayList<String> prepareDataForQuePap(String[] subject_names) {
        return new ArrayList<>(Arrays.asList(subject_names));
    }
}
