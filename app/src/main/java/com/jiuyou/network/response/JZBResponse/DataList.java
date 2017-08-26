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
import java.util.List;

/**
 * 首页商品
 */
public class DataList extends AbstractResponse implements Serializable {

    @ParamName("id")
    private String id;
    @ParamName("title")
    private String title;
    @ParamName("price")
    private String price;
    @ParamName("masterImg")
    private String masterImg;
    @ParamName("comment")
    private String comment;
    @ParamName("comm_time")
    private String comm_time;
    @ParamName("avatar")
    private String avatar;
    @ParamName("nickname")
    private String nickname;
    @ParamName("product_name")
    private String product_name;
    @ParamName("product_id")
    private String product_id;
    @ParamName("master_img")
    private String master_img;
    @ParamName("body")
    private String body;
    @ParamName("Full_time")
    private String Full_time;
    @ParamName("update_time")
    private String update_time;
    @ParamName("status")
    private String status;
    @ParamName("icon")
    private String icon;
    @ParamName("order_no")
    private String order_no;
    @ParamName("pkcode")
    private String pkcode;
    @ParamName("qrcode")
    private String qrcode;
    @ParamName("rel_price")
    private String rel_price;
    @ParamName("count")
    private String count;
    @ParamName("order_detail")
    private List<OrderDetail> order_details;

    public String getOrder_no() {
        return order_no;
    }

    public String getPkcode() {
        return pkcode;
    }

    public String getQrcode() {
        return qrcode;
    }

    public String getRel_price() {
        return rel_price;
    }

    public String getCount() {
        return count;
    }

    public List<OrderDetail> getOrder_details() {
        return order_details;
    }

    public String getBody() {
        return body;
    }

    public String getFull_time() {
        return Full_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public String getStatus() {
        return status;
    }

    public String getIcon() {
        return icon;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getProduct_id() {
        return product_id;
    }

    public String getMaster_img() {
        return master_img;
    }

    public String getComment() {
        return comment;
    }

    public String getComm_time() {
        return comm_time;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getNickname() {
        return nickname;
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

    public String getMasterImg() {
        return masterImg;
    }

}


