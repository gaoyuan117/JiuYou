package com.jiuyou.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.jiuyou.R;
import com.jiuyou.global.BaseApp;
import com.jiuyou.global.MyListView;
import com.jiuyou.model.CommonBean;
import com.jiuyou.model.OrderBean;
import com.jiuyou.model.OrderInfoBean;
import com.jiuyou.model.OrderInfoItemAdapter;
import com.jiuyou.retrofit.BaseObjObserver;
import com.jiuyou.retrofit.HttpResult;
import com.jiuyou.retrofit.RetrofitClient;
import com.jiuyou.retrofit.RxUtils;
import com.jiuyou.ui.adapter.OrderItemAdapter;
import com.jiuyou.ui.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReturnMoneyActivity extends BaseActivity {

    @Bind(R.id.tv_return_title)
    TextView mTvReturnTitle;
    @Bind(R.id.img_return)
    ImageView mImgReturn;
    @Bind(R.id.tv_return_name)
    TextView mTvReturnName;
    @Bind(R.id.tv_return_type)
    TextView mTvReturnType;
    @Bind(R.id.tv_return_price)
    TextView mTvReturnPrice;
    @Bind(R.id.tv_return_num)
    TextView mTvReturnNum;
    @Bind(R.id.rg_return_buxiang)
    RadioButton mRgReturnBuxiang;
    @Bind(R.id.rg_return_diancuo)
    RadioButton mRgReturnDiancuo;
    @Bind(R.id.rg_return_bufu)
    RadioButton mRgReturnBufu;
    @Bind(R.id.rg_return_other)
    RadioButton mRgReturnOther;
    @Bind(R.id.tv_return)
    TextView mTvReturn;
    @Bind(R.id.lv_return_money)
    MyListView listView;

    private String reason;
    private OrderBean orderBean;
    private OrderInfoBean orderInfoBean;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_money);
        ButterKnife.bind(this);
        setCenterTitle("申请退款");

        orderBean = (OrderBean) getIntent().getSerializableExtra("data");
        orderInfoBean = (OrderInfoBean) getIntent().getSerializableExtra("data2");

        position = getIntent().getIntExtra("position", -1);
        if (orderBean != null) {
            OrderItemAdapter adapter = new OrderItemAdapter(this, orderBean.getOrder_detail());
            listView.setAdapter(adapter);
        }else {
            OrderInfoItemAdapter adapter2 = new OrderInfoItemAdapter(this,orderInfoBean.getDetail_Info());
            listView.setAdapter(adapter2);
        }

    }

    @OnClick({R.id.rg_return_buxiang, R.id.rg_return_diancuo, R.id.rg_return_bufu, R.id.rg_return_other, R.id.tv_return})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rg_return_buxiang:
                reason = mRgReturnBuxiang.getText().toString();
                break;
            case R.id.rg_return_diancuo:
                reason = mRgReturnDiancuo.getText().toString();
                break;
            case R.id.rg_return_bufu:
                reason = mRgReturnBufu.getText().toString();
                break;
            case R.id.rg_return_other:
                reason = mRgReturnOther.getText().toString();
                break;
            case R.id.tv_return://提交退款
                if (TextUtils.isEmpty(reason)) {
                    showToastMsg("请选择退款理由");
                    return;
                }
                if(orderBean!=null){
                    returnMoney(orderBean.getId());
                }else if(orderInfoBean!=null){
                    returnMoney(orderInfoBean.getDetail_Info().get(0).getOrder_id());
                }
                break;
        }
    }

    /**
     * 申请退款
     */
    private void returnMoney(String id) {
        RetrofitClient.getInstance().createApi().returnMoney(BaseApp.token(), id, reason)
                .compose(RxUtils.<HttpResult<CommonBean>>io_main())
                .subscribe(new BaseObjObserver<CommonBean>(this, "申请中") {
                    @Override
                    protected void onHandleSuccess(CommonBean commonBean) {
                        showToastMsg("申请已提交");
                        Intent intent = new Intent();
                        intent.putExtra("position", position);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
    }
}
