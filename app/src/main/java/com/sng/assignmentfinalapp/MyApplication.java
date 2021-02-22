package com.sng.assignmentfinalapp;

import android.app.Application;

import com.sng.assignmentfinalapp.roomdb.database.AppDatabase;
import com.sng.assignmentfinalapp.utils.MyPreference;
import com.sng.assignmentfinalapp.utils.MySqliteHelper;

import androidx.room.Room;

public class MyApplication extends Application {
    public static MySqliteHelper mySqliteHelper;
    public static MyPreference myPreference;
    public static AppDatabase appDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        mySqliteHelper = new MySqliteHelper();
        myPreference = new MyPreference(this);
        appDatabase= Room.databaseBuilder(this,AppDatabase.class,"atom_db").build();
    }
}
