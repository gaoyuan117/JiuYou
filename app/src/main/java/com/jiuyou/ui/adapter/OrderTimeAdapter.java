package com.jiuyou.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiuyou.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by gaoyuan on 2017/8/12.
 */

public class OrderTimeAdapter extends BaseAdapter {
    private Context activity;
    private List<String> mList;

    public OrderTimeAdapter(Context activity, List<String> mList) {
        this.activity = activity;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = View.inflate(activity, R.layout.pop_order_time, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.tvPopTime.setText(mList.get(i));
        if(i==0){
            viewHolder.imgSelector.setVisibility(View.VISIBLE);
        }else {
            viewHolder.imgSelector.setVisibility(View.GONE);
        }
        return view;
    }


    static class ViewHolder {
        @Bind(R.id.tv_pop_time)
        TextView tvPopTime;
        @Bind(R.id.img_selector)
        ImageView imgSelector;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
