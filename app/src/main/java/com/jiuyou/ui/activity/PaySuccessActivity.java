package com.jiuyou.ui.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiuyou.R;
import com.jiuyou.core.AppContext;
import com.jiuyou.global.AppConfig;
import com.jiuyou.network.response.JZBResponse.Detail_info;
import com.jiuyou.ui.base.BaseActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class PaySuccessActivity extends BaseActivity {
    @Bind(R.id.title_bar_operate_1)
    ImageView title_bar_operate_1;
    @Bind(R.id.tv_goumai)
    TextView tv_goumai;
    @Bind(R.id.tv_quhuohao)
    TextView tv_quhuohao;
    @Bind(R.id.erweima)
    ImageView erweima;
    @Bind(R.id.btn_exit)
    Button btn_exit;
    private ArrayList<Detail_info> detail_infos;
    private String qrcode;
    private String  pkcode;
    private String orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paysuccess);
        ButterKnife.bind(this);
        initView();
        initDatas();
    }

    private void initView(){
        title_bar_operate_1=(ImageView) findViewById(R.id.title_bar_operate_1);
        title_bar_operate_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConfig.currentTAB=MainActivity.TAB_ACCOUNT;
                PaySuccessActivity.this.finish();
            }
        });
        tv_goumai=(TextView) findViewById(R.id.tv_goumai);
        tv_quhuohao=(TextView) findViewById(R.id.tv_quhuohao);
        erweima=(ImageView) findViewById(R.id.erweima);
        btn_exit=(Button) findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConfig.currentTAB=MainActivity.TAB_MAP;
                AppConfig.currentOrder_Id=orderId;
                AppConfig.ismap=true;
                PaySuccessActivity.this.finish();
            }
        });

    }
    private void initDatas(){
        detail_infos=(ArrayList<Detail_info>) getIntent().getSerializableExtra("detail_Info");
        qrcode=getIntent().getStringExtra("qrcode");
        pkcode=getIntent().getStringExtra("pkcode");
        orderId=getIntent().getStringExtra("orderId");
        AppContext.getImageLoaderProxy().displayImage(AppConfig.ENDPOINTPIC+qrcode,erweima);
        tv_quhuohao.setText(pkcode);
        String str="您已成功购买";
        for(int i=0;i<detail_infos.size();i++){
            str+=(detail_infos.get(i).getProduct_name()+"X"+detail_infos.get(i).getQuantity());
        }
        tv_goumai.setText(str);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            AppConfig.currentTAB=MainActivity.TAB_ACCOUNT;
            PaySuccessActivity.this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
