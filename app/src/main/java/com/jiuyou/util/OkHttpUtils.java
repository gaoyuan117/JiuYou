/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014. Ray
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.jiuyou.util;

import android.content.Context;

import com.jiuyou.global.AppConfig;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * OkHttpClient自定义工具类
 */
public class OkHttpUtils {

    private static OkHttpClient singleton;

    public static OkHttpClient getInstance(Context context) {
        if (singleton == null) {
            synchronized (OkHttpUtils.class) {
                if (singleton == null) {
                    File cacheDir = new File(context.getCacheDir(), AppConfig.RESPONSE_CACHE);

                    singleton = new OkHttpClient();
                    try {
                    	//singleton.setCache(null);
                        singleton.setCache(new Cache(cacheDir, AppConfig.RESPONSE_CACHE_SIZE));
                         
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    singleton.setConnectTimeout(AppConfig.HTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
                    singleton.setReadTimeout(AppConfig.HTTP_READ_TIMEOUT, TimeUnit.MILLISECONDS);
                }
            }
        }
        return singleton;
    }
}
