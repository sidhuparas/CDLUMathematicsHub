package com.parassidhu.cdlumaths.activities;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.parassidhu.cdlumaths.BuildConfig;
import com.parassidhu.cdlumaths.R;
import com.parassidhu.cdlumaths.fragments.About;
import com.parassidhu.cdlumaths.fragments.Notices;
import com.parassidhu.cdlumaths.fragments.Offline;
import com.parassidhu.cdlumaths.fragments.QuestionPapers;
import com.parassidhu.cdlumaths.fragments.Results;
import com.parassidhu.cdlumaths.fragments.StudyMaterial;
import com.parassidhu.cdlumaths.fragments.Support;
import com.parassidhu.cdlumaths.fragments.Syllabus;
import com.parassidhu.cdlumaths.fragments.Timetable;
import com.parassidhu.cdlumaths.fragments.Tools;
import com.parassidhu.cdlumaths.services.DownloadService;
import com.parassidhu.cdlumaths.utils.AppUtils;
import com.parassidhu.cdlumaths.utils.DialogUtils;
import com.parassidhu.cdlumaths.utils.PrefsUtils;
import com.yarolegovich.lovelydialog.LovelyChoiceDialog;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hotchemi.android.rate.AppRate;
import io.fabric.sdk.android.Fabric;

import static com.parassidhu.cdlumaths.R.id.nav_view;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Fragment fragment;
    private InterstitialAd mInterstitialAd;
    private NavigationView navigationView;
    private FloatingActionButton fab;

    private String TAG = null; // For Fragment Transactions
    private String TAGd = "HomeDrawer";
    private boolean doubleBackToExitPressedOnce = false;
    private boolean drawe = false;
    public static int r, g, b, e, f, v;
    private boolean old = false;
    private String[] sortItems = {"Name", "Date Created"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (!BuildConfig.DEBUG)
        Fabric.with(this, new Crashlytics());

        try {
            PrefsUtils.initialize(this, "colors");
            r = PrefsUtils.getIntValue("r", 1);
            g = PrefsUtils.getIntValue("g", 87);
            b = PrefsUtils.getIntValue("b", 155);
            e = PrefsUtils.getIntValue("e", 0);
            f = PrefsUtils.getIntValue("f", 47);
            v = PrefsUtils.getIntValue("v", 108);

            initializeView();
            ActionBarClr(r, g, b);
            StatusBarClr(e, f, v);

            showAd();

            checkPerm();

            RateApp();

            setUpNavDrawer(toolbar);

            if (savedInstanceState != null) { // To retain fragment selection
                old = true;
            }

            getValues();

            setView();
            checkBack();
            DialogUtils.welcomeReleaseNotes(this,"What's New In This Update?", AppUtils.releaseNotes, 1111);

        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void initializeView() {
        fab = findViewById(R.id.fab);
    }

    public void StatusBarClr(int r, int g, int b) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(r, g, b));
            window.setNavigationBarColor(Color.rgb(r, g, b));
        }
    }

    public void ActionBarClr(int r, int g, int b) {
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.rgb(r, g, b)));
    }

    private void chooseDrawerItem(int p, int q) {
        if (!old) {
            onNavigationItemSelected(navigationView.getMenu().getItem(p).getSubMenu().getItem(q));
        }

        old = false;
    }

    private void checkBack() {
        try {
            if (getIntent().getExtras() != null) {
                String msg = getIntent().getStringExtra("text");
                if (!msg.isEmpty())
                    DialogUtils.showNotifMessage(this, msg);
            }
        } catch (Exception e) {
        }
    }

    private void setView() {
        PrefsUtils.initialize(this, "Startup");
        int pos = PrefsUtils.getIntValue("pos", 0);
        fragment = new QuestionPapers();

        switch (pos) {
            case 0:
                chooseDrawerItem(0, 0);
                break;
            case 1:
                chooseDrawerItem(0, 1);
                break;
            case 2:
                chooseDrawerItem(0, 2);
                break;
            case 3:
                chooseDrawerItem(0, 6);
                break;
        }
    }

    private void checkForUpdates() {
        PrefsUtils.initialize(this, "Values");
        String latestVersion = PrefsUtils.getValue("version", giveVersion());

        int times = PrefsUtils.getIntValue("update", 0);
        if (times == 0) {
            String change = "New update comes with various new features and enhancements";
            if (!latestVersion.equals(giveVersion()) && !latestVersion.equals("0"))
                UpdateDialog(PrefsUtils.getValue("whatsnew", change));
        } else {
            if (times + 1 < 4)
                PrefsUtils.saveOffline("update", times + 1);
            else
                PrefsUtils.saveOffline("update", 0);
        }
    }

    //Returns App Version
    private String giveVersion() {
        PackageInfo packageInfo;
        try {
            packageInfo = getPackageManager().getPackageInfo(this.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "0";
    }


    private void getValues() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                getResources().getString(R.string.info), new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject o = new JSONObject(response);
                    PrefsUtils.initialize(Home.this, "Values");
                    PrefsUtils.saveOffline("ttenable", o.getString("tt"));
                    PrefsUtils.saveOffline("version", o.getString("version"));
                    PrefsUtils.saveOffline("whatsnew", o.getString("whatsnew"));
                    PrefsUtils.saveOffline("ttmsg", o.getString("ttmsg"));

                    PrefsUtils.saveOffline("t1", o.getString("t1"));
                    PrefsUtils.saveOffline("t2", o.getString("t2"));
                    PrefsUtils.saveOffline("t3", o.getString("t3"));
                    PrefsUtils.saveOffline("t4", o.getString("t4"));
                    PrefsUtils.saveOffline("t5", o.getString("t5"));

                    PrefsUtils.saveOffline("smtext", o.getString("smtext"));
                    checkForUpdates();
                } catch (Exception e) {
                    scheduleHandler();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                scheduleHandler();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        try {
            String msg = intent.getStringExtra("text");
            if (!msg.isEmpty())
                DialogUtils.showNotifMessage(this ,msg);
        } catch (Exception e) {
        }
    }

    private void scheduleHandler() {
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                getValues();
            }
        }, 10000);
    }

    public void UpdateDialog(String msg) {
        new LovelyStandardDialog(this)
                .setTopColorRes(R.color.blue)
                .setIcon(R.drawable.ic_info)
                .setNegativeButton("Update Now", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
                        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        try {
                            startActivity(goToMarket);
                        } catch (ActivityNotFoundException e) {
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("http://play.google.com/store/apps/details?id="
                                            + getApplicationContext().getPackageName())));
                        }
                    }
                })
                .setPositiveButton("Not Now", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PrefsUtils.initialize(Home.this, "Values");
                        int times = PrefsUtils.getIntValue("update", 0);
                        PrefsUtils.saveOffline("update", times + 1);
                    }
                })
                .setTitle("New Update Available!")
                .setCancelable(false)
                .setMessage(msg)
                .show();
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

    public void showAd() {
        PrefsUtils.initialize(this, "ad");
        int num = PrefsUtils.getIntValue("ad", 0);

        if (num < 3) {
            PrefsUtils.saveOffline("ad", num + 1);
        } else {
            mInterstitialAd = new InterstitialAd(Home.this);
            mInterstitialAd.setAdUnitId(BuildConfig.INTERSTITIAL_AD_KEY);

            requestNewInterstitial();
            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                        PrefsUtils.initialize(Home.this, "ad");
                        PrefsUtils.saveOffline("ad", 0);
                    }
                }
            });
            mInterstitialAd.show();
        }
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("39C695F82AC6C82B1C9874FBBDCC2D46")
                .addTestDevice("B9849F4A3D98550C1E14D76D816CD052")
                .addTestDevice("0A8FC7FC744BC150F4A34C86227EDD41")
                .build();
        mInterstitialAd.loadAd(adRequest);
    }

    private void setUpNavDrawer(Toolbar toolbar) {
        navigationView = findViewById(nav_view);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onNavigationItemSelected(navigationView.getMenu().getItem(0).getSubMenu().getItem(1));
            }
        });

        fab.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(r, g, b)));
        //navigationView.setItemIconTintList(null);
        //navigationView.setItemTextColor(ColorStateList.valueOf(AppUtils.getColor()));
        //navigationView.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.white)));
        navigationView.setBackground(getResources().getDrawable(R.drawable.nav_back));
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void RateApp() {
        AppRate.with(this)
                .setInstallDays(8) // default 10, 0 means install day.
                .setLaunchTimes(15) // default 10
                .setRemindInterval(2) // default 1
                .setShowLaterButton(true) // default true
                .setDebug(false) // default false
                .monitor();

        AppRate.showRateDialogIfMeetsConditions(this);
    }

    private void checkPerm() {
        hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (!drawe) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                if (fragment == getSupportFragmentManager().findFragmentByTag("Home")) {
                    super.onBackPressed();
                } else {
                    drawer.openDrawer(GravityCompat.START);
                    if (doubleBackToExitPressedOnce) {
                        super.onBackPressed();
                        return;
                    }

                    this.doubleBackToExitPressedOnce = true;
                    this.drawe = true;
                    Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            doubleBackToExitPressedOnce = false;
                            drawe = false;
                        }
                    }, 2000);
                }
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent i = new Intent(this, DownloadService.class);
        stopService(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.Share);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.sort) {
            DialogUtils.showSortDialog(this, sortItems, TAG);
            return true;
        } else if (id == R.id.Share) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Hello! I'm using CDLU Mathematics Hub app to download question papers, syllabus and more. Get it from Play Store:" +
                    "" +
                    "" +
                    "https://play.google.com/store/apps/details?id=com.parassidhu.cdlumaths";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Download CDLU Mathematics Hub");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        } else if (id == R.id.rate) {
            Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            try {
                startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
            }
        } else if (id == R.id.sendfeedback) {
            Intent i = new Intent(this, Feedback.class);
            startActivity(i);
        } else if (id == R.id.defaultView) {
            setDefaultView();
        } else if (id == R.id.releasenotes) {
            DialogUtils.MsgBox(this,"Release Notes", AppUtils.releaseNotes, 1001);
        } else if (id == R.id.theme) {
            changeThemeOptions();
        }

        return super.onOptionsItemSelected(item);
    }

    private void changeThemeOptions() {
        DialogUtils.showThemeDialog(this);
    }

    private void setDefaultView() {
        PrefsUtils.initialize(this, "Startup");

        String[] list = {"Question Papers", "Offline", "Study Material", "Digital TimeTable"};
        List<String> defaultList = new ArrayList<>(Arrays.asList(list));

        new LovelyChoiceDialog(this)
                .setTitle("Choose Startup View")
                .setItems(defaultList, new LovelyChoiceDialog.OnItemSelectedListener<String>() {
                    @Override
                    public void onItemSelected(int position, String item) {
                        PrefsUtils.saveOffline("pos", position);
                    }
                })
                .setIcon(R.drawable.default_view)
                .setTopColor(AppUtils.getColor())
                .show();
    }

    private void setNavProps(boolean showOrNot, int translation) {
        AppBarLayout appbar = findViewById(R.id.appbar);

        if (showOrNot)
            fab.show();
        else
            fab.hide();

        if (AppUtils.isLollipop()) {
            appbar.setTranslationZ(translation);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();
            navigationView.setCheckedItem(id);
            if (id == R.id.home && fragment != getSupportFragmentManager().findFragmentByTag("Question Papers")) {
                TAG = "Question Papers";
                fragment = new QuestionPapers();
                setNavProps(true, 4);
            } else if (id == R.id.offline && fragment != getSupportFragmentManager().findFragmentByTag("Offline")) {
                TAG = "Offline";
                fragment = new Offline();
                setNavProps(false, 4);
            } else if (id == R.id.results && fragment != getSupportFragmentManager().findFragmentByTag("Results")) {
                TAG = "Results";
                fragment = new Results();
                setNavProps(false, 0);
            } else if (id == R.id.syll && fragment != getSupportFragmentManager().findFragmentByTag("Syllabus")) {
                TAG = "Syllabus";
                fragment = new Syllabus();
                setNavProps(true, 4);
            } else if (id == R.id.Tools && fragment != getSupportFragmentManager().findFragmentByTag("Tools")) {
                TAG = "Tools";
                fragment = new Tools();
                setNavProps(false, 4);
            } else if (id == R.id.about && fragment != getSupportFragmentManager().findFragmentByTag("About")) {
                TAG = "About";
                fragment = new About();
                setNavProps(false, 4);
            } else if (id == R.id.notices && fragment != getSupportFragmentManager().findFragmentByTag("Notices")) {
                TAG = "Notices";
                fragment = new Notices();
                setNavProps(true, 4);
            } else if (id == R.id.donate && fragment != getSupportFragmentManager().findFragmentByTag("Donate")) {
                TAG = "Donate";
                fragment = new Support();
                setNavProps(false, 4);
            } else if (id == R.id.timeicon && fragment != getSupportFragmentManager().findFragmentByTag("Timetable")) {
                TAG = "Timetable";
                fragment = new Timetable();
                setNavProps(false, 4);
            } else if (id == R.id.studymaterial && fragment != getSupportFragmentManager().findFragmentByTag("Study Material")) {
                TAG = "Study Material";
                fragment = new StudyMaterial();
                setNavProps(true, 4);
            }
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

            if (fragment != null && !old) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment, TAG);
                ft.commit();
                Log.d(TAGd, "onNavigationItemSelected: Transaction Done " + TAG);
            }

            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
