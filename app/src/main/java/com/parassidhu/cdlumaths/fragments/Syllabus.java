package com.parassidhu.cdlumaths.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.parassidhu.cdlumaths.R;
import com.parassidhu.cdlumaths.adapters.HomeAdapter;
import com.parassidhu.cdlumaths.utils.AppUtils;
import com.parassidhu.cdlumaths.utils.ItemClickSupport;
import com.parassidhu.cdlumaths.utils.SyllabusUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Syllabus extends Fragment {

    @BindView(R.id.choose5Years) TextView choose5Years;
    @BindView(R.id.fiveYearsRV) RecyclerView rcl5Years;
    @BindView(R.id.choose2Years) TextView choose2Years;
    @BindView(R.id.twoYearsRV) RecyclerView rcl2Years;
    @BindView(R.id.DownloadAll5Years) Button downloadAll5Years;
    @BindView(R.id.DownloadAll2Years) Button downloadAll2Years;

    private HomeAdapter adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_syllabus, container, false);
        ButterKnife.bind(this,rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Syllabus");
        setHasOptionsMenu(true);
        setUpRVFor5Years();
        setUpRVFor2Years();
        setThemeColor();
    }

    private void setThemeColor() {
        choose2Years.setTextColor(AppUtils.getColor());
        choose5Years.setTextColor(AppUtils.getColor());
        downloadAll2Years.setBackgroundColor(AppUtils.getColor());
        downloadAll5Years.setBackgroundColor(AppUtils.getColor());
    }

    private void setUpRVFor5Years(){
        rcl5Years.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),4);
        rcl5Years.setLayoutManager(layoutManager);

        adapter = new HomeAdapter(getActivity(), AppUtils.prepareDataFor5Years());
        rcl5Years.setAdapter(adapter);

        ItemClickSupport.addTo(rcl5Years).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    SyllabusUtils.downloadSyllabus(getActivity(),String.valueOf(position+1));
            }
        });
    }

    private void setUpRVFor2Years(){
        rcl2Years.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),4);
        rcl2Years.setLayoutManager(layoutManager);

        adapter = new HomeAdapter(getActivity(), AppUtils.prepareDataFor2Years());
        rcl2Years.setAdapter(adapter);

        ItemClickSupport.addTo(rcl2Years).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                SyllabusUtils.downloadSyllabus2Years(getActivity(),String.valueOf(position+1));
            }
        });
    }

    @OnClick(R.id.DownloadAll5Years)
    public void onDownloadAllClick(){
        final String add="CDLU/syllabus/5year/";

        askForConfirmation(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AppUtils.startDownload("MSc Maths 5-Year.pdf",
                        add+"M.Sc.%20Math%20(5%20years).pdf",getActivity());
            }
        });
    }

    @OnClick(R.id.DownloadAll2Years)
    public void onDownloadAll2Click(){
        final String add="CDLU/syllabus/2year/";

        askForConfirmation(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AppUtils.startDownload("MSc Maths 2-Year.pdf",
                        add+"MSc%20Maths%202-Year.pdf",getActivity());
            }
        });
    }

    // For full syllabus downloads of 5-Years and 2-Years
    private void askForConfirmation(DialogInterface.OnClickListener yes){
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Start Download?")
                .setMessage("Do you want to download the full syllabus?")
                .setPositiveButton("Yes", yes)
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.sort);
        AppUtils.setOptVisibility(menu,false,true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
}
