package com.jiuyou.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jiuyou.R;
import com.jiuyou.model.OrderTimeBean;
import com.jiuyou.retrofit.RetrofitClient;
import com.jiuyou.retrofit.RxUtils;
import com.jiuyou.ui.adapter.OrderTimeAdapter;
import com.jiuyou.ui.base.BaseActivity;
import com.jiuyou.util.PopUtil;
import com.jiuyou.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class SelectPsTypeActivity extends BaseActivity {

    @Bind(R.id.tv_select_ziqu)
    TextView tvSelectZiqu;
    @Bind(R.id.tv_select_ps)
    TextView tvSelectPs;
    private Intent intent;

    private String dialogMessage;//距离太远 提示语句
    private List<String> timeList;//配送时间集合
    private String adressId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_ps_type);
        ButterKnife.bind(this);
        setCenterTitle("取货方式");
        adressId = getIntent().getStringExtra("id");
        timeList = new ArrayList<>();
        intent = new Intent();
    }

    @OnClick({R.id.tv_select_ziqu, R.id.tv_select_ps})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_select_ziqu:
                intent.putExtra("type", "1");
                intent.putExtra("send_type", "");
                setResult(110, intent);
                finish();
                break;
            case R.id.tv_select_ps:
                if(TextUtils.isEmpty(adressId)||adressId.equals("null")){
                    ToastUtil.show("请选择收货地址");
                }
                getDistance(adressId);
                break;
        }
    }

    /**
     * 获取时间距离列表
     */
    private void getDistance(String id) {
        RetrofitClient.getInstance().createApi().getDistance(id)
                .compose(RxUtils.<OrderTimeBean>io_main())
                .subscribe(new Consumer<OrderTimeBean>() {
                    @Override
                    public void accept(OrderTimeBean orderTimeBean) throws Exception {
                        timeList.clear();
                        dialogMessage = "";
                        if (orderTimeBean.getCode() == 200) {
                            timeList.addAll(orderTimeBean.getData().getTime());
                            showTimePopWindow();
                        } else if (orderTimeBean.getCode() == 201) {
                            dialogMessage = orderTimeBean.getMessage();
                            showTimeDialog();
                        }
                    }
                });
    }

    /**
     * 显示配送时间PopoupWindow
     */
    private void showTimePopWindow() {
        View view = View.inflate(this, R.layout.pop_confirm_order, null);
        ListView listView = (ListView) view.findViewById(R.id.lv_pop_time);
        timeList.add("取消");
        OrderTimeAdapter adapter = new OrderTimeAdapter(this, timeList);
        listView.setAdapter(adapter);
        final PopupWindow window = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setTouchable(true);
        window.setOutsideTouchable(true);
        window.setFocusable(true);

        window.showAtLocation(this.findViewById(R.id.layoutContent), Gravity.BOTTOM, 0, 0);
        setBackgroundAlpha(this, 0.5f);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (timeList.get(i).equals("取消")) {
                    window.dismiss();
                    return;
                }
                ImageView imageView = (ImageView) view.findViewById(R.id.img_selector);
                imageView.setVisibility(View.VISIBLE);
                window.dismiss();
                Intent intent = new Intent();
                intent.putExtra("ps_time",timeList.get(i));
                intent.putExtra("type", "2");
                intent.putExtra("send_type", "1");
                setResult(110,intent);
                finish();
            }
        });

        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (this != null) {
                    setBackgroundAlpha(SelectPsTypeActivity.this, 1f);
                }
            }
        });

    }


    /**
     * 显示距离太远 配送时间没有
     */
    private void showTimeDialog() {
        PopUtil.showDialog(SelectPsTypeActivity.this, "温馨提醒", dialogMessage, "取消", "确认", null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("ps_time",dialogMessage);
                intent.putExtra("type", "2");
                intent.putExtra("send_type", "2");
                PopUtil.dismissPop();
                setResult(110,intent);
                finish();
            }
        });
    }

    /**
     * 设置页面的透明度
     *
     * @param bgAlpha 1表示不透明
     */
    public static void setBackgroundAlpha(Activity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        if (bgAlpha == 1) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//此行代码主要是解决在华为手机上半透明效果无效的bug
        }
        activity.getWindow().setAttributes(lp);

    }

}
