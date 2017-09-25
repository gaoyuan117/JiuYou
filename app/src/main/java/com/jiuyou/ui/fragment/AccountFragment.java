package com.jiuyou.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jiuyou.R;
import com.jiuyou.customctrls.pullableview.PullToRefreshLayout1;
import com.jiuyou.customctrls.pullableview.PullableListView;
import com.jiuyou.global.AppConfig;
import com.jiuyou.network.response.JZBResponse.Cart;
import com.jiuyou.network.response.JZBResponse.CartResponse;
import com.jiuyou.ui.Utils.CartUtils;
import com.jiuyou.ui.activity.ConfirmOrderActivity;
import com.jiuyou.ui.activity.LoginActivity;
import com.jiuyou.ui.activity.MainActivity;
import com.jiuyou.ui.adapter.ShoppingCartAdapter;
import com.jiuyou.ui.base.BaseFragment;
import com.jiuyou.util.PopUtil;
import com.jiuyou.util.PrefereUtils;
import com.jiuyou.util.ToastUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AccountFragment extends BaseFragment implements View.OnClickListener {
    private View view;
    private ImageView title_bar_operate_2;
    private List<Cart> data;
    private PullableListView mListView;
    private ShoppingCartAdapter adapter;
    private CheckBox checkBox_select_all;
    private TextView integral_sum;
    private int sum = 0;
    private int[] sumIntegral;
    private Context context;
    private int showpage;
    private boolean isPermitFlag;
    private TextView tv_all_select;
    private LinearLayout ll_selectall;
    private Button payBt;
    private PullToRefreshLayout1 activity_refreshview;
    private LinearLayout ll_jieusan;
    private TextView tv_none;
    //当前页
    private int currentPage = 1;
    private String[] delList;
    private boolean isRefresh = false;
    private LinearLayout ll_botttom;


    private Handler handler = new Handler() {
        @SuppressWarnings("unchecked")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 10) { //更改选中商品的总价格
                float price = (Float) msg.obj;
                DecimalFormat decimalFormat = new DecimalFormat(".00");
                if (price > 0 && price < 1) {
                    integral_sum.setText("¥0" + decimalFormat.format(price) + "");
                } else if (price >= 1) {
                    integral_sum.setText("¥" + decimalFormat.format(price) + "");
                } else {
                    integral_sum.setText("¥" + "0");
                }
            } else if (msg.what == 12) { //更改选中商品的总数量
                int sum = (int) msg.obj;
                tv_all_select.setText("全选(" + sum + ")");
            } else if (msg.what == 11) {
                //列表选中状态
                List<Boolean> array = (List<Boolean>) msg.obj;
                if (Test.isAllFalse(array)) {
                    checkBox_select_all.setChecked(false);
//                    checkBox_add.setChecked(false);
                }
                if (Test.isAllTrue(array)) {
                    checkBox_select_all.setChecked(true);
//                    checkBox_add.setChecked(true);
                }
                if (Test.isHaveOneFasle(array)) {
                    checkBox_select_all.setChecked(false);
                }
                if (Test.isHaveOneTrue(array)) {
//                    checkBox_add.setChecked(true);
                }
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        initData2();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            initData2();
        }
    }

    private void initData2() {
        currentPage = 1;
        CartUtils.getCartList(PrefereUtils.getInstance().getToken(), currentPage + "", AppConfig.pageSize + "", new CartUtils.getCartListener() {
            @Override
            public void load(boolean status, CartResponse info, String message) {
                if (status) {
                    ll_jieusan.setVisibility(View.VISIBLE);
                    tv_none.setVisibility(View.INVISIBLE);
                    title_bar_operate_2.setVisibility(View.VISIBLE);
                    ll_botttom.setVisibility(View.VISIBLE);
                    if (AppConfig.currentTAB.equals(MainActivity.TAB_ACCOUNT)) {
                        data = info.getCarts();
                        initdata();
                        AppConfig.currentTAB = "";
                    }
                    if (data == null) {
                        data = info.getCarts();
                        initdata();
                    } else {
                        adapter.notifyDataSetChanged();
                    }
                    if (isRefresh) {
                        activity_refreshview.refreshFinish(PullToRefreshLayout1.SUCCEED);
                        isRefresh = false;
                    }

                } else {
                    ll_jieusan.setVisibility(View.INVISIBLE);
                    tv_none.setVisibility(View.VISIBLE);
                    title_bar_operate_2.setVisibility(View.INVISIBLE);
                    ll_botttom.setVisibility(View.GONE);
                    ll_botttom.setBackgroundResource(R.color.line);
                    tv_all_select.setText("全选(" + "0" + ")");
                    checkBox_select_all.setChecked(false);

                }
            }
        });
    }

    /**
     * 计算选中商品的数量
     *
     * @return 返回需要商品的数量
     */
    private int getTotalSum() {
        Cart bean = null;
        int totalNum = 0;
        for (int i = 0; i < data.size(); i++) {
            bean = data.get(i);
            if (ShoppingCartAdapter.getIsSelected().get(i)) {
                totalNum += Integer.valueOf(bean.getQuantity());
            }
        }
        return totalNum;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.activity_shopping_cart, container, false);
        } else {
            if (view.getParent() != null) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
        }
        initView(view);
        return view;
    }

    private void initView(View view) {
        ll_botttom = (LinearLayout) view.findViewById(R.id.ll_botttom);
        ll_jieusan = (LinearLayout) view.findViewById(R.id.ll_jieusan);
        tv_none = (TextView) view.findViewById(R.id.tv_none);
        payBt = (Button) view.findViewById(R.id.payBt);
        payBt.setOnClickListener(this);
        tv_all_select = (TextView) view.findViewById(R.id.tv_all_select);
        ll_selectall = (LinearLayout) view.findViewById(R.id.ll_selectall);
        integral_sum = (TextView) view.findViewById(R.id.totalPrice);
        title_bar_operate_2 = (ImageView) view.findViewById(R.id.title_bar_operate_2);
        title_bar_operate_2.setOnClickListener(this);
        checkBox_select_all = (CheckBox) view.findViewById(R.id.lv_all_select);
        activity_refreshview = (PullToRefreshLayout1) view.findViewById(R.id.refresh_view);
        activity_refreshview.setOnRefreshListener(new PullToRefreshLayout1.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout1 pullToRefreshLayout) {
                isRefresh = true;
                splshData();
            }

            @Override
            public void onLoadMore(PullToRefreshLayout1 pullToRefreshLayout) {
                activity_refreshview.loadmoreFinish(PullToRefreshLayout1.SUCCEED);
            }
        });
        context = getActivity();
        showpage = 1;
        isPermitFlag = true;
        initData();
    }

    private void initData() {
        currentPage = 1;
        CartUtils.getCartList(PrefereUtils.getInstance().getToken(), currentPage + "", AppConfig.pageSize + "", new CartUtils.getCartListener() {
            @Override
            public void load(boolean status, CartResponse info, String message) {
                if (status) {
                    ll_jieusan.setVisibility(View.VISIBLE);
                    tv_none.setVisibility(View.INVISIBLE);
                    title_bar_operate_2.setVisibility(View.VISIBLE);
                    ll_botttom.setVisibility(View.VISIBLE);
                    if (AppConfig.currentTAB.equals(MainActivity.TAB_ACCOUNT)) {
                        data = info.getCarts();
                        initdata();
                        AppConfig.currentTAB = "";
                    }
                    if (data == null) {
                        data = info.getCarts();
                        initdata();
                    } else {
                        adapter.notifyDataSetChanged();
                    }
                    if (isRefresh) {
                        activity_refreshview.refreshFinish(PullToRefreshLayout1.SUCCEED);
                        isRefresh = false;
                    }

                } else {
                    if (message.contains("您还没有登录")) {
                        toNext(LoginActivity.class);
                    }
                    ll_jieusan.setVisibility(View.INVISIBLE);
                    tv_none.setVisibility(View.VISIBLE);
                    title_bar_operate_2.setVisibility(View.INVISIBLE);
                    ll_botttom.setVisibility(View.GONE);
                    ll_botttom.setBackgroundResource(R.color.line);
                    tv_all_select.setText("全选(" + "0" + ")");
                    checkBox_select_all.setChecked(false);

                }
            }
        });
    }

    private void splshData() {
        currentPage = 1;
        CartUtils.getCartList(PrefereUtils.getInstance().getToken(), currentPage + "", AppConfig.pageSize + "", new CartUtils.getCartListener() {
            @Override
            public void load(boolean status, CartResponse info, String message) {
                if (status) {
                    ll_jieusan.setVisibility(View.VISIBLE);
                    tv_none.setVisibility(View.INVISIBLE);
                    title_bar_operate_2.setVisibility(View.VISIBLE);
                    ll_botttom.setVisibility(View.VISIBLE);
                    ll_botttom.setBackgroundResource(R.color.white);
                    data = info.getCarts();
                    initdata();
                    if (isRefresh) {
                        activity_refreshview.refreshFinish(PullToRefreshLayout1.SUCCEED);
                        isRefresh = false;
                    }

                } else {
                    if (message.contains("您还没有登录")) {
                        toNext(LoginActivity.class);
                    }
                    ll_jieusan.setVisibility(View.INVISIBLE);
                    tv_none.setVisibility(View.VISIBLE);
                    title_bar_operate_2.setVisibility(View.INVISIBLE);
                    ll_botttom.setVisibility(View.GONE);
                    ll_botttom.setBackgroundResource(R.color.line);

                }
            }
        });
    }

    private void initdata() {
        tv_all_select.setText("全选(" + "0" + ")");
        checkBox_select_all.setChecked(false);
        integral_sum.setText("¥" + 0 + ".00");
        adapter = new ShoppingCartAdapter((MainActivity) getActivity(), handler, data);
        sumIntegral = new int[data.size() + 1];
        ll_selectall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox_select_all.isChecked()) {
                    checkBox_select_all.setChecked(false);
                } else {
                    checkBox_select_all.setChecked(true);
                }
                HashMap<Integer, Boolean> isSelected = ShoppingCartAdapter
                        .getIsSelected();
                Iterator iterator = isSelected.entrySet().iterator();
                List<Boolean> array = new ArrayList<Boolean>();//列表中checkbox选中状态
                List<Integer> nums = new ArrayList<Integer>();//列表中商品数量
                while (iterator.hasNext()) {
                    HashMap.Entry entry = (HashMap.Entry) iterator.next();
                    Integer key = (Integer) entry.getKey();
                    Boolean val = (Boolean) entry.getValue();
                    array.add(val);
                }
                for (int i = 0; i < data.size(); i++) {
                    int num = Integer.valueOf(data.get(i).getQuantity());
//                    int integral = Integer.valueOf(data.get(i).getPrice());
                    nums.add(num);
                }
                if (checkBox_select_all.isChecked()) {

                    for (int i = 0; i < data.size(); i++) {
                        ShoppingCartAdapter.getIsSelected().put(i, true);
                    }
//                    checkBox_add.setChecked(true);
                    adapter.notifyDataSetChanged();
                } else {
                    for (int i = 0; i < data.size(); i++) {
                        ShoppingCartAdapter.getIsSelected().put(i, false);
                    }
//                    checkBox_add.setChecked(false);
                    adapter.notifyDataSetChanged();
                    integral_sum.setText("¥" + 0 + ".00");
                }
            }
        });
        checkBox_select_all.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                HashMap<Integer, Boolean> isSelected = ShoppingCartAdapter
                        .getIsSelected();
                Iterator iterator = isSelected.entrySet().iterator();
                List<Boolean> array = new ArrayList<Boolean>();//列表中checkbox选中状态
                List<Integer> nums = new ArrayList<Integer>();//列表中商品数量
                while (iterator.hasNext()) {
                    HashMap.Entry entry = (HashMap.Entry) iterator.next();
                    Integer key = (Integer) entry.getKey();
                    Boolean val = (Boolean) entry.getValue();
                    array.add(val);
                }
                for (int i = 0; i < data.size(); i++) {
                    int num = Integer.valueOf(data.get(i).getQuantity());
//                    int integral = Integer.valueOf(data.get(i).getPrice());
                    nums.add(num);
                }
                if (checkBox_select_all.isChecked()) {

                    for (int i = 0; i < data.size(); i++) {
                        ShoppingCartAdapter.getIsSelected().put(i, true);
                    }
//                    checkBox_add.setChecked(true);
                    adapter.notifyDataSetChanged();
                } else {
                    for (int i = 0; i < data.size(); i++) {
                        ShoppingCartAdapter.getIsSelected().put(i, false);
                    }
//                    checkBox_add.setChecked(false);
                    adapter.notifyDataSetChanged();
                    integral_sum.setText("¥" + 0 + ".00");
                }

            }
        });
        mListView = (PullableListView) view.findViewById(R.id.finance_list);
        mListView.setAdapter(adapter);
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//
//                int num = Integer.valueOf(data.get(position).getQuantity());
//                if (num == 0) {
//                    Toast.makeText(context, "请选择商品数量", Toast.LENGTH_LONG)
//                            .show();
//                } else {
//                    boolean cu = !ShoppingCartAdapter.getIsSelected().get(position);
//                    ShoppingCartAdapter.getIsSelected().put(position, cu);
//                    adapter.notifyDataSetChanged();
//                    //遍历获取列表中checkbox的选中状态
//                    HashMap<Integer, Boolean> isSelected = ShoppingCartAdapter
//                            .getIsSelected();
//                    Iterator iterator = isSelected.entrySet().iterator();
//                    List<Boolean> array = new ArrayList<Boolean>();
//                    while (iterator.hasNext()) {
//                        HashMap.Entry entry = (HashMap.Entry) iterator.next();
//                        Integer key = (Integer) entry.getKey();
//                        Boolean val = (Boolean) entry.getValue();
//                        array.add(val);
//                    }
//                    if (Test.isAllFalse(array)) {
//                        checkBox_select_all.setChecked(false);
////                        checkBox_add.setChecked(false);
//                    }
//                    if (Test.isAllTrue(array)) {
//                        checkBox_select_all.setChecked(true);
////                        checkBox_add.setChecked(true);
//                    }
//                    if (Test.isHaveOneFasle(array)) {
//                        checkBox_select_all.setChecked(false);
//                    }
//                    if (Test.isHaveOneTrue(array)) {
////                        checkBox_add.setChecked(true);
//                    }
//                }
//            }
//        });
    }

    HashMap<Integer, Boolean> selectDate;
    int length = 0;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.payBt:
                //结算
                ArrayList<Cart> selectCarts = new ArrayList<>();
                for (Map.Entry<Integer, Boolean> entry : ShoppingCartAdapter.getIsSelected().entrySet()) {
                    if (entry.getValue()) {
                        selectCarts.add(data.get(entry.getKey()));
                    }
                }
                if (selectCarts.size() > 0) {
                    String totalPrice = integral_sum.getText().toString();
                    String txt = tv_all_select.getText().toString();
                    String totalNum = txt.substring(txt.indexOf("("), txt.indexOf(")"));
                    Intent jiesuan = new Intent(getActivity(), ConfirmOrderActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("selectCarts", selectCarts);
                    bundle.putString("totalNum", totalNum);
                    bundle.putString("totalPrice", totalPrice);
                    jiesuan.putExtras(bundle);
                    getActivity().startActivity(jiesuan);
                } else {
                    ToastUtil.show("没有选中商品");
                }
                break;
            case R.id.title_bar_operate_2:
                selectDate = ShoppingCartAdapter.getIsSelected();
                length = 0;
                if (selectDate != null) {
                    for (Map.Entry<Integer, Boolean> entry : selectDate.entrySet()) {
                        if (entry.getValue()) {
                            length++;
                        }
                    }
                    if (length > 0) {
                        //删除确认
                        PopUtil.showDialog(getActivity(), "温馨提醒", "您确认要删除选中的商品吗？", "取消", "删除", null, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                delPro(length, selectDate);
                            }
                        });

                    } else {
                        ToastUtil.show("请选择要删除的商品");
                    }
                }
                break;
        }
    }

    private void delPro(int length, HashMap<Integer, Boolean> selectDate) {
        delList = new String[length];
        for (Map.Entry<Integer, Boolean> entry : selectDate.entrySet()) {
            if (entry.getValue()) {
                delList[--length] = data.get(entry.getKey()).getProduct_id();
                data.remove(entry.getKey());
                selectDate.put(entry.getKey(), false);
            }
        }
        ShoppingCartAdapter.setIsSelected(selectDate);
        getLoadingDataBar().show();
        String token = PrefereUtils.getInstance().getToken();
        CartUtils.getDelCart(token, delList, new CartUtils.getdelCartListener() {
            @Override
            public void load(boolean status, CartResponse info, String message) {
                if (status) {
                    splshData();
                    integral_sum.setText("¥" + 0 + ".00");
                    checkBox_select_all.setChecked(false);
                    handler.sendMessage(handler.obtainMessage(12, getTotalSum()));
                } else {
                    ToastUtil.show(message);
                }
                PopUtil.dismissPop();
                loadingDataBarClose();
            }
        });
    }
}
