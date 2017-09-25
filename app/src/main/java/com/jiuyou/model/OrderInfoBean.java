package com.jiuyou.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gaoyuan on 2017/8/14.
 */

public class OrderInfoBean implements Serializable {


    /**
     * id : 978
     * uid : 51
     * order_no : 2017090237150
     * status : 7
     * pkcode : null
     * qrcode : qrcode/2017090237150.png
     * pickup_mode : 配送
     * create_time : 2017-09-02 21:41:16
     * send_time : 23:17
     * detail_Info : [{"id":"1185","order_id":"978","order_no":"2017090237150","product_id":"64","quantity":"1","price":"425.00","product_name":"55度内盒西凤酒","pick":"0","is_comment":"0","comment":null,"comm_time":null,"goods_img":"Product/masterImg/2017-08-24/599eb0f8556c1.jpg"}]
     * total_price : 425
     * pay_channel : 余额
     * receive : {"address":"山东济南市历下区济南商贸学校","mobile":"17615866248","realname":"哈哈","lng":"117.15596810774","lat":"36.698253363471"}
     * ps_lng : 117.170205
     * ps_lat : 36.66639
     */

    private String id;
    private String uid;
    private String order_no;
    private String status;
    private Object pkcode;
    private String qrcode;
    private String pickup_mode;
    private String create_time;
    private String send_time;
    private String total_price;
    private String pay_channel;
    private ReceiveBean receive;
    private String ps_lng;
    private String ps_lat;
    private List<DetailInfoBean> detail_Info;

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

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getPkcode() {
        return pkcode;
    }

    public void setPkcode(Object pkcode) {
        this.pkcode = pkcode;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getPickup_mode() {
        return pickup_mode;
    }

    public void setPickup_mode(String pickup_mode) {
        this.pickup_mode = pickup_mode;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getSend_time() {
        return send_time;
    }

    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getPay_channel() {
        return pay_channel;
    }

    public void setPay_channel(String pay_channel) {
        this.pay_channel = pay_channel;
    }

    public ReceiveBean getReceive() {
        return receive;
    }

    public void setReceive(ReceiveBean receive) {
        this.receive = receive;
    }

    public String getPs_lng() {
        return ps_lng;
    }

    public void setPs_lng(String ps_lng) {
        this.ps_lng = ps_lng;
    }

    public String getPs_lat() {
        return ps_lat;
    }

    public void setPs_lat(String ps_lat) {
        this.ps_lat = ps_lat;
    }

    public List<DetailInfoBean> getDetail_Info() {
        return detail_Info;
    }

    public void setDetail_Info(List<DetailInfoBean> detail_Info) {
        this.detail_Info = detail_Info;
    }

    public static class ReceiveBean  implements Serializable{
        /**
         * address : 山东济南市历下区济南商贸学校
         * mobile : 17615866248
         * realname : 哈哈
         * lng : 117.15596810774
         * lat : 36.698253363471
         */

        private String address;
        private String mobile;
        private String realname;
        private String lng;
        private String lat;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }
    }

    public static class DetailInfoBean implements Serializable {
        /**
         * id : 1185
         * order_id : 978
         * order_no : 2017090237150
         * product_id : 64
         * quantity : 1
         * price : 425.00
         * product_name : 55度内盒西凤酒
         * pick : 0
         * is_comment : 0
         * comment : null
         * comm_time : null
         * goods_img : Product/masterImg/2017-08-24/599eb0f8556c1.jpg
         */

        private String id;
        private String order_id;
        private String order_no;
        private String product_id;
        private String quantity;
        private String price;
        private String product_name;
        private String pick;
        private String is_comment;
        private Object comment;
        private Object comm_time;
        private String goods_img;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public String getPick() {
            return pick;
        }

        public void setPick(String pick) {
            this.pick = pick;
        }

        public String getIs_comment() {
            return is_comment;
        }

        public void setIs_comment(String is_comment) {
            this.is_comment = is_comment;
        }

        public Object getComment() {
            return comment;
        }

        public void setComment(Object comment) {
            this.comment = comment;
        }

        public Object getComm_time() {
            return comm_time;
        }

        public void setComm_time(Object comm_time) {
            this.comm_time = comm_time;
        }

        public String getGoods_img() {
            return goods_img;
        }

        public void setGoods_img(String goods_img) {
            this.goods_img = goods_img;
        }
    }
}
