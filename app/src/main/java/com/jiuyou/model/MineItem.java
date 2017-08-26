package com.jiuyou.model;

/**
 * Created by huangzuoliang on 16/6/23.
 */
public class MineItem {
    private int icon;
    private String item;
    private Boolean isVisible ;


    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Boolean getVisible() {
        return isVisible;
    }

    public void setVisible(Boolean visible) {
        isVisible = visible;
    }
}
