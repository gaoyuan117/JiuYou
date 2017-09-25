package com.jiuyou.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.jiuyou.model.OrderBusBean;
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
    @Bind(R.id.rl_nogoods)
    RelativeLayout mRlEmpty;


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
        BaseApp.allDingDanActivity = this;
        mList = new ArrayList<>();
        mListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        mListView.setHeaderLayout(new RotateLoadingLayout(this, PullToRefreshBase.Mode.PULL_FROM_START, mListView.getPullToRefreshScrollDirection()));
        mListView.setOnRefreshListener(this);
        mListView.setOnItemClickListener(this);
        mListView.setEmptyView(mRlEmpty);
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
        intent.putExtra("id", orderBean.getOrder_no());
        intent.putExtra("id2", orderBean.getId());
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
            if (status.equals("4")) {
                mList.remove(position);
            } else {
                mList.get(position).setStatus(status);
            }
            adapter.notifyDataSetChanged();
        }

        if (requestCode == 133) {
            int position = data.getIntExtra("position", -1);
            String status = data.getStringExtra("status");
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
                        mList.remove(posotion);
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
        Intent intent = new Intent(this, ConfirmOrder2Activity.class);
        String order_no = mList.get(position).getOrder_no();
        intent.putExtra("order_no", order_no);
        intent.putExtra("position", position);
        startActivityForResult(intent, 133);
    }

}