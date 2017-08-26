package com.jiuyou.network.response.OtherResponse;

import java.util.List;

/**
 * Created by 子皓 on 2016/9/28.
 */
public class MyCollectionInfo {


    /**
     * statuscode : 0
     * result : [{"count":"2","object_name":"语文"},{"count":"2","object_name":"数学"}]
     */

    private int statuscode;
    /**
     * count : 2
     * object_name : 语文
     */

    private List<ResultBean> result;

    public int getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(int statuscode) {
        this.statuscode = statuscode;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        private String count;
        private String object_name;

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getObject_name() {
            return object_name;
        }

        public void setObject_name(String object_name) {
            this.object_name = object_name;
        }
    }
}
