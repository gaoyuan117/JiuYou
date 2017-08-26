package com.jiuyou.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jiuyou.R;
import com.jiuyou.model.TuiJianBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by gaoyuan on 2017/8/11.
 */

public class TuiJianAdapter<T> extends BaseAdapter {
    private Context activity;
    private List<T> mList;

    public TuiJianAdapter(Context activity, List<T> mList) {
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
            view = View.inflate(activity, R.layout.item_tuijian, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        T t = mList.get(i);
        if (t instanceof TuiJianBean.ZtlistBean) {
            TuiJianBean.ZtlistBean bean = (TuiJianBean.ZtlistBean) mList.get(i);
            viewHolder.tvTuijianName.setText(bean.getNickname());
            viewHolder.tvTuijianPhone.setText(bean.getMobile());

        } else if (t instanceof TuiJianBean.JlistBean) {
            TuiJianBean.JlistBean bean = (TuiJianBean.JlistBean) mList.get(i);
            viewHolder.tvTuijianName.setText(bean.getNickname());
            viewHolder.tvTuijianPhone.setText(bean.getMobile());
        }


        return view;
    }

    static class ViewHolder {
        @Bind(R.id.tv_tuijian_name)
        TextView tvTuijianName;
        @Bind(R.id.tv_tuijian_phone)
        TextView tvTuijianPhone;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
