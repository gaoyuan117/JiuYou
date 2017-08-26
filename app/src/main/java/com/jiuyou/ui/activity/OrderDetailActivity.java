package com.jiuyou.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jiuyou.R;
import com.jiuyou.alipay.AliPay;
import com.jiuyou.core.AppContext;
import com.jiuyou.customctrls.widget.OnPasswordInputFinish;
import com.jiuyou.customctrls.widget.PopEnterPassword;
import com.jiuyou.global.AppConfig;
import com.jiuyou.global.BaseApp;
import com.jiuyou.global.MyListView;
import com.jiuyou.model.CommonBean;
import com.jiuyou.model.OrderInfoBean;
import com.jiuyou.model.OrderInfoItemAdapter;
import com.jiuyou.model.ToPayBean;
import com.jiuyou.network.interfaces.HomeApi;
import com.jiuyou.network.response.JZBResponse.PayResponse;
import com.jiuyou.network.response.JZBResponse.QuickResponse;
import com.jiuyou.network.response.JZBResponse.UserResponse;
import com.jiuyou.overlayutil.DrivingRouteOverlay;
import com.jiuyou.retrofit.BaseObjObserver;
import com.jiuyou.retrofit.HttpResult;
import com.jiuyou.retrofit.RetrofitClient;
import com.jiuyou.retrofit.RxUtils;
import com.jiuyou.ui.Utils.CartUtils;
import com.jiuyou.ui.Utils.UserUtils;
import com.jiuyou.ui.activity.saomiao.CaptureActivity;
import com.jiuyou.ui.base.BaseActivity;
import com.jiuyou.util.MD5Utils;
import com.jiuyou.util.PopUtil;
import com.jiuyou.util.PrefereUtils;
import com.jiuyou.util.ToastUtil;
import com.jiuyou.wxpay.WxPay;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class OrderDetailActivity extends BaseActivity {

    @Bind(R.id.tv_order_detail_status)
    TextView mTvDetailStatus;
    @Bind(R.id.tv_order_detail_people)
    TextView mTvDetailPeople;
    @Bind(R.id.tv_order_detail_phone)
    TextView mTvDetailPhone;
    @Bind(R.id.tv_order_detail_adress)
    TextView mTvDetailAdress;
    @Bind(R.id.lv_order_detail)
    MyListView mListView;
    @Bind(R.id.textView2)
    TextView mTvDetailPrice;
    @Bind(R.id.tv_order_detail_no)
    TextView mTvDetailNo;
    @Bind(R.id.tv_order_detail_time)
    TextView mTvDetailTime;
    @Bind(R.id.tv_order_detail_type)
    TextView mTvDetailType;
    @Bind(R.id.tv_order_detail_pstime)
    TextView mTvDetailPstime;
    @Bind(R.id.tv_order_detail_chat)
    TextView mTvDetailChat;
    @Bind(R.id.tv_order_detail_quxiao)
    TextView mTvDetailQuxiao;
    @Bind(R.id.tv_order_detail_zhifu)
    TextView mTvDetailZhifu;
    @Bind(R.id.scrollView)
    ScrollView scrollView;
    @Bind(R.id.ll_order_detail_ps)
    LinearLayout llPsZhong;
    @Bind(R.id.ll_order_detail_daizhifu)
    LinearLayout llDaiZhiFu;
    @Bind(R.id.tv_order_detail_erweima)
    ImageView imgErWeiMa;
    @Bind(R.id.ll_order_detail_ziqu)
    LinearLayout linearLayout;
    @Bind(R.id.ll_order_detail_yanzheng)
    LinearLayout llYanzheng;
    @Bind(R.id.tv_order_detail_tuikuan)
    TextView mTvDetailTuiKuan;
    @Bind(R.id.tv_order_detail_pingjia)
    TextView mTvDetailPingJia;
    @Bind(R.id.tv_order_detail_jujue)
    TextView mTvDetailJuJue;
    @Bind(R.id.tv_order_detail_quere)
    TextView mTvDetailQueRen;
    @Bind(R.id.tv_order_detail_nearby)
    TextView mTvDetailFujin;

    private MapView mMapView;
    private BaiduMap baiduMap;
    private RoutePlanSearch routePlanSearch;

    private String type;//订单类型
    private String psType;//取货方式
    private String id;//订单id
    private int positon;
    private OrderInfoBean orderInfoBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        mMapView = (MapView) findViewById(R.id.bmapView);
        baiduMap = mMapView.getMap();
        setCenterTitle("订单详情");
        type = getIntent().getStringExtra("type");
        psType = getIntent().getStringExtra("psType");
        positon = getIntent().getIntExtra("position", -1);
        id = getIntent().getStringExtra("id");
        Log.e("gy", "订单ID：" + id);
        Log.e("gy", "订单type：" + type);
        Log.e("gy", "position：" + positon);
        initView();
        initEvent();
        getOrderInfo();

        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("position", positon);
                intent.putExtra("status", type);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void initView() {
        llDaiZhiFu.setVisibility(View.GONE);
        llPsZhong.setVisibility(View.GONE);
        linearLayout.setVisibility(View.GONE);
        mTvDetailTuiKuan.setVisibility(View.GONE);
        llYanzheng.setVisibility(View.GONE);
        mTvDetailQueRen.setVisibility(View.GONE);
        mTvDetailJuJue.setVisibility(View.GONE);
        mTvDetailPingJia.setVisibility(View.GONE);
        mTvDetailFujin.setVisibility(View.GONE);
        imgErWeiMa.setVisibility(View.GONE);

        if (type.equals("0")) {//待支付
            mTvDetailStatus.setText("待支付");
            llDaiZhiFu.setVisibility(View.VISIBLE);
        }
        if (type.equals("2")) {//退款申请中
            mTvDetailStatus.setText("退款申请中...");
        }
        if (type.equals("3")) {//已退款
            mTvDetailStatus.setText("已退款");
        }
        if (type.equals("4")) {//取消订单
            mTvDetailStatus.setText("已取消");
        }
        if (type.equals("7")) {//配送中
            llPsZhong.setVisibility(View.VISIBLE);
            try {
                search();//显示地图
            } catch (Exception e) {

            }
            mTvDetailStatus.setText("配送中");
        }

        if (psType.equals("配送")) {//配送
            if (type.equals("1")) {//已支付，商家还未接单
                mTvDetailStatus.setText("已支付，商家还未接单");
                linearLayout.setVisibility(View.VISIBLE);
                mTvDetailTuiKuan.setVisibility(View.VISIBLE);
            } else if (type.equals("5")) {
                mTvDetailStatus.setText("等待配送员接单");
            } else if (type.equals("8")) {
                mTvDetailStatus.setText("已完成");
                linearLayout.setVisibility(View.VISIBLE);
                llYanzheng.setVisibility(View.VISIBLE);
                mTvDetailQueRen.setVisibility(View.VISIBLE);
                mTvDetailJuJue.setVisibility(View.VISIBLE);
            } else if (type.equals("9")) {
                mTvDetailStatus.setText("已完成");
                linearLayout.setVisibility(View.VISIBLE);
                mTvDetailPingJia.setVisibility(View.VISIBLE);
            } else if (type.equals("10")) {
                mTvDetailStatus.setText("拒绝收货");
            } else if (type.equals("11")) {
                mTvDetailStatus.setText("已评价");
            }

        } else if (psType.equals("自取")) {//自取

            if (type.equals("1")) {//已支付，还未取货
                mTvDetailStatus.setText("已支付，请及时取货!");
                mTvDetailFujin.setVisibility(View.VISIBLE);
                mTvDetailTuiKuan.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.VISIBLE);
                imgErWeiMa.setVisibility(View.VISIBLE);
            } else if (type.equals("6")) {
                mTvDetailStatus.setText("商家已扫码确认");
                linearLayout.setVisibility(View.VISIBLE);
                llYanzheng.setVisibility(View.VISIBLE);
                mTvDetailQueRen.setVisibility(View.VISIBLE);
                mTvDetailJuJue.setVisibility(View.VISIBLE);
                imgErWeiMa.setVisibility(View.VISIBLE);
            } else if (type.equals("9")) {
                mTvDetailStatus.setText("已取货");
                linearLayout.setVisibility(View.VISIBLE);
                mTvDetailFujin.setVisibility(View.VISIBLE);
                mTvDetailPingJia.setVisibility(View.VISIBLE);
                imgErWeiMa.setVisibility(View.VISIBLE);
            } else if (type.equals("10")) {
                mTvDetailStatus.setText("拒绝收货");
                linearLayout.setVisibility(View.VISIBLE);
                mTvDetailFujin.setVisibility(View.VISIBLE);
                mTvDetailTuiKuan.setVisibility(View.INVISIBLE);
                imgErWeiMa.setVisibility(View.VISIBLE);
            } else if (type.equals("11")) {
                mTvDetailStatus.setText("已完成");
                linearLayout.setVisibility(View.VISIBLE);
                mTvDetailFujin.setVisibility(View.VISIBLE);
                mTvDetailTuiKuan.setVisibility(View.INVISIBLE);
                imgErWeiMa.setVisibility(View.VISIBLE);
            }
        }

    }

    private void initEvent() {
        mMapView.getChildAt(0).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        mMapView.requestDisallowInterceptTouchEvent(true);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        mMapView.requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });


    }

    @OnClick({R.id.tv_order_detail_chat, R.id.tv_order_detail_quxiao, R.id.tv_order_detail_zhifu, R.id.tv_order_detail_tuikuan,R.id.tv_order_detail_pingjia,R.id.tv_order_detail_quere,R.id.tv_order_detail_jujue,R.id.tv_order_detail_yanzheng})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.tv_order_detail_quxiao://取消订单
                cancleOrder();
                break;

            case R.id.tv_order_detail_zhifu://去付款
                toPay();
                break;

            case R.id.tv_order_detail_tuikuan://退款
                toReturnMoney();
                break;

            case R.id.tv_order_detail_pingjia://评价
                toEvaluation();
                break;

            case R.id.tv_order_detail_quere://确认收货
                confirmReceipt();
                break;

            case R.id.tv_order_detail_jujue://拒绝收货
                toRefuse();
                break;

            case R.id.tv_order_detail_yanzheng://验证
                startActivity(new Intent(this, CaptureActivity.class));
                break;
        }
    }

    /**
     * 获取订单详情
     */
    private void getOrderInfo() {
        RetrofitClient.getInstance().createApi().orderInfo(BaseApp.token(), id)
                .compose(RxUtils.<HttpResult<OrderInfoBean>>io_main())
                .subscribe(new BaseObjObserver<OrderInfoBean>(this, "获取中") {

                    @Override
                    protected void onHandleSuccess(OrderInfoBean bean) {
                        if (bean == null) {
                            return;
                        }
                        orderInfoBean = bean;
                        psType = bean.getPickup_mode();
                        setOrderInfo(bean);
                    }
                });
    }

    /**
     * 设置订单信息
     *
     * @param bean
     */
    private void setOrderInfo(OrderInfoBean bean) {
        mTvDetailAdress.setText("收货地址：" + bean.getReceive().getAddress());
        mTvDetailPeople.setText("收货人：" + bean.getReceive().getRealname());
        mTvDetailPhone.setText(bean.getReceive().getMobile());
        OrderInfoItemAdapter adapter = new OrderInfoItemAdapter(OrderDetailActivity.this, bean.getDetail_Info());
        mListView.setAdapter(adapter);
        mTvDetailNo.setText("订单号：" + bean.getDetail_Info().get(0).getOrder_no());
        mTvDetailTime.setText("创建时间：" + bean.getCreate_time());
        mTvDetailPrice.setText("总计：¥ " + bean.getTotal_price());
        mTvDetailType.setText("取货方式：" + psType);
        if (psType.equals("自取")) {
            mTvDetailPstime.setVisibility(View.GONE);
        } else {
            mTvDetailPstime.setVisibility(View.VISIBLE);
            mTvDetailPstime.setText("配送时间：" + bean.getSend_time());
        }

        Glide.with(this).load(AppConfig.ENDPOINTPIC + bean.getQrcode()).diskCacheStrategy(DiskCacheStrategy.ALL).error(R.mipmap.icon_nopic).into(imgErWeiMa);
    }

    /**
     * 取消订单
     */
    public void cancleOrder() {
        RetrofitClient.getInstance().createApi().cancelOrder(BaseApp.token(), id)
                .compose(RxUtils.<HttpResult<CommonBean>>io_main())
                .subscribe(new BaseObjObserver<CommonBean>(this, "取消中") {
                    @Override
                    protected void onHandleSuccess(CommonBean commonBean) {
                        type = "4";
                        initView();
                    }
                });
    }

    /**
     * 前往退款页面
     */
    public void toReturnMoney() {
        Intent intent = new Intent(this, ReturnMoneyActivity.class);
        intent.putExtra("data2", orderInfoBean);
        startActivityForResult(intent, 111);
    }

    /**
     * 评价
     */
    public void toEvaluation() {
        Intent intent = new Intent(this, EvaluateActivity.class);
        intent.putExtra("order_id", orderInfoBean.getDetail_Info().get(0).getOrder_id());
        startActivityForResult(intent, 110);
    }

    /**
     * 确认收货
     */
    public void confirmReceipt() {
        RetrofitClient.getInstance().createApi().confirmReceipt(BaseApp.token(), orderInfoBean.getDetail_Info().get(0).getOrder_id())
                .compose(RxUtils.<HttpResult<CommonBean>>io_main())
                .subscribe(new BaseObjObserver<CommonBean>(this, "确认收货中") {
                    @Override
                    protected void onHandleSuccess(CommonBean commonBean) {
                        Toast.makeText(OrderDetailActivity.this, "确认收货成功", Toast.LENGTH_SHORT).show();
                        type = "9";
                        initView();
                    }
                });
    }

    /**
     * 前往拒绝收货页面
     */
    public void toRefuse() {
        Intent intent = new Intent(this, RefuseActivity.class);
        intent.putExtra("orderNo", orderInfoBean.getDetail_Info().get(0).getOrder_no());
        intent.putExtra("orderId", orderInfoBean.getDetail_Info().get(0).getOrder_id());
        startActivityForResult(intent, 109);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == 111) {//退款
            type = "2";
            initView();
        }

        if (requestCode == 110) {//评价
            type = "11";
            initView();
        }

        if (requestCode == 109) {//拒绝收货
            type = "10";
            initView();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            Intent intent = new Intent();
            intent.putExtra("position", positon);
            intent.putExtra("status", type);
            setResult(RESULT_OK, intent);
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 立即支付
     */
    public void toPay() {
        String pay_type = orderInfoBean.getPay_type();
        if (pay_type.equals("微信")) {
            pay_type = "1";
        } else if (pay_type.equals("支付宝")) {
            pay_type = "2";
        } else if (pay_type.equals("余额")) {
            pay_type = "3";
        }
        RetrofitClient.getInstance().createApi().toPay(BaseApp.token(), orderInfoBean.getDetail_Info().get(0).getOrder_id(), pay_type)
                .compose(RxUtils.<HttpResult<ToPayBean>>io_main())
                .subscribe(new BaseObjObserver<ToPayBean>(this, "请稍后") {
                    @Override
                    protected void onHandleSuccess(ToPayBean toPayBean) {
                        String pickup_mode = orderInfoBean.getPickup_mode();
                        String psType = "";
                        if (pickup_mode.equals("自取")) {
                            psType = "1";
                        } else {
                            psType = "2";
                        }
                        String pay_type = orderInfoBean.getPay_type();
                        if (pay_type.equals("微信")) {
                            wxPay(orderInfoBean.getTotal_price(), psType, toPayBean.getOrder_no());
                        } else if (pay_type.equals("支付宝")) {
                            aliPay(orderInfoBean.getTotal_price(), psType, toPayBean.getOrder_no());

                        } else if (pay_type.equals("余额")) {
                            yue(orderInfoBean.getTotal_price(), psType, toPayBean.getOrder_no());
                        }
                    }
                });
    }

    private void wxPay(String currentMoney, final String psType, final String orderId) {
        WxPay.getWxPay().pay(OrderDetailActivity.this, orderId, "jiaofei", currentMoney, "http://cupboard.jzbwlkj.com/index.php/api/Notify/prodWx", new WxPay.WxCallBack() {
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
                                    intent = new Intent(OrderDetailActivity.this, PaySuccessActivity.class);
                                } else {
                                    intent = new Intent(OrderDetailActivity.this, PaySuccess2Activity.class);
                                }
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("detail_Info", quickResponse.getData().getDetail_infos());
                                bundle.putString("pkcode", quickResponse.getData().getPkcode());
                                bundle.putString("qrcode", quickResponse.getData().getQrcode());
                                bundle.putString("orderId", orderId);
                                intent.putExtras(bundle);
                                type = "1";
                                initView();
                                OrderDetailActivity.this.startActivity(intent);
                            }
                        }

                        @Override
                        public void failure(RetrofitError retrofitError) {

                        }
                    });
                    ToastUtil.show("缴费成功");
                } else {
                    ToastUtil.show("缴费失败");
                }
            }
        });
    }

    private void aliPay(String currentMoney, final String psType, final String orderId) {

        AliPay aliPay = new AliPay(this);
        aliPay.payV2(true, currentMoney, "缴费", orderId, new AliPay.AlipayCallBack() {
            @Override
            public void onSuccess() {
                AppContext.createRequestApi(HomeApi.class).order_info(PrefereUtils.getInstance().getToken(), orderId, new Callback<QuickResponse>() {
                    @Override
                    public void success(QuickResponse quickResponse, Response response) {
                        Log.e("tgh", "token=" + PrefereUtils.getInstance().getToken() + " orderid=" + orderId);
                        if (quickResponse.getCode() == 200 && quickResponse.getData().getDetail_infos().size() > 0) {
                            Intent intent = null;
                            if (psType.equals("1")) {
                                intent = new Intent(OrderDetailActivity.this, PaySuccessActivity.class);
                            } else {
                                intent = new Intent(OrderDetailActivity.this, PaySuccess2Activity.class);
                            }
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("detail_Info", quickResponse.getData().getDetail_infos());
                            bundle.putString("pkcode", quickResponse.getData().getPkcode());
                            bundle.putString("qrcode", quickResponse.getData().getQrcode());
                            bundle.putString("orderId", orderId);
                            intent.putExtras(bundle);
                            type = "1";//状态更改为已支付
                            initView();
                            OrderDetailActivity.this.startActivity(intent);

                        }
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {

                    }
                });
                ToastUtil.show("缴费成功");
            }

            @Override
            public void onDeeling() {
                ToastUtil.show("支付完成，后台处理中");
            }

            @Override
            public void onCancle() {
                ToastUtil.show("取消缴费");
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.show(msg);
            }
        });
    }

    private void yue(final String currentMoney, final String psType, final String orderId) {
        getLoadingDataBar().show();
        CartUtils.isFirstPay(PrefereUtils.getInstance().getToken(), new CartUtils.isFirstPayListener() {
            @Override
            public void load(boolean status, PayResponse info, String message) {
                if (status) {
                    if (info.getPay().getIs_first_pay() == 1) {
                        //支付密码尚未设置
                        showPayKeyBoard(true, currentMoney, new OnPasswordInputFinish() {
                            @Override
                            public void inputFinish(String password) {
                                popEnterPassword.dismiss();
                                UserUtils.setPayInfo(PrefereUtils.getInstance().getToken(), password, new UserUtils.setPayListener() {
                                    @Override
                                    public void load(boolean status, UserResponse info, String message) {
                                        if (status) {
                                            //密码设置成功
                                            showPayKeyBoard(false, currentMoney, new OnPasswordInputFinish() {
                                                @Override
                                                public void inputFinish(String password) {
                                                    //进行支付操作
                                                    getLoadingDataBar().show();
                                                    String token = PrefereUtils.getInstance().getToken();
                                                    Log.e("tgh", "token=" + token + "orderId=" + orderId + "pwd=" + MD5Utils.md5(password.getBytes()));
                                                    CartUtils.quick(token, orderId, password, new CartUtils.quickListener() {
                                                        @Override
                                                        public void load(boolean status, QuickResponse info, String message) {
                                                            if (status) {
                                                                //零钱支付成功
                                                                Intent intent = null;
                                                                if (psType.equals("1")) {
                                                                    intent = new Intent(OrderDetailActivity.this, PaySuccessActivity.class);
                                                                } else {
                                                                    intent = new Intent(OrderDetailActivity.this, PaySuccess2Activity.class);
                                                                }
                                                                Bundle bundle = new Bundle();
                                                                bundle.putSerializable("detail_Info", info.getData().getDetail_infos());
                                                                bundle.putString("pkcode", info.getData().getPkcode());
                                                                bundle.putString("qrcode", info.getData().getQrcode());
                                                                bundle.putString("orderId", orderId);
                                                                intent.putExtras(bundle);
                                                                OrderDetailActivity.this.startActivity(intent);
                                                                type = "1";//状态更改为已支付
                                                                initView();
                                                            } else {
                                                                if (message.contains("余额不足")) {
                                                                    PopUtil.showDialog(OrderDetailActivity.this, "温馨提醒", "您的余额不足，是否前往充值？", "取消", "去充值", null, new View.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(View v) {
                                                                            //充值界面
                                                                            startActivity(new Intent(OrderDetailActivity.this, RechargeActivity.class));
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
                        showPayKeyBoard(false, currentMoney, new OnPasswordInputFinish() {
                            @Override
                            public void inputFinish(String password) {
                                //进行支付操作
                                popEnterPassword.dismiss();
                                getLoadingDataBar().show();
                                Log.e("tgh", "token=" + PrefereUtils.getInstance().getToken() + "orderId=" + orderId + "pwd=" + MD5Utils.md5(password.getBytes()));
                                CartUtils.quick(PrefereUtils.getInstance().getToken(), orderId, password, new CartUtils.quickListener() {
                                    @Override
                                    public void load(boolean status, QuickResponse info, String message) {
                                        if (status) {
                                            //零钱支付成功
                                            Intent intent = null;
                                            if (psType.equals("1")) {
                                                intent = new Intent(OrderDetailActivity.this, PaySuccessActivity.class);
                                            } else {
                                                intent = new Intent(OrderDetailActivity.this, PaySuccess2Activity.class);
                                            }
                                            Bundle bundle = new Bundle();
                                            bundle.putSerializable("detail_Info", info.getData().getDetail_infos());
                                            bundle.putString("pkcode", info.getData().getPkcode());
                                            bundle.putString("qrcode", info.getData().getQrcode());
                                            bundle.putString("orderId", orderId);
                                            intent.putExtras(bundle);
                                            type = "1";//状态更改为已支付
                                            initView();
                                            OrderDetailActivity.this.startActivity(intent);
                                        } else {

                                            if (message.contains("余额不足")) {
                                                PopUtil.showDialog(OrderDetailActivity.this, "温馨提醒", "您的余额不足，是否前往充值？", "取消", "去充值", null, new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        //充值界面
                                                        startActivity(new Intent(OrderDetailActivity.this, RechargeActivity.class));
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
        popEnterPassword = new PopEnterPassword(this, isFirstPay, amount, "", listener);
        // 显示窗口
        popEnterPassword.showAtLocation(this.findViewById(R.id.layoutContent),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置

    }

    /**
     * 配送中路线
     */
    private void search() {
        routePlanSearch = RoutePlanSearch.newInstance();
        DrivingRoutePlanOption drivingOption = new DrivingRoutePlanOption();
        PlanNode from = PlanNode.withLocation(new LatLng(40.065796, 116.449868));//创建起点
        PlanNode to = PlanNode.withLocation(new LatLng(40.065796, 116.349868));//创建终点
        drivingOption.from(from);//设置起点
        drivingOption.to(to);//设置终点

        List<PlanNode> nodes = new ArrayList<PlanNode>();
        nodes.add(PlanNode.withCityNameAndPlaceName("北京", "天安门"));
        drivingOption.passBy(nodes);
        drivingOption.policy(DrivingRoutePlanOption.DrivingPolicy.ECAR_DIS_FIRST);//设置策略，驾乘检索策略常量：最短距离

        routePlanSearch.drivingSearch(drivingOption);

        routePlanSearch.setOnGetRoutePlanResultListener(new OnGetRoutePlanResultListener() {

            @Override
            public void onGetWalkingRouteResult(WalkingRouteResult arg0) {

            }

            @Override
            public void onGetTransitRouteResult(TransitRouteResult arg0) {

            }

            @Override
            public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

            }

            // 自驾搜索结果回调
            @Override
            public void onGetDrivingRouteResult(DrivingRouteResult result) {
                if (result == null
                        || SearchResult.ERRORNO.RESULT_NOT_FOUND == result.error) {
                    Toast.makeText(getApplicationContext(), "未搜索到结果", Toast.LENGTH_SHORT).show();
                    return;
                }

                DrivingRouteOverlay drivingOverlay = new MyDrivingRouteOverlay(baiduMap);
                baiduMap.setOnMarkerClickListener(drivingOverlay);
                drivingOverlay.setData(result.getRouteLines().get(0));//设置线路为搜索结果的第一条
                drivingOverlay.addToMap();
                drivingOverlay.zoomToSpan();
            }

            @Override
            public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

            }

            @Override
            public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

            }
        });
    }

    //定制RouteOverly
    private class MyDrivingRouteOverlay extends DrivingRouteOverlay {

        public MyDrivingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public int getLineColor() {
            // TODO Auto-generated method stub
            //覆写此方法以改变默认绘制颜色
            //Returns:
            //线颜色
            return Color.parseColor("#59b9f5");
        }

    }

}
