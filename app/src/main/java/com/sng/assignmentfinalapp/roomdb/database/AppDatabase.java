package com.sng.assignmentfinalapp.roomdb.database;

import com.sng.assignmentfinalapp.roomdb.daos.EntryDAO;
import com.sng.assignmentfinalapp.roomdb.entitties.Entry;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Entry.class},version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
public abstract EntryDAO entryDAO();
}
