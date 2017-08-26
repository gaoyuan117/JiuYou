package com.jiuyou.ui.base.impl;

import android.graphics.Bitmap;
import android.widget.LinearLayout;

import com.sheyuan.universalimageloader.core.DisplayImageOptions;
import com.sheyuan.universalimageloader.core.assist.ImageScaleType;
import com.sheyuan.universalimageloader.core.assist.LoadedFrom;
import com.sheyuan.universalimageloader.core.display.BitmapDisplayer;
import com.sheyuan.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.sheyuan.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.sheyuan.universalimageloader.core.imageaware.ImageAware;
import com.sheyuan.universalimageloader.core.process.BitmapProcessor;
import com.jiuyou.R;
import com.jiuyou.util.UiUtils;


/**
 * Created by wisely on 2016/4/7.
 */
public class DisplayImageOptionsUtils {

    public static DisplayImageOptions getOptionInHomeList() {
        return new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.mipmap.icon_nopic)
                .showImageOnFail(R.mipmap.icon_nopic)
                .showStubImage(R.mipmap.icon_nopic)

//                .showImageOnLoading(R.drawable.icon_nopic)//设置了该配置，在重新进入已加载的page时会发生图片闪烁
//                .resetViewBeforeLoading(false).delayBeforeLoading(1000)
                .resetViewBeforeLoading(false)  //加载图片之前是否重置图片
                .cacheInMemory(true)
                .cacheOnDisk(true)
//                设置FadeInBitmapDisplayer会造成闪烁
//                .displayer(new FadeInBitmapDisplayer(100))
                .displayer(new SimpleBitmapDisplayer())
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    public static DisplayImageOptions getOptionInSplash() {
        return new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.mipmap.image_no_advertise)
                .showImageOnFail(R.mipmap.image_no_advertise)
                .showStubImage(R.mipmap.image_no_advertise)
                .cacheInMemory(true).displayer(new FadeInBitmapDisplayer(100)).build();
    }

    public static DisplayImageOptions getOptionInHeadPic() {
        return new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).cacheInMemory(true)
                .preProcessor(new BitmapProcessor() {
                    @Override
                    public Bitmap process(Bitmap bitmap) {
                        int size = UiUtils.dip2px(25);
                        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, size, size, false);
                        return scaledBitmap;
                    }
                })
                .showImageForEmptyUri(R.drawable.photo_inter)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(false)
//                .imageScaleType(ImageScaleType.EXACTLY)
                .showImageOnFail(R.drawable.photo_inter)
                .showStubImage(R.drawable.photo_inter)
                .build();

    }

    public static DisplayImageOptions getOptionInNongHong() {
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true)
                .displayer(new BitmapDisplayer() {
                    @Override
                    public void display(Bitmap bitmap, ImageAware imageAware, LoadedFrom loadedFrom) {

                        int width = UiUtils.getPhoneWidth() / 2 - 2 * UiUtils.dip2px(5);

                        int width1 = bitmap.getWidth();
                        int height1 = bitmap.getHeight();
                        double ratio = (double) height1 / width1;
                        int height = (int) (ratio * width);
                        System.out.println("wisely-----width:" + bitmap.getWidth() + "-----height:" + bitmap.getHeight());

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
                        imageAware.getWrappedView().setLayoutParams(params);

                        imageAware.setImageBitmap(Bitmap.createScaledBitmap(bitmap, width, height, false));
//                        bitmap.recycle();
                    }
                })
                .showImageForEmptyUri(R.mipmap.empty_photo)
                .showImageOnFail(R.mipmap.empty_photo)
                .showStubImage(R.mipmap.empty_photo)
                .build();

        return options;
    }

    public static DisplayImageOptions getOptionInNongHong_2() {
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true)
//                .displayer(new BitmapDisplayer() {
//                    @Override
//                    public void display(Bitmap bitmap, ImageAware imageAware, LoadedFrom loadedFrom) {
//
////                        int width = UiUtils.getPhoneWidth() / 2 - 2 * UiUtils.dip2px(5);
////
////                        int width1 = bitmap.getWidth();
////                        int height1 = bitmap.getHeight();
////                        double ratio = (double) height1 / width1;
////                        int height = (int) (ratio * width);
//////                        System.out.println("wisely-----width:" + bitmap.getWidth() + "-----height:" + bitmap.getHeight());
////
////                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
////                        imageAware.getWrappedView().setLayoutParams(params);
////
////                        imageAware.setImageBitmap(bitmap);
//                    }
//                })
                .preProcessor(new BitmapProcessor() {
                    @Override
                    public Bitmap process(Bitmap bitmap) {
                        int width = UiUtils.getPhoneWidth() / 2 - 2 * UiUtils.dip2px(5);
                        int width1 = bitmap.getWidth();
                        int height1 = bitmap.getHeight();
                        double ratio = (double) height1 / width1;
                        int height = (int) (ratio * width);
//                        System.out.println("wisely-----width_1:" + bitmap.getWidth() + "-----height_1:" + bitmap.getHeight());
                        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
//                        System.out.println("wisely-----width_2:" + scaledBitmap.getWidth() + "-----height_2:" + scaledBitmap.getHeight());

                        return scaledBitmap;
                    }
                })
                .bitmapConfig(Bitmap.Config.RGB_565)
                .showImageOnLoading(R.mipmap.empty_photo)
//                .considerExifParams(false)
//                .imageScaleType(ImageScaleType.EXACTLY)
                .showImageForEmptyUri(R.mipmap.empty_photo)
                .showImageOnFail(R.mipmap.empty_photo)
//                .showStubImage(R.drawable.empty_photo)
                .build();

        return options;
    }

    /** 农红推荐的图片处理 */
    public static DisplayImageOptions getOptionsInNongHongRecommend(){
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true)
                .preProcessor(new BitmapProcessor() {
                    @Override
                    public Bitmap process(Bitmap bitmap) {

                        int size = UiUtils.dip2px(110);
                        System.out.println("wisely-----rec_width_1:" + bitmap.getWidth() + "-----height_1:" + bitmap.getHeight());
                        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, size, size, false);
                        System.out.println("wisely-----rec_width_2:" + scaledBitmap.getWidth() + "-----height_2:" + scaledBitmap.getHeight());

                        return scaledBitmap;
                    }
                })
                .bitmapConfig(Bitmap.Config.RGB_565)
//                .showImageForEmptyUri(R.drawable.empty_photo)
//                .showImageOnFail(R.drawable.empty_photo)
//                .showStubImage(R.drawable.empty_photo)
                .build();
        return options;
    }

    /** 获取轮播图的options */
    public static DisplayImageOptions getOptionsInViewflow(){
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true)
                .preProcessor(new BitmapProcessor() {
                    @Override
                    public Bitmap process(Bitmap bitmap) {

                        int height = UiUtils.dip2px(140);
                        int width = UiUtils.getPhoneWidth();
                        System.out.println("wisely-----flow_width_1:" + bitmap.getWidth() + "-----height_1:" + bitmap.getHeight());
                        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
                        System.out.println("wisely-----flow_width_2:" + scaledBitmap.getWidth() + "-----height_2:" + scaledBitmap.getHeight());

                        return scaledBitmap;
                    }
                })
                .bitmapConfig(Bitmap.Config.RGB_565)
//                .showImageForEmptyUri(R.drawable.empty_photo)
//                .showImageOnFail(R.drawable.empty_photo)
//                .showStubImage(R.drawable.empty_photo)
                .build();
        return options;
    }


}
