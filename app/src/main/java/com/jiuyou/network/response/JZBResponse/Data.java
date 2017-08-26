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

package com.jiuyou.network.response.JZBResponse;

import com.jiuyou.network.annotate.ParamName;
import com.jiuyou.network.response.AbstractResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 首页商品/商品详情
 */
public class Data extends AbstractResponse implements Serializable {

    @ParamName("id")
    private String id;
    @ParamName("title")
    private String title;
    @ParamName("price")
    private String price;
    @ParamName("old_price")
    private String old_price;
    @ParamName("description")
    private String description;
    @ParamName("Carousel_img")
    private List<String> Carousel_img;
    @ParamName("moreImg")
    private List<String> moreImg;
    @ParamName("verify")
    private String verify;
    @ParamName("page")
    private String page;
    @ParamName("totalPage")
    private String totalPage;
    @ParamName("data-list")
    private ArrayList<DataList> data_list;
    @ParamName("token")
    private String token;
    @ParamName("uid")
    private String uid;
    @ParamName("quantity")
    private String quantity;

    public String getOld_price() {
        return old_price;
    }

    public void setOld_price(String old_price) {
        this.old_price = old_price;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getToken() {
        return token;
    }

    public String getUid() {
        return uid;
    }

    public String getVerify() {
        return verify;
    }
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getCarousel_img() {
        return Carousel_img;
    }

    public List<String> getMoreImg() {
        return moreImg;
    }



    public String getPage() {
        return page;
    }

    public String getTotalPage() {
        return totalPage;
    }

    public ArrayList<DataList> getData_list() {
        return data_list;
    }
}
