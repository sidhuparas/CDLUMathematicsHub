package com.parassidhu.cdlumaths.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.parassidhu.cdlumaths.fragments.QuestionPapers;
import com.parassidhu.cdlumaths.fragments.Result1;
import com.parassidhu.cdlumaths.fragments.Result2;
import com.parassidhu.cdlumaths.fragments.Summary;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private int mNumOfTabs;
    private String activity;

    public PagerAdapter(FragmentManager fm, int NumOfTabs, String activity) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.activity = activity;
    }

    @Override
    public Fragment getItem(int position) {
        switch (activity){
            case "QuestionPap":
                return new QuestionPapers();
             case "Result":
                return Result(position);
        }
      return null;
    }


    private Fragment Result(int position){
        switch (position) {
            case 0:
                return new Result1();
            case 1:
                return new Result2();
            case 2:
                return new Summary();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}