package com.sng.assignmentfinalapp.repository;


import com.sng.assignmentfinalapp.MyApplication;
import com.sng.assignmentfinalapp.data_model.EntryModel;
import com.sng.assignmentfinalapp.roomdb.entitties.Entry;
import com.sng.assignmentfinalapp.utils.ApiInterface;
import com.sng.assignmentfinalapp.utils.MyPreference;
import com.sng.assignmentfinalapp.utils.MySqliteHelper;
import com.sng.assignmentfinalapp.utils.NetworkInstance;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import androidx.lifecycle.MutableLiveData;
import fr.arnaudguyon.xmltojsonlib.XmlToJson;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedRepository {

    MutableLiveData<List<EntryModel>> feedMutableLiveData = new MutableLiveData<>();
    MutableLiveData<Boolean> isLoading = new MutableLiveData<>();


    private static FeedRepository feedRepository;


    public static FeedRepository getInstance() {
        if (feedRepository == null)
            feedRepository = new FeedRepository();

        return feedRepository;
    }


    public MutableLiveData<List<EntryModel>> getFeedDataInFromJson() {
        isLoading.setValue(true);
        ApiInterface apiInterface = NetworkInstance.getRetrofit().create(ApiInterface.class);
        Call<String> call = apiInterface.getXmlFeed(MyPreference.getPreference().getString(MySqliteHelper.FEED_TYPE, ""));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    XmlToJson xmlToJson = new XmlToJson.Builder(response.body().toString()).build();
                    JSONObject jsonObject = xmlToJson.toJson();

                    try {

                        ArrayList<EntryModel> entryModels = new ArrayList<>();
                        JSONObject jsonObjectFeed = jsonObject.getJSONObject("feed");


                        JSONArray jsonObjectEntries = jsonObjectFeed.getJSONArray("entry");
                        for (int i = 0; i < jsonObjectEntries.length(); i++) {
                            EntryModel entryModel = new EntryModel();
                            JSONObject jsonObjectEntry = jsonObjectEntries.getJSONObject(i);
                            entryModel.setTitle(jsonObjectEntry.getString("title"));
                            entryModel.setId(jsonObjectEntry.getString("id"));


                            JSONObject jsonObjectLink = jsonObjectEntry.getJSONObject("link");
                            entryModel.setEvent_page_url(jsonObjectLink.getString("href"));

                            JSONArray jsonArrayCategories = jsonObjectEntry.getJSONArray("category");
                            for (int i1 = 0; i1 < jsonArrayCategories.length(); i1++) {
                                JSONObject jsonObjectCategory = jsonArrayCategories.getJSONObject(i1);
                                if (jsonObjectCategory.getString("label").equalsIgnoreCase("Age"))
                                    entryModel.setAge(jsonObjectCategory.getString("term"));

                                if (jsonObjectCategory.getString("label").equalsIgnoreCase("Magnitude"))
                                    entryModel.setMagnitude(jsonObjectCategory.getString("term"));
                            }
                            entryModel.setUpdated_on(jsonObjectEntry.getString("updated"));
                            String summary = jsonObjectEntry.getJSONObject("summary").
                                    getString("content");
                            entryModel.setSummary(summary.substring(summary.indexOf("<dl>"), summary.length() - 1).replace("<dt>", "<br><b>").
                                    replace("</dt>", "</b>").
                                    replace("<dd>", "&nbsp;<br>").
                                    replace("</dd>", ""));
                            XmlToJson xmlToJsonTimeLocation = new XmlToJson.Builder(jsonObjectEntry.getJSONObject("summary").getString("content")).build();
                            JSONObject jsonObjectTimeLocation = xmlToJsonTimeLocation.toJson();
                            HashMap<String, String> map = getTimeLocation(jsonObjectTimeLocation);
                            entryModel.setTime(map.get("Time"));
                            Float realMag=Float.parseFloat(jsonObjectEntry.getString("title").split(" ")[1].trim().replace("M","").trim());
                            entryModel.setRealMagnitude(realMag);
                            //Log.i("Json string",jsonObjectTime.toString());
                            entryModels.add(entryModel);
                        }
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                insertDataInDB(entryModels);
                            }
                        }).start();


                        feedMutableLiveData.postValue(entryModels);
                        isLoading.setValue(false);

                    } catch (Exception e) {

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                isLoading.postValue(true);
                                feedMutableLiveData.postValue(getEntriesFromDb());

                            }
                        }).start();
                    }
                    // convert to a Json String
                    //String jsonString = xmlToJson.toString();

                    // convert to a formatted Json String
                    //String formatted = xmlToJson.toFormattedString();
                    //Log.i("JSON formatted",jsonString);
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        feedMutableLiveData.postValue(getEntriesFromDb());
                        isLoading.postValue(false);
                    }
                }).start();

            }
        });

        return feedMutableLiveData;
    }

    private HashMap<String, String> getTimeLocation(JSONObject jsonObject) {
        HashMap<String, String> map = new HashMap();
        try {
            JSONObject jsonObjectDL = jsonObject.getJSONObject("dl");
            JSONArray jsonArrayDD = jsonObjectDL.getJSONArray("dd");

            map.put("Time", jsonArrayDD.getJSONObject(0).getString("content"));
            map.put("Location", jsonArrayDD.getJSONObject(1).getString("content"));


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return map;
    }

    public MutableLiveData<Boolean> getLoadingState() {
        return isLoading;
    }

    public void insertDataInDB(ArrayList<EntryModel> entryModels) {
        for (int i = 0; i < entryModels.size(); i++) {
            EntryModel entryModel = entryModels.get(i);
            Entry entry = new Entry();
            entry.setId(entryModel.getId());
            entry.setAge(entryModel.getAge());
            entry.setEvent_page_link(entryModel.getEvent_page_url());
            entry.setMagnitude(entryModel.getMagnitude());
            entry.setSummary(entryModel.getSummary());
            entry.setTime(entryModel.getTime());
            entry.setTitle(entryModel.getTitle());
            entry.setType(MyPreference.getPreference().getString(MySqliteHelper.FEED_TYPE, ""));
            entry.setUpdated_on(entryModel.getUpdated_on());
            entry.setReal_magnitude(entryModel.getRealMagnitude());
            try {
                MyApplication.appDatabase.entryDAO().insert(entry);
            } catch (Exception e) {

            }

        }

    }

    public ArrayList<EntryModel> getEntriesFromDb() {
        ArrayList<EntryModel> entryModels = new ArrayList<>();
        List<Entry> entries = MyApplication.appDatabase.entryDAO().getEntries();
        Float magnitudeToComapre=0.0f;
        if(!MyPreference.getPreference().getString(MySqliteHelper.FEED_TYPE,"").contains("all"))
        magnitudeToComapre=Float.parseFloat(MyPreference.getPreference().getString(MySqliteHelper.FEED_TYPE,"").replace("_day",""));

        for (int i = 0; i < entries.size(); i++) {
            Entry entry = entries.get(i);
            if (checkForPastDayValidation(entry.getTime())&&entry.getReal_magnitude()>=magnitudeToComapre) {
                EntryModel entryModel = new EntryModel();
                entryModel.setId(entry.getId());
                entryModel.setAge(entry.getAge());
                entryModel.setEvent_page_url(entryModel.getEvent_page_url());
                entryModel.setMagnitude(entry.getMagnitude());
                entryModel.setSummary(entry.getSummary());
                entryModel.setTime(entry.getTime());
                entryModel.setTitle(entry.getTitle());
                entryModel.setType(entry.getType());
                entryModel.setUpdated_on(entry.getUpdated_on());
                entryModels.add(entryModel);
            }
        }
        isLoading.postValue(false);
        return entryModels;
    }

    public boolean checkForPastDayValidation(String dob) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
            Date date1 = simpleDateFormat.parse(dob.replace("UTC", ""));

            long diff = getCurrentDate() - date1.getTime();
            long seconds = diff / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            long days = hours / 24;
            if (days > 1)
                return true;
        } catch (Exception e) {
            return false;
        }
        return false;

    }

    public long getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date today = Calendar.getInstance().getTime();
        return today.getTime();
    }


}
