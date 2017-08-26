package com.jiuyou.ui.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.jiuyou.ui.base.DefaultHolder;

import java.util.List;

/**
 * Created by wisely on 2016/4/18.
 */
public abstract class CommonAdapter extends CustomBaseAdapter {
    public CommonAdapter(List list) {
        super(list);
    }

    int position;

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        position = i;
        DefaultHolder holder = view == null ? getHolder() : (DefaultHolder) view.getTag();
        holder.setPosition(i);
        holder.setSize(mDataList.size());
        holder.setData(getItem(i));
        return holder.getRootView();
    }

    public abstract DefaultHolder getHolder();

}
