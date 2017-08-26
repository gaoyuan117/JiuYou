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

public class OrderDetail extends AbstractResponse implements Serializable {

    @ParamName("product_name")
    private String product_name;
    @ParamName("price")
    private String price;
    @ParamName("quantity")
    private String quantity;
    @ParamName("product_id")
    private String product_id;
    @ParamName("is_comment")
    private String is_comment;
    @ParamName("master_img")
    private String master_img;

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public void setIs_comment(String is_comment) {
        this.is_comment = is_comment;
    }

    public void setMaster_img(String master_img) {
        this.master_img = master_img;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getPrice() {
        return price;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getProduct_id() {
        return product_id;
    }

    public String getIs_comment() {
        return is_comment;
    }

    public String getMaster_img() {
        return master_img;
    }
}
