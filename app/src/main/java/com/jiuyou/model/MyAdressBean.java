package com.jiuyou.model;

import java.io.Serializable;

/**
 * Created by gaoyuan on 2017/8/11.
 */

public class MyAdressBean  implements Serializable{

    /**
     * id : 8
     * realname : 高原
     * mobile : 14763766689
     * address : 凤凰路海信创智谷
     * province_id : 370000
     * province : 山东
     * city_id : 370100
     * city : 济南市
     * area_id : 370102
     * area : 历下区
     * is_default : 0
     */

    private String id;
    private String realname;
    private String mobile;
    private String address;
    private String province_id;
    private String province;
    private String city_id;
    private String city;
    private String area_id;
    private String area;
    private String is_default;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvince_id() {
        return province_id;
    }

    public void setProvince_id(String province_id) {
        this.province_id = province_id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getIs_default() {
        return is_default;
    }

    public void setIs_default(String is_default) {
        this.is_default = is_default;
    }
}
