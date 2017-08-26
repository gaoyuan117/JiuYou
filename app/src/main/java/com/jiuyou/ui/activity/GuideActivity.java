package com.jiuyou.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.jiuyou.R;
import com.jiuyou.core.AppContext;
import com.jiuyou.global.AppConfig;
import com.jiuyou.network.interfaces.HomeApi;
import com.jiuyou.network.response.JZBResponse.AboutResponse;
import com.jiuyou.network.response.JZBResponse.BannerData;
import com.jiuyou.network.response.JZBResponse.BannerResponse1;
import com.jiuyou.network.response.JZBResponse.Cart;
import com.jiuyou.network.response.JZBResponse.CartResponse;
import com.jiuyou.ui.Utils.GoodsUtils;
import com.jiuyou.ui.Utils.UserUtils;
import com.jiuyou.ui.base.BaseActivity;
import com.jiuyou.util.PrefereUtils;
import com.jiuyou.util.ToastUtil;
import com.jiuyou.util.UiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * Created by wisely on 2016/5/17.
 */
public class GuideActivity extends BaseActivity{

  ViewPager mViewPager;
  PrefereUtils mInstance;
  int[] guideRes = new int[]{R.mipmap.guide_01, R.mipmap.guide_02, R.mipmap.guide_03, R.mipmap.guide_04};

  private int currentPos = 0; // 成员变量记录viewpager当前滑动的位置

  @Override
  protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.activity_guide);
    mInstance = PrefereUtils.getInstance();
    GoodsUtils.getHomeBanner(new GoodsUtils.getHomeBannerListener(){
      @Override
      public void load(boolean status, BannerResponse1 info, String message){
        isInitFinished = true;
        if(status){
          if(AppConfig.bannerDatas == null){
            AppConfig.bannerDatas = new ArrayList<BannerData>();
            AppConfig.bannerDatas = info.getData();
          }
        }
      }
    });
    UserUtils.about("", new UserUtils.aboutListener(){
      @Override
      public void load(boolean status, AboutResponse info, String message){
        if(status){
          AppConfig.DisCount = info.getInfos().get(0).getDiscount();
        }else{
          ToastUtil.show(message);
        }
        closeProgressBar();
      }
    });
    if(!mInstance.isFirstOpen()){
      toNext(SplashActivity.class);
      finish();
    }else{
      mViewPager = (ViewPager) findViewById(R.id.vp_guide);
      mViewPager.setAdapter(new GuideAdapter());
    }
    initEdgeListener();
  }


  private void initEdgeListener(){
    mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
      @Override public void onPageSelected(int position){
        currentPos = position;
      }
    });

    mViewPager.setOnTouchListener(new View.OnTouchListener(){
      float startX;
      float startY;
      float endX;
      float endY;

      @Override
      public boolean onTouch(View v, MotionEvent event){
        switch(event.getAction()){
          case MotionEvent.ACTION_DOWN:
            startX = event.getX();
            startY = event.getY();
            break;
          case MotionEvent.ACTION_UP:
            endX = event.getX();
            endY = event.getY();
            WindowManager windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
            //获取屏幕的宽度
            Point size = new Point();
            windowManager.getDefaultDisplay().getSize(size);
            int width = size.x;
            //首先要确定的是，是否到了最后一页，然后判断是否向左滑动，并且滑动距离是否符合，我这里的判断距离是屏幕宽度的4分之一（这里可以适当控制）
            if(currentPos == (guideRes.length - 1) && startX - endX > 0 && startX - endX >= (width / 6)){
              startActivity(new Intent(GuideActivity.this, MainActivity.class));
              finish();
            }
            break;
        }
        return false;
      }
    });
  }


  private void initGuide(){
    AppContext.getInstance().getServerReqFactory().createRequestApi(HomeApi.class).boot("", new Callback<CartResponse>(){
      @Override
      public void success(CartResponse cartResponse, Response response){
        if(cartResponse.getCode() == 200){
          isInitFinished = true;
          List<Cart> list = cartResponse.getCarts();
//                     guideRes=new String[list.size()];
          for(int i = 0; i < list.size(); i++){
//                         guideRes[i]=list.get(i).getBoot();
          }
          mViewPager = (ViewPager) findViewById(R.id.vp_guide);
          mViewPager.setAdapter(new GuideAdapter());
        }else{
          toNext(SplashActivity.class);
        }
      }

      @Override
      public void failure(RetrofitError retrofitError){

      }
    });
  }

  @Override
  protected void onResume(){
    super.onResume();
  }


  class GuideAdapter extends PagerAdapter{

    @Override
    public int getCount(){
      return guideRes.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object){
      return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position){
      View view = UiUtils.inflate(R.layout.guide_image);
      ImageView imageView = (ImageView) view.findViewById(R.id.iv_guide);
//            AppContext.getInstance().getImageLoader().displayImage(AppConfig.ENDPOINTPIC+guideRes[position],imageView);
      imageView.setScaleType(ImageView.ScaleType.FIT_XY);
      imageView.setImageResource(guideRes[position]);
      container.addView(view);
      imageView.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v){
          if(position == guideRes.length - 1){

            toNext(MainActivity.class);
//            if(isInitFinished){
//              toNext(MainActivity.class);
////              mInstance.setNotFirstOpen();
//            }else{
//              if(isNotifyFinished){
////                                HomeActivity.channels = Utils.getDefaultChannelList();
//                toNext(MainActivity.class);
//              }else{
//                if(NetworkUtil.isConnected()){
////                                    ToastUtil.show("正在初始化数据，再次点击进入主页");
//                  isNotifyFinished = true;
//                }else{
////                                    HomeActivity.channels = Utils.getDefaultChannelList();
//                  toNext(MainActivity.class);
//                }
////                mInstance.setNotFirstOpen();
//              }
//            }
//
          }
        }
      });
      return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
      container.removeView((View) object);
    }
  }

  public void toNext(Class cls){
    startActivity(new Intent(this, cls));
    finish();
  }

  private boolean isInitFinished;
  private boolean isNotifyFinished;//如果频道数据并未加载完毕，提示一下，第二次点击时则直接进入


}
