package com.example.guanzhuli.icart.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Guanzhu Li on 1/2/2017.
 */
// name +  email + mobile + pwd;
    // design it as singleton
public class  SPManipulation {
    public static final String PREFS_NAME = "USER";
    public static final String PREFS_KEY_NAME = "NAME";
    public static final String PREFS_KEY_MOBILE = "MOBILE";
    public static final String PREFS_KEY_PWD = "PWD";
    public static final String PREFS_KEY_EMAIL = "EMAIL";
    public static final String PREFS_USER_KEY = "USER_INFOR";
    Context mContext;
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    private static SPManipulation mInstance = null;

    // constructor
    private SPManipulation(Context context) {
        this.mContext = context;
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
    }

    public static SPManipulation getInstance(Context context) {
        if(mInstance == null) {
            mInstance = new SPManipulation(context);
        }
        return mInstance;
    }

    public boolean hasUserLoggedIn() {
        return settings.contains(PREFS_KEY_MOBILE);
    }

    // using shared preference to store the user mobile and user name
    public void save(Context context, String text) {
        editor = settings.edit(); //2
        editor.putString(PREFS_USER_KEY, text); //3
        editor.commit(); //4
    }

    public void savePwd(String text) {
        editor = settings.edit(); //2
        editor.putString(PREFS_KEY_PWD, text); //3
        editor.commit(); //4
    }

    public void saveEmail(String text) {
        editor = settings.edit(); //2
        editor.putString(PREFS_KEY_EMAIL, text); //3
        editor.commit(); //4
    }

    public void saveName(String text) {
        editor = settings.edit(); //2
        editor.putString(PREFS_KEY_NAME, text); //3
        editor.commit(); //4
    }

    public void saveMobile(String text) {
        editor = settings.edit(); //2
        editor.putString(PREFS_KEY_MOBILE, text); //3
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
        text = settings.getString(PREFS_USER_KEY, null); //2
        return text;
    }
    public String getValue(Context context, String prefs_key) {
        String text;
        text = settings.getString(prefs_key, null); //2
        return text;
    }

    public String getMobile() {
        return settings.getString(PREFS_KEY_MOBILE, null);
    }

    public String getEmail() {
        return settings.getString(PREFS_KEY_EMAIL, null);
    }

    public String getName() {
        return settings.getString(PREFS_KEY_NAME, null);
    }

    public String getPwd() {
        return settings.getString(PREFS_KEY_PWD, null);
    }

    public void clearSharedPreference(Context context) {
        editor = settings.edit();

        editor.clear();
        editor.commit();
    }
    public void removeValue(Context context, String prefs_key) {
        editor = settings.edit();
        editor.remove(prefs_key);
        editor.commit();
    }
}
