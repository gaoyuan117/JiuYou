package com.jiuyou.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jiuyou.R;
import com.jiuyou.alipay.AliPay;
import com.jiuyou.core.AppContext;
import com.jiuyou.customctrls.CircleImageView;
import com.jiuyou.customctrls.MyGridView;
import com.jiuyou.global.AppConfig;
import com.jiuyou.network.response.JZBResponse.AmountResponse;
import com.jiuyou.ui.Utils.UserUtils;
import com.jiuyou.ui.adapter.MyGridAdapter;
import com.jiuyou.unionpay.BaseActivity;
import com.jiuyou.util.PrefereUtils;
import com.jiuyou.util.ToastUtil;
import com.jiuyou.wxpay.WxPay;
import com.unionpay.UPPayAssistEx;
import com.unionpay.uppay.PayActivity;


public class RechargeActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private RelativeLayout rl_zhifubao, rl_weixin, rl_yinlian;
    private ImageButton ib_zhifubao, ib_weixin, ib_yinlian;
    private Button btn_chongzhi;
    private MyGridView gridView;
    private MyGridAdapter adapter;
    private TextView chongzhijilu, tv_nickname, tv_yue;
    private CircleImageView head_image;
    private String currentMoney = "1000";
    public String[] moneys = {"20", "50", "100", "500", "1000"};
    private int chongzhiTYPE = 1;
    private String payChannel = "alipay";
    private ImageView title_bar_operate_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        initView();
    }

    private void initView() {
        title_bar_operate_1 = (ImageView) findViewById(R.id.title_bar_operate_1);
        title_bar_operate_1.setOnClickListener(this);
        head_image = (CircleImageView) findViewById(R.id.head_image);

        Glide.with(this).load(AppConfig.ENDPOINTPIC + PrefereUtils.getInstance().getUser().getAvatar())
                .error(R.mipmap.logo)
                .into(head_image);
//        AppContext.getInstance().getImageLoader().displayImage(,head_image);
        tv_nickname = (TextView) findViewById(R.id.tv_nickname);
        tv_nickname.setText("昵称：" + PrefereUtils.getInstance().getUser().getNickname());
        tv_yue = (TextView) findViewById(R.id.tv_yue);
        String amount = PrefereUtils.getInstance().getUser().getAmount();
        double am = Double.parseDouble(amount);
        tv_yue.setText("余额：¥" + String.format("%.2f", am));
        chongzhijilu = (TextView) findViewById(R.id.chongzhijilu);
        chongzhijilu.setOnClickListener(this);
        gridView = (MyGridView) findViewById(R.id.my_gridView);
        adapter = new MyGridAdapter(this, currentMoney);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentMoney = moneys[position];
                adapter.setCurrentMoney(currentMoney);
            }
        });
        rl_zhifubao = (RelativeLayout) findViewById(R.id.rl_zhifubao);
        rl_zhifubao.setOnClickListener(this);
        rl_weixin = (RelativeLayout) findViewById(R.id.rl_weixin);
        rl_weixin.setOnClickListener(this);
        rl_yinlian = (RelativeLayout) findViewById(R.id.rl_yinlian);
        rl_yinlian.setOnClickListener(this);
        ib_zhifubao = (ImageButton) findViewById(R.id.ib_zhifubao);
        ib_weixin = (ImageButton) findViewById(R.id.ib_weixin);
        ib_yinlian = (ImageButton) findViewById(R.id.ib_yinlian);
        btn_chongzhi = (Button) findViewById(R.id.btn_chongzhi);
        btn_chongzhi.setOnClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void doStartUnionPayPlugin(Activity activity, String tn, String mode) {
        UPPayAssistEx.startPayByJAR(activity,
                PayActivity.class, null, null, tn, mode);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.rl_zhifubao:
                changeIB(R.id.rl_zhifubao);
                chongzhiTYPE = 1;
                break;
            case R.id.rl_weixin:
                changeIB(R.id.rl_weixin);
                chongzhiTYPE = 2;
                break;
            case R.id.rl_yinlian:
                changeIB(R.id.rl_yinlian);
                chongzhiTYPE = 3;
                break;
            case R.id.btn_chongzhi:
                //充值
                getLoadingDataBar().show();
                UserUtils.reChargeOrder(PrefereUtils.getInstance().getToken(), Double.valueOf(currentMoney), payChannel, new UserUtils.reChargeListener() {
                    @Override
                    public void load(boolean status, AmountResponse info, String message) {
                        if (status) {
                            String order_no = info.getOrderInfo().getOrder_no();
                            recharge(order_no);
                        } else {
                            ToastUtil.show(message);
                        }
                        closeProgressBar();
                    }
                });
                break;
            case R.id.chongzhijilu:
                startActivity(new Intent(RechargeActivity.this, RechargeListActivity.class));
                break;
            case R.id.title_bar_operate_1:
                RechargeActivity.this.finish();
                break;

        }
    }

    private void recharge(String order_no) {
        if (payChannel.equals("alipay")) {
            AliPay aliPay = new AliPay(RechargeActivity.this);
            aliPay.payV2(false, currentMoney, "充值", order_no, new AliPay.AlipayCallBack() {
                @Override
                public void onSuccess() {
                    finish();
                    AppConfig.currentTAB = MainActivity.TAB_MINE;
                    ToastUtil.show("充值成功");
                }

                @Override
                public void onDeeling() {
                    ToastUtil.show("支付完成，后台处理中");
                }

                @Override
                public void onCancle() {
                    ToastUtil.show("取消充值");
                }

                @Override
                public void onFailure(String msg) {
                    ToastUtil.show(msg);
                }
            });
        } else if (payChannel.equals("wxpay")) {
            WxPay.getWxPay().pay(RechargeActivity.this, order_no, "jiaofei", currentMoney, "http://cupboard.jzbwlkj.com/index.php/api/Notify/WXPay", new WxPay.WxCallBack() {
                @Override
                public void payResponse(int code) {
                    if (code == 0) {
                        finish();
                        AppConfig.currentTAB = MainActivity.TAB_MINE;
                        ToastUtil.show("充值成功");
                    } else {
                        ToastUtil.show("充值失败");
                    }
                }
            });

        } else if (payChannel.equals("unionpay")) {
            getTn((int) (Double.valueOf(currentMoney) * 100), order_no, "1");
        }
    }

    private void changeIB(int id) {
        switch (id) {
            case R.id.rl_zhifubao:
                payChannel = "alipay";
                ib_zhifubao.setBackgroundResource(R.drawable.icon_fuxuankuan_xuanzhong);
                ib_weixin.setBackgroundResource(R.drawable.icon_fuxuankuan);
                ib_yinlian.setBackgroundResource(R.drawable.icon_fuxuankuan);
                break;
            case R.id.rl_weixin:
                ToastUtil.show("微信支付功能尚未完善，请先选择其他支付方式支付");
//                return;
//                payChannel="wxpay";
//                ib_zhifubao.setBackgroundResource(R.drawable.icon_fuxuankuan);
//                ib_weixin.setBackgroundResource(R.drawable.icon_fuxuankuan_xuanzhong);
//                ib_yinlian.setBackgroundResource(R.drawable.icon_fuxuankuan);
                break;
            case R.id.rl_yinlian:
                payChannel = "unionpay";
                ib_zhifubao.setBackgroundResource(R.drawable.icon_fuxuankuan);
                ib_weixin.setBackgroundResource(R.drawable.icon_fuxuankuan);
                ib_yinlian.setBackgroundResource(R.drawable.icon_fuxuankuan_xuanzhong);
                break;
        }
    }
}
