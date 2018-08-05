package com.parassidhu.cdlumaths.fragments;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parassidhu.cdlumaths.R;
import com.parassidhu.cdlumaths.activities.MyResult;
import com.parassidhu.cdlumaths.adapters.HomeAdapter;
import com.parassidhu.cdlumaths.utils.AppUtils;
import com.parassidhu.cdlumaths.utils.ItemClickSupport;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultIndividual extends Fragment {

    @BindView(R.id.choose5Years) TextView choose5Years;
    @BindView(R.id.fiveYearsRV) RecyclerView rcl5Years;
    @BindView(R.id.choose2Years) TextView choose2Years;
    @BindView(R.id.twoYearsRV) RecyclerView rcl2Years;

    private HomeAdapter adapter;
    public ResultIndividual() {}
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result_individual, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        setUp5YearsRV();
        setUp2YearsRV();

        choose2Years.setTextColor(AppUtils.getColor());
        choose5Years.setTextColor(AppUtils.getColor());
    }
    
    private void setUp5YearsRV(){
        rcl5Years.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
        rcl5Years.setLayoutManager(layoutManager);

        adapter = new HomeAdapter(getActivity(), AppUtils.prepareDataFor5Years());
        rcl5Years.setAdapter(adapter);

        ItemClickSupport.addTo(rcl5Years).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                openResult5Years(position);
            }
        });
    }
    
    private void setUp2YearsRV(){
        rcl2Years.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
        rcl2Years.setLayoutManager(layoutManager);
       
        adapter = new HomeAdapter(getActivity(), AppUtils.prepareDataFor2Years());
        rcl2Years.setAdapter(adapter);

        ItemClickSupport.addTo(rcl2Years).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                openResult2Years(position);
            }
        });
    }

    public void openResult5Years(int i){
        Intent intent = new Intent(getActivity(),MyResult.class);
        intent.putExtra("code","340" + (i+1));
        intent.putExtra("sem", String.valueOf(i+1));
        startActivity(intent);
    }

    public void openResult2Years(int i){
        Intent intent = new Intent(getActivity(),MyResult.class);
        intent.putExtra("code","140" + (i+1));
        intent.putExtra("sem",String.valueOf(i+1));
        startActivity(intent);
    }
}
