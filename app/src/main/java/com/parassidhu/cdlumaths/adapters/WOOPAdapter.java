package com.parassidhu.cdlumaths.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parassidhu.cdlumaths.R;
import com.parassidhu.cdlumaths.models.AndroidVersion;
import com.parassidhu.cdlumaths.utils.AnimationUtil;

import java.util.ArrayList;

public class WOOPAdapter extends RecyclerView.Adapter<WOOPAdapter.ViewHolder> {
    private ArrayList<AndroidVersion> android_versions;
    private Context context;
    int previousPosition = 0;

    public WOOPAdapter(Context context, ArrayList<AndroidVersion> android_versions) {
        this.context = context;
        this.android_versions = android_versions;

    }

    @Override
    public WOOPAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.woop_layout, viewGroup, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.tv_android.setText(android_versions.get(i).getAndroid_version_name());
        if (i > previousPosition) {
            AnimationUtil.animate(viewHolder,true);
        }else {
            AnimationUtil.animate(viewHolder,false);
        }
        previousPosition = i;
    }

    @Override
    public int getItemCount() {
        return android_versions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_android;

        public ViewHolder(View view) {
            super(view);
            tv_android = view.findViewById(R.id.tv_android);
        }
    }
}