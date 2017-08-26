package com.jiuyou.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jiuyou.R;
import com.jiuyou.global.AppConfig;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by gaoyuan on 2017/8/14.
 */

public class OrderInfoItemAdapter extends BaseAdapter {

    private List<OrderInfoBean.DetailInfoBean> list;
    private Context mContext;

    public OrderInfoItemAdapter(Context context, List<OrderInfoBean.DetailInfoBean> list) {
        this.list = list;
        this.mContext = context;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_all_order, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        OrderInfoBean.DetailInfoBean bean = list.get(position);
        holder.tvAllDdTitle.setText(bean.getProduct_name());
        holder.tvAllDdPrice.setText("¥ " + bean.getPrice());
        holder.tvAllDdNum.setText("x " + bean.getQuantity());

        Glide.with(mContext).load(AppConfig.ENDPOINTPIC + bean.getImg()).diskCacheStrategy(DiskCacheStrategy.ALL).error(R.mipmap.icon_nopic).into(holder.imgAllDd);
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.img_all_dd)
        ImageView imgAllDd;
        @Bind(R.id.tv_all_dd_title)
        TextView tvAllDdTitle;
        @Bind(R.id.tv_all_dd_price)
        TextView tvAllDdPrice;
        @Bind(R.id.tv_all_dd_num)
        TextView tvAllDdNum;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
