package com.jiuyou.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.jiuyou.R;
import com.jiuyou.global.AppConfig;
import com.jiuyou.ui.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PaySuccess2Activity extends BaseActivity {

    @Bind(R.id.tv_success_back)
    ImageView tvSuccessBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_success2);
        ButterKnife.bind(this);
        setCenterTitle("支付成功");
    }

    @OnClick(R.id.tv_success_back)
    public void onViewClicked() {
        AppConfig.currentTAB = MainActivity.TAB_ACCOUNT;
        PaySuccess2Activity.this.finish();
    }
}
