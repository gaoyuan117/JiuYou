package com.jiuyou.ui.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jiuyou.R;
import com.jiuyou.global.AppConfig;
import com.jiuyou.network.response.JZBResponse.OrderDetail;
import com.jiuyou.ui.activity.BuyHistoryActivity;
import com.jiuyou.ui.activity.EvaluateActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HistoryListAdapter extends BaseAdapter {

    private List<OrderDetail> list;
    private BuyHistoryActivity mContext;
    private String order_no;

    public HistoryListAdapter( BuyHistoryActivity context,List<OrderDetail> list,String order_no) {
        this.list = list;
        this.mContext = context;
        this.order_no=order_no;
    }


    // 移除一个项目的时候
    public void remove(int position) {
        this.list.remove(position);
    }

    public void selectAll(List<OrderDetail> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    public void noSelectAll(List<OrderDetail> list) {
        this.list = list;
        this.notifyDataSetChanged();
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.historylist_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(list.get(position).getIs_comment().equals("0")){
            holder.pingjia.setEnabled(true);
            holder.pingjia.setVisibility(View.VISIBLE);
            holder.pingjia.setText("评价");
            holder.pingjia.setBackgroundResource(R.drawable.shape_corner2);
            holder.pingjia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext, EvaluateActivity.class);
                    intent.putExtra("order_no",order_no);
                    intent.putExtra("product_id",list.get(position).getProduct_id());
                    mContext.startActivityForResult(intent,100);

                }
            });
        }else{
            holder.pingjia.setBackgroundResource(R.drawable.shape_corner2);
            holder.pingjia.setEnabled(false);
            holder.pingjia.setVisibility(View.VISIBLE);
            holder.pingjia.setText("已评价");
            holder.pingjia.setBackgroundResource(R.drawable.shape_corner21);
        }
        holder.pro_name.setText(list.get(position).getProduct_name());
        holder.pro_num.setText("X"+list.get(position).getQuantity());
        holder.pro_price.setText("¥"+String.format("%.2f", Double.parseDouble(list.get(position).getPrice())));
        Glide.with(mContext).load(AppConfig.ENDPOINTPIC+list.get(position).getMaster_img()).placeholder(R.mipmap.icon_nopic).into(holder.imageview);

        return convertView;
    }


    static class ViewHolder {
        @Bind(R.id.imageview)
        ImageView imageview;
        @Bind(R.id.pro_name)
        TextView pro_name;
        @Bind(R.id.pro_num)
        TextView pro_num;
        @Bind(R.id.pro_price)
        TextView pro_price;
        @Bind(R.id.pingjia)
        TextView pingjia;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
