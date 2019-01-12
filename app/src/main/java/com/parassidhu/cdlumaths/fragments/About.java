package com.parassidhu.cdlumaths.fragments;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parassidhu.cdlumaths.R;
import com.parassidhu.cdlumaths.utils.AppUtils;

public class About extends Fragment {
    private TextView appversion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.sort);
        AppUtils.setOptVisibility(menu,false,true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("About");
        setHasOptionsMenu(true);
        try {
            setup();
        }catch (Exception ex){}
    }

  public void setup() throws PackageManager.NameNotFoundException {
        ImageView facebook = getActivity().findViewById(R.id.facebook);
        ImageView twitter = getActivity().findViewById(R.id.twitter);
        ImageView urs = getActivity().findViewById(R.id.urs);
        appversion = getActivity().findViewById(R.id.appversion);
      PackageManager packageManager = getActivity().getPackageManager();
      PackageInfo info = packageManager.getPackageInfo(getActivity().getPackageName(),0);
      String ver = "VERSION " + info.versionName;
      appversion.setText(ver);
        urs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWebPage("https://yourstory.com/2017/02/7ebc559479-digitizing-my-university-department/");
            }
        });
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebPage("https://www.facebook.com/DownloadInformer");
            }
        });
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebPage("http://twitter.com/parassidhu1");
            }
        });

        TextView live = getActivity().findViewById(R.id.live);
        TextView seeall = getActivity().findViewById(R.id.seeall);
        TextView di = getActivity().findViewById(R.id.DI);

        live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebPage("http://www.downloadinformer.com/2016/07/live-html-makes-html-coding-easier.html");
            }
        });

        seeall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebPage("http://www.downloadinformer.com/p/top-10-freewares.html");
            }
        });

        di.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebPage("http://www.downloadinformer.com/");
            }
        });

    }

    public void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}
