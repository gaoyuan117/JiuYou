package com.jiuyou.model;

import java.util.List;

/**
 * Created by gaoyuan on 2017/8/14.
 */

public class OrderTimeBean {

    /**
     * code : 200
     * message : 操作成功
     * data : {"time":["今日（周六) 立即送出(大约14:14)","14:33","14:52","15:11","15:30","15:49"]}
     */

    private int code;
    private String message;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<String> time;

        public List<String> getTime() {
            return time;
        }

        public void setTime(List<String> time) {
            this.time = time;
        }
    }
}
