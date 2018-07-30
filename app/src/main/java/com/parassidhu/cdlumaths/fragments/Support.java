package com.parassidhu.cdlumaths.fragments;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.parassidhu.cdlumaths.R;
import com.parassidhu.cdlumaths.utils.AppUtils;

public class Support extends Fragment {
    private Button paytm2;
    InterstitialAd mInterstitialAd;
    public Support() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Thank You!");
        return inflater.inflate(R.layout.fragment_support, container, false);
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("39C695F82AC6C82B1C9874FBBDCC2D46")
                .addTestDevice("B9849F4A3D98550C1E14D76D816CD052")
                .build();

        mInterstitialAd.loadAd(adRequest);

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
            }
        });
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.sort);
        AppUtils.setOptVisibility(menu,false,true);
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstanceState){
        setHasOptionsMenu(true);
        paytm2= getActivity().findViewById(R.id.paytm2);
        paytm2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Please wait while we load an ad...", Toast.LENGTH_SHORT).show();
                mInterstitialAd = new InterstitialAd(getActivity());
                mInterstitialAd.setAdUnitId("ca-app-pub-6089158898128407/8257639804");
                requestNewInterstitial();
                mInterstitialAd.show();
            }
        });
    }
}
