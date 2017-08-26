package com.jiuyou.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jiuyou.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by gaoyuan on 2017/8/10.
 */

public class SelectAdressAdapter extends BaseAdapter {
    private Context activity;
    private List<String> mList;

    public SelectAdressAdapter(Context activity, List<String> mList) {
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = View.inflate(activity, R.layout.item_select_adress, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.tvAdress.setText(mList.get(i));
        return view;
    }

    static class ViewHolder {
        @Bind(R.id.tv_adress)
        TextView tvAdress;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
