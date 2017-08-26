package com.jiuyou.ui.base;

import android.view.View;

public abstract class DefaultHolder<Data> {

    private View mRootView;
    protected Data data;
    private Data rightData;

    private int position;
    private int size;

    public DefaultHolder() {
        mRootView = initView();
        mRootView.setTag(this);
    }

    public View getRootView() {
        return mRootView;
    }

    public void setData(Data data) {
        this.data = data;
        initData();
    }

    public void setData(Data data, Data rightData) {
        this.data = data;
        this.rightData = rightData;
        initData();
    }

    /**
     * 初始化控件
     */
    public abstract View initView();

    /**
     * 刷新数据
     */
    public abstract void initData();

    public Data getData() {
        return data;
    }

    public Data getRightData() {
        return rightData;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
