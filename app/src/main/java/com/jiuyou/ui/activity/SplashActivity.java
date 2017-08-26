package com.jiuyou.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jiuyou.R;
import com.jiuyou.core.AppContext;
import com.jiuyou.core.task.UIThread;
import com.jiuyou.customctrls.CountDownView;
import com.jiuyou.global.AppConfig;
import com.jiuyou.network.response.JZBResponse.AboutResponse;
import com.jiuyou.network.response.JZBResponse.BannerData;
import com.jiuyou.network.response.JZBResponse.BannerResponse1;
import com.jiuyou.ui.Utils.GoodsUtils;
import com.jiuyou.ui.Utils.UserUtils;
import com.jiuyou.ui.base.BaseActivity;
import com.jiuyou.util.ToastUtil;

import java.util.ArrayList;


public class SplashActivity extends BaseActivity implements View.OnClickListener, CountDownView.OnCancelListener {
    private ImageView mImageView;
    private CountDownView mCountDownView;
    private Button roundBtn;
    private LinearLayout ll_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mCountDownView = (CountDownView) findViewById(R.id.tv_advertise_count_down);
        mImageView = (ImageView) findViewById(R.id.iv_advertise_img);
        roundBtn = (Button) findViewById(R.id.roundBtn);
        ll_in=(LinearLayout) findViewById(R.id.ll_in);
        ll_in.setVisibility(View.GONE);
        roundBtn.setOnClickListener(this);
        mCountDownView.setOnCancelListener(this);
        mCountDownView.setOnClickListener(this);
        ll_in.setOnClickListener(this);
        GoodsUtils.getHomeBanner(new GoodsUtils.getHomeBannerListener() {
            @Override
            public void load(boolean status, BannerResponse1 info, String message) {
                if (status) {
                    if(AppConfig.bannerDatas==null){
                         AppConfig.bannerDatas=new ArrayList<BannerData>();
                        AppConfig.bannerDatas=info.getData();
                    }
                }
            }
        });
        UserUtils.about("",new UserUtils.aboutListener() {
            @Override
            public void load(boolean status, AboutResponse info, String message) {
                if(status){
                    AppConfig.DisCount = info.getInfos().get(0).getDiscount();
                }else{
                    ToastUtil.show(message);
                }
                closeProgressBar();
            }
        });
        //开始倒计时
        UIThread.execute(new CountDownTask());
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_advertise_count_down) {
            //调用了该方法后，会自动调用OnCancelListener的cancel方法。
            //之前，在该句代码之下，还有toNext()方法，结果再HomeActivity中出现了一个奇怪的bug。在mPagerTab的OnPageChangeListener监听的onPageSelected()方法中，mAdapter.mTitles[i]一直报空指针异常，而mAdapter并不为null
            //经过猜想，应该是退出时用System.exit(0)，直接杀死了进程，而mAdapter.mTitles[i]获取的是BaseApp.channels的值，再加上打开了2个HomeActivity,所以在第一HomeActivity中强杀主进程后，第二个就报出了空指针
            mCountDownView.cancel();
            ToastUtil.show("倒计时取消");
            toNextActivity();

        } else if (id == R.id.roundBtn) {
            mCountDownView.cancel();
//            ToastUtil.show("Button取消");
            toNextActivity();
        }else if (id == R.id.ll_in) {
            mCountDownView.cancel();
//            ToastUtil.show("Button取消");
            toNextActivity();
        }
    }


    public class CountDownTask implements Runnable {

        @Override
        public void run() {
            UIThread.execute(new Runnable() {
                @Override
                public void run() {
                    displayAdvertise();
                }
            });
        }
    }

    /**
     * 显示启动页，并开始倒计时
     */
    private void displayAdvertise() {
        //展示启动页
//        mCountDownView.setVisibility(View.VISIBLE);
        mCountDownView.setVisibility(View.GONE);
//        AppContext.getImageLoaderProxy().displayImage("drawable://" + R.mipmap.image_no_advertise, mImageView, DisplayImageOptionsUtils.getOptionInSplash());

        AppContext.getImageLoaderProxy().displayImage("drawable://" + R.mipmap.splash, mImageView);
        //开始倒计时
        mCountDownView.start();
    }

    private void toNextActivity() {
        //判断是否登录 如果登录跳转到主页 没有登录跳转到登录页
//        if (BaseApp.getjurisdiction() == 1) {
            //直接启动
            toNext(MainActivity.class);
//        } else {
            //跳转到登录页
//            toNext(SignInActivity.class);
//        }
        SplashActivity.this.finish();
    }

    /**
     * 当倒计时完毕后，跳转至下一主页
     */
    @Override
    public void cancel() {
        mCountDownView.setVisibility(View.INVISIBLE);
        toNextActivity();
    }

}
