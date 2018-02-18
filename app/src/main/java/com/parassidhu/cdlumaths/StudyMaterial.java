package com.parassidhu.cdlumaths;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.javiersantos.bottomdialogs.BottomDialog;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;

public class StudyMaterial extends Fragment {

    public StudyMaterial() {
    }

    private MaterialSpinner sem, course;
    private final String[] semList = {
            "1st",
            "2nd",
            "3rd",
            "4th",
            "5th",
            "6th",
            "7th",
            "8th",
            "9th",
            "10th"
    };

    private final String[] twoList = {
            "1st",
            "2nd",
            "3rd",
            "4th"
    };
    private String URL = "";
    private StudyAdapter adapter;
    private ArrayList<StudyData> listItems = new ArrayList<>();

    private RecyclerView rcl;
    private CircularProgressBar progressBar;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private TextView smtext;

    private LinearLayout suggestMaterial;
    private Button suggest;

    public void onPrepareOptionsMenu(Menu menu) {
        sidhu.setOptVisibility(menu, false, true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_study_material, container, false);
    }

    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        initViews();

        changeURL(51);
        loadJSON();
        setSpinners();
    }

    private void initViews() {
        getActivity().setTitle("Study Material");
        setHasOptionsMenu(true);

        sharedPreferences = getActivity().getSharedPreferences("Values", Context.MODE_PRIVATE);

        sem = getActivity().findViewById(R.id.sem);
        course = getActivity().findViewById(R.id.course);
        rcl = getActivity().findViewById(R.id.materialList);
        progressBar = getActivity().findViewById(R.id.progressBar);
        smtext = getActivity().findViewById(R.id.welcome);
        editor = sharedPreferences.edit();
        suggestMaterial = getActivity().findViewById(R.id.suggestMaterial);
        suggest = getActivity().findViewById(R.id.suggest);

        //Set spinner items
        course.setItems("5-Years", "2-Years");
        sem.setItems(semList);

        //Setup RecyclerView
        rcl.setLayoutManager(new LinearLayoutManager(getActivity()));
        sidhu.setFastScrolling(rcl);
        ItemClickSupport.addTo(rcl).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                StudyData sd = listItems.get(position);
                showInfo(sd.getName(), sd.getDetail(), position);
            }
        });

        progressBar.setVisibility(View.GONE);
        suggestMaterial.setVisibility(View.GONE);

        //This code enables the ability to don't show "What's new in Study Material" twice or more
        String smData = sharedPreferences.getString("smtext", "Study Material is newly launched feature so not much content will be available for now. But we're adding new material very soon.");
        if (smData.equals(sharedPreferences.getString("smdataold", "")))
            smtext.setVisibility(View.GONE);
        else {
            smtext.setText(smData);
            editor.putString("smdataold", smData);
            editor.apply();
        }

        suggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),feedback.class);
                startActivity(i);
            }
        });
    }

    private void updateList(int position) {
        if (course.getSelectedIndex() == 0) {
            String numText = "5" + String.valueOf(position + 1);
            Integer num = Integer.valueOf(numText);
            changeURL(num);
            loadJSON();
        } else if (course.getSelectedIndex() == 1) {
            String numText = "2" + String.valueOf(position + 1);
            Integer num = Integer.valueOf(numText);
            changeURL(num);
            loadJSON();
        }
    }

    private void setSpinners() {
        sem.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                updateList(position);
            }
        });
        course.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                switch (position) {
                    case 0:
                        sem.setItems(semList);
                        break;
                    case 1:
                        sem.setItems(twoList);
                        break;
                }
                updateList(sem.getSelectedIndex());
            }
        });
    }

    private void showInfo(final String title, String detail, final int pos) {
        BottomDialog.Builder bottomDialog = new BottomDialog.Builder(getActivity())
                .setTitle(title)
                .setContent(detail);

        switch (listItems.get(pos).getParam()){
            case "0":
                bottomDialog.setPositiveText("View")
                        .onPositive(new BottomDialog.ButtonCallback() {
                            @Override
                            public void onClick(@NonNull BottomDialog bottomDialog) {
                                sidhu.openWebPage((AppCompatActivity) getActivity(), listItems.get(pos).getLink());
                            }
                        });
                break;
            case "1":
                 bottomDialog.setPositiveText("Download")
                    .setNegativeText("Open In Browser")
                    .onPositive(new BottomDialog.ButtonCallback() {
                        @Override
                        public void onClick(@NonNull BottomDialog bottomDialog) {
                            sidhu.startDownload(title + " (" + listItems.get(pos).getSubject() + ").pdf",
                                    listItems.get(pos).getLink(), getActivity());
                            increaseDownloads();
                        }
                    })
                    .onNegative(new BottomDialog.ButtonCallback() {
                        @Override
                        public void onClick(@NonNull BottomDialog bottomDialog) {
                            sidhu.openWebPage((AppCompatActivity) getActivity(), listItems.get(pos).getLink());
                            increaseDownloads();
                        }
                    });
                break;
        }
        bottomDialog.show();

    }

    private void increaseDownloads(){
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    getResources().getString(R.string.study), new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);
        }catch (Exception e){}
    }

    private void loadJSON() {
        try {
            progressBar.setVisibility(View.VISIBLE);
            rcl.setVisibility(View.GONE);
            suggestMaterial.setVisibility(View.GONE);

            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    URL, new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        listItems.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject o = jsonArray.getJSONObject(i);
                            StudyData item = new StudyData(o.getString("name"), o.getString("subject")
                                    , o.getString("detail"), o.getString("link"), o.getString("param"));
                            listItems.add(item);
                        }
                        adapter = new StudyAdapter(getActivity(), listItems);
                        if (rcl.getAdapter()==null)
                        rcl.setAdapter(adapter);
                        else rcl.getAdapter().notifyDataSetChanged();

                        progressBar.setVisibility(View.GONE);
                        rcl.setVisibility(View.VISIBLE);
                        suggestMaterial.setVisibility(View.VISIBLE);
                    } catch (Exception e) {
                        try {
                            progressBar.setVisibility(View.GONE);
                            rcl.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), "Error loading study material! Please check your internet connection.", Toast.LENGTH_LONG).show();
                        }catch (Exception ex){}
                    }
                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try {
                        progressBar.setVisibility(View.GONE);
                        rcl.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(), "Error loading study material! Please check your internet connection.", Toast.LENGTH_LONG).show();
                    }catch (Exception e){}

                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);
        } catch (Exception e) {
        }
    }

    private void changeURL(int i) {
        switch (i) {
            case 51:
                URL = getResources().getString(R.string.f1);
                break;
            case 52:
                URL = getResources().getString(R.string.f2);
                break;
            case 53:
                URL = getResources().getString(R.string.f3);
                break;
            case 54:
                URL = getResources().getString(R.string.f4);
                break;
            case 55:
                URL = getResources().getString(R.string.f5);
                break;
            case 56:
                URL = getResources().getString(R.string.f6);
                break;
            case 57:
                URL = getResources().getString(R.string.f7);
                break;
            case 58:
                URL = getResources().getString(R.string.f8);
                break;
            case 59:
                URL = getResources().getString(R.string.f9);
                break;
            case 510:
                URL = getResources().getString(R.string.f10);
                break;
            case 21:
                URL = getResources().getString(R.string.p1);
                break;
            case 22:
                URL = getResources().getString(R.string.p2);
                break;
            case 23:
                URL = getResources().getString(R.string.p3);
                break;
            case 24:
                URL = getResources().getString(R.string.p4);
                break;
        }

    }
}

class StudyAdapter extends RecyclerView.Adapter<StudyAdapter.ViewHolder> {
    private ArrayList<StudyData> listItems;
    private Context context;

    public StudyAdapter(Context context, ArrayList<StudyData> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    @Override
    public StudyAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.study_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        StudyData st = listItems.get(i);
        viewHolder.name.setText(st.getName());
        viewHolder.subject.setText(st.getSubject());

        sidhu.setFont(context, viewHolder.name, "Raleway-Medium.ttf");
        sidhu.setFont(context, viewHolder.subject, "Raleway.ttf");

        if (i % 5 == 0)
            viewHolder.itemView.setBackgroundResource(R.drawable.ngreen);
        else if (i % 5 == 1)
            viewHolder.itemView.setBackgroundResource(R.drawable.nblue);
        else if (i % 5 == 2)
            viewHolder.itemView.setBackgroundResource(R.drawable.nred);
        else if (i % 5 == 3)
            viewHolder.itemView.setBackgroundResource(R.drawable.norange);
        else if (i % 5 == 4)
            viewHolder.itemView.setBackgroundResource(R.drawable.npink);
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, subject;

        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            subject = view.findViewById(R.id.subjectName);
        }
    }
}

class StudyData {

    private String name;
    private String subject;
    private String detail;
    private String link;
    private String param;

    public StudyData(String name, String subject, String detail, String link, String param) {
        this.name = name;
        this.subject = subject;
        this.detail = detail;
        this.link = link;
        this.param = param;
    }

    public String getName() {
        return name;
    }

    public String getSubject() {
        return subject;
    }

    public String getDetail() {
        return detail;
    }

    public String getLink() {
        return link;
    }

    public String getParam() {
        return param;
    }
}