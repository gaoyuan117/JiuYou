package com.jiuyou.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jiuyou.R;
import com.jiuyou.global.AppConfig;
import com.jiuyou.ui.base.BaseViewHolder;

import java.util.List;

public class GoodsDetailAdapter extends BaseAdapter {

    private List<String> list;
    private Context mContext;

    public GoodsDetailAdapter(List<String> list, Context context) {
        this.list = list;
        this.mContext = context;
    }


    public void remove(int position) {
        this.list.remove(position);
    }

    public void selectAll(List<String> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    public void noSelectAll(List<String> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(list!=null){
            return list.size();
        }
        return 0;
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
                    R.layout.item_more, parent, false);
        }
        ImageView image = BaseViewHolder.get(convertView, R.id.image);
        if(list!=null&&list.size()>0){
            String picPath=AppConfig.ENDPOINTPIC+list.get(position);
            Log.e("tgh","picPath="+picPath);
            Glide.with(mContext).load(picPath).placeholder(R.mipmap.icon_nopic).into(image);
//            AppContext.getInstance().getImageLoader().displayImage(AppConfig.ENDPOINTPIC+list.get(position), image);
        }
        return convertView;
    }


}
