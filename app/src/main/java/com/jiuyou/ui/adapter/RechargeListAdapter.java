package com.jiuyou.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiuyou.R;
import com.jiuyou.core.AppContext;
import com.jiuyou.customctrls.CircleImageView;
import com.jiuyou.global.AppConfig;
import com.jiuyou.network.response.JZBResponse.DataList;
import com.jiuyou.util.DateUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RechargeListAdapter extends BaseAdapter {

    private List<DataList> list;
    private Context mContext;
    private String lastData="";

    public RechargeListAdapter( Context context,List<DataList> list) {
        this.list = list;
        this.mContext = context;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.recharge_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(position>0){
            lastData=list.get(position-1).getUpdate_time().substring(0,7);
        }
        Log.e("gy",AppConfig.ENDPOINTPIC+list.get(position).getIcon());

        if(lastData.equals(list.get(position).getUpdate_time().substring(0,7))){
              holder.ll1.setVisibility(View.GONE);
              holder.tv_time.setText(DateUtils.transForDate2(Integer.parseInt(list.get(position).getFull_time())));
              holder.tv_cztime.setText(list.get(position).getUpdate_time());
              holder.chongzhi_success.setText(list.get(position).getStatus());
              holder.tv_chongzhi.setText(list.get(position).getBody());
              AppContext.getInstance().getImageLoader().displayImage(AppConfig.ENDPOINTPIC+list.get(position).getIcon(),holder.item_head);
        }else{
            holder.ll1.setVisibility(View.VISIBLE);
            holder.tv_time.setText(DateUtils.transForDate2(Integer.parseInt(list.get(position).getFull_time())));
            holder.tv_cztime.setText(list.get(position).getUpdate_time());
            holder.chongzhi_success.setText(list.get(position).getStatus());
            holder.tv_chongzhi.setText(list.get(position).getBody());
            AppContext.getInstance().getImageLoader().displayImage(AppConfig.ENDPOINTPIC+list.get(position).getIcon(),holder.item_head);
        }
        return convertView;
    }
    static class ViewHolder {
        @Bind(R.id.ll1)
        LinearLayout ll1;
        @Bind(R.id.tv_time)
        TextView tv_time;
        @Bind(R.id.item_head)
        CircleImageView item_head;
        @Bind(R.id.tv_chongzhi)
        TextView tv_chongzhi;
        @Bind(R.id.tv_cztime)
        TextView tv_cztime;
        @Bind(R.id.chongzhi_success)
        TextView chongzhi_success;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
