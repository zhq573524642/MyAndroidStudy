package com.zhq.exclusivememory.ui.activity.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhq.exclusivememory.R;

import java.util.List;

/**
 * Created by Huiqiang Zhang
 * on 2019/10/29.
 */

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.MainViewHolder> {

    private Context context;
    private List<String> data;
    private final LayoutInflater layoutInflater;

    public MainListAdapter(Context context, List<String> list) {
        this.context = context;
        data = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = layoutInflater.inflate(R.layout.item_main_list, parent, false);
        final MainViewHolder viewHolder = new MainViewHolder(inflate);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onMainItemClickListener!=null){
                    int position = viewHolder.getAdapterPosition();
                    onMainItemClickListener.onMainItemClick(position);
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        holder.tvItemName.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class MainViewHolder extends RecyclerView.ViewHolder {

        TextView tvItemName;

        public MainViewHolder(View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tv_item_name);
        }
    }

    public interface OnMainItemClickListener {
        void onMainItemClick(int position);
    }

    private OnMainItemClickListener onMainItemClickListener;

    public void setOnMainItemClickListener(OnMainItemClickListener onMainItemClickListener) {
        this.onMainItemClickListener = onMainItemClickListener;
    }
}
