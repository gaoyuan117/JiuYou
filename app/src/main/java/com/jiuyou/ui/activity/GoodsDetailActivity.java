package com.jiuyou.ui.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiuyou.R;
import com.jiuyou.customctrls.ImageCycleView;
import com.jiuyou.global.AppConfig;
import com.jiuyou.network.response.JZBResponse.CartResponse;
import com.jiuyou.network.response.JZBResponse.GoodsResponse;
import com.jiuyou.ui.Utils.CartUtils;
import com.jiuyou.ui.Utils.GoodsUtils;
import com.jiuyou.ui.adapter.GoodsDetailAdapter;
import com.jiuyou.ui.base.BaseActivity;
import com.jiuyou.util.PrefereUtils;
import com.jiuyou.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GoodsDetailActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.title_bar_operate_1)
    ImageView title_bar_operate_1;
    RelativeLayout rl_pingjia;
    @Bind(R.id.listview)
    ListView listview;
    @Bind(R.id.iv_jiahao)
    ImageView iv_jiahao;

    TextView tvPriceOver;


    String goodid;
    private View headView;
    private ImageCycleView goodsbanner;
    private TextView head_textView;
    private TextView head_info;
    private List<String> banners;
    private ArrayList<String> listBanners;
    private GoodsDetailAdapter adapter;
    private String product_id;
    private List<String> getMoreImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodsdetail);
        ButterKnife.bind(this);
        goodid = getIntent().getStringExtra("goodid");
        initView();
        initDatas();
    }

    private void initView() {


        iv_jiahao.setOnClickListener(this);
        title_bar_operate_1.setOnClickListener(this);
        headView = LayoutInflater.from(this).inflate(R.layout.goodsinfo_head, null, false);
        tvPriceOver = (TextView) headView.findViewById(R.id.head_textView_over);
        tvPriceOver.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        rl_pingjia = (RelativeLayout) headView.findViewById(R.id.rl_pingjia);
        rl_pingjia.setOnClickListener(this);
        goodsbanner = (ImageCycleView) headView.findViewById(R.id.goodsbanner);
        head_textView = (TextView) headView.findViewById(R.id.head_textView);
        head_info = (TextView) headView.findViewById(R.id.head_info);
        ViewGroup.LayoutParams params = goodsbanner.getLayoutParams();
        DisplayMetrics metrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int widthPixels = metrics.widthPixels;
        params.height = (int) (widthPixels / 1.33);
        goodsbanner.setLayoutParams(params);
        listview.addHeaderView(headView);


    }

    private void initDatas() {
        getLoadingDataBar().show();
        GoodsUtils.getGoodsInfos(goodid, new GoodsUtils.getGoodsInfosListener() {
            @Override
            public void load(boolean status, GoodsResponse info, String message) {
                if (status) {
                    banners = info.getData().getCarousel_img();
                    goodsbanner.setGuideGravity(true);
                    listBanners = new ArrayList<String>();
                    if (banners != null && banners.size() > 0) {
                        for (int i = 0; i < banners.size(); i++) {
                            listBanners.add(AppConfig.ENDPOINTPIC + banners.get(i));
                        }
                        goodsbanner.setImageResources2(getApplication(), listBanners, mAdCycleViewListener, 1);
                    }
                    head_textView.setText("¥" + info.getData().getPrice());
                    tvPriceOver.setText("¥" + info.getData().getOld_price());
                    head_info.setText(info.getData().getTitle());
                    product_id = info.getData().getId();
                    getMoreImg = info.getData().getMoreImg();
                    adapter = new GoodsDetailAdapter(info.getData().getMoreImg(), GoodsDetailActivity.this);
                    listview.setAdapter(adapter);
                    String quantity = info.getData().getQuantity();

//                    if (quantity != null && Integer.parseInt(quantity) > 0) {
                        iv_jiahao.setBackgroundResource(R.drawable.icon_gouwuche);
//                    } else {
//                        iv_jiahao.setBackgroundResource(R.drawable.iicon_jiahao_huise);
//                        iv_jiahao.setClickable(false);
//                    }

                } else {
                    if (!message.equals("")) {
                        ToastUtil.show(message);
                    }
                }
                goodsbanner.setCurrentItem(0);
                closeProgressBar();
            }
        });


    }

    private ImageCycleView.ImageCycleViewListener mAdCycleViewListener = new ImageCycleView.ImageCycleViewListener() {
        @Override
        public void onImageClick(int position, View imageView) {
        }
    };

    @NonNull
    private ViewGroup.LayoutParams getLayoutWidthParams(RelativeLayout layout) {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int widthPixels = metrics.widthPixels;
        ViewGroup.LayoutParams params = layout.getLayoutParams();
        params.width = widthPixels;
        return params;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_bar_operate_1:
                GoodsDetailActivity.this.finish();
                AppConfig.currentTAB = MainActivity.TAB_HOME;
                break;
            case R.id.rl_pingjia:
                //商品評價頁面
                Intent intent = new Intent(GoodsDetailActivity.this, CommentActivity.class);
                intent.putExtra("product_id", product_id);
                GoodsDetailActivity.this.startActivity(intent);
                break;
            case R.id.iv_jiahao:
                if (PrefereUtils.getInstance().isLogin()) {
                    //结算
                    CartUtils.getchangeCart(PrefereUtils.getInstance().getToken(), "add", product_id, "1", new CartUtils.getChangeCartListener() {
                        @Override
                        public void load(boolean status, CartResponse info, String message) {
                            if (status) {
                                AppConfig.currentTAB = MainActivity.TAB_ACCOUNT;
                                GoodsDetailActivity.this.finish();
                            } else {
                                ToastUtil.show(message);
                            }

                        }
                    });


                } else {
                    toNext(LoginActivity.class);
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            GoodsDetailActivity.this.finish();
            AppConfig.currentTAB = MainActivity.TAB_HOME;
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
