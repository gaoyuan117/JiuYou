package com.jiuyou.ui.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiuyou.R;
import com.jiuyou.global.AppConfig;
import com.jiuyou.network.response.JZBResponse.AboutResponse;
import com.jiuyou.ui.Utils.UserUtils;
import com.jiuyou.ui.base.BaseActivity;
import com.jiuyou.util.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 关于界面
 */
public class AboutActivity extends BaseActivity implements OnClickListener {
    @Bind(R.id.title_bar_operate_1)
    ImageView title_bar_operate_1;
    @Bind(R.id.tv_appname)
    TextView tv_appname;
    @Bind(R.id.tv_company)
    TextView tv_company;


    public static final String TAG = "AboutActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        initialize();
    }

    private void initialize() {
        title_bar_operate_1.setOnClickListener(this);
        getLoadingDataBar().show();
        UserUtils.about("", new UserUtils.aboutListener() {
            @Override
            public void load(boolean status, AboutResponse info, String message) {
                if (status) {
//                    tv_appname.setText(info.getInfos().get(0).getApp_name());
//                    tv_company.setText(info.getInfos().get(0).getCompany());
                    tv_appname.setText("酒水商城");
                } else {
                    ToastUtil.show(message);
                }
                closeProgressBar();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_bar_operate_1:
                AboutActivity.this.finish();
                AppConfig.currentTAB = MainActivity.TAB_MINE;
                break;
            default:
                break;
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            AboutActivity.this.finish();
            AppConfig.currentTAB = MainActivity.TAB_MINE;
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
