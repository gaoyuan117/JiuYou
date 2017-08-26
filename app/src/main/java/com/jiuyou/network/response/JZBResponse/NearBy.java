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
 * 商家周边信息
 */
public class NearBy extends AbstractResponse implements Serializable {

    @ParamName("cupboard_name")
    private String cupboard_name;
    @ParamName("linkman")
    private String linkman;
    @ParamName("link_type")
    private String link_type;
    @ParamName("link_address")
    private String link_address;
    @ParamName("province")
    private String province;
    @ParamName("city")
    private String city;
    @ParamName("dist")
    private String dist;
    @ParamName("address")
    private String address;
    @ParamName("lng")
    private String lng;
    @ParamName("lat")
    private String lat;
    @ParamName("type_name")
    private String type_name;
    @ParamName("path")
    private String path;

    public void setLng(String lng) {
        this.lng = lng;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCupboard_name() {
        return cupboard_name;
    }

    public String getLinkman() {
        return linkman;
    }

    public String getLink_type() {
        return link_type;
    }

    public String getLink_address() {
        return link_address;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public String getDist() {
        return dist;
    }

    public String getAddress() {
        return address;
    }

    public String getLng() {
        return lng;
    }

    public String getLat() {
        return lat;
    }

    public String getType_name() {
        return type_name;
    }

    public String getPath() {
        return path;
    }
}
