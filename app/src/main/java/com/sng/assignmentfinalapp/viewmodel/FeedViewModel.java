package com.sng.assignmentfinalapp.viewmodel;

import com.sng.assignmentfinalapp.data_model.EntryModel;
import com.sng.assignmentfinalapp.repository.FeedRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

public class FeedViewModel extends ViewModel {
    MutableLiveData<List<EntryModel>> feedMutableLiveData;
    MediatorLiveData<Boolean> loading;

    public FeedViewModel() {
        feedMutableLiveData = new MutableLiveData<>();
        loading=new MediatorLiveData<>();
        loading.addSource(FeedRepository.getInstance().getLoadingState(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                loading.setValue(aBoolean);
            }
        });
        try {

            feedMutableLiveData = FeedRepository.getInstance().getFeedDataInFromJson();

        } catch (Exception e) {

            feedMutableLiveData.setValue(null);
        }

    }


    public LiveData<List<EntryModel>> getFeedData() {
        return feedMutableLiveData;
    }
    public void refreshFeed()
    {
        //feedMutableLiveData=FeedRepository.getInstance().getFeedDataInFromJson();
        feedMutableLiveData.postValue(FeedRepository.getInstance().getFeedDataInFromJson().getValue());
    }

    public LiveData<Boolean> getLoadingState()
    {
        return loading;
    }

}
