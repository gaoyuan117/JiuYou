package com.jiuyou.customctrls;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.loopj.android.image.SmartImageView;
import com.sheyuan.universalimageloader.core.DisplayImageOptions;
import com.jiuyou.R;

import java.util.ArrayList;

/**
 * 广告图片自动轮播控件</br>
 */
public class ImageCycleView extends LinearLayout {
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 图片轮播视图
     */
    private ViewPager mAdvPager = null;
    /**
     * 滚动图片视图适配
     */
    private ImageCycleAdapter mAdvAdapter;
    /**
     * 图片轮播指示器控件
     */
    private LinearLayout mGroup;

    /**
     * 图片轮播指示个图
     */
    private ImageView mImageView = null;

    /**
     * 滚动图片指示视图列表
     */
    private ImageView[] mImageViews = null;

    /**
     * 图片滚动当前图片下标
     */

    private boolean isStop;

    /**
     * 游标是圆形还是长条，要是设置为0是长条，要是1就是圆形 默认是圆形
     */
    public int stype = 1;

    /**
     * @param context
     */
    public ImageCycleView(Context context) {
        super(context);
    }

    private ArrayList<Bitmap> bitmaps;

    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .cacheInMemory(true).cacheOnDisk(true)
            .showImageForEmptyUri(R.mipmap.icon_nopic)
            .showImageOnFail(R.mipmap.icon_nopic)
            .showImageOnLoading(R.mipmap.icon_nopic)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .build();

    public void setBitmaps(ArrayList<Bitmap> bitmaps) {
        this.bitmaps = bitmaps;
    }

    /**
     * @param context
     * @param attrs
     */
    @SuppressLint("Recycle")
    public ImageCycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.ad_cycle_view, this);
        mAdvPager = (ViewPager) findViewById(R.id.adv_pager);
        mAdvPager.setOnPageChangeListener(new GuidePageChangeListener());
        // 滚动图片右下指示器视
        mGroup = (LinearLayout) findViewById(R.id.viewGroup);
    }

    public void setGuideGravity(boolean isCenter) {
        if (isCenter) {
            mGroup.setGravity(Gravity.CENTER);
        } else {
            mGroup.setGravity(Gravity.END);
        }
    }

    /**
     * 触摸停止计时器，抬起启动计时器
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            // 开始图片滚动
            startImageTimerTask();
        } else {
            // 停止图片滚动
            stopImageTimerTask();
        }
        return super.dispatchTouchEvent(event);
    }

    /**
     * 装填图片数据
     *
     * @param imageUrlList
     * @param imageCycleViewListener
     */
    public void setImageResources(ArrayList<Integer> imageUrlList,
                                  ImageCycleViewListener imageCycleViewListener, int stype) {
        this.stype = stype;
        // 清除
        mGroup.removeAllViews();
        // 图片广告数量
        final int imageCount = imageUrlList.size();
        mImageViews = new ImageView[imageCount];
        for (int i = 0; i < imageCount; i++) {
            mImageView = new ImageView(mContext);
            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);
            params.leftMargin = 30;
            mImageView.setScaleType(ScaleType.CENTER_CROP);
            mImageView.setLayoutParams(params);

            mImageViews[i] = mImageView;
            if (i == 0) {
                if (this.stype == 1)
                    // mImageViews[i].setBackgroundResource(R.mipmap.banner_dian_focus);
                    mImageViews[i]
                            .setBackgroundResource(R.mipmap.cicle_banner_dian_focus);// 换点
                else
                    mImageViews[i]
                            .setBackgroundResource(R.mipmap.cicle_banner_dian_focus);
            } else {
                if (this.stype == 1)
                    // mImageViews[i].setBackgroundResource(R.mipmap.banner_dian_blur);
                    mImageViews[i]
                            .setBackgroundResource(R.mipmap.cicle_banner_dian_blur);
                else
                    mImageViews[i]
                            .setBackgroundResource(R.mipmap.cicle_banner_dian_blur);
            }
            mGroup.addView(mImageViews[i]);
        }


    }

    public void setCurrentItem(int item) {
        mAdvPager.setCurrentItem(item);
        startImageTimerTask();
    }



    public void setImageResources2(Application mApplication,ArrayList<String> adStrList,
                                   ImageCycleViewListener imageCycleViewListener, int stype) {
        this.stype = stype;
        // 清除
        mGroup.removeAllViews();
        // 图片广告数量
        final int imageCount = adStrList.size();
        mImageViews = new ImageView[imageCount];
        for (int i = 0; i < imageCount; i++) {
            mImageView = new ImageView(mContext);
            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);
            params.leftMargin = 30;
            params.gravity = Gravity.CENTER;
//			mImageView.setScaleType(ScaleType.CENTER_CROP);
            mImageView.setLayoutParams(params);

            mImageViews[i] = mImageView;
            if (i == 0) {
                if (this.stype == 1)
                    // mImageViews[i].setBackgroundResource(R.mipmap.banner_dian_focus);
                    mImageViews[i]
                            .setBackgroundResource(R.mipmap.banner_cicle_notchoose);// 换点
                else
                    mImageViews[i]
                            .setBackgroundResource(R.mipmap.banner_cicle_notchoose);
            } else {
                if (this.stype == 1)
                    // mImageViews[i].setBackgroundResource(R.mipmap.banner_dian_blur);
                    mImageViews[i]
                            .setBackgroundResource(R.mipmap.banner_cicle_choosed);
                else
                    mImageViews[i]
                            .setBackgroundResource(R.mipmap.banner_cicle_choosed);
            }
            mGroup.addView(mImageViews[i]);
        }
        mAdvAdapter = new ImageCycleAdapter(mApplication,mContext, adStrList,
                imageCycleViewListener);
        mAdvPager.setAdapter(mAdvAdapter);
        mAdvPager.setCurrentItem(Integer.MAX_VALUE / 2);
        if (imageCount > 1) {
            startImageTimerTask();
        }

    }


    public void refresh() {
        mAdvAdapter.notifyDataSetChanged();
    }

    /**
     * 图片轮播(手动控制自动轮播与否，便于资源控件）
     */
    public void startImageCycle() {
        startImageTimerTask();
    }

    /**
     * 暂停轮播—用于节省资源
     */
    public void pushImageCycle() {
        stopImageTimerTask();
    }

    /**
     * 图片滚动任务
     */
    private void startImageTimerTask() {
        stopImageTimerTask();
        // 图片滚动
        mHandler.postDelayed(mImageTimerTask, 5000);
    }

    /**
     * 停止图片滚动任务
     */
    private void stopImageTimerTask() {
        isStop = true;
        mHandler.removeCallbacks(mImageTimerTask);
        mHandler.removeCallbacksAndMessages(mImageTimerTask);
    }

    private Handler mHandler = new Handler();

    /**
     * 图片自动轮播Task
     */
    private Runnable mImageTimerTask = new Runnable() {
        @Override
        public void run() {
            if (mImageViews != null) {
                mAdvPager.setCurrentItem(mAdvPager.getCurrentItem() + 1);
                if (!isStop) { // if isStop=true //当你退出后 要把这个给停下来 不然 这个一直存在
                    // 就一直在后台循环
                    mHandler.postDelayed(mImageTimerTask, 5000);
                }

            }
        }
    };

    public interface Swip2ViewPageListener {
        void setSwipInfo();
    }

    ;
    private Swip2ViewPageListener svpl;

    public void setSwipViewPageListener(Swip2ViewPageListener svpl) {
        this.svpl = svpl;
    }

    /**
     * 轮播图片监听
     *
     * @author minking
     */
    private final class GuidePageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                startImageTimerTask();
                if (svpl != null) {
                    svpl.setSwipInfo();
                }

            }

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int index) {
            index = index % mImageViews.length;
            // 设置当前显示的图片
            // 设置图片滚动指示器背
            if (stype != 1)
                mImageViews[index]
                        .setBackgroundResource(R.mipmap.banner_cicle_notchoose);
            else
                mImageViews[index]
                        .setBackgroundResource(R.mipmap.banner_cicle_notchoose);
            for (int i = 0; i < mImageViews.length; i++) {
                if (index != i) {
                    if (stype != 1)
                        mImageViews[i]
                                .setBackgroundResource(R.mipmap.banner_cicle_choosed);
                    else
                        mImageViews[i]
                                .setBackgroundResource(R.mipmap.banner_cicle_choosed);
                }
            }
        }
    }

    private class ImageCycleAdapter extends PagerAdapter {

        /**
         * 图片视图缓存列表
         */
        private ArrayList<SmartImageView> mImageViewCacheList;

        /**
         * 图片资源列表
         */
        private ArrayList<String> mAdList = new ArrayList<String>();

        /**
         * 广告图片点击监听
         */
        private ImageCycleViewListener mImageCycleViewListener;

        private Context mContext;
        private Application mApplication;

        public ImageCycleAdapter(Application mApplication,Context context, ArrayList<String> adList,
                                 ImageCycleViewListener imageCycleViewListener) {
            this.mApplication=mApplication;
            this.mContext = context;
            this.mAdList = adList;
            mImageCycleViewListener = imageCycleViewListener;
            mImageViewCacheList = new ArrayList<SmartImageView>();
        }


        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            String imageUrl = mAdList.get(position % mAdList.size());
            SmartImageView imageView = null;
            if (mImageViewCacheList.isEmpty()) {
                imageView = new SmartImageView(mContext);
                imageView.setLayoutParams(new LayoutParams(
                        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

                imageView.setScaleType(ScaleType.CENTER_CROP);

            } else {
                imageView = mImageViewCacheList.remove(0);
            }
//            imageView.setTag(imageUrl);
            imageView.setImageResource(R.mipmap.icon_nopic);
            container.addView(imageView);

            if (bitmaps != null && bitmaps.size() > 0) {
                imageView
                        .setImageBitmap(bitmaps.get(position % bitmaps.size()));
            } else {

                String imagePath = mAdList.get(position % mAdList.size());
                if (imagePath != null && !imagePath.contains("http")) {
                    imagePath = "file://" + imagePath;
                }
                if(Util.isOnMainThread()) {
                    Glide.with(mApplication).load(imagePath).placeholder(R.mipmap.icon_nopic).into(imageView);
                }
            }
            // 设置图片点击监听
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mImageCycleViewListener.onImageClick(
                            position % mAdList.size(), v);
                }
            });
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            SmartImageView view = (SmartImageView) object;
            mAdvPager.removeView(view);
            mImageViewCacheList.add(view);

        }

    }

    /**
     * 轮播控件的监听事件
     *
     * @author minking
     */
    public static interface ImageCycleViewListener {

        /**
         * 单击图片事件
         *
         * @param position
         * @param imageView
         */
        public void onImageClick(int position, View imageView);
    }

}
