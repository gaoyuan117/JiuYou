package com.jiuyou.db.model;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table REGION.
 */
public class Region {

    private Long id;
    /** Not-null value. */
    private String code;
    /** Not-null value. */
    private String name;
    /** Not-null value. */
    private String firstChar;
    private Integer level;
    private String parentCode;
    private Integer isHot;
    private Integer status;

    public Region() {
    }

    public Region(Long id) {
        this.id = id;
    }

    public Region(Long id, String code, String name, String firstChar, Integer level, String parentCode, Integer isHot, Integer status) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.firstChar = firstChar;
        this.level = level;
        this.parentCode = parentCode;
        this.isHot = isHot;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Not-null value. */
    public String getCode() {
        return code;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setCode(String code) {
        this.code = code;
    }

    /** Not-null value. */
    public String getName() {
        return name;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setName(String name) {
        this.name = name;
    }

    /** Not-null value. */
    public String getFirstChar() {
        return firstChar;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setFirstChar(String firstChar) {
        this.firstChar = firstChar;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public Integer getIsHot() {
        return isHot;
    }

    public void setIsHot(Integer isHot) {
        this.isHot = isHot;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
