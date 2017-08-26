package com.jiuyou.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gaoyuan on 2017/8/23.
 */

public class TuiJianBean implements Serializable {

    /**
     * code : 200
     * message : 操作成功
     * data : {"ztlist":[{"id":"50","uid":"35","nickname":"15737978615","sex":"0","birthday":"0000-00-00","qq":"","score":"0","login":"1","reg_ip":"124132","reg_time":"1498010796","last_login_ip":"119181","last_login_time":"1500079809","avatar":"Picture/2017-06-21/5949d5e1cdc29.jpg","status":"1","pay_pwd":"e10adc3949ba59abbe56e057f20f883e","amount":"19779.3315","cd_acc":null,"referrer":"13639447983"},{"id":"60","uid":"45","nickname":"18766876730","sex":"0","birthday":"0000-00-00","qq":"","score":"0","login":"1","reg_ip":"124132","reg_time":"1498877842","last_login_ip":"124132","last_login_time":"1501296494","avatar":null,"status":"1","pay_pwd":"e10adc3949ba59abbe56e057f20f883e","amount":"0","cd_acc":null,"referrer":"13639447983"}],"jlist":[{"id":"46","uid":"31","nickname":"13675478823","sex":"0","birthday":"0000-00-00","qq":"","score":"0","login":"1","reg_ip":"124132","reg_time":"1497574064","last_login_ip":"124132","last_login_time":"1497574068","avatar":null,"status":"1","pay_pwd":"11154cc9c7a17501e32cc6a019fb7192","amount":"9.8","cd_acc":null,"referrer":"15737978615"},{"id":"61","uid":"46","nickname":"15689788825","sex":"0","birthday":"0000-00-00","qq":"","score":"0","login":"1","reg_ip":"124132","reg_time":"1500549564","last_login_ip":"124132","last_login_time":"1500549574","avatar":null,"status":"1","pay_pwd":null,"amount":"0","cd_acc":null,"referrer":"18766876730"}]}
     */
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

    public static class ZtlistBean implements Serializable {
        /**
         * id : 50
         * uid : 35
         * nickname : 15737978615
         * sex : 0
         * birthday : 0000-00-00
         * qq :
         * score : 0
         * login : 1
         * reg_ip : 124132
         * reg_time : 1498010796
         * last_login_ip : 119181
         * last_login_time : 1500079809
         * avatar : Picture/2017-06-21/5949d5e1cdc29.jpg
         * status : 1
         * pay_pwd : e10adc3949ba59abbe56e057f20f883e
         * amount : 19779.3315
         * cd_acc : null
         * referrer : 13639447983
         */

        private String id;
        private String uid;
        private String nickname;
        private String sex;
        private String birthday;
        private String qq;
        private String score;
        private String login;
        private String reg_ip;
        private String reg_time;
        private String last_login_ip;
        private String last_login_time;
        private String avatar;
        private String status;
        private String pay_pwd;
        private String amount;
        private String mobile;
        private Object cd_acc;
        private String referrer;

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getReg_ip() {
            return reg_ip;
        }

        public void setReg_ip(String reg_ip) {
            this.reg_ip = reg_ip;
        }

        public String getReg_time() {
            return reg_time;
        }

        public void setReg_time(String reg_time) {
            this.reg_time = reg_time;
        }

        public String getLast_login_ip() {
            return last_login_ip;
        }

        public void setLast_login_ip(String last_login_ip) {
            this.last_login_ip = last_login_ip;
        }

        public String getLast_login_time() {
            return last_login_time;
        }

        public void setLast_login_time(String last_login_time) {
            this.last_login_time = last_login_time;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPay_pwd() {
            return pay_pwd;
        }

        public void setPay_pwd(String pay_pwd) {
            this.pay_pwd = pay_pwd;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public Object getCd_acc() {
            return cd_acc;
        }

        public void setCd_acc(Object cd_acc) {
            this.cd_acc = cd_acc;
        }

        public String getReferrer() {
            return referrer;
        }

        public void setReferrer(String referrer) {
            this.referrer = referrer;
        }
    }

    public static class JlistBean implements Serializable {
        /**
         * id : 46
         * uid : 31
         * nickname : 13675478823
         * sex : 0
         * birthday : 0000-00-00
         * qq :
         * score : 0
         * login : 1
         * reg_ip : 124132
         * reg_time : 1497574064
         * last_login_ip : 124132
         * last_login_time : 1497574068
         * avatar : null
         * status : 1
         * pay_pwd : 11154cc9c7a17501e32cc6a019fb7192
         * amount : 9.8
         * cd_acc : null
         * referrer : 15737978615
         */

        private String id;
        private String uid;
        private String nickname;
        private String sex;
        private String birthday;
        private String qq;
        private String score;
        private String login;
        private String reg_ip;
        private String reg_time;
        private String last_login_ip;
        private String last_login_time;
        private Object avatar;
        private String status;
        private String pay_pwd;
        private String amount;
        private String mobile;
        private Object cd_acc;
        private String referrer;

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getReg_ip() {
            return reg_ip;
        }

        public void setReg_ip(String reg_ip) {
            this.reg_ip = reg_ip;
        }

        public String getReg_time() {
            return reg_time;
        }

        public void setReg_time(String reg_time) {
            this.reg_time = reg_time;
        }

        public String getLast_login_ip() {
            return last_login_ip;
        }

        public void setLast_login_ip(String last_login_ip) {
            this.last_login_ip = last_login_ip;
        }

        public String getLast_login_time() {
            return last_login_time;
        }

        public void setLast_login_time(String last_login_time) {
            this.last_login_time = last_login_time;
        }

        public Object getAvatar() {
            return avatar;
        }

        public void setAvatar(Object avatar) {
            this.avatar = avatar;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPay_pwd() {
            return pay_pwd;
        }

        public void setPay_pwd(String pay_pwd) {
            this.pay_pwd = pay_pwd;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public Object getCd_acc() {
            return cd_acc;
        }

        public void setCd_acc(Object cd_acc) {
            this.cd_acc = cd_acc;
        }

        public String getReferrer() {
            return referrer;
        }

        public void setReferrer(String referrer) {
            this.referrer = referrer;
        }
    }
}
