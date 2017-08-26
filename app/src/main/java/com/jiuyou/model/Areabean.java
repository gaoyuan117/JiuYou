package com.jiuyou.model;

import java.io.Serializable;

/**
 * Created by gaoyuan on 2017/8/11.
 */

public class Areabean implements Serializable {

    /**
     * id : 474
     * area_id : 210202
     * area : 中山区
     * father : 210200
     */

    private String id;
    private String area_id;
    private String area;
    private String father;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }
}
