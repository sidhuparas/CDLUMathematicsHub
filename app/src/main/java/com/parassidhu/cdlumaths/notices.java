package com.parassidhu.cdlumaths;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class notices extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<ListItem> listItems = new ArrayList<>();
    private NoticesData adapter;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    public notices() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notices, container, false);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.sort);
        sidhu.setOptVisibility(menu,false,true);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        try {
            setHasOptionsMenu(true);
            getActivity().setTitle("Notices");
            progressBar = (ProgressBar) getActivity().findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
            initViews();

            swipeRefreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipeRefreshLayout);

            swipeRefreshLayout.setNestedScrollingEnabled(true);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    listItems.clear();
                    loadJSON();
                }
            });

        }catch (Exception ex){

        }
    }

    private void initViews(){
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        loadJSON();
    }

    public void startDownload(String filename, String url,int i){
        MyApp x =  (MyApp)getActivity().getApplicationContext();
        x.getUrl(url);
        x.getFilename(filename);
        x.getID(i);
        Intent intent = new Intent(getActivity(),DownloadService.class);
        getActivity().startService(intent);
    }

    public void loadNotice(String url, String name, String param) {
        switch (param){
            case "0":
                sidhu.MsgBox(getActivity(),name.substring(0,name.length()-4),url);
                break;
            case "1":
                sidhu.openWebPage((AppCompatActivity) getActivity(),url);
                break;
            default:
                startDownload(name,url, ((int) System.currentTimeMillis()));
                Toast.makeText(getActivity(), "Starting download... You can view it from Offline section", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void loadJSON() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                getResources().getString(R.string.notices), new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("notices");

                    for (int i=0; i<jsonArray.length();i++){
                        JSONObject o = jsonArray.getJSONObject(i);
                        ListItem item = new ListItem(o.getString("title"),o.getString("content"),
                                o.getString("param"));
                        listItems.add(item);
                    }

                    adapter = new NoticesData(listItems,recyclerView,notices.this);
                    recyclerView.setAdapter(adapter);
                    progressBar.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);

                }catch (Exception e){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Error: Internet connection isn't buttery smooth!" , Toast.LENGTH_SHORT).show();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Error: Internet connection isn't buttery smooth!", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

}