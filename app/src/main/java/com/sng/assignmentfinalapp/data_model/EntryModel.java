package com.sng.assignmentfinalapp.data_model;

public class EntryModel {
    String id, title, updated_on, event_page_url, summary, age, magnitude, time, type;
    Float realMagnitude;

    public String getType() {
        return type;
    }

    public Float getRealMagnitude() {
        return realMagnitude;
    }

    public void setRealMagnitude(Float realMagnitude) {
        this.realMagnitude = realMagnitude;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public String getUpdated_on() {
        return updated_on;
    }

    public void setUpdated_on(String updated_on) {
        this.updated_on = updated_on;
    }

    public String getEvent_page_url() {
        return event_page_url;
    }

    public void setEvent_page_url(String event_page_url) {
        this.event_page_url = event_page_url;
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
}
