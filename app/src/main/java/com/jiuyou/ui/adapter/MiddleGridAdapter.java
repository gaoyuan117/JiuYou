package com.jiuyou.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.jiuyou.R;
import com.jiuyou.ui.base.BaseViewHolder;

/**
 * @author http://blog.csdn.net/finddreams
 * @Description:gridview的Adapter
 */
public class MiddleGridAdapter extends BaseAdapter {
    private Context mContext;

    public int[] imgs = {R.drawable.yuwen, R.drawable.shuxue, R.drawable.yingyu, R.drawable.wuli, R.drawable.huaxue};

    public MiddleGridAdapter(Context mContext) {
        super();
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return imgs.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.middle_grid_item, parent, false);
        }
        ImageView iv = BaseViewHolder.get(convertView, R.id.iv_item);
        iv.setBackgroundResource(imgs[position]);

        return convertView;
    }

}
