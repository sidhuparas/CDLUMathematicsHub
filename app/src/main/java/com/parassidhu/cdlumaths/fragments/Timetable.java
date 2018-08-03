package com.parassidhu.cdlumaths.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.parassidhu.cdlumaths.R;
import com.parassidhu.cdlumaths.adapters.TimeTableAdapter;
import com.parassidhu.cdlumaths.models.TTItem;
import com.parassidhu.cdlumaths.utils.AppUtils;
import com.parassidhu.cdlumaths.utils.DialogUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class Timetable extends Fragment {

    private MaterialSpinner spinner, ttSem;
    private Calendar calendar;
    private TimeTableAdapter adapter;
    private ArrayList<TTItem> listItems;
    private RecyclerView ttList;
    private Handler ha;
    private Runnable ra;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private TextView disableText;
    private ImageView next;
    private int tt;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_timetable, container, false);
    }

    public void onPrepareOptionsMenu(Menu menu) {
        AppUtils.setOptVisibility(menu, false, true);
    }

    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        initViews();
        setSpinnerListener();
        sharedPreferences = getActivity().getSharedPreferences("Values", Context.MODE_PRIVATE);

        DialogUtils.tipMsg(getActivity(), "You can set Timetable to launch at startup. Just tap the button above beside the three dots.", 2000);
        try {
            spinner.setSelectedIndex(calendar.get(Calendar.DAY_OF_WEEK) - 2);
            //ttSem.setSelectedIndex();
        } catch (Exception e) {
            spinner.setSelectedIndex(0);
        }

        int ttSemPos = sharedPreferences.getInt("sem",0);
        setupSemSpinner(ttSemPos);

        if (Integer.valueOf(clockHour()) > 14) {
            if (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                if (spinner.getSelectedIndex() != spinner.getItems().size() - 1) {
                    spinner.setSelectedIndex(spinner.getSelectedIndex()+1);
                    changeItem(spinner.getItems().get(spinner.getSelectedIndex()));
                }
                else {
                    spinner.setSelectedIndex(0);
                    changeItem(spinner.getItems().get(0));
                }
            }else changeItem(getDay());
        }else  changeItem(getDay());


    }

    private void setupSemSpinner(int position){
        try{
            ttSem.setSelectedIndex(position);
        }catch (Exception e){}
        if (position==0)
            tt=R.string.timetable;
        else tt=R.string.timetable2;

    }

    // Returns Calendar Day Except Sunday
    // On Sunday, it returns Monday but isCurrent() handles it and
    // don't show Sunday's timetable
    private String getDay() {
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:
                return "Monday";
            case Calendar.TUESDAY:
                return "Tuesday";
            case Calendar.WEDNESDAY:
                return "Wednesday";
            case Calendar.THURSDAY:
                return "Thursday";
            case Calendar.FRIDAY:
                return "Friday";
            case Calendar.SATURDAY:
                return "Saturday";
        }
        return "Monday";
    }

    private void setSpinnerListener() {
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                changeItem(item);
            }
        });
        ttSem.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                //setupSemSpinner(position);
                editor = sharedPreferences.edit();
                editor.putInt("sem",position);
                editor.apply();
                if (position==0)
                    tt=R.string.timetable;
                else tt=R.string.timetable2;
                changeItem(spinner.getItems().get(spinner.getSelectedIndex()));
            }
        });
    }

    private void changeItem(Object item) {
        listItems.clear();
        try {
            ha.removeCallbacks(ra);
        } catch (Exception e) {
        }
        getTimeTable();
        loadJSON(item.toString(),tt);
        String day = "";
        if (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            if (item.toString().equals(getDay()))
                day = "Today";
            else {
                int pos = 0;
                for (int i = 0; i < spinner.getItems().size(); i++) {
                    if (getDay().equals(spinner.getItems().get(i))) {
                        pos = i;
                        break;
                    }
                }
                day = getTomo(pos);
            }
        } else {
            if (spinner.getSelectedIndex() == 0)
                day = "Tomorrow";
        }
        if (!day.equals(""))
        getActivity().setTitle("" + day);
        else getActivity().setTitle("TimeTable");
    }

    private void initViews() {
        calendar = Calendar.getInstance();
        sharedPreferences = getActivity().getSharedPreferences("Values", Context.MODE_PRIVATE);

        spinner = getActivity().findViewById(R.id.ttSpinner);
        spinner.setItems("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday");
        listItems = new ArrayList<>();
        setHasOptionsMenu(true);
        ttList = getActivity().findViewById(R.id.ttList);
        next = getActivity().findViewById(R.id.next);
        ttList.setLayoutManager(new LinearLayoutManager(getActivity()));
        ttList.setFocusable(false);
        disableText = getActivity().findViewById(R.id.disableText);
        ttSem = getActivity().findViewById(R.id.ttSem);
        AppUtils.setFastScrolling(ttList);
        getActivity().setTitle("TimeTable");
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int curItem = spinner.getSelectedIndex();
                if (curItem != spinner.getItems().size() - 1)
                    spinner.setSelectedIndex(spinner.getSelectedIndex() + 1);
                else spinner.setSelectedIndex(0);
                changeItem(spinner.getItems().get(spinner.getSelectedIndex()));
            }
        });

        ttSem.setItems(sharedPreferences.getString("t1","6th"),
                sharedPreferences.getString("t2", "8th"));
    }

    //Return text for Toolbar
    private String getTomo(int pos) {
        if (pos != spinner.getItems().size() - 1) {
            if (spinner.getSelectedIndex() == pos + 1) {
                return "Tomorrow";
            }
        } else {
            if (spinner.getSelectedIndex() == 0)
                return "Tomorrow";
        }

        return "";
    }

    //Time passed on to Adapter
    private String getHour(int i) {
        String currentHour = String.valueOf(i);
        if (currentHour.equals("0"))
            currentHour = "12";
        String time = "";
        if (currentHour.equals("12"))
            time = Integer.valueOf(currentHour) + ":00 - " + "01:00";
        else
            time = Integer.valueOf(currentHour) + ":00 - " + (Integer.valueOf(currentHour) + 1) + ":00";
        return time;
    }

    public void onDestroy() {
        super.onDestroy();
        try {
            ha.removeCallbacks(ra);
        } catch (Exception e) {
        }
    }

    //Check if Now, Next kind of Text would be shown
    private boolean isCurrent() {
        return spinner.getText().toString().equals(getDay()) && Integer.valueOf(clockHour()) <= 14 &&
                Integer.valueOf(clockHour()) >= 9 && calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY;
    }

    //Hour passed on to Adapter. Not full time just exact hour
    private Integer currentHour(int i) {
        switch (i) {
            case 0:
                return 9;
            case 1:
                return 10;
            case 2:
                return 11;
            case 3:
                return 12;
            case 4:
                return 2;
        }
        return 9;
    }

    //Time in clock in 24-hour format
    private String clockHour() {
        return String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
    }

    //The ultimate code block :)
    //Fast forward to 6 months, I don't really know what is ultimate about it :(

    private void adjustData() {
        try {
            calendar = Calendar.getInstance();
            sharedPreferences = getActivity().getSharedPreferences("Values", Context.MODE_PRIVATE);
            if (sharedPreferences.getString("ttenable", "1").equals("1")) {
                controlViews(0);
                try {
                    ha.removeCallbacks(ra);
                } catch (Exception e) {
                }
                if (isCurrent()) {
                    if (spinner.getText().toString().equals(getDay())) {
                        for (int i = 0; i < listItems.size(); i++) {
                            TTItem currentItem;
                            try {
                                currentItem = listItems.get(i);
                            } catch (Exception e) {
                                break;
                            }
                            int actime = currentItem.getTime().indexOf(":");
                            String timeStr = currentItem.getTime().substring(0, actime);
                            if (timeStr.equals("2"))
                                timeStr = "14";
                            int time = Integer.valueOf(timeStr);
                            String clockTime = clockHour();
                            int clockTimeint = Integer.valueOf(clockTime);
                            if (clockTimeint > time) {
                                listItems.remove(i);
                                i--;
                            }
                        }
                    }
                }
                if (isCurrent()) {
                    switch (listItems.size()) {
                        case 0:

                            break;
                        case 1:
                            if (clockHour().equals("13"))
                                listItems.get(0).setNow("Next");
                            else
                                listItems.get(0).setNow("Now");
                            break;
                        default:
                            listItems.get(0).setNow("Now");
                            listItems.get(1).setNow("Next");
                            break;
                    }
                }

                if (ttList.getAdapter() == null) {
                    adapter = new TimeTableAdapter(getActivity(), listItems);
                    ttList.setAdapter(adapter);
                } else ttList.getAdapter().notifyDataSetChanged();

                scheduleData();
            } else {
                controlViews(1);
                checkTTAvailability();
            }
        } catch (Exception e) {
        }
    }

    // When TT is disabled from server, show this
    private String whyTTisDisabled(){
        return sharedPreferences.getString("ttmsg","Developer Paras has messed up with something, I guess :(");
    }

    //0 means TT is ENABLED, 1 means DISABLED
    private void controlViews(int i) {
        if (i == 0) {
            ttList.setVisibility(View.VISIBLE);
            disableText.setVisibility(View.GONE);
        } else {
            ttList.setVisibility(View.GONE);
            disableText.setVisibility(View.VISIBLE);
            disableText.setText(whyTTisDisabled());
        }
    }

    private void scheduleData() {
        try {
            ha = new Handler();
            ra = new Runnable() {
                @Override
                public void run() {
                    adjustData();
                }
            };
            ha.postDelayed(ra, 10000);
        } catch (Exception e) {
        }
    }

    private void checkTTAvailability() {
        try {
            ha = new Handler();
            ra = new Runnable() {
                @Override
                public void run() {
                    adjustData();
                }
            };
            ha.postDelayed(ra, 1000);
        } catch (Exception e) {
        }
    }

    private void getTimeTable() {
        String day = spinner.getText().toString();
        sharedPreferences = getActivity().getSharedPreferences(day, Context.MODE_PRIVATE);

        String[] t1 = {"t11", "t12", "t13", "t14", "t15"};
        String[] s1 = {"s11", "s12", "s13", "s14", "s15"};

        String[] t2 = {"t21", "t22", "t23", "t24", "t25"};
        String[] s2 = {"s21", "s22", "s23", "s24", "s25"};

        listItems.clear();

        switch (ttSem.getSelectedIndex()){
            case 0:
                for (int i = 0; i < 5; i++) {
                    TTItem ttItem = new TTItem(sharedPreferences.getString(t1[i], "--"),
                            sharedPreferences.getString(s1[i], "--"), "", getHour(currentHour(i)));
                    listItems.add(ttItem);
                }
                break;
            default:
                for (int i = 0; i < 5; i++) {
                    TTItem ttItem = new TTItem(sharedPreferences.getString(t2[i], "--"),
                            sharedPreferences.getString(s2[i], "--"), "", getHour(currentHour(i)));
                    listItems.add(ttItem);
                }
        }

        adjustData();
    }

    private void setTimeTable(ArrayList<TTItem> list) {
        String day = spinner.getText().toString();
        sharedPreferences = getActivity().getSharedPreferences(day, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        String[] t1 = {"t11", "t12", "t13", "t14", "t15"};
        String[] s1 = {"s11", "s12", "s13", "s14", "s15"};

        String[] t2 = {"t21", "t22", "t23", "t24", "t25"};
        String[] s2 = {"s21", "s22", "s23", "s24", "s25"};

        switch (ttSem.getSelectedIndex()){
            case 0:
                for (int i = 0; i < 5; i++) {
                    editor.putString(t1[i], list.get(i).getTeacherName());
                    editor.putString(s1[i], list.get(i).getSubName());
                }
                break;
            default:
                for (int i = 0; i < 5; i++) {
                    editor.putString(t2[i], list.get(i).getTeacherName());
                    editor.putString(s2[i], list.get(i).getSubName());
                }
        }

        editor.apply();
        getTimeTable();
    }

    private void loadJSON(final String day, int tt) {
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    getResources().getString(tt), new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray(day);
                        ArrayList<TTItem> list = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject o = jsonArray.getJSONObject(i);
                            TTItem item = new TTItem(o.getString("teacher"), o.getString("sub"), "", getHour(currentHour(i)));
                            list.add(item);
                        }
                        setTimeTable(list);
                    } catch (Exception e) {
                    }
                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);
        } catch (Exception e) {
        }
    }

}
