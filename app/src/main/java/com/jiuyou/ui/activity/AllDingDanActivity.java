package com.jiuyou.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.jiuyou.R;
import com.jiuyou.alipay.AliPay;
import com.jiuyou.core.AppContext;
import com.jiuyou.customctrls.pager.PullToRefreshBase;
import com.jiuyou.customctrls.pager.PullToRefreshListView;
import com.jiuyou.customctrls.pager.RotateLoadingLayout;
import com.jiuyou.customctrls.widget.OnPasswordInputFinish;
import com.jiuyou.customctrls.widget.PopEnterPassword;
import com.jiuyou.global.AppConfig;
import com.jiuyou.global.BaseApp;
import com.jiuyou.model.CommonBean;
import com.jiuyou.model.OrderBean;
import com.jiuyou.model.ToPayBean;
import com.jiuyou.network.interfaces.HomeApi;
import com.jiuyou.network.response.JZBResponse.PayResponse;
import com.jiuyou.network.response.JZBResponse.QuickResponse;
import com.jiuyou.network.response.JZBResponse.UserResponse;
import com.jiuyou.retrofit.BaseListObserver;
import com.jiuyou.retrofit.BaseObjObserver;
import com.jiuyou.retrofit.HttpArray;
import com.jiuyou.retrofit.HttpResult;
import com.jiuyou.retrofit.RetrofitClient;
import com.jiuyou.retrofit.RxUtils;
import com.jiuyou.ui.Utils.CartUtils;
import com.jiuyou.ui.Utils.UserUtils;
import com.jiuyou.ui.adapter.AllDingDanAdapter;
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
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AllDingDanActivity extends BaseActivity implements AdapterView.OnItemClickListener, PullToRefreshBase.OnRefreshListener2 {
    @Bind(R.id.comment_listview)
    PullToRefreshListView mListView;

    //当前页
    private AllDingDanAdapter adapter;
    private List<OrderBean> mList;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_ding_dan);
        ButterKnife.bind(this);
        type = getIntent().getStringExtra("type");
        if (type.equals("99")) {
            setCenterTitle("全部订单");
        } else if (type.equals("0")) {
            setCenterTitle("待支付");
        } else if (type.equals("1")) {
            setCenterTitle("已支付");
        } else if (type.equals("7")) {
            setCenterTitle("配送中");
        } else if (type.equals("2")) {
            setCenterTitle("退款");
        } else if (type.equals("9")) {
            setCenterTitle("已完成");
        }
        initView();
        initDatas();
        mListView.setOnItemClickListener(this);

        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConfig.currentTAB = MainActivity.TAB_MINE;
                finish();
            }
        });
    }

    private void initView() {
        mList = new ArrayList<>();
        mListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        mListView.setHeaderLayout(new RotateLoadingLayout(this, PullToRefreshBase.Mode.PULL_FROM_START, mListView.getPullToRefreshScrollDirection()));
        mListView.setOnRefreshListener(this);
        mListView.setOnItemClickListener(this);
        adapter = new AllDingDanAdapter(this, mList);
        mListView.setAdapter(adapter);

    }

    private void initDatas() {
        getMyOrders();
    }

    /**
     * 获取订单
     */
    private void getMyOrders() {
        RetrofitClient.getInstance().createApi().getMyOrders(BaseApp.token(), type)
                .compose(RxUtils.<HttpArray<OrderBean>>io_main())
                .subscribe(new BaseListObserver<OrderBean>(this, "获取中") {
                    @Override
                    protected void onHandleSuccess(List<OrderBean> list) {
                        mListView.onRefreshComplete(0);
                        if (list == null || list.size() <= 0) {
                            return;
                        }
                        mList.clear();
                        mList.addAll(list);
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        getMyOrders();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        OrderBean orderBean = mList.get(position - 1);
        Intent intent = new Intent(this, OrderDetailActivity.class);
        intent.putExtra("type", orderBean.getStatus());
        intent.putExtra("psType", orderBean.getPickup_mode());
        intent.putExtra("id", orderBean.getId());
        intent.putExtra("position", position - 1);
        startActivityForResult(intent, 110);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            this.finish();
            AppConfig.currentTAB = MainActivity.TAB_MINE;
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == 111) {//退款
            int position = data.getIntExtra("position", -1);
            if (position == -1) {
                return;
            }
            mList.get(position).setStatus("2");
            adapter.notifyDataSetChanged();
        }

        if (requestCode == 110) {//根据详情页返回的数据 刷新列表
            int position = data.getIntExtra("position", -1);
            String status = data.getStringExtra("status");
            if (position == -1) {
                return;
            }
            mList.get(position).setStatus(status);
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 前往退款页面
     */
    public void toReturnMoney(int position) {
        Intent intent = new Intent(this, ReturnMoneyActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("data", mList.get(position));
        startActivityForResult(intent, 111);
    }

    /**
     * 前往拒绝收货页面
     */
    public void toRefuse(int position) {
        Intent intent = new Intent(this, RefuseActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("orderNo", mList.get(position).getOrder_no());
        intent.putExtra("orderId", mList.get(position).getId());
        startActivityForResult(intent, 110);
    }

    /**
     * 取消订单
     */
    public void cancleOrder(final int posotion) {
        RetrofitClient.getInstance().createApi().cancelOrder(BaseApp.token(), mList.get(posotion).getId())
                .compose(RxUtils.<HttpResult<CommonBean>>io_main())
                .subscribe(new BaseObjObserver<CommonBean>(this, "取消中") {
                    @Override
                    protected void onHandleSuccess(CommonBean commonBean) {
                        mList.get(posotion).setStatus("4");
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    /**
     * 评价
     */
    public void toEvaluation(final int posotion) {
        Intent intent = new Intent(this, EvaluateActivity.class);
        intent.putExtra("position", posotion);
        intent.putExtra("order_id", mList.get(posotion).getId());
        startActivityForResult(intent, 110);
    }

    /**
     * 确认收货
     */
    public void confirmReceipt(final int posotion) {
        RetrofitClient.getInstance().createApi().confirmReceipt(BaseApp.token(), mList.get(posotion).getId())
                .compose(RxUtils.<HttpResult<CommonBean>>io_main())
                .subscribe(new BaseObjObserver<CommonBean>(this, "确认收货中") {
                    @Override
                    protected void onHandleSuccess(CommonBean commonBean) {
                        Toast.makeText(AllDingDanActivity.this, "确认收货成功", Toast.LENGTH_SHORT).show();
                        mList.get(posotion).setStatus("9");
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    /**
     * 立即支付
     *
     * @param position
     */
    public void toPay(final int position) {
        RetrofitClient.getInstance().createApi().toPay(BaseApp.token(), mList.get(position).getId(), mList.get(position).getPay_type())
                .compose(RxUtils.<HttpResult<ToPayBean>>io_main())
                .subscribe(new BaseObjObserver<ToPayBean>(this, "请稍后") {
                    @Override
                    protected void onHandleSuccess(ToPayBean toPayBean) {
                        String pickup_mode = mList.get(position).getPickup_mode();
                        String psType = "";
                        if (pickup_mode.equals("自取")) {
                            psType = "1";
                        } else {
                            psType = "2";
                        }
                        String pay_type = mList.get(position).getPay_type();
                        if (pay_type.equals("1")) {
                            wxPay(position, mList.get(position).getTotal_price(), psType, toPayBean.getOrder_no());
                        } else if (pay_type.equals("2")) {
                            aliPay(position, mList.get(position).getTotal_price(), psType, toPayBean.getOrder_no());

                        } else if (pay_type.equals("3")) {
                            yue(position, mList.get(position).getTotal_price(), psType, toPayBean.getOrder_no());
                        }
                    }
                });
    }


    private void wxPay(final int position, String currentMoney, final String psType, final String orderId) {
        WxPay.getWxPay().pay(AllDingDanActivity.this, orderId, "jiaofei", currentMoney, "http://cupboard.jzbwlkj.com/index.php/api/Notify/prodWx", new WxPay.WxCallBack() {
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
                                    intent = new Intent(AllDingDanActivity.this, PaySuccessActivity.class);
                                } else {
                                    intent = new Intent(AllDingDanActivity.this, PaySuccess2Activity.class);
                                }
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("detail_Info", quickResponse.getData().getDetail_infos());
                                bundle.putString("pkcode", quickResponse.getData().getPkcode());
                                bundle.putString("qrcode", quickResponse.getData().getQrcode());
                                bundle.putString("orderId", orderId);
                                intent.putExtras(bundle);
                                mList.get(position).setStatus("1");//状态更改为已支付
                                adapter.notifyDataSetChanged();
                                AllDingDanActivity.this.startActivity(intent);
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

    private void aliPay(final int position, String currentMoney, final String psType, final String orderId) {

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
                                intent = new Intent(AllDingDanActivity.this, PaySuccessActivity.class);
                            } else {
                                intent = new Intent(AllDingDanActivity.this, PaySuccess2Activity.class);
                            }
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("detail_Info", quickResponse.getData().getDetail_infos());
                            bundle.putString("pkcode", quickResponse.getData().getPkcode());
                            bundle.putString("qrcode", quickResponse.getData().getQrcode());
                            bundle.putString("orderId", orderId);
                            intent.putExtras(bundle);
                            mList.get(position).setStatus("1");//状态更改为已支付
                            adapter.notifyDataSetChanged();
                            AllDingDanActivity.this.startActivity(intent);

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

    private void yue(final int position, final String currentMoney, final String psType, final String orderId) {
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
                                                                    intent = new Intent(AllDingDanActivity.this, PaySuccessActivity.class);
                                                                } else {
                                                                    intent = new Intent(AllDingDanActivity.this, PaySuccess2Activity.class);
                                                                }
                                                                Bundle bundle = new Bundle();
                                                                bundle.putSerializable("detail_Info", info.getData().getDetail_infos());
                                                                bundle.putString("pkcode", info.getData().getPkcode());
                                                                bundle.putString("qrcode", info.getData().getQrcode());
                                                                bundle.putString("orderId", orderId);
                                                                intent.putExtras(bundle);
                                                                AllDingDanActivity.this.startActivity(intent);
                                                                mList.get(position).setStatus("1");//状态更改为已支付
                                                                adapter.notifyDataSetChanged();
                                                            } else {
                                                                if (message.contains("余额不足")) {
                                                                    PopUtil.showDialog(AllDingDanActivity.this, "温馨提醒", "您的余额不足，是否前往充值？", "取消", "去充值", null, new View.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(View v) {
                                                                            //充值界面
                                                                            startActivity(new Intent(AllDingDanActivity.this, RechargeActivity.class));
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
                                                intent = new Intent(AllDingDanActivity.this, PaySuccessActivity.class);
                                            } else {
                                                intent = new Intent(AllDingDanActivity.this, PaySuccess2Activity.class);
                                            }
                                            Bundle bundle = new Bundle();
                                            bundle.putSerializable("detail_Info", info.getData().getDetail_infos());
                                            bundle.putString("pkcode", info.getData().getPkcode());
                                            bundle.putString("qrcode", info.getData().getQrcode());
                                            bundle.putString("orderId", orderId);
                                            intent.putExtras(bundle);
                                            mList.get(position).setStatus("1");//状态更改为已支付
                                            adapter.notifyDataSetChanged();
                                            AllDingDanActivity.this.startActivity(intent);
                                        } else {

                                            if (message.contains("余额不足")) {
                                                PopUtil.showDialog(AllDingDanActivity.this, "温馨提醒", "您的余额不足，是否前往充值？", "取消", "去充值", null, new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        //充值界面
                                                        startActivity(new Intent(AllDingDanActivity.this, RechargeActivity.class));
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
}