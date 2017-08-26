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

/**
 * 购物车
 */
public class Cart extends AbstractResponse implements Serializable {

    @ParamName("id")
    private String id;
    @ParamName("price")
    private String price;
    @ParamName("quantity")
    private String quantity;
    @ParamName("title")
    private String title;
    @ParamName("product_id")
    private String product_id;
    @ParamName("masterImg")
    private String masterImg;
    @ParamName("boot")
    private String boot;

    public String getBoot() {
        return boot;
    }

    public String getId() {
        return id;
    }

    public String getPrice() {
        return price;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getTitle() {
        return title;
    }

    public String getProduct_id() {
        return product_id;
    }

    public String getMasterImg() {
        return masterImg;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public void setMasterImg(String masterImg) {
        this.masterImg = masterImg;
    }
}
