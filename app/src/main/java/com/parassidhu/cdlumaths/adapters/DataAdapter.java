package com.parassidhu.cdlumaths.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parassidhu.cdlumaths.R;
import com.parassidhu.cdlumaths.models.AndroidVersion;
import com.parassidhu.cdlumaths.offline;
import com.parassidhu.cdlumaths.utils.sidhu;
import com.parassidhu.cdlumaths.utils.AnimationUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private ArrayList<AndroidVersion> android_versions;
    private Context context;
    int previousPosition = 0;
    boolean create=false;
    Boolean isOffline=false;
    offline fragment;
    int i =0;

    public DataAdapter(Context context,ArrayList<AndroidVersion> android_versions) {
        this.context = context;
        this.android_versions = android_versions;
    }

    public DataAdapter(Context context, ArrayList<AndroidVersion> android_versions, Boolean isOffline, offline fragment) {
        this.context = context;
        this.android_versions = android_versions;
        this.isOffline = isOffline;
        this.fragment = fragment;
    }
    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout, viewGroup, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {

        viewHolder.tv_android.setText(android_versions.get(i).getAndroid_version_name());
        Picasso.with(context).load(android_versions.get(i).getAndroid_image_url()).resize(180, 180).into(viewHolder.img_android);
        sidhu.setFont(context,viewHolder.tv_android,"segoeuisl.ttf");
        if (isOffline){
            viewHolder.imageButton.setVisibility(View.VISIBLE);
           // sidhu.setFont(context, viewHolder.tv_android,"Raleway.ttf");
            viewHolder.imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fragment.showSheet(view,android_versions,viewHolder.tv_android,viewHolder.getAdapterPosition());
                }
            });
        }else {
            viewHolder.imageButton.setVisibility(View.GONE);
        }


        if (!create){
        }else {
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
        TextView tv_android;
        ImageView img_android;
        TextView imageButton;
        public ViewHolder(View view) {
            super(view);
            tv_android = view.findViewById(R.id.tv_android);
            img_android = view.findViewById(R.id.img_android);
            imageButton = view.findViewById(R.id.imgButton);
        }
    }
}