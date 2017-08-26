package com.jiuyou.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiuyou.R;
import com.jiuyou.ui.activity.MyEvaluateActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by gaoyuan on 2017/8/11.
 */

public class MyEvaluateAdapter extends BaseAdapter {
    private MyEvaluateActivity activity;
    private List<String> mList;

    public MyEvaluateAdapter(MyEvaluateActivity activity, List<String> mList) {
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
            view = View.inflate(activity, R.layout.item_my_evaluate, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        return view;
    }


    static class ViewHolder {
        @Bind(R.id.img_my_evaluate)
        ImageView imgMyEvaluate;
        @Bind(R.id.tv_my_evaluate_title)
        TextView tvMyEvaluateTitle;
        @Bind(R.id.tv_my_evaluate_content)
        TextView tvMyEvaluateContent;
        @Bind(R.id.tv_my_evaluate_time)
        TextView tvMyEvaluateTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
