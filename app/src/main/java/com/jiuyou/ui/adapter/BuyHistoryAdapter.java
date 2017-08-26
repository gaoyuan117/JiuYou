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
import com.jiuyou.customctrls.MyListView;
import com.jiuyou.global.AppConfig;
import com.jiuyou.network.response.JZBResponse.DataList;
import com.jiuyou.ui.activity.ActivityImageShower;
import com.jiuyou.ui.activity.BuyHistoryActivity;
import com.jiuyou.ui.activity.MainActivity;
import com.jiuyou.util.DateUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BuyHistoryAdapter extends BaseAdapter {

    private List<DataList> list;
    private BuyHistoryActivity mContext;


    public BuyHistoryAdapter( BuyHistoryActivity context,List<DataList> list) {
        this.list = list;
        this.mContext = context;
    }

    public void setList(List<DataList> list){
        this.list=list;
    }


    // 移除一个项目的时候
    public void remove(int position) {
        this.list.remove(position);
    }

    public void selectAll(List<DataList> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    public void noSelectAll(List<DataList> list) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.buyhistory_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.time1.setText(DateUtils.transForDate1(Integer.parseInt(list.get(position).getUpdate_time())));
        holder.quhuoma.setText(list.get(position).getPkcode());
        holder.jianshu.setText("共"+list.get(position).getCount()+"件商品");
        holder.heji.setText("合计¥"+String.format("%.2f", Double.parseDouble(list.get(position).getRel_price())));
        holder.list_history.setAdapter(new HistoryListAdapter(mContext,list.get(position).getOrder_details(),list.get(position).getOrder_no()));
        Glide.with(mContext).load(AppConfig.ENDPOINTPIC+list.get(position).getQrcode()).placeholder(R.mipmap.icon_nopic).into(holder.erweima);
        holder.erweima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, ActivityImageShower.class);
                intent.putExtra("erweima",AppConfig.ENDPOINTPIC+list.get(position).getQrcode());
                mContext.startActivity(intent);
            }
        });
        holder.fujinhuogui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConfig.currentTAB= MainActivity.TAB_MAP;
                AppConfig.currentOrder_Id=list.get(position).getOrder_no();
                AppConfig.ismap=true;
                mContext.finish();
            }
        });

        return convertView;
    }
    static class ViewHolder {
        @Bind(R.id.time1)
        TextView time1;
        @Bind(R.id.quhuoma)
        TextView quhuoma;
        @Bind(R.id.erweima)
        ImageView erweima;
        @Bind(R.id.jianshu)
        TextView jianshu;
        @Bind(R.id.heji)
        TextView heji;
        @Bind(R.id.fujinhuogui)
        TextView fujinhuogui;
        @Bind(R.id.list_history)
        MyListView list_history;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
