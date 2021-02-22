package com.sng.assignmentfinalapp.roomdb.entitties;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Entry {
    @PrimaryKey
    @NonNull
    String id;
    @ColumnInfo(name = "title")
    String title;
    @ColumnInfo(name = "summary")
    String summary;
    @ColumnInfo(name = "age")
    String age;
    @ColumnInfo(name = "magnitude")
    String magnitude;
    @ColumnInfo(name = "event_page_link")
    String event_page_link;
    @ColumnInfo(name = "time")
    String time;
    @ColumnInfo(name = "type")
    String type;
    @ColumnInfo(name = "updated_on")
    String updated_on;
    @ColumnInfo(name = "real_magnitude")
    Float real_magnitude;

    public Float getReal_magnitude() {
        return real_magnitude;
    }

    public void setReal_magnitude(Float real_magnitude) {
        this.real_magnitude = real_magnitude;
    }

    public String getUpdated_on() {
        return updated_on;
    }

    public void setUpdated_on(String updated_on) {
        this.updated_on = updated_on;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(String magnitude) {
        this.magnitude = magnitude;
    }

    public String getEvent_page_link() {
        return event_page_link;
    }

    public void setEvent_page_link(String event_page_link) {
        this.event_page_link = event_page_link;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
