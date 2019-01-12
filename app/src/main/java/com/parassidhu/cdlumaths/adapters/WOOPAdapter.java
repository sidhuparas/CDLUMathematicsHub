package com.parassidhu.cdlumaths.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parassidhu.cdlumaths.R;
import com.parassidhu.cdlumaths.models.OldItem;
import com.parassidhu.cdlumaths.utils.AnimationUtil;

import java.util.ArrayList;

public class WOOPAdapter extends RecyclerView.Adapter<WOOPAdapter.ViewHolder> {
    private ArrayList<OldItem> android_versions;
    private Context context;
    int previousPosition = 0;

    public WOOPAdapter(Context context, ArrayList<OldItem> android_versions) {
        this.context = context;
        this.android_versions = android_versions;

    }

    @Override
    public WOOPAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_woop, viewGroup, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.tv_android.setText(android_versions.get(i).getName());
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