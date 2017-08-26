package com.jiuyou.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gaoyuan on 2017/8/14.
 */

public class OrderInfoBean implements Serializable{

    /**
     * pkcode : 000399
     * qrcode : qrcode/2017081267056.png
     * pickup_mode : 自取
     * create_time : 2017-08-12 16:10:30
     * send_time :
     * detail_Info : [{"id":"1041","order_id":"850","order_no":"2017081267056","product_id":"57","quantity":"1","price":"0.01","product_name":"53°茅台迎宾酒500ml","pick":"0","is_comment":"0","comment":null,"comm_time":null},{"id":"1042","order_id":"850","order_no":"2017081267056","product_id":"56","quantity":"1","price":"0.01","product_name":"猪耳朵","pick":"0","is_comment":"0","comment":null,"comm_time":null}]
     * receive : {"address":"山东济宁市兖州市富阳路百丰国际商务中心1层","mobile":"13639447983","realname":"刘","lng":"116.813224","lat":"35.533252"}
     * ps_lng : null
     * ps_lat : null
     */

    private String pkcode;
    private String total_price;
    private String qrcode;
    private String pickup_mode;
    private String create_time;
    private String send_time;
    private String pay_type;
    private ReceiveBean receive;
    private Object ps_lng;
    private Object ps_lat;
    private List<DetailInfoBean> detail_Info;

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getPkcode() {
        return pkcode;
    }

    public void setPkcode(String pkcode) {
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

    public ReceiveBean getReceive() {
        return receive;
    }

    public void setReceive(ReceiveBean receive) {
        this.receive = receive;
    }

    public Object getPs_lng() {
        return ps_lng;
    }

    public void setPs_lng(Object ps_lng) {
        this.ps_lng = ps_lng;
    }

    public Object getPs_lat() {
        return ps_lat;
    }

    public void setPs_lat(Object ps_lat) {
        this.ps_lat = ps_lat;
    }

    public List<DetailInfoBean> getDetail_Info() {
        return detail_Info;
    }

    public void setDetail_Info(List<DetailInfoBean> detail_Info) {
        this.detail_Info = detail_Info;
    }

    public static class ReceiveBean implements Serializable{
        /**
         * address : 山东济宁市兖州市富阳路百丰国际商务中心1层
         * mobile : 13639447983
         * realname : 刘
         * lng : 116.813224
         * lat : 35.533252
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

    public static class DetailInfoBean implements Serializable{
        /**
         * id : 1041
         * order_id : 850
         * order_no : 2017081267056
         * product_id : 57
         * quantity : 1
         * price : 0.01
         * product_name : 53°茅台迎宾酒500ml
         * pick : 0
         * is_comment : 0
         * comment : null
         * comm_time : null
         */

        private String id;
        private String order_id;
        private String order_no;
        private String product_id;
        private String quantity;
        private String price;
        private String product_name;
        private String pick;
        private String goods_img;
        private String is_comment;
        private Object comment;
        private Object comm_time;

        public String getImg() {
            return goods_img;
        }

        public void setImg(String img) {
            this.goods_img = img;
        }

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
    }
}
