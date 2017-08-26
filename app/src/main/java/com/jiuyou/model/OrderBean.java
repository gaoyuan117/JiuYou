package com.jiuyou.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gaoyuan on 2017/8/14.
 */

public class OrderBean implements Serializable {

    /**
     * id : 854
     * uid : 53
     * order_no : 2017081473906
     * order_type : 0
     * pkcode : 000436
     * qrcode : qrcode/2017081473906.png
     * rel_price : 0.01
     * total_price : 0.01
     * pay_channel : QkPay
     * is_read : 0
     * status : 1
     * is_valid : 0
     * create_time : 2017-08-14 10:45:55
     * update_time : 1502678768
     * receive_id : 0
     * pickup_mode : 配送
     * send_time :
     * pay_type : 0
     * pid : null
     * count : 1
     * order_detail : [{"id":"1046","order_id":"854","order_no":"2017081473906","product_id":"57","quantity":"1","price":"0.01","product_name":"53°茅台迎宾酒500ml","pick":"0","is_comment":"0","comment":null,"comm_time":null,"master_img":"Product/masterImg/2017-08-12/598e56188cf6c.jpg"}]
     */

    private String id;
    private String uid;
    private String order_no;
    private String order_type;
    private String pkcode;
    private String qrcode;
    private String rel_price;
    private String total_price;
    private String pay_channel;
    private String is_read;
    private String status;
    private String is_valid;
    private String create_time;
    private String update_time;
    private String receive_id;
    private String pickup_mode;
    private String send_time;
    private String pay_type;
    private Object pid;
    private int count;
    private List<OrderDetailBean> order_detail;

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

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
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

    public String getRel_price() {
        return rel_price;
    }

    public void setRel_price(String rel_price) {
        this.rel_price = rel_price;
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

    public String getIs_read() {
        return is_read;
    }

    public void setIs_read(String is_read) {
        this.is_read = is_read;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIs_valid() {
        return is_valid;
    }

    public void setIs_valid(String is_valid) {
        this.is_valid = is_valid;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getReceive_id() {
        return receive_id;
    }

    public void setReceive_id(String receive_id) {
        this.receive_id = receive_id;
    }

    public String getPickup_mode() {
        return pickup_mode;
    }

    public void setPickup_mode(String pickup_mode) {
        this.pickup_mode = pickup_mode;
    }

    public String getSend_time() {
        return send_time;
    }

    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public Object getPid() {
        return pid;
    }

    public void setPid(Object pid) {
        this.pid = pid;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<OrderDetailBean> getOrder_detail() {
        return order_detail;
    }

    public void setOrder_detail(List<OrderDetailBean> order_detail) {
        this.order_detail = order_detail;
    }

    public static class OrderDetailBean implements Serializable {
        /**
         * id : 1046
         * order_id : 854
         * order_no : 2017081473906
         * product_id : 57
         * quantity : 1
         * price : 0.01
         * product_name : 53°茅台迎宾酒500ml
         * pick : 0
         * is_comment : 0
         * comment : null
         * comm_time : null
         * master_img : Product/masterImg/2017-08-12/598e56188cf6c.jpg
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
        private String master_img;

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

        public String getMaster_img() {
            return master_img;
        }

        public void setMaster_img(String master_img) {
            this.master_img = master_img;
        }
    }
}
