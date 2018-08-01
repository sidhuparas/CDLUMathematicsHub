package com.parassidhu.cdlumaths.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parassidhu.cdlumaths.R;
import com.parassidhu.cdlumaths.models.OldItem;
import com.parassidhu.cdlumaths.fragments.Offline;
import com.parassidhu.cdlumaths.utils.AppUtils;
import com.parassidhu.cdlumaths.utils.AnimationUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private ArrayList<OldItem> listItems;
    private Context context;
    private int previousPosition = 0;
    private boolean create=false;
    private Boolean isOffline=false;
    private Offline fragment;

    public DataAdapter(Context context,ArrayList<OldItem> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    public DataAdapter(Context context, ArrayList<OldItem> listItems, Boolean isOffline, Offline fragment) {
        this.context = context;
        this.listItems = listItems;
        this.isOffline = isOffline;
        this.fragment = fragment;
    }
    @NonNull
    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout, viewGroup, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        viewHolder.bind(position);
    }
    
    @Override
    public int getItemCount() {
        return listItems.size();
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
        
        void bind(int position){
            OldItem item = listItems.get(position);

            // Main Text
            tv_android.setText(item.getName());

            // Left side image
            Picasso.get().load(item.getImage_url())
                    .resize(180, 180).into(img_android);

            AppUtils.setFont(context, tv_android,"segoeuisl.ttf");

            // Only for offline section
            if (isOffline){
                imageButton.setVisibility(View.VISIBLE);
                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        fragment.showSheet(view, listItems, tv_android, getAdapterPosition());
                    }
                });
            }else {
                imageButton.setVisibility(View.GONE);
            }
        }
    }
}