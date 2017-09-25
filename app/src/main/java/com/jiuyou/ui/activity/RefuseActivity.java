package com.jiuyou.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jiuyou.R;
import com.jiuyou.global.BaseApp;
import com.jiuyou.model.CommonBean;
import com.jiuyou.retrofit.BaseObjObserver;
import com.jiuyou.retrofit.HttpResult;
import com.jiuyou.retrofit.RetrofitClient;
import com.jiuyou.retrofit.RxUtils;
import com.jiuyou.ui.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RefuseActivity extends BaseActivity {

    @Bind(R.id.tv_refuse_no)
    TextView tvRefuseNo;
    @Bind(R.id.rg_refuse_jia)
    RadioButton rgRefuseJia;
    @Bind(R.id.rg_refuse_po)
    RadioButton rgRefusePo;
    @Bind(R.id.et_refuse_other)
    EditText etRefuseOther;
    @Bind(R.id.ll_refuse_input)
    LinearLayout llRefuseInput;
    @Bind(R.id.tv_refuse)
    TextView tvRefuse;

    private String reason, orderNo, orderId;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refuse);
        ButterKnife.bind(this);

        setCenterTitle("拒绝收货");

        orderNo = getIntent().getStringExtra("orderNo");
        orderId = getIntent().getStringExtra("orderId");
        position = getIntent().getIntExtra("position", -1);

        tvRefuseNo.setText("单号："+orderNo);
    }

    @OnClick({R.id.rg_refuse_jia, R.id.rg_refuse_po, R.id.ll_refuse_input, R.id.tv_refuse})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rg_refuse_jia://假货
                reason = rgRefuseJia.getText().toString();
                break;
            case R.id.rg_refuse_po: //破损
                reason = rgRefusePo.getText().toString();
                break;
            case R.id.ll_refuse_input:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
                break;
            case R.id.tv_refuse://确定
                String s = etRefuseOther.getText().toString();
                if (TextUtils.isEmpty(reason)) {
                    if (TextUtils.isEmpty(s)) {
                        showToastMsg("请选择理由");
                    } else {
                        reason = s;
                    }
                }
                refuseGoods();
                break;
        }
    }

    /**
     * 拒绝收货
     */
    public void refuseGoods() {
        RetrofitClient.getInstance().createApi().refuseGoods(BaseApp.token(), orderId, reason)
                .compose(RxUtils.<HttpResult<CommonBean>>io_main())
                .subscribe(new BaseObjObserver<CommonBean>(this, "拒绝收货中") {
                    @Override
                    protected void onHandleSuccess(CommonBean commonBean) {
                        Toast.makeText(RefuseActivity.this, "拒绝收货成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.putExtra("position", position);
                        intent.putExtra("status", "10");
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
    }
}
