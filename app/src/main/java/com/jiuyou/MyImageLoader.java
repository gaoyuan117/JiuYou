package com.jiuyou;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jiuyou.global.AppConfig;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by gaoyuan on 2017/9/5.
 */

public class MyImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {

        Glide.with(context).load(AppConfig.ENDPOINTPIC + path).crossFade().into(imageView);

    }
}
