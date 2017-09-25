package com.jiuyou.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

    String position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_success2);
        ButterKnife.bind(this);
        position = getIntent().getStringExtra("position");
        Log.e("gy", "position：" + position);
        setCenterTitle("支付成功");
    }

    @OnClick(R.id.tv_success_back)
    public void onViewClicked() {
        if (!TextUtils.isEmpty(position)) {
            AppConfig.currentTAB = MainActivity.TAB_HOME;
            startActivity(new Intent(PaySuccess2Activity.this, MainActivity.class));
        } else {
            AppConfig.currentTAB = MainActivity.TAB_HOME;
            PaySuccess2Activity.this.finish();

        }
    }
}
