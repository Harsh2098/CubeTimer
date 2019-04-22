package com.hmproductions.cubetimer.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.hmproductions.cubetimer.R;
import com.hmproductions.cubetimer.data.Record;

import java.util.List;

public class StatisticsRecyclerAdapter extends RecyclerView.Adapter<StatisticsRecyclerAdapter.StatsViewHolder> {

    private Context context;
    private List<Record> list;
    private OnStatisticClickListener listener;

    public interface OnStatisticClickListener {
        void onStatsDeleteClick(long dbId);
    }

    public StatisticsRecyclerAdapter(Context context, List<Record> list, OnStatisticClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
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
        holder.scrambleTextView.setText(currentStats.getScramble());
        holder.dateTextView.setText(currentStats.getDateString());

        holder.itemView.setOnClickListener(v -> {
            boolean expanded = currentStats.getExpanded();

            holder.scrambleTextView.setVisibility(expanded ? View.GONE : View.VISIBLE);
            holder.deleteImageView.setVisibility(expanded ? View.GONE : View.VISIBLE);
            holder.dateTextView.setVisibility(expanded ? View.GONE : View.VISIBLE);

            currentStats.setExpanded(!expanded);
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        if (list == null || list.size() == 0) return 0;
        return list.size();
    }

    public void swapData(List<Record> newData) {
        if (list == null) {
            list = newData;
            notifyDataSetChanged();
        } else if (list.size() + 1 == newData.size()) {
            list = newData;
            notifyItemInserted(0);
        } else if (list.size() - 1 == newData.size()) {
            list = newData;
            notifyItemRemoved(0);
        } else {
            list = newData;
            notifyDataSetChanged();
        }
    }

    class StatsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView idTextView, timeTextView, ao5TextView, ao12TextView, scrambleTextView, dateTextView;
        ImageView deleteImageView;

        StatsViewHolder(@NonNull View itemView) {
            super(itemView);
            idTextView = itemView.findViewById(R.id.idTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            ao5TextView = itemView.findViewById(R.id.ao5TextView);
            ao12TextView = itemView.findViewById(R.id.ao12TextView);
            scrambleTextView = itemView.findViewById(R.id.listItemScrambleTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            deleteImageView = itemView.findViewById(R.id.deleteImageView);

            deleteImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onStatsDeleteClick(list.get(getAdapterPosition()).getDbId());
        }
    }
}
