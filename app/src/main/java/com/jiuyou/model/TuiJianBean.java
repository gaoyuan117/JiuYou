package com.jiuyou.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gaoyuan on 2017/8/23.
 */

public class TuiJianBean implements Serializable {


    private List<ZtlistBean> ztlist;
    private List<JlistBean> jlist;

    public List<ZtlistBean> getZtlist() {
        return ztlist;
    }

    public void setZtlist(List<ZtlistBean> ztlist) {
        this.ztlist = ztlist;
    }

    public List<JlistBean> getJlist() {
        return jlist;
    }

    public void setJlist(List<JlistBean> jlist) {
        this.jlist = jlist;
    }

    public static class ZtlistBean {
        /**
         * mobile : 15634447686
         * nickname : 15634447686
         */

        private String mobile;
        private String nickname;

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
    }

    public static class JlistBean {
        /**
         * mobile : 15737978615
         * nickname : 15737978615
         */

        private String mobile;
        private String nickname;

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
    }
}
