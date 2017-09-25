package com.jiuyou.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jiuyou.R;
import com.jiuyou.alipay.AliPay;
import com.jiuyou.core.AppContext;
import com.jiuyou.customctrls.MyListView;
import com.jiuyou.customctrls.widget.OnPasswordInputFinish;
import com.jiuyou.customctrls.widget.PopEnterPassword;
import com.jiuyou.global.AppConfig;
import com.jiuyou.global.BaseApp;
import com.jiuyou.model.MyAdressBean;
import com.jiuyou.model.OrderTimeBean;
import com.jiuyou.network.interfaces.HomeApi;
import com.jiuyou.network.response.JZBResponse.Cart;
import com.jiuyou.network.response.JZBResponse.PayResponse;
import com.jiuyou.network.response.JZBResponse.QuickResponse;
import com.jiuyou.network.response.JZBResponse.UserResponse;
import com.jiuyou.retrofit.BaseListObserver;
import com.jiuyou.retrofit.HttpArray;
import com.jiuyou.retrofit.RetrofitClient;
import com.jiuyou.retrofit.RxUtils;
import com.jiuyou.ui.Utils.CartUtils;
import com.jiuyou.ui.Utils.UserUtils;
import com.jiuyou.ui.adapter.CountOrderAdapter;
import com.jiuyou.ui.adapter.OrderTimeAdapter;
import com.jiuyou.unionpay.BaseActivity;
import com.jiuyou.util.MD5Utils;
import com.jiuyou.util.PopUtil;
import com.jiuyou.util.PrefereUtils;
import com.jiuyou.util.ToastUtil;
import com.jiuyou.wxpay.WxPay;
import com.unionpay.UPPayAssistEx;
import com.unionpay.uppay.PayActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class ConfirmOrderActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.tv_confirm_order_people)
    TextView tvConfirmOrderPeople;
    @Bind(R.id.tv_confirm_order_phone)
    TextView tvConfirmOrderPhone;
    @Bind(R.id.tv_confirm_order_adress)
    TextView tvConfirmOrderAdress;
    @Bind(R.id.tv_confirm_order_type)
    TextView tvConfirmOrderType;
    @Bind(R.id.ll_confirm_order_type)
    LinearLayout llConfirmOrderType;
    @Bind(R.id.ll_confirm_order_adress)
    LinearLayout llConfirmOrderAdress;
    @Bind(R.id.tv_confirm_order_add)
    TextView tvConfirmOrderAdd;


    private ImageView title_bar_operate_1;
    private MyListView mylistview;
    private TextView tv_pronum, tv_proprice, tv_zongji, tv_tijiao;
    private RelativeLayout rl_lingqian, rl_weixin, rl_zhifubao, rl_yinlian;
    private CountOrderAdapter adapter;
    private List<Cart> list;
    private String totalNum, totalPrice;
    private String orderId;
    private String[] selPros;
    private String channelPay = "zhifubao";
    private ImageButton ib_lingqian, ib_weixin, ib_zhifubao, ib_yinlian;
    private TextView tv1;
    private Intent intent2;
    private MyAdressBean adressBean;

    private String dialogMessage;//距离太远 提示语句
    private List<String> timeList;//配送时间集合
    private String psType, send_type;//取货方式 1 自取  2 配送
    private String ps_time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmorder);
        ButterKnife.bind(this);
        initView();
        initData();
        intent2 = new Intent(this, AdressActivity.class);
        intent2.putExtra("type", "select");

        getReceiveAddrs();
    }

    @Override
    public void doStartUnionPayPlugin(Activity activity, String tn, String mode) {
        UPPayAssistEx.startPayByJAR(activity,
                PayActivity.class, null, null, tn, mode);
    }

    private void initView() {
        tv1 = (TextView) findViewById(R.id.tv1);
        ib_lingqian = (ImageButton) findViewById(R.id.ib_lingqian);
        ib_lingqian.setOnClickListener(this);
        ib_weixin = (ImageButton) findViewById(R.id.ib_weixin);
        ib_weixin.setOnClickListener(this);
        ib_zhifubao = (ImageButton) findViewById(R.id.ib_zhifubao);
        ib_zhifubao.setOnClickListener(this);
        ib_yinlian = (ImageButton) findViewById(R.id.ib_yinlian);
        ib_yinlian.setOnClickListener(this);
        title_bar_operate_1 = (ImageView) findViewById(R.id.title_bar_operate_1);
        title_bar_operate_1.setOnClickListener(this);
        mylistview = (MyListView) findViewById(R.id.mylistview);
        tv_pronum = (TextView) findViewById(R.id.tv_pronum);
        tv_proprice = (TextView) findViewById(R.id.tv_proprice);
        tv_zongji = (TextView) findViewById(R.id.tv_zongji);
        tv_tijiao = (TextView) findViewById(R.id.tv_tijiao);
        tv_tijiao.setOnClickListener(this);
        rl_lingqian = (RelativeLayout) findViewById(R.id.rl_lingqian);
        rl_lingqian.setOnClickListener(this);
        rl_weixin = (RelativeLayout) findViewById(R.id.rl_weixin);
        rl_weixin.setOnClickListener(this);
        rl_zhifubao = (RelativeLayout) findViewById(R.id.rl_zhifubao);
        rl_zhifubao.setOnClickListener(this);
        rl_yinlian = (RelativeLayout) findViewById(R.id.rl_yinlian);
        rl_yinlian.setOnClickListener(this);

        tvConfirmOrderAdd.setOnClickListener(this);
        llConfirmOrderAdress.setOnClickListener(this);
        llConfirmOrderType.setOnClickListener(this);
    }

    private void initData() {
        list = (ArrayList<Cart>) getIntent().getSerializableExtra("selectCarts");
        timeList = new ArrayList<>();
        selPros = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            selPros[i] = list.get(i).getId();
        }
        totalNum = getIntent().getStringExtra("totalNum");
        totalPrice = getIntent().getStringExtra("totalPrice");
        adapter = new CountOrderAdapter(ConfirmOrderActivity.this, list);
        mylistview.setAdapter(adapter);
        tv_pronum.setText("共" + totalNum.replace("(", "") + "件商品");
        tv_proprice.setText(totalPrice);
        tv_zongji.setText("总计:" + totalPrice);
        tv1.setText("余额支付");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_bar_operate_1:
                ConfirmOrderActivity.this.finish();
                break;

            case R.id.rl_lingqian:
                channelPay = "lingqian";
                changeIB(R.id.rl_lingqian);
                break;
            case R.id.ib_lingqian:
                channelPay = "lingqian";
                changeIB(R.id.rl_lingqian);
                break;
            case R.id.rl_weixin:
                ToastUtil.show("微信支付功能尚未完善，请先选择其他支付方式支付");
//                channelPay = "weixin";
//                changeIB(R.id.rl_weixin);
                break;
            case R.id.ib_weixin:
                ToastUtil.show("微信支付功能尚未完善，请先选择其他支付方式支付");
//                channelPay = "weixin";
//                changeIB(R.id.rl_weixin);
                break;
            case R.id.rl_zhifubao:
                channelPay = "zhifubao";
                changeIB(R.id.rl_zhifubao);
                break;
            case R.id.ib_zhifubao:
                channelPay = "zhifubao";
                changeIB(R.id.rl_zhifubao);
                break;
            case R.id.rl_yinlian:
                channelPay = "yinlian";
                changeIB(R.id.rl_yinlian);
                break;
            case R.id.ib_yinlian:
                channelPay = "yinlian";
                changeIB(R.id.rl_yinlian);
                break;
            case R.id.tv_tijiao://提交订单

                if (adressBean == null) {
                    Toast.makeText(this, "请添加收货地址", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(psType)) {
                    Toast.makeText(this, "请选择取货方式", Toast.LENGTH_SHORT).show();
                    return;
                }

                toPay(ps_time);

                break;
            case R.id.ll_confirm_order_type://配送方式
                if (adressBean == null) {
                    ToastUtil.show("请选择收货地址");
                    return;
                }
                Intent in = new Intent(ConfirmOrderActivity.this, SelectPsTypeActivity.class);
                in.putExtra("id", adressBean.getId() + "");
                startActivityForResult(in, 111);
                break;

            case R.id.tv_confirm_order_add://添加收货地址
                startActivityForResult(this.intent2, 1);
                break;
            case R.id.ll_confirm_order_adress://更换收货地址
                startActivityForResult(this.intent2, 2);
                break;
        }
    }


    /**
     * 去支付  生成订单号
     */
    private void toPay(String sendTime) {
        if (orderId != null && !orderId.equals("")) {
            pay();
        } else {
            getLoadingDataBar().show();
            String token = PrefereUtils.getInstance().getToken();
            String payType = "1";
            if (channelPay.equals("weixin")) {
                payType = "1";
            } else if (channelPay.equals("zhifubao")) {
                payType = "2";
            } else if (channelPay.equals("lingqian")) {
                payType = "3";
            }

            Log.e("gy", "配送方式：" + psType);
            Log.e("gy", "支付方式：" + payType);
            CartUtils.toTrade(adressBean.getId(), psType, sendTime, payType, token, selPros, send_type, new CartUtils.toTradeListener() {
                @Override
                public void load(boolean status, PayResponse info, String message) {
                    if (status) {
                        orderId = info.getPay().getOrder_no();
                        pay();
                    } else {
                        ToastUtil.show(message);
                    }
                    closeProgressBar();
                }
            });
        }
    }

    //支付
    private void pay() {
        if (channelPay.equals("lingqian")) {
            pay_1();
        } else if (channelPay.equals("weixin")) {
            pay_2();
        } else if (channelPay.equals("zhifubao")) {
            pay_3();
        } else if (channelPay.equals("yinlian")) {
            pay_4();
        }
    }

    private void pay_2() {
        String currentMoney = totalPrice.substring(1);
        WxPay.getWxPay().pay(ConfirmOrderActivity.this, orderId, "jiaofei", currentMoney, "http://cupboard.jzbwlkj.com/index.php/api/Notify/prodWx", new WxPay.WxCallBack() {
            @Override
            public void payResponse(int code) {
                if (code == 0) {
                    AppContext.createRequestApi(HomeApi.class).order_info(PrefereUtils.getInstance().getToken(), orderId, new Callback<QuickResponse>() {
                        @Override
                        public void success(QuickResponse quickResponse, Response response) {
                            Log.e("tgh", "token=" + PrefereUtils.getInstance().getToken() + " orderid=" + orderId);
                            if (quickResponse.getCode() == 200 && quickResponse.getData().getDetail_infos().size() > 0) {
                                Intent intent = null;
                                if (psType.equals("1")) {
                                    intent = new Intent(ConfirmOrderActivity.this, PaySuccessActivity.class);
                                } else {
                                    intent = new Intent(ConfirmOrderActivity.this, PaySuccess2Activity.class);
                                }
//                                Intent intent = new Intent(ConfirmOrderActivity.this, PaySuccessActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("detail_Info", quickResponse.getData().getDetail_infos());
                                bundle.putString("pkcode", quickResponse.getData().getPkcode());
                                bundle.putString("qrcode", quickResponse.getData().getQrcode());
                                bundle.putString("orderId", orderId);
                                intent.putExtras(bundle);
                                ConfirmOrderActivity.this.startActivity(intent);
                                ConfirmOrderActivity.this.finish();
                            } else {
                                AppConfig.currentTAB = MainActivity.TAB_ACCOUNT;
                                ConfirmOrderActivity.this.finish();
                            }
                        }

                        @Override
                        public void failure(RetrofitError retrofitError) {

                        }
                    });
//                    ToastUtil.show("缴费成功");
                } else {
                    ToastUtil.show("购买失败");
                }
            }
        });
    }

    private void pay_3() {

        AliPay aliPay = new AliPay(ConfirmOrderActivity.this);
        String currentMoney = totalPrice.substring(1);
        aliPay.payV2(true, currentMoney, "购买", orderId, new AliPay.AlipayCallBack() {
            @Override
            public void onSuccess() {
                AppContext.createRequestApi(HomeApi.class).order_info(PrefereUtils.getInstance().getToken(), orderId, new Callback<QuickResponse>() {
                    @Override
                    public void success(QuickResponse quickResponse, Response response) {
                        Log.e("tgh", "token=" + PrefereUtils.getInstance().getToken() + " orderid=" + orderId + "    " + quickResponse.getCode());
                        if (quickResponse.getCode() == 200 && quickResponse.getData().getDetail_infos().size() > 0) {
                            Intent intent = null;
                            if (psType.equals("1")) {
                                intent = new Intent(ConfirmOrderActivity.this, PaySuccessActivity.class);
                            } else {
                                intent = new Intent(ConfirmOrderActivity.this, PaySuccess2Activity.class);
                            }
//                            Intent intent = new Intent(ConfirmOrderActivity.this, PaySuccessActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("detail_Info", quickResponse.getData().getDetail_infos());
                            bundle.putString("pkcode", quickResponse.getData().getPkcode());
                            bundle.putString("qrcode", quickResponse.getData().getQrcode());
                            bundle.putString("orderId", orderId);
                            intent.putExtras(bundle);
                            ConfirmOrderActivity.this.startActivity(intent);
                            ConfirmOrderActivity.this.finish();
                        } else {
                            AppConfig.currentTAB = MainActivity.TAB_ACCOUNT;
                            ConfirmOrderActivity.this.finish();
                        }
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {

                    }
                });
                ToastUtil.show("购买成功");
            }

            @Override
            public void onDeeling() {
                ToastUtil.show("支付完成，后台处理中");
            }

            @Override
            public void onCancle() {
                ToastUtil.show("取消购买");
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.show(msg);
            }
        });
    }

    private void pay_4() {
        String currentMoney = totalPrice.substring(1);
        getTn((int) (Double.valueOf(currentMoney) * 100), orderId, "2");
    }

    private void pay_1() {
        getLoadingDataBar().show();
        CartUtils.isFirstPay(PrefereUtils.getInstance().getToken(), new CartUtils.isFirstPayListener() {
            @Override
            public void load(boolean status, PayResponse info, String message) {
                if (status) {
                    if (info.getPay().getIs_first_pay() == 1) {
                        //支付密码尚未设置
                        showPayKeyBoard(true, tv_zongji.getText().toString().trim(), new OnPasswordInputFinish() {
                            @Override
                            public void inputFinish(String password) {
                                popEnterPassword.dismiss();
                                UserUtils.setPayInfo(PrefereUtils.getInstance().getToken(), password, new UserUtils.setPayListener() {
                                    @Override
                                    public void load(boolean status, UserResponse info, String message) {
                                        if (status) {
                                            //密码设置成功
                                            showPayKeyBoard(false, tv_zongji.getText().toString().trim(), new OnPasswordInputFinish() {
                                                @Override
                                                public void inputFinish(String password) {
                                                    //进行支付操作
                                                    getLoadingDataBar().show();
                                                    String token = PrefereUtils.getInstance().getToken();
                                                    Log.e("tgh", "token=" + token + "orderId=" + orderId + "pwd=" + MD5Utils.toMD5(password));
                                                    CartUtils.quick(token, orderId, password, new CartUtils.quickListener() {
                                                        @Override
                                                        public void load(boolean status, QuickResponse info, String message) {
                                                            if (status) {
                                                                //零钱支付成功
                                                                Intent intent = null;
                                                                if (psType.equals("1")) {
                                                                    intent = new Intent(ConfirmOrderActivity.this, PaySuccessActivity.class);
                                                                } else {
                                                                    intent = new Intent(ConfirmOrderActivity.this, PaySuccess2Activity.class);
                                                                }
                                                                Bundle bundle = new Bundle();
                                                                bundle.putSerializable("detail_Info", info.getData().getDetail_infos());
                                                                bundle.putString("pkcode", info.getData().getPkcode());
                                                                bundle.putString("qrcode", info.getData().getQrcode());
                                                                bundle.putString("orderId", orderId);
                                                                intent.putExtras(bundle);
                                                                ConfirmOrderActivity.this.startActivity(intent);
                                                                ConfirmOrderActivity.this.finish();
                                                            } else {
                                                                if (message.contains("余额不足")) {
                                                                    PopUtil.showDialog(ConfirmOrderActivity.this, "温馨提醒", "您的余额不足，是否前往充值？", "取消", "去充值", null, new View.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(View v) {
                                                                            //充值界面
                                                                            startActivity(new Intent(ConfirmOrderActivity.this, RechargeActivity.class));
                                                                            finish();
                                                                        }
                                                                    });
                                                                } else {
                                                                    ToastUtil.show(message);
                                                                }
                                                            }
                                                            closeProgressBar();
                                                        }
                                                    });

                                                }
                                            });
                                        }
                                    }
                                });
                            }
                        });
                    } else if (info.getPay().getIs_first_pay() == 0) {
                        //支付密码已经设置
                        showPayKeyBoard(false, tv_zongji.getText().toString().trim(), new OnPasswordInputFinish() {
                            @Override
                            public void inputFinish(String password) {
                                //进行支付操作
                                popEnterPassword.dismiss();
                                getLoadingDataBar().show();
//                                String md5pwd=MD5Utils.md5(password.getBytes());
                                Log.e("tgh", "token=" + PrefereUtils.getInstance().getToken() + "orderId=" + orderId + "pwd=" + MD5Utils.toMD5(password));
                                CartUtils.quick(PrefereUtils.getInstance().getToken(), orderId, password, new CartUtils.quickListener() {
                                    @Override
                                    public void load(boolean status, QuickResponse info, String message) {
                                        if (status) {
                                            //零钱支付成功
                                            Intent intent = null;
                                            if (psType.equals("1")) {
                                                intent = new Intent(ConfirmOrderActivity.this, PaySuccessActivity.class);
                                            } else {
                                                intent = new Intent(ConfirmOrderActivity.this, PaySuccess2Activity.class);
                                            }
                                            Bundle bundle = new Bundle();
                                            bundle.putSerializable("detail_Info", info.getData().getDetail_infos());
                                            bundle.putString("pkcode", info.getData().getPkcode());
                                            bundle.putString("qrcode", info.getData().getQrcode());
                                            bundle.putString("orderId", orderId);
                                            intent.putExtras(bundle);
                                            ConfirmOrderActivity.this.startActivity(intent);
                                            ConfirmOrderActivity.this.finish();
                                        } else {

                                            if (message.contains("余额不足")) {
                                                PopUtil.showDialog(ConfirmOrderActivity.this, "温馨提醒", "您的余额不足，是否前往充值？", "取消", "去充值", null, new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        //充值界面
                                                        startActivity(new Intent(ConfirmOrderActivity.this, RechargeActivity.class));
                                                        finish();
                                                    }
                                                });
                                            } else {
                                                ToastUtil.show(message);
                                            }
                                        }
                                        closeProgressBar();
                                    }
                                });


                            }
                        });
                    }
                } else {
                    ToastUtil.show(message);
                }
                closeProgressBar();
            }
        });
    }

    PopEnterPassword popEnterPassword;

    public void showPayKeyBoard(boolean isFirstPay, String amount, OnPasswordInputFinish listener) {
        popEnterPassword = new PopEnterPassword(this, isFirstPay, amount, listener);
        // 显示窗口
        popEnterPassword.showAtLocation(this.findViewById(R.id.layoutContent),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置

    }

    private void changeIB(int id) {
        switch (id) {
            case R.id.rl_lingqian:

//                tv_zongji.setText("总计:¥"+String.format("%.2f", (Double.parseDouble(totalPrice.substring(1)))*75/100));
                tv_zongji.setText("总计:¥" + Double.parseDouble(totalPrice.substring(1)));
                ib_lingqian.setBackgroundResource(R.mipmap.icon_fuxuankuan_xuanzhong);
                ib_zhifubao.setBackgroundResource(R.mipmap.icon_fuxuankuan_weixuan);
                ib_weixin.setBackgroundResource(R.mipmap.icon_fuxuankuan_weixuan);
                ib_yinlian.setBackgroundResource(R.mipmap.icon_fuxuankuan_weixuan);
                break;
            case R.id.rl_zhifubao:
                tv_zongji.setText("总计:¥" + (Double.parseDouble(totalPrice.substring(1))));
                ib_lingqian.setBackgroundResource(R.mipmap.icon_fuxuankuan_weixuan);
                ib_zhifubao.setBackgroundResource(R.mipmap.icon_fuxuankuan_xuanzhong);
                ib_weixin.setBackgroundResource(R.mipmap.icon_fuxuankuan_weixuan);
                ib_yinlian.setBackgroundResource(R.mipmap.icon_fuxuankuan_weixuan);
                break;
            case R.id.rl_weixin:
                tv_zongji.setText("总计:¥" + (Double.parseDouble(totalPrice.substring(1))));
                ib_lingqian.setBackgroundResource(R.mipmap.icon_fuxuankuan_weixuan);
                ib_zhifubao.setBackgroundResource(R.mipmap.icon_fuxuankuan_weixuan);
                ib_weixin.setBackgroundResource(R.mipmap.icon_fuxuankuan_xuanzhong);
                ib_yinlian.setBackgroundResource(R.mipmap.icon_fuxuankuan_weixuan);
                break;
            case R.id.rl_yinlian:
                tv_zongji.setText("总计:¥" + (Double.parseDouble(totalPrice.substring(1))));
                ib_lingqian.setBackgroundResource(R.mipmap.icon_fuxuankuan_weixuan);
                ib_zhifubao.setBackgroundResource(R.mipmap.icon_fuxuankuan_weixuan);
                ib_weixin.setBackgroundResource(R.mipmap.icon_fuxuankuan_weixuan);
                ib_yinlian.setBackgroundResource(R.mipmap.icon_fuxuankuan_xuanzhong);
                break;
        }
    }

    /**
     * 获取送货地址
     */
    private void getReceiveAddrs() {
        RetrofitClient.getInstance().createApi().getReceiveAddrs(BaseApp.token())
                .compose(RxUtils.<HttpArray<MyAdressBean>>io_main())
                .subscribe(new BaseListObserver<MyAdressBean>(this) {
                    @Override
                    protected void onHandleSuccess(List<MyAdressBean> list) {
                        if (list != null && list.size() > 0) {
                            llConfirmOrderAdress.setVisibility(View.VISIBLE);
                            tvConfirmOrderAdd.setVisibility(View.GONE);
                            adressBean = list.get(0);
                            setAdressData(adressBean);
                        } else {
                            llConfirmOrderAdress.setVisibility(View.GONE);
                            tvConfirmOrderAdd.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    /**
     * 设置收货地址的信息
     */
    private void setAdressData(MyAdressBean bean) {
        tvConfirmOrderPhone.setText(bean.getMobile());
        tvConfirmOrderPeople.setText("收货人：" + bean.getRealname());
        String adress = bean.getProvince() + bean.getCity() + bean.getArea() + bean.getAddress();
        tvConfirmOrderAdress.setText(adress);

        String id = bean.getId();//收货地址ID
        Log.e("gy", "收货地址🆔：" + id);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {//地址
            llConfirmOrderAdress.setVisibility(View.VISIBLE);
            tvConfirmOrderAdd.setVisibility(View.GONE);
            adressBean = (MyAdressBean) data.getSerializableExtra("data");
            setAdressData(adressBean);
        }

        if (resultCode == 110) {//配送方式
            psType = data.getStringExtra("type");
            ps_time = data.getStringExtra("ps_time");
            send_type = data.getStringExtra("send_type");
            if (psType.equals("1")) {
                tvConfirmOrderType.setText("自取");
            } else if (psType.equals("2")) {
                tvConfirmOrderType.setText("配送");
            }
        }
    }
}
