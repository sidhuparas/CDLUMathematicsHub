package com.parassidhu.cdlumaths;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private ArrayList<AndroidVersion> android_versions;
    private Context context;
    int previousPosition = 0;
    boolean create=false;
    int i =0;
    public HomeAdapter(Context context,ArrayList<AndroidVersion> android_versions) {
        this.context = context;
        this.android_versions = android_versions;
    }

    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grid_layout, viewGroup, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Picasso.with(context).load(android_versions.get(i).getAndroid_image_url()).resize(180, 180).into(viewHolder.img_android);
        viewHolder.img_android.setColorFilter(Color.rgb(Home.r,Home.g,Home.b));

        if (!create){}else {
            if (i > previousPosition) {
                AnimationUtil.animate(viewHolder, true);
            } else {
                AnimationUtil.animate(viewHolder, false);
            }
            previousPosition = i;
        }
        i++;
        if (i==6){
            create=true;
        }
    }
    @Override
    public int getItemCount() {
        return android_versions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img_android;
        public ViewHolder(View view) {
            super(view);
            img_android = (ImageView)view.findViewById(R.id.img_android);
        }
    }
}