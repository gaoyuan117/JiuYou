package com.jiuyou.ui.base.impl;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiuyou.R;
import com.jiuyou.model.MineItem;
import com.jiuyou.ui.base.DefaultHolder;
import com.jiuyou.util.UiUtils;

/**
 * Created by wisely on 2016/4/18.
 */
public class PersonalCenterHolder extends DefaultHolder<MineItem> {

    TextView mTextView;
    TextView line;
    ImageView icon;
    TextView tvReviewing;
    @Override
    public View initView() {

        View view = UiUtils.inflate(R.layout.item_personal_center);
        mTextView = (TextView) view.findViewById(R.id.tv_personal_center);
        line = (TextView) view.findViewById(R.id.line);
        icon=(ImageView)view.findViewById(R.id.icon);
        tvReviewing = (TextView) view.findViewById(R.id.tv_reviewing);
        return view;
    }

    @Override
    public void initData() {
        MineItem data =getData();
        mTextView.setText(data.getItem());
        icon.setImageResource(data.getIcon());
        if (data.getVisible()){
            tvReviewing.setVisibility(View.VISIBLE);
        }else {
            tvReviewing.setVisibility(View.GONE);
        }
        if (getPosition() + 1 == getSize()) {
            line.setVisibility(View.GONE);
        } else {
            line.setVisibility(View.VISIBLE);
        }

    }


}
