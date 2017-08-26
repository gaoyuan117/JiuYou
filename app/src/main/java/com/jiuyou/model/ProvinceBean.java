package com.jiuyou.model;

import java.io.Serializable;

/**
 * Created by gaoyuan on 2017/8/10.
 */

public class ProvinceBean implements Serializable {

    /**
     * id : 1
     * province_id : 110000
     * province : 北京
     */

    private String id;
    private String province_id;
    private String province;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

}
