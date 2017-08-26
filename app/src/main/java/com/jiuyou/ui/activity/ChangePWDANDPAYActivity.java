package com.jiuyou.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jiuyou.R;
import com.jiuyou.ui.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ChangePWDANDPAYActivity extends BaseActivity {
    @Bind(R.id.title_bar_operate_1)
    ImageView title_bar_operate_1;
    @Bind(R.id.rl_zhifu)
    RelativeLayout rl_zhifu;
    @Bind(R.id.rl_login)
    RelativeLayout rl_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepwd);
        ButterKnife.bind(this);
        initView();
    }
    Editable etext;
    private void initView() {
        title_bar_operate_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePWDANDPAYActivity.this.finish();
            }
        });
        rl_zhifu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent=new Intent(ChangePWDANDPAYActivity.this,ChangePayPasswordActivity.class);
                intent.putExtra("status","pay");
                startActivity(intent);
            }
        });
        rl_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ChangePWDANDPAYActivity.this,ChangeLoginPassWordActivity.class);
                intent.putExtra("status","login");
                startActivity(intent);
            }
        });
    }

}
