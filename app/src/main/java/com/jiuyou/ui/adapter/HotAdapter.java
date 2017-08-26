package com.jiuyou.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jiuyou.R;
import com.jiuyou.network.response.OtherResponse.KeyWord;
import com.jiuyou.ui.base.BaseViewHolder;

import java.util.List;

public class HotAdapter extends BaseAdapter {

    private List<KeyWord> list;
    private Context mContext;

    public HotAdapter(List<KeyWord> list, Context context) {
        this.list = list;
        this.mContext = context;
    }


    // 移除一个项目的时候
    public void remove(int position) {
        this.list.remove(position);
    }

    public void selectAll(List<KeyWord> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    public void noSelectAll(List<KeyWord> list) {
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
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_search, parent, false);
        }
        TextView tv_hotpic = BaseViewHolder.get(convertView, R.id.tv_hotpic);
        switch (position) {
            case 0:
                tv_hotpic.setBackgroundResource(R.drawable.circle1);
                break;
            case 1:
                tv_hotpic.setBackgroundResource(R.drawable.circle2);
                break;
            case 2:
                tv_hotpic.setBackgroundResource(R.drawable.circle3);
                break;
            default:tv_hotpic.setBackgroundResource(R.drawable.circle4);
        }
        tv_hotpic.setText(position+1+"");
        TextView tv_hotwords = BaseViewHolder.get(convertView, R.id.tv_hotwords);
        tv_hotwords.setText(list.get(position).getTitle());

        return convertView;
    }


}
