package com.jiuyou.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jiuyou.R;
import com.jiuyou.ui.base.BaseViewHolder;

public class MyGridAdapter extends BaseAdapter {
    private Context mContext;
    private String currentMoney;

    public String[] moneys = {"20","50","100","500","1000"};

    public MyGridAdapter(Context mContext,String currentMoney) {
        super();
        this.mContext = mContext;
        this.currentMoney=currentMoney;
    }
    public void setCurrentMoney(String money) {
        this.currentMoney = money;
        this.notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return moneys.length;
    }

    @Override
    public Object getItem(int position) {
        return moneys[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_choice_subject, parent, false);
        }
        TextView tv_choice_word = BaseViewHolder.get(convertView, R.id.tv_choice_word);
        if(currentMoney.equals(moneys[position])){
            tv_choice_word.setBackgroundResource(R.drawable.shape_corner6);
            tv_choice_word.setTextColor(mContext.getResources().getColor(R.color.color_red));
        }else{
            tv_choice_word.setBackgroundResource(R.drawable.shape_corner7);
            tv_choice_word.setTextColor(mContext.getResources().getColor(R.color.black));
        }
        tv_choice_word.setText(moneys[position]);
        return convertView;
    }

}
