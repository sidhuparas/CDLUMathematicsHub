package com.parassidhu.cdlumaths;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

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
                return QuestionPap(position);
            case "Syllabus":
                return Syllabus(position);
            case "Result":
                return Result(position);
        }
      return null;
    }

    private Fragment QuestionPap(int position){
        switch (position) {
            case 0:
                return new Q5Year();
            case 1:
                return new Q2Year();
            case 2:
                return new misc();
            default:
                return null;
        }
    }

    private Fragment Syllabus(int position){
        switch (position) {
            case 0:
                return new Syllabus5Year();
            case 1:
                return new Syllabus2Year();
            default:
                return null;
        }
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