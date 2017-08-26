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

package com.jiuyou.network.response.OtherResponse;

import com.jiuyou.network.annotate.ParamName;

import java.io.Serializable;

/**
 *搜索结果
 */
public class SourceInfos implements Serializable {

    @ParamName("first_name")
    private String first_name;
    @ParamName("first_catalog")
    private String first_catalog;
    @ParamName("second_name")
    private String second_name;
    @ParamName("second_catalog")
    private String second_catalog;
    @ParamName("third_name")
    private String third_name;
    @ParamName("source_url")
    private String source_url;
    @ParamName("pic_url")
    private String pic_url;

    private boolean flag;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getFirst_catalog() {
        return first_catalog;
    }

    public void setFirst_catalog(String first_catalog) {
        this.first_catalog = first_catalog;
    }

    public String getSecond_name() {
        return second_name;
    }

    public void setSecond_name(String second_name) {
        this.second_name = second_name;
    }

    public String getSecond_catalog() {
        return second_catalog;
    }

    public void setSecond_catalog(String second_catalog) {
        this.second_catalog = second_catalog;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getSource_url() {
        return source_url;
    }

    public void setSource_url(String source_url) {
        this.source_url = source_url;
    }

    public String getThird_name() {
        return third_name;
    }

    public void setThird_name(String third_name) {
        this.third_name = third_name;
    }

    public SourceInfos(boolean flag,String first_name, String first_catalog, String second_name, String second_catalog, String third_name, String source_url, String pic_url) {
        this.flag=flag;
        this.first_name = first_name;
        this.first_catalog = first_catalog;
        this.second_name = second_name;
        this.second_catalog = second_catalog;
        this.third_name = third_name;
        this.source_url = source_url;
        this.pic_url = pic_url;
    }
}
