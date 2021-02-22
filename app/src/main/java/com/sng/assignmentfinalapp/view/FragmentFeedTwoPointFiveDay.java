package com.sng.assignmentfinalapp.view;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sng.assignmentfinalapp.MainActivity;
import com.sng.assignmentfinalapp.R;
import com.sng.assignmentfinalapp.adapter.AdapterEntries;
import com.sng.assignmentfinalapp.data_model.EntryModel;
import com.sng.assignmentfinalapp.interfaces.FilterInterface;
import com.sng.assignmentfinalapp.interfaces.SortingInterface;
import com.sng.assignmentfinalapp.utils.MyPreference;
import com.sng.assignmentfinalapp.utils.MySqliteHelper;
import com.sng.assignmentfinalapp.viewmodel.FeedViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class FragmentFeedTwoPointFiveDay extends Fragment {
    List<EntryModel> entryModels;
    ProgressDialog progressDialog;
    RecyclerView rv_entries;
    AdapterEntries adapterEntries;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_feed, container, false);
        rv_entries = view.findViewById(R.id.rv_entries);
        swipeRefreshLayout = view.findViewById(R.id.swipe_container);
        rv_entries.setLayoutManager(new LinearLayoutManager(getActivity()));
        entryModels = new ArrayList<>();
        adapterEntries = new AdapterEntries(getActivity(), entryModels);
        rv_entries.setAdapter(adapterEntries);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Connecting..");
        progressDialog.setMessage("Getting Data From server");

        SharedPreferences.Editor editor = MyPreference.getPreference().edit();
        editor.putString(MySqliteHelper.FEED_TYPE, "2.5_day");
        editor.commit();


        FeedViewModel viewModel = new ViewModelProvider(this).get(FeedViewModel.class);
        //progressDialog.show();
        viewModel.getFeedData().observe(getActivity(), new Observer<List<EntryModel>>() {
            @Override
            public void onChanged(List<EntryModel> entryModelsList) {

                entryModels=entryModelsList;
                adapterEntries.updateEntries(entryModels);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        viewModel.getLoadingState().observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean==true)
                    progressDialog.show();
                else
                    progressDialog.dismiss();
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                viewModel.refreshFeed();

            }
        });
        ((MainActivity) getActivity()).sortEntries(new SortingInterface() {
            @Override
            public void sortNow(int sortcase) {
                switch (sortcase) {
                    case 1:
                        adapterEntries.sortEntriesByTitle();
                        break;
                    case 2:
                        adapterEntries.sortByTime();
                        break;
                }
            }
        });
        ((MainActivity) getActivity()).filterMeasurment(new FilterInterface() {
            @Override
            public void filterByMeasurment(int filterCase) {
                adapterEntries.filterByMeasurment(filterCase);
            }
        });
        return view;
    }
}
