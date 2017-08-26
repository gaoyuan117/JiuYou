package com.jiuyou.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jiuyou.R;
import com.jiuyou.global.BaseApp;
import com.jiuyou.model.CommonBean;
import com.jiuyou.model.MyAdressBean;
import com.jiuyou.retrofit.BaseListObserver;
import com.jiuyou.retrofit.BaseObjObserver;
import com.jiuyou.retrofit.HttpArray;
import com.jiuyou.retrofit.HttpResult;
import com.jiuyou.retrofit.RetrofitClient;
import com.jiuyou.retrofit.RxUtils;
import com.jiuyou.ui.adapter.AdressAdapter;
import com.jiuyou.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdressActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @Bind(R.id.lv_address)
    ListView mListView;
    @Bind(R.id.tv_adress_add)
    TextView mTvAdressAdd;
    @Bind(R.id.ll_adress)
    LinearLayout layout;

    private List<MyAdressBean> mList;
    private AdressAdapter mAdapter;

    private String type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adress);
        ButterKnife.bind(this);
        setCenterTitle("收货地址");
        type = getIntent().getStringExtra("type");
        Log.e("gy", "type：" + type);
        init();
        mListView.setOnItemClickListener(this);
    }

    private void init() {
        mList = new ArrayList<>();
        mAdapter = new AdressAdapter(this, mList);
        mListView.setAdapter(mAdapter);
        getReceiveAddrs();
        mListView.setEmptyView(layout);
    }

    @OnClick(R.id.tv_adress_add)
    public void onViewClicked() {
        Intent intent = new Intent(this, AddAdressActivity.class);
        int size = mList.size();
        if (size > 0) {
            intent.putExtra("num", mList.size());
        }
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        getReceiveAddrs();
    }

    /**
     * 获取送货地址
     */
    private void getReceiveAddrs() {
        RetrofitClient.getInstance().createApi().getReceiveAddrs(BaseApp.token())
                .compose(RxUtils.<HttpArray<MyAdressBean>>io_main())
                .subscribe(new BaseListObserver<MyAdressBean>(this) {
                    @Override
                    protected void onHandleSuccess(List<MyAdressBean> list) {
                        mList.clear();
                        if (list == null) {
                            mList.clear();
                            mAdapter.notifyDataSetChanged();
                            return;
                        }
                        mList.addAll(list);
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    /**
     * 设置默认地址
     */
    public void setDefaultAdress(String id) {
        RetrofitClient.getInstance().createApi().defaultaReceiveAddr(BaseApp.token(), id)
                .compose(RxUtils.<HttpResult<CommonBean>>io_main())
                .subscribe(new BaseObjObserver<CommonBean>(this, "设置中") {
                    @Override
                    protected void onHandleSuccess(CommonBean commonBean) {
                        showToastMsg("设置成功");
                        getReceiveAddrs();
                    }
                });
    }

    /**
     * 删除地址
     */
    public void delAdress(String id) {
        RetrofitClient.getInstance().createApi().delReceiveAddr(BaseApp.token(), id)
                .compose(RxUtils.<HttpResult<CommonBean>>io_main())
                .subscribe(new BaseObjObserver<CommonBean>(this, "删除中") {
                    @Override
                    protected void onHandleSuccess(CommonBean commonBean) {
                        showToastMsg("删除成功");
                        getReceiveAddrs();
                    }
                });
    }

    /**
     * 编辑收货地址
     */
    public void editAdress(MyAdressBean bean) {
        Intent intent = new Intent(this, AddAdressActivity.class);
        intent.putExtra("type", "edit");
        intent.putExtra("data", bean);
        startActivityForResult(intent, 2);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (type != null && type.equals("select")) {
            Intent intent = new Intent();
            intent.putExtra("data", mList.get(i));
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
