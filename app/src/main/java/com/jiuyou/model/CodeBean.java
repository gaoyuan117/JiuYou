package com.jiuyou.model;

/**
 * Created by gaoyuan on 2017/8/22.
 */

public class CodeBean {

    /**
     * code : 200
     * message : 操作成功
     * data : {"qrcode":"http://www.jiuks.com/Uploads/qrcode/44.png"}
     */


        /**
         * qrcode : http://www.jiuks.com/Uploads/qrcode/44.png
         */

        private String qrcode;

        public String getQrcode() {
            return qrcode;
        }

        public void setQrcode(String qrcode) {
            this.qrcode = qrcode;
        }
}
