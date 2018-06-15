package com.parassidhu.cdlumaths.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parassidhu.cdlumaths.R;
import com.parassidhu.cdlumaths.models.AndroidVersion;

import java.util.ArrayList;

public class QueAdapter extends RecyclerView.Adapter<QueAdapter.ViewHolder> {
    private ArrayList<AndroidVersion> android_versions;
    private Context context;

    public QueAdapter(Context context,ArrayList<AndroidVersion> android_versions) {
        this.context = context;
        this.android_versions = android_versions;
    }

    @Override
    public QueAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.que_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    public int getRandomColor(int a){
        int colors[] = {R.drawable.nred,R.drawable.blue};
        return colors[a];
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        viewHolder.tv_android.setText(android_versions.get(i).getAndroid_version_name());
        viewHolder.itemView.setBackgroundResource(getRandomColor(i%2));
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