package com.parassidhu.cdlumaths;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class assignments extends Fragment {
    private RecyclerView rcl;
    private static final String URL = "http://downloadinformer.com/CDLU/3/assignments";
    List<ListItem> listItems;
    List<ListItem> newList;
    public assignments() {}
    AssignmentAdapter assignmentAdapter;
    Animation animationUp, animationDown;
    ProgressBar loading;
    int list[];
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_assignments, container, false);
    }

    public String getDefault() {
        String x;
        SharedPreferences sharedpreferences = getActivity().getSharedPreferences("pass", Context.MODE_PRIVATE);
        x = sharedpreferences.getString("pass", "pass");
        return x;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.sort);
        sidhu.setOptVisibility(menu,false,true);
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        getActivity().setTitle("Assignments");
        rcl = getActivity().findViewById(R.id.recycle_assignments);
        loading = getActivity().findViewById(R.id.loading);
        sharedPreferences = getActivity().getSharedPreferences("Assignments", Context.MODE_PRIVATE);
        loading.setVisibility(View.VISIBLE);
        rcl.setLayoutManager(new LinearLayoutManager(getActivity()));
        listItems = new ArrayList<>();
        newList = new ArrayList();
        animationUp = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_up);
        animationDown = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_down);
        loadJSON();

    }

    private void loadJSON() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject o = jsonArray.getJSONObject(i);
                                ListItem item = new ListItem(o.getString("sub"),
                                        o.getString("content"));
                                listItems.add(item);
                            }
                            checkNewValues();
                            assignmentAdapter = new AssignmentAdapter(getActivity(), listItems, animationUp, animationDown,list);
                            rcl.setAdapter(assignmentAdapter);
                            loading.setVisibility(View.GONE);
                            SaveValues();
                        } catch (JSONException e) {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.setVisibility(View.GONE);
                GetValues();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void SaveValues() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int i=0; i <rcl.getAdapter().getItemCount();i++){
            editor.putString(String.valueOf(i),listItems.get(i).getSubName());
            editor.putString(String.valueOf(i) + String.valueOf(i),listItems.get(i).getContent());
            editor.putInt("total",rcl.getAdapter().getItemCount());
        }
        editor.apply();
    }

    private void GetValues(){
        Snackbar.make(getActivity().findViewById(R.id.assignLay),"Unable to show latest assignments. Most recently downloaded assignments are shown.",Snackbar.LENGTH_LONG).show();
        getActivity().setTitle("Assignments (Offline)");
        int total = sharedPreferences.getInt("total",5);
        String defaultText = "No Internet Connection!";
        for (int i=0; i<total;i++){
            ListItem item = new ListItem(sharedPreferences.getString(String.valueOf(i),defaultText),
                    sharedPreferences.getString(String.valueOf(i) + String.valueOf(i),defaultText));
            listItems.add(item);
        }
        assignmentAdapter = new AssignmentAdapter(getActivity(), listItems, animationUp, animationDown,list);
        rcl.setAdapter(assignmentAdapter);
    }

    private void checkNewValues(){
        int total = sharedPreferences.getInt("total",5);
        String defaultText = "No Internet Connection!";
        list = new int[6];
        for(int i=0;i<total;i++){
            String getVal = sharedPreferences.getString(String.valueOf(i) + String.valueOf(i),defaultText);
            if (!getVal.equals(listItems.get(i).getContent())){
                if (i==0)
                    list[0]=-1;
                else list[i]=i;
                Log.d("Jatt", "checkNewValues: "+list[i]);
            }
        }
    }
}