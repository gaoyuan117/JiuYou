package com.jiuyou.ui.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.jiuyou.R;
import com.jiuyou.ui.adapter.MyEvaluateAdapter;
import com.jiuyou.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyEvaluateActivity extends BaseActivity {

    @Bind(R.id.lv_my_evaluate)
    ListView mListView;

    private List<String> mList;
    private MyEvaluateAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_evaluate);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        mList = new ArrayList<>();
        mList.add("");
        mList.add("");
        mList.add("");
        mAdapter = new MyEvaluateAdapter(this,mList);
        mListView.setAdapter(mAdapter);
    }
}
