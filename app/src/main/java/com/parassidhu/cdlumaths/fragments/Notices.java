package com.parassidhu.cdlumaths.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.parassidhu.cdlumaths.R;
import com.parassidhu.cdlumaths.adapters.NoticesAdapter;
import com.parassidhu.cdlumaths.models.ListItem;
import com.parassidhu.cdlumaths.utils.AppUtils;
import com.parassidhu.cdlumaths.utils.DialogUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Notices extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<ListItem> listItems = new ArrayList<>();
    private NoticesAdapter adapter;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;

    public Notices() {
    }

    String TAG = "Notices";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notices, container, false);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.sort);
        AppUtils.setOptVisibility(menu, false, true);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        try {
            setHasOptionsMenu(true);
            getActivity().setTitle("Notices");


            progressBar = getActivity().findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
            initViews();

            swipeRefreshLayout = getActivity().findViewById(R.id.swipeRefreshLayout);

            swipeRefreshLayout.setNestedScrollingEnabled(true);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    listItems.clear();
                    loadJSON();
                }
            });

        } catch (Exception ex) {

        }
    }

    private void initViews() {
        recyclerView = getActivity().findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        loadJSON();
    }

    public void loadNotice(String url, String name, String param) {
        switch (param) {
            case "0":
                DialogUtils.MsgBox(getActivity(), name.substring(0, name.length() - 4), url);
                break;
            case "1":
                AppUtils.openWebPage((AppCompatActivity) getActivity(), url);
                break;
            default:
                AppUtils.startDownload(name, url, getActivity());
                break;
        }
    }

    private void loadJSON() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                getResources().getString(R.string.notices), new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("notices");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject o = jsonArray.getJSONObject(i);
                        ListItem item = new ListItem(o.getString("title"), o.getString("content"),
                                o.getString("param"));
                        listItems.add(item);
                    }

                    adapter = new NoticesAdapter(listItems, recyclerView, Notices.this);
                    recyclerView.setAdapter(adapter);
                    progressBar.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);

                } catch (Exception e) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Error: Internet connection isn't buttery smooth!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Error: Internet connection isn't buttery smooth!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                }
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

}