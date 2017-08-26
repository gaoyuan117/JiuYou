package com.jiuyou.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jiuyou.R;
import com.jiuyou.global.BaseApp;
import com.jiuyou.model.CodeBean;
import com.jiuyou.retrofit.BaseObjObserver;
import com.jiuyou.retrofit.HttpResult;
import com.jiuyou.retrofit.RetrofitClient;
import com.jiuyou.retrofit.RxUtils;
import com.jiuyou.ui.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InviteCodeActivity extends BaseActivity {

    @Bind(R.id.img_invite_code)
    ImageView mImgCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_code);
        ButterKnife.bind(this);
        setCenterTitle("邀请码");
        getCode();
    }

    @OnClick(R.id.img_invite_code)
    public void onViewClicked() {
    }

    private void getCode(){
        RetrofitClient.getInstance().createApi().getCode(BaseApp.token(),"1")
                .compose(RxUtils.<HttpResult<CodeBean>>io_main())
                .subscribe(new BaseObjObserver<CodeBean>(this) {
                    @Override
                    protected void onHandleSuccess(CodeBean codeBean) {
                        Glide.with(InviteCodeActivity.this).load(codeBean.getQrcode()).into(mImgCode);
                    }
                });
    }
}
