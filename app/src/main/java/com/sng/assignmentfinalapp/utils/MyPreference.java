package com.sng.assignmentfinalapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class MyPreference {
    private static Context context;

    public MyPreference(Context context)
    {
        this.context=context;
    }
    private static final String PREF_NAME="feed_pref";
    private static SharedPreferences sharedPreferences;
    public static SharedPreferences getPreference()
    {
        if(sharedPreferences==null)
        sharedPreferences=context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences;
    }

}
