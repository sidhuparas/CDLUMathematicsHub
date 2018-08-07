package com.parassidhu.cdlumaths.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.parassidhu.cdlumaths.utils.PrefsUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Timetable extends Fragment {

    @BindView(R.id.ttSpinner) MaterialSpinner ttDay;
    @BindView(R.id.ttSem) MaterialSpinner ttSem;
    @BindView(R.id.ttList) RecyclerView ttList;
    @BindView(R.id.disableText) TextView disableText;
    @BindView(R.id.next) ImageView next;

    private Calendar calendar;
    private TimeTableAdapter adapter;
    private ArrayList<TTItem> listItems = new ArrayList<>();
    private Handler ha;
    private Runnable ra;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String TAG = "TimeTableLog";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timetable, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public void onPrepareOptionsMenu(Menu menu) {
        AppUtils.setOptVisibility(menu, false, true);
    }

    public void onViewCreated(@NonNull View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        initViews();
        setSpinnerListener();
        PrefsUtils.initialize(getActivity(), "Values");

        DialogUtils.tipMsg(getActivity(),
                "You can set Timetable to launch at startup. Just tap the button above beside the three dots.", 2000);

        int ttSemPos = PrefsUtils.getIntValue("sem", 0);
        setupSemSpinner(ttSemPos);

        // If time is above 3 PM
        if (Integer.valueOf(clockHour()) > 15) {
            // If it's not Sunday
            if (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                int selectedIndex = ttDay.getSelectedIndex();

                // If selected index isn't the last item
                if (selectedIndex != ttDay.getItems().size() - 1) {
                    // Increment the selected index by one
                    ttDay.setSelectedIndex(selectedIndex + 1);
                    selectedIndex = ttDay.getSelectedIndex();
                    changeItem(ttDay.getItems().get(selectedIndex));
                } else {
                    // Set spinner to first item
                    ttDay.setSelectedIndex(0);
                    changeItem(ttDay.getItems().get(0));
                }
            } else {
                // It's Sunday :P
                changeItem(getDay());
            }
        } else {
            // Time is below 3 PM
            changeItem(getDay());
        }
    }

    private void initViews() {
        calendar = Calendar.getInstance();
        PrefsUtils.initialize(getActivity(), "Values");
        ttDay.setItems("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday");
        setHasOptionsMenu(true);

        ttList.setLayoutManager(new LinearLayoutManager(getActivity()));
        ttList.setFocusable(false);
        AppUtils.setFastScrolling(ttList);
        getActivity().setTitle("TimeTable");

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int curItem = ttDay.getSelectedIndex();
                if (curItem != ttDay.getItems().size() - 1)
                    ttDay.setSelectedIndex(ttDay.getSelectedIndex() + 1);
                else ttDay.setSelectedIndex(0);
                changeItem(ttDay.getItems().get(ttDay.getSelectedIndex()));
            }
        });

        ttSem.setItems(getSemesters());

        try {
            ttDay.setSelectedIndex(calendar.get(Calendar.DAY_OF_WEEK) - 2);
        } catch (Exception e) {
            ttDay.setSelectedIndex(0);
        }

    }

    private void setupSemSpinner(int position) {
        if (ttSem.getItems().size() > position) {
            ttSem.setSelectedIndex(position);
        }
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
        ttDay.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                changeItem(item);
            }
        });

        ttSem.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                PrefsUtils.saveOffline("sem", position);
                changeItem(ttDay.getItems().get(ttDay.getSelectedIndex()));
            }
        });
    }

    private void changeItem(Object item) {
        listItems.clear();
        removeHandler();
        //getTimeTable();
        loadJSON(item.toString());
        String day = "";
        // If it's not Sunday
        if (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            // If passed Day is the same as Calendar's today
            if (item.toString().equals(getDay())) {
                day = "Today";
            } else {
                int pos = 0;
                // pos stores index of today in spinner
                for (int i = 0; i < ttDay.getItems().size(); i++) {
                    if (getDay().equals(ttDay.getItems().get(i))) {
                        pos = i;
                        break;
                    }
                }
                day = getTomo(pos);
            }
        } else {
            if (ttDay.getSelectedIndex() == 0)
                day = "Tomorrow";
        }


        if (!day.equals(""))
            getActivity().setTitle("" + day);
        else getActivity().setTitle("TimeTable");
    }

    // Returns list of valid (Odd/Even) semesters for setting in Spinner
    private List<String> getSemesters() {
        String[] semesters = {PrefsUtils.getValue("t1", "1st"),
                PrefsUtils.getValue("t2", "3rd"),
                PrefsUtils.getValue("t3", "5th"),
                PrefsUtils.getValue("t4", "7th"),
                PrefsUtils.getValue("t5", "9th")};

        return Arrays.asList(semesters);
    }

    //Return text for Toolbar
    private String getTomo(int pos) {
        if (pos != ttDay.getItems().size() - 1) {
            if (ttDay.getSelectedIndex() == pos + 1) {
                return "Tomorrow";
            }
        } else {
            if (ttDay.getSelectedIndex() == 0)
                return "Tomorrow";
        }

        return "";
    }

    private String formURL() {
        String baseURL = getResources().getString(R.string.base_timetable);
        switch (ttSem.getSelectedIndex()) {
            case 0:
                return baseURL + "t1";
            case 1:
                return baseURL + "t2";
            case 2:
                return baseURL + "t3";
            case 3:
                return baseURL + "t4";
            case 4:
                return baseURL + "t5";
        }
        return "";
    }

    //Time passed on to Adapter
    private String getHour(int i) {
        String currentHour = String.valueOf(i);
        if (currentHour.equals("0"))
            currentHour = "12";
        String time;
        if (currentHour.equals("12"))
            time = Integer.valueOf(currentHour) + ":00 - " + "01:00";
        else
            time = Integer.valueOf(currentHour) + ":00 - " + (Integer.valueOf(currentHour) + 1) + ":00";
        return time;
    }

    public void onDestroy() {
        super.onDestroy();
        removeHandler();
    }

    //Check if Now, Next kind of Text would be shown
    private boolean isCurrent() {
        return ttDay.getText().toString().equals(getDay()) && Integer.valueOf(clockHour()) <= 15 &&
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
            case 5:
                return 3;
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
            PrefsUtils.initialize(getActivity(), "Values");

            //TimeTable is enabled from server side
            if (PrefsUtils.getValue("ttenable", "1").equals("1")) {
                controlViews(0);
                removeHandler();

                // If to show Now, Next kind of things
                if (isCurrent()) {

                    for (int i = 0; i < listItems.size(); i++) {
                        TTItem currentItem = listItems.get(i);

                        int actime = currentItem.getTime().indexOf(":");
                        String timeStr = currentItem.getTime().substring(0, actime);
                        if (timeStr.equals("2"))
                            timeStr = "14";
                        else if (timeStr.equals("3"))
                            timeStr = "15";
                        int time = Integer.valueOf(timeStr);
                        int clockTimeint = Integer.valueOf(clockHour());
                        if (clockTimeint > time) {
                            listItems.remove(i);
                            i--;
                        }
                    }

                    setNowNextToFinalData();
                }

                if (ttList.getAdapter() == null) {
                    adapter = new TimeTableAdapter(getActivity(), listItems);
                    ttList.setAdapter(adapter);
                } else
                    ttList.getAdapter().notifyDataSetChanged();

                scheduleData();
            } else {
                // TimeTable is disabled
                controlViews(1);
                checkTTAvailability();
            }
        } catch (Exception e) {
        }
    }

    private void setNowNextToFinalData() {
        /*switch (listItems.size()) {
            case 0:

                break;
            case 1:
                if (clockHour().equals("14"))
                    listItems.get(0).setNow("Next");
                else
                    listItems.get(0).setNow("Now");
                break;
            default:
                listItems.get(0).setNow("Now");
                listItems.get(1).setNow("Next");
                break;
        }*/
        try {
            listItems.get(0).setNow("Now");
            listItems.get(1).setNow("Next");
        }catch (Exception e){};
    }

    private void removeHandler() {
        try {
            ha.removeCallbacks(ra);
        } catch (Exception e) {
        }
    }

    // When TT is disabled from server, show this
    private String whyTTisDisabled() {
        return PrefsUtils.getValue("ttmsg", "Developer Paras has messed up with something, I guess :(");
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

    private void setTimeTable(List<TTItem> list) {
        String day = ttDay.getText().toString();

        listItems.clear();
        Log.d("Meee", "Items: " + list.size());
        for (int i = 0; i < list.size(); i++) {
            TTItem item = list.get(i);
            String hour = getHour(currentHour(i));
            TTItem ttItem = new TTItem(item.getTeacherName(),
                    item.getSubName(), "", hour);

            Log.d("", "Items: " + hour);
            listItems.add(ttItem);
        }

        adjustData();
    }

    /*private void setTimeTable(ArrayList<TTItem> list) {
        String day = ttDay.getText().toString();
        sharedPreferences = getActivity().getSharedPreferences(day, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        String[] t1 = {"t11", "t12", "t13", "t14", "t15"};
        String[] s1 = {"s11", "s12", "s13", "s14", "s15"};

        String[] t2 = {"t21", "t22", "t23", "t24", "t25"};
        String[] s2 = {"s21", "s22", "s23", "s24", "s25"};

        switch (ttSem.getSelectedIndex()) {
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
        //getTimeTable();*/


    private void loadJSON(final String day) {
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    formURL(), new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray(day);
                        ArrayList<TTItem> list = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject o = jsonArray.getJSONObject(i);
                            if (!o.optString("teacher").isEmpty()) {
                                TTItem item = new TTItem(o.getString("teacher"), o.getString("sub"),
                                        "", getHour(currentHour(i)));
                                list.add(item);
                                Log.d("Meee", "onResponse: Added");
                            }
                        }
                        Log.d("Meee", "onResponse: " + list.size());
                        setTimeTable(list);
                    } catch (Exception e) {
                    }
                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) { }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);
        } catch (Exception ignored) {
        }
    }

}
