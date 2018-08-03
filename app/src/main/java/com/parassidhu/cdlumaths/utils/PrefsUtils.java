package com.parassidhu.cdlumaths.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PrefsUtils {

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;


    public static void initialize(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void initialize(Context context, String name){
        sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public static void saveOffline(String name, String value){
        editor = sharedPreferences.edit();
        editor.putString(name,value);
        editor.apply();
    }

    public static void saveOffline(String name, int value){
        editor = sharedPreferences.edit();
        editor.putInt(name,value);
        editor.apply();
    }

    public static String getValue(String query, String def){
        return sharedPreferences.getString(query, def);
    }

    public static int getIntValue(String query, int def){
        return sharedPreferences.getInt(query, def);
    }
}
