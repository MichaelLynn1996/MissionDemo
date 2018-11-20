package com.xingkong.sl.starmessage.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xingkong.sl.starmessage.R;
import com.xingkong.sl.starmessage.model.Message;

import java.util.List;

/**
 * Created by SeaLynn0 on 2018/1/12.
 *
 * 自定义RecyclerView的适配器
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private Context mContext;

    private List<Message> mItemList;

    public ItemAdapter(List<Message> mItemList) {
        this.mItemList = mItemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Message message = mItemList.get(position);
        holder.time.setText(message.getTime());
        holder.content.setText(message.getContent());
        holder.username.setText(message.getUsername());
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView time;
        TextView content;
        TextView username;

        ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            time = itemView.findViewById(R.id.tv_data_time);
            content = itemView.findViewById(R.id.tv_message);
            username = itemView.findViewById(R.id.tv_username);
        }
    }
}
