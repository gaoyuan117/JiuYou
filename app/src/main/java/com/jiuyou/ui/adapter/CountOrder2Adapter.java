package com.jiuyou.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jiuyou.R;
import com.jiuyou.core.AppContext;
import com.jiuyou.customctrls.RoundImageView;
import com.jiuyou.global.AppConfig;
import com.jiuyou.model.OrderInfoBean;
import com.jiuyou.network.response.JZBResponse.Cart;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CountOrder2Adapter extends BaseAdapter {

    private List<OrderInfoBean.DetailInfoBean> list;
    private Context mContext;

    public CountOrder2Adapter(Context context, List<OrderInfoBean.DetailInfoBean> list) {
        this.list = list;
        this.mContext = context;
    }


    // 移除一个项目的时候
    public void remove(int position) {
        this.list.remove(position);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_countorder, parent, false);
            holder = new ViewHolder(convertView);
            holder.pic_goods.setType(RoundImageView.TYPE_ROUND);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        OrderInfoBean.DetailInfoBean bean = list.get(position);
        Glide.with(mContext).load(AppConfig.ENDPOINTPIC + bean.getGoods_img()).error(R.mipmap.logo).into(holder.pic_goods);
        holder.color_goods.setText(bean.getProduct_name());
        holder.integral_goods.setText("¥" + bean.getPrice());
        holder.tv_pronum.setText("x" + bean.getQuantity());
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.pic_goods)
        RoundImageView pic_goods;
        @Bind(R.id.color_goods)
        TextView color_goods;
        @Bind(R.id.integral_goods)
        TextView integral_goods;
        @Bind(R.id.tv_pronum)
        TextView tv_pronum;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
