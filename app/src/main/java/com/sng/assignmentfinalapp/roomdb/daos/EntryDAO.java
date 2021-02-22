package com.sng.assignmentfinalapp.roomdb.daos;

import com.sng.assignmentfinalapp.data_model.EntryModel;
import com.sng.assignmentfinalapp.roomdb.entitties.Entry;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface EntryDAO {
    @Query("SELECT * FROM entry")
    List<Entry> getEntries();

    @Insert
    void insert(Entry entry);
}
