package com.parassidhu.cdlumaths.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.parassidhu.cdlumaths.R;
import com.parassidhu.cdlumaths.activities.WeightOnOtherPlanets;
import com.parassidhu.cdlumaths.activities.Feedback;
import com.parassidhu.cdlumaths.activities.quadratic;
import com.parassidhu.cdlumaths.adapters.DataAdapter;
import com.parassidhu.cdlumaths.models.OldItem;
import com.parassidhu.cdlumaths.utils.AppUtils;
import com.parassidhu.cdlumaths.utils.DialogUtils;
import com.parassidhu.cdlumaths.utils.ItemClickSupport;

import java.util.ArrayList;

public class Tools extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tools, container, false);
    }

    private final String tools[] = {"Add Time Table Shortcut To Home Screen",
            "Download Our 2nd Android App Developed For A School",
            "Weight On Other Planets",
            "Quadratic Equations Solver",
            "Add a New Tool"};

    DataAdapter adapter;

    private void addShortcut() {
        DialogUtils.MsgBox(getActivity(),"Instructions","1. Download the latest Timetable from Notices\n2. Open Offline section\n" +
                "3. Tap and hold the " +
                "downloaded Timetable\n4. Choose Add To Home Screen");
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.sort);
        AppUtils.setOptVisibility(menu,false,true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Extras");
        setHasOptionsMenu(true);
        RecyclerView rcl = view.findViewById(R.id.crd);
        rcl.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rcl.setLayoutManager(layoutManager);
        final Intent i = new Intent(getContext(), WeightOnOtherPlanets.class);
        final Intent a = new Intent(getContext(),Feedback.class);
        final Intent b = new Intent(getContext(),quadratic.class);
        a.putExtra("radio",3);
        ArrayList<OldItem> oldItems;
        oldItems = new ArrayList<>(prepareData());
        adapter = new DataAdapter(getActivity(), oldItems);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rcl.getContext(),DividerItemDecoration.VERTICAL);
        rcl.addItemDecoration(dividerItemDecoration);
        rcl.setAdapter(adapter);
        ItemClickSupport.addTo(rcl).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                switch (position){
                    case 0:
                        addShortcut();
                        break;
                    case 1:
                        AppUtils.openWebPage((AppCompatActivity) getActivity(),
                                "https://play.google.com/store/apps/details?id=com.parassidhu.sarvodaya");
                        break;
                    case 2:
                        startActivity(i);
                        break;
                    case 3:
                        startActivity(b);
                        break;
                    case 4:
                        startActivity(a);
                        break;
                }
            }
        });
    }

    private ArrayList<OldItem> prepareData(){
        ArrayList<OldItem> android_version = new ArrayList<>();
        for(int i=0;i<tools.length;i++){
            OldItem oldItem = new OldItem();
            oldItem.setName(tools[i]);
            switch (i){
                case 0:
                    oldItem.setImage_url(R.drawable.ic_news);
                    break;
                case 1:
                    oldItem.setImage_url(R.drawable.icon);
                    break;
                case 2:
                    oldItem.setImage_url(R.drawable.earth);
                    break;
                case 3:
                    oldItem.setImage_url(R.drawable.x2);
                    break;
                case 4:
                    oldItem.setImage_url(R.drawable.plus);
                    break;
            }
            android_version.add(oldItem);
        }
        return android_version;
    }
}