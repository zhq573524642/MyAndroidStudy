package com.zhq.exclusivememory.ui.activity.third_party.map;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.zhq.exclusivememory.R;

import java.util.List;

public class PoiSearchResultAdapter extends RecyclerView.Adapter<PoiSearchResultAdapter.PoiViewHolder> {

    private Context context;
    private List<PoiInfo> data;
    private final LayoutInflater layoutInflater;

    public PoiSearchResultAdapter(Context context, List<PoiInfo> list) {
        this.context = context;
        data = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public PoiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = layoutInflater.inflate(R.layout.item_poi_info, parent, false);
        final PoiViewHolder viewHolder = new PoiViewHolder(inflate);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPoiInfoItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    onPoiInfoItemClickListener.onPoiInfoItemClick(position);
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PoiViewHolder holder, int position) {
        PoiInfo info = data.get(position);
        holder.tvPoiName.setText(info.getName());
        holder.tvPoiAddress.setText(info.getAddress());
        holder.tvOtherInfo.setText("省:" + info.getProvince() +  " 市:" + info.getCity() + " 区:" + info.getArea() + " 邮编:" + info.getPostCode());
        if (!TextUtils.isEmpty(info.getPhoneNum())) {
            holder.tvPhoneNum.setText("联系电话：" + info.getPhoneNum());
        } else {
            holder.tvPhoneNum.setText("暂无联系电话");

        }
        holder.tvLatlng.setText("纬度："+info.getLocation().latitude+"  经度："+info.getLocation().longitude);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class PoiViewHolder extends RecyclerView.ViewHolder {

        TextView tvPoiName;
        TextView tvPoiAddress;
        TextView tvOtherInfo;
        TextView tvPhoneNum;
        TextView tvLatlng;

        public PoiViewHolder(View itemView) {
            super(itemView);
            tvPoiName = itemView.findViewById(R.id.tv_poi_name);
            tvPoiAddress = itemView.findViewById(R.id.tv_poi_address);
            tvOtherInfo = itemView.findViewById(R.id.tv_other_info);
            tvPhoneNum = itemView.findViewById(R.id.tv_phone_num);
            tvLatlng = itemView.findViewById(R.id.tv_latlng);
        }
    }

    public interface OnPoiInfoItemClickListener {
        void onPoiInfoItemClick(int position);
    }

    private OnPoiInfoItemClickListener onPoiInfoItemClickListener;

    public void setOnPoiInfoItemClickListener(OnPoiInfoItemClickListener onPoiInfoItemClickListener) {
        this.onPoiInfoItemClickListener = onPoiInfoItemClickListener;
    }
}
