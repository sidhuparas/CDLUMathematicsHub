package com.parassidhu.cdlumaths.adapters;

import android.content.Context;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.parassidhu.cdlumaths.activities.Home;
import com.parassidhu.cdlumaths.R;
import com.parassidhu.cdlumaths.models.OldItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private ArrayList<OldItem> android_versions;
    private Context context;

    public HomeAdapter(Context context,ArrayList<OldItem> android_versions) {
        this.context = context;
        this.android_versions = android_versions;
    }

    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_grid, viewGroup, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Picasso.get().load(android_versions.get(i).getImage_url())
                .resize(150, 150).into(viewHolder.img_android);
        viewHolder.img_android.setColorFilter(Color.rgb(Home.r,Home.g,Home.b));
    }
    @Override
    public int getItemCount() {
        return android_versions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img_android;
        public ViewHolder(View view) {
            super(view);
            img_android = view.findViewById(R.id.img_android);
        }
    }
}