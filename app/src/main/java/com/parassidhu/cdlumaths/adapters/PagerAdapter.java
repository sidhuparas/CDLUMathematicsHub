package com.parassidhu.cdlumaths.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.parassidhu.cdlumaths.fragments.QuestionPapers;
import com.parassidhu.cdlumaths.fragments.ResultIndividual;
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
                return new ResultIndividual();
            case 1:
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