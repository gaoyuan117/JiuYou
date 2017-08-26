package com.jiuyou.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by wisely on 2016/4/14.
 */
public abstract class CustomBaseAdapter<Data> extends BaseAdapter implements AdapterView.OnItemClickListener {

    List<Data> mDataList;
    public CustomBaseAdapter(List<Data> list){
        mDataList = list;
    }

    @Override
    public int getCount() {
        return mDataList == null?0:mDataList.size();
    }

    @Override
    public Data getItem(int i) {
        return mDataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public abstract View getView(int i, View view, ViewGroup viewGroup) ;

    public void _onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    /** 该方法不允许被重写，要加final修饰 */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        if(i>getCount()) {
            return;
        }
        _onItemClick(adapterView,view,i,l);

    }

    
}
