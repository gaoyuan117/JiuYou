package com.jiuyou.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jiuyou.R;
import com.jiuyou.ui.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectPsTypeActivity extends BaseActivity {

    @Bind(R.id.tv_select_ziqu)
    TextView tvSelectZiqu;
    @Bind(R.id.tv_select_ps)
    TextView tvSelectPs;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_ps_type);
        ButterKnife.bind(this);
        setCenterTitle("取货方式");
        intent = new Intent();

    }

    @OnClick({R.id.tv_select_ziqu, R.id.tv_select_ps})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_select_ziqu:
                intent.putExtra("type", "1");
                setResult(110, intent);
                finish();
                break;
            case R.id.tv_select_ps:
                intent.putExtra("type", "2");
                setResult(110, intent);
                finish();
                break;
        }
    }
}
