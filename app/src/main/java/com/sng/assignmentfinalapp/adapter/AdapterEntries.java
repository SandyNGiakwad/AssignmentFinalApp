package com.sng.assignmentfinalapp.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sng.assignmentfinalapp.R;
import com.sng.assignmentfinalapp.data_model.EntryModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterEntries extends RecyclerView.Adapter<AdapterEntries.MyViewHolder> {
    Context context;
    List<EntryModel> entryModels;
    List<EntryModel> originalData;

    public AdapterEntries(Context context, List<EntryModel> entryModels) {
        this.context = context;
        this.entryModels = entryModels;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_item_entries, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        EntryModel entryModel = entryModels.get(position);
        holder.tv_title.setText(entryModel.getTitle());
        holder.tv_magnitude.setText(entryModel.getMagnitude());
        holder.tv_summary.setText(Html.fromHtml(entryModel.getSummary()));
    }

    @Override
    public int getItemCount() {
        return entryModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title, tv_magnitude, tv_summary;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_magnitude = itemView.findViewById(R.id.tv_magnitude);
            tv_summary = itemView.findViewById(R.id.tv_summary);
        }
    }

    public void updateEntries(List<EntryModel> entryModels) {
        this.entryModels = entryModels;
        this.originalData = entryModels;
        notifyDataSetChanged();
    }

    public void sortEntriesByTitle() {
        Collections.sort(entryModels, new Comparator<EntryModel>() {
            @Override
            public int compare(EntryModel entryModel, EntryModel t1) {
                return entryModel.getTitle().compareTo(t1.getTitle());
            }
        });
        notifyDataSetChanged();
    }

    public void sortByTime() {
        Collections.sort(entryModels, new Comparator<EntryModel>() {
            @Override
            public int compare(EntryModel entryModel, EntryModel t1) {
                return t1.getTime().compareTo(entryModel.getTime());
            }
        });
        notifyDataSetChanged();
    }

    public void filterByMeasurment(int i) {
        ArrayList<EntryModel> entryModels1 = new ArrayList<>();
        switch (i) {
            case 1:

                for (EntryModel entryModel : originalData) {
                    if (entryModel.getMagnitude().compareTo("Magnitude 2") < 0) {
                        entryModels1.add(entryModel);
                    }
                }
                this.entryModels = entryModels1;
                notifyDataSetChanged();
                break;
            case 2:

                for (EntryModel entryModel : originalData) {
                    if (entryModel.getMagnitude().compareTo("Magnitude 2") > 0 && entryModel.getMagnitude().compareTo("Magnitude 4") < 0) {
                        entryModels1.add(entryModel);
                    }
                }
                this.entryModels = entryModels1;
                notifyDataSetChanged();
                break;
            case 3:

                for (EntryModel entryModel : originalData) {
                    if (entryModel.getMagnitude().compareTo("Magnitude 4") > 0 && entryModel.getMagnitude().compareTo("Magnitude 6") < 0) {
                        entryModels1.add(entryModel);
                    }
                }
                this.entryModels = entryModels1;
                notifyDataSetChanged();
                break;
            case 4:

                for (EntryModel entryModel : originalData) {
                    if (entryModel.getMagnitude().compareTo("Magnitude 6") > 0) {
                        entryModels1.add(entryModel);
                    }
                }
                this.entryModels = entryModels1;
                notifyDataSetChanged();
                break;
            case 5:
                this.entryModels = originalData;
                notifyDataSetChanged();
                break;
        }
    }
}
