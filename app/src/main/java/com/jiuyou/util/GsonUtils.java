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

import com.google.gson.FieldNamingPolicy;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jiuyou.global.AppConfig;
import com.jiuyou.network.annotate.ParamName;
import com.jiuyou.network.response.OtherResponse.Gender;

import java.lang.reflect.Field;

/**
 * 自定义的Gson
 */
public class GsonUtils {

    public static Gson newInstance() {
        GsonBuilder builder = new GsonBuilder();
        // 自定义的反序列化适配器
        builder.registerTypeAdapter(Gender.class, new Gender.GenderAdapter());

        builder.setFieldNamingStrategy(new AnnotateNaming());
        if (AppConfig.DEBUG) {
            builder.setPrettyPrinting();
        }

        return builder.create();
    }

    private static class AnnotateNaming implements FieldNamingStrategy {

        @Override
        public String translateName(Field field) {
            ParamName a = field.getAnnotation(ParamName.class);
            return a != null ? a.value() : FieldNamingPolicy.IDENTITY.translateName(field);
        }
    }
}
