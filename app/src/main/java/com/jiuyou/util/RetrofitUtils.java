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

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

public class RetrofitUtils {

    private static RestAdapter singleton;

    public static <T> T createApi(Context context, Class<T> clazz) {
        if (singleton == null) {
            synchronized (RetrofitUtils.class) {
                if (singleton == null) {
                    RestAdapter.Builder builder = new RestAdapter.Builder();
                    builder.setEndpoint(AppConfig.ENDPOINT);
                    builder.setConverter(new GsonConverter(GsonUtils.newInstance()));
                    builder.setClient(new OkClient(OkHttpUtils.getInstance(context)));
                    builder.setLogLevel(
                            AppConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE);
                    singleton = builder.build();
                }
            }
        }
        return singleton.create(clazz);
    }
    
    /*public static <T> T createSywApi(Context context, Class<T> clazz) {
        if (singleton == null) {
            synchronized (RetrofitUtils.class) {
                if (singleton == null) {
                    RestAdapter.Builder builder = new RestAdapter.Builder();
                    builder.setEndpoint(Config.ENDPOINT_SYW);
                    builder.setConverter(new GsonConverter(GsonUtils.newInstance()));
                    builder.setClient(new OkClient(OkHttpUtils.getInstance(context)));
                    builder.setLogLevel(
                            Config.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE);
                    singleton = builder.build();
                }
            }
        }
        return singleton.create(clazz);
    }*/
}
