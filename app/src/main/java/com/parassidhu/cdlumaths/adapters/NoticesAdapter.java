package com.parassidhu.cdlumaths.adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parassidhu.cdlumaths.fragments.Notices;
import com.parassidhu.cdlumaths.R;
import com.parassidhu.cdlumaths.models.ListItem;
import com.parassidhu.cdlumaths.utils.ItemClickSupport;

import java.util.ArrayList;

public class NoticesAdapter extends RecyclerView.Adapter<NoticesAdapter.ViewHolder> {

    private ArrayList<ListItem> mAndroidList;
    private RecyclerView recyclerView;
    private Notices frag;

    public NoticesAdapter(ArrayList<ListItem> androidList, RecyclerView rcl, Notices fragment) {
        mAndroidList = androidList;
        recyclerView=rcl;
        frag=fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_notices, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mTvName.setText(mAndroidList.get(position).getSubName());
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                try{
                frag.loadNotice(mAndroidList.get(position).getContent(),mAndroidList.get(position).getSubName()+".pdf",
                        mAndroidList.get(position).getParam());
                }catch (Exception e){}
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAndroidList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView mTvName;
        public ViewHolder(View view) {
            super(view);
            mTvName = view.findViewById(R.id.tv_name);
        }
    }
}