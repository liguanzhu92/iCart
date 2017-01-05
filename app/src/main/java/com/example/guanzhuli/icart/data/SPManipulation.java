package com.example.guanzhuli.icart.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Guanzhu Li on 1/2/2017.
 */
public class SPManipulation {
    public static final String PREFS_NAME = "USER";
    public static final String PREFS_USER_KEY = "USER_INFOR";
    SharedPreferences settings;
    SharedPreferences.Editor editor;

    // using shared preference to store the user mobile and user name
    public void save(Context context, String text) {
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2
        editor.putString(PREFS_USER_KEY, text); //3
        editor.commit(); //4
    }
    public void save(Context context, String text, String prefs_key) {
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2
        editor.putString(prefs_key, text); //3
        editor.commit(); //4
    }

    public String getValue(Context context) {
        String text;
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
        text = settings.getString(PREFS_USER_KEY, null); //2
        return text;
    }
    public String getValue(Context context, String prefs_key) {
        String text;
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
        text = settings.getString(prefs_key, null); //2
        return text;
    }

    public String getMobile(Context context) {
        String temp = getValue(context);
        String[] s = temp.split(" ");
        return s[2];
    }

    public String getEmail(Context context) {
        String temp = getValue(context);
        String[] s = temp.split(" ");
        return s[1];
    }

    public String getName(Context context) {
        String temp = getValue(context);
        String[] s = temp.split(" ");
        return s[0];
    }

    public void clearSharedPreference(Context context) {
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.clear();
        editor.commit();
    }
    public void removeValue(Context context, String prefs_key) {
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.remove(prefs_key);
        editor.commit();
    }
}
