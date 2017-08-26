package com.jiuyou.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

public class CommentListAdapter extends BaseAdapter {

    private List<DataList> list;
    private Context mContext;

    public CommentListAdapter(List<DataList> list, Context context) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.commentlist_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Integer time=Integer.parseInt(list.get(position).getComm_time()==null?"0":list.get(position).getComm_time());
        holder.tv_leave_time.setText(DateUtils.transForDate1(time));
        holder.tv_name.setText(list.get(position).getNickname());
        holder.tv_leave_connect.setText(list.get(position).getComment());
        AppContext.getInstance().getImageLoader().displayImage(AppConfig.ENDPOINTPIC+list.get(position).getAvatar(),holder.comment_portrait);
        return convertView;
    }
    static class ViewHolder {
        @Bind(R.id.comment_portrait)
        CircleImageView comment_portrait;
        @Bind(R.id.tv_name)
        TextView tv_name;
        @Bind(R.id.tv_leave_time)
        TextView tv_leave_time;
        @Bind(R.id.tv_leave_connect)
        TextView tv_leave_connect;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
