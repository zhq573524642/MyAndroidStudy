package com.zhq.exclusivememory.ui.activity.four_module.content_provide;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhq.exclusivememory.R;
import com.zhq.exclusivememory.ui.activity.four_module.bean.ContactBean;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Huiqiang Zhang
 * on 2019/1/21.
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder> {
    private List<ContactBean> itemData;
    private Context mContext;

    public ContactsAdapter(Context context, List<ContactBean> itemData) {
        this.itemData = itemData;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_contacts, parent, false));
        myViewHolder.mLlCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnContactCallListener != null) {
                    mOnContactCallListener.onCall(myViewHolder.getAdapterPosition());
                }
            }
        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Glide.with(mContext).load(itemData.get(position).getPortrait()).into(holder.mIvContactPortrait);
        holder.mTvName.setText("联系人：" + itemData.get(position).getName());
        holder.mTvPhone.setText("手机号：" + itemData.get(position).getPhone());

    }

    @Override
    public int getItemCount() {
        return itemData.size() > 0 ? itemData.size() : 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {


        private final CircleImageView mIvContactPortrait;
        private final TextView mTvName;
        private final TextView mTvPhone;
        private final LinearLayout mLlCall;

        public MyViewHolder(View itemView) {
            super(itemView);
            mIvContactPortrait = itemView.findViewById(R.id.iv_contact_portrait);
            mTvName = itemView.findViewById(R.id.tv_name);
            mTvPhone = itemView.findViewById(R.id.tv_phone);
            mLlCall = itemView.findViewById(R.id.ll_call);
        }
    }

    private OnContactCallListener mOnContactCallListener;

    public interface OnContactCallListener {
        void onCall(int position);
    }

    public void setOnContactCallListener(OnContactCallListener onContactCallListener) {
        this.mOnContactCallListener = onContactCallListener;
    }
}
