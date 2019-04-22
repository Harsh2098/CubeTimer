package com.hmproductions.cubetimer.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.hmproductions.cubetimer.R;
import com.hmproductions.cubetimer.data.Record;

import java.util.List;

public class StatisticsRecyclerAdapter extends RecyclerView.Adapter<StatisticsRecyclerAdapter.StatsViewHolder> {

    private Context context;
    private List<Record> list;

    public StatisticsRecyclerAdapter(Context context, List<Record> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public StatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StatsViewHolder(LayoutInflater.from(context).inflate(R.layout.stats_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StatsViewHolder holder, int position) {
        Record currentStats = list.get(position);

        holder.idTextView.setText(String.valueOf(currentStats.getNumber()));
        holder.timeTextView.setText(currentStats.getTime());
        holder.ao5TextView.setText(currentStats.getAo5());
        holder.ao12TextView.setText(currentStats.getAo12());
    }

    @Override
    public int getItemCount() {
        if (list == null || list.size() == 0) return 0;
        return list.size();
    }

    public void insertNewStatistic(Record newStats) {
        list.add(0, newStats);
        notifyItemInserted(0);
    }

    public void swapData(List<Record> newData) {
        list = newData;
        notifyDataSetChanged();
    }

    class StatsViewHolder extends RecyclerView.ViewHolder {

        TextView idTextView, timeTextView, ao5TextView, ao12TextView;

        StatsViewHolder(@NonNull View itemView) {
            super(itemView);
            idTextView = itemView.findViewById(R.id.idTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            ao5TextView = itemView.findViewById(R.id.ao5TextView);
            ao12TextView = itemView.findViewById(R.id.ao12TextView);
        }
    }
}
