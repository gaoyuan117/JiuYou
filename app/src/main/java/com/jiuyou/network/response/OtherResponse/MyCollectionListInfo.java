package com.jiuyou.network.response.OtherResponse;

import java.util.List;

/**
 * Created by 子皓 on 2016/9/28.
 */
public class MyCollectionListInfo {

    /**
     * statuscode : 0
     * result : [{"id":"1","stage_id":"1","first_catalog":"现代文阅读","second_catalog":"表达方式及表现手法","title":"理解文中重要词、句、段意","video_id":"83FCDE65816741129C33DC5901307461","pic_url":"http://www.234.com","total_time":"1200000"},{"id":"3","stage_id":"2","first_catalog":"现代文阅读","second_catalog":"表达方式及表现手法","title":"有理数不知道123","video_id":"83FCDE65816741129C33DC5901307461","pic_url":"http://www.456.com","total_time":"1200000"}]
     */

    private int statuscode;
    /**
     * id : 1
     * stage_id : 1
     * first_catalog : 现代文阅读
     * second_catalog : 表达方式及表现手法
     * title : 理解文中重要词、句、段意
     * video_id : 83FCDE65816741129C33DC5901307461
     * pic_url : http://www.234.com
     * total_time : 1200000
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
        private String id;
        private String stage_id;
        private String first_catalog;
        private String second_catalog;
        private String title;
        private String video_id;
        private String pic_url;
        private String total_time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStage_id() {
            return stage_id;
        }

        public void setStage_id(String stage_id) {
            this.stage_id = stage_id;
        }

        public String getFirst_catalog() {
            return first_catalog;
        }

        public void setFirst_catalog(String first_catalog) {
            this.first_catalog = first_catalog;
        }

        public String getSecond_catalog() {
            return second_catalog;
        }

        public void setSecond_catalog(String second_catalog) {
            this.second_catalog = second_catalog;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getVideo_id() {
            return video_id;
        }

        public void setVideo_id(String video_id) {
            this.video_id = video_id;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public String getTotal_time() {
            return total_time;
        }

        public void setTotal_time(String total_time) {
            this.total_time = total_time;
        }
    }
}
