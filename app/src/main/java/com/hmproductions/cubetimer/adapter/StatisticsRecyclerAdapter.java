package com.hmproductions.cubetimer.adapter;

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

    private static final int NORMAL_VIEW_TYPE = 1;
    private static final int EXPANDED_VIEW_TYPE = 2;

    private Context context;
    private List<Record> list;
    private OnStatisticClickListener listener;
    private int expandedItemPosition = -1;

    public interface OnStatisticClickListener {
        void onStatsDeleteClick(long dbId, int position);
        void smoothScrollToTop();
    }

    public StatisticsRecyclerAdapter(Context context, List<Record> list, OnStatisticClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public StatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == NORMAL_VIEW_TYPE)
            return new StatsViewHolder(LayoutInflater.from(context).inflate(R.layout.stats_list_item, parent, false),
                    viewType);
        else
            return new StatsViewHolder(LayoutInflater.from(context).inflate(R.layout.stats_expanded_list_item, parent, false),
                    viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull StatsViewHolder holder, int position) {
        Record currentStats = list.get(position);

        holder.idTextView.setText(String.valueOf(currentStats.getNumber()));
        holder.timeTextView.setText(currentStats.getTime());
        holder.ao5TextView.setText(currentStats.getAo5());
        holder.ao12TextView.setText(currentStats.getAo12());

        if (getItemViewType(position) == EXPANDED_VIEW_TYPE) {
            holder.scrambleTextView.setText(currentStats.getScramble());
            holder.dateTextView.setText(currentStats.getDateString());
        }
    }

    @Override
    public int getItemCount() {
        if (list == null || list.size() == 0) return 0;
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == expandedItemPosition) return EXPANDED_VIEW_TYPE;
        return NORMAL_VIEW_TYPE;
    }

    public void swapData(List<Record> newData, int position) {
        if (list == null) {
            list = newData;
            notifyDataSetChanged();
        } else if (list.size() + 1 == newData.size()) {
            list = newData;
            listener.smoothScrollToTop();
            notifyItemInserted(position);
        } else if (list.size() - 1 == newData.size()) {
            list = newData;
            notifyItemRemoved(position);
            notifyItemRangeChanged(0, expandedItemPosition);
            expandedItemPosition = -1;
        } else {
            list = newData;
            expandedItemPosition = -1;
            notifyDataSetChanged();
        }
    }

    class StatsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView idTextView, timeTextView, ao5TextView, ao12TextView, scrambleTextView, dateTextView;
        ImageView deleteImageView;

        StatsViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            idTextView = itemView.findViewById(R.id.idTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            ao5TextView = itemView.findViewById(R.id.ao5TextView);
            ao12TextView = itemView.findViewById(R.id.ao12TextView);

            if (viewType == EXPANDED_VIEW_TYPE) {
                scrambleTextView = itemView.findViewById(R.id.listItemScrambleTextView);
                dateTextView = itemView.findViewById(R.id.dateTextView);
                deleteImageView = itemView.findViewById(R.id.deleteImageView);

                deleteImageView.setOnClickListener(this);
            }

            itemView.setOnClickListener(v -> {
                if (expandedItemPosition == getAdapterPosition()) {
                    expandedItemPosition = -1;
                    notifyItemChanged(getAdapterPosition());
                } else /* else expand item */ {
                    int oldPosition = expandedItemPosition;
                    expandedItemPosition = getAdapterPosition();
                    notifyItemChanged(expandedItemPosition);
                    notifyItemChanged(oldPosition);
                }
            });
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            listener.onStatsDeleteClick(list.get(position).getDbId(), position);
        }
    }
}
