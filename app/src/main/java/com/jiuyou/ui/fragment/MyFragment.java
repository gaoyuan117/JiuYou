package com.jiuyou.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jiuyou.R;
import com.jiuyou.model.TuiJianBean;
import com.jiuyou.ui.adapter.TuiJianAdapter;

/**
 * Project Name :mytest
 * package Name :com.xzwzz.mytest
 * Author       :xzwzz@vip.qq.com
 * Createtime   :2017/8/11  15:31
 */
public class MyFragment extends Fragment {
    private ListView mListView;
    private String type;
    private TuiJianAdapter mAdapter;
    private TuiJianBean tuiJianBean;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);
        initView(view);
        initdata();
        return view;
    }

    private void initdata() {
        type = getArguments().getString("text");
        tuiJianBean = (TuiJianBean) getArguments().getSerializable("data");

        if (tuiJianBean == null) {
            return;
        }

        if (type.equals("直推会员")) {
            if (tuiJianBean.getZtlist() == null) {
                return;
            }
            mAdapter = new TuiJianAdapter(getActivity(), tuiJianBean.getZtlist());
            mListView.setAdapter(mAdapter);

        } else {
            if (tuiJianBean.getJlist() == null) {
                return;
            }
            mAdapter = new TuiJianAdapter(getActivity(), tuiJianBean.getJlist());
            mListView.setAdapter(mAdapter);
        }

    }

    private void initView(View view) {
        mListView = (ListView) view.findViewById(R.id.fragment_lv);

    }

}
