/*
 * PayConstants     2016/12/2 17:11
 * Copyright (c) 2016 Koterwong All right reserved
 */
package com.jiuyou.wxpay;

/**
 * Created by Koterwong on 2016/12/2 17:11
 */
public interface Constants {
  interface AliPay {
    /** 是否使用RSA2加密方式 */
    boolean USER_RSA2 = true;
    /** 支付宝AppID */
    String APPID = "2017020405513862";
    /** 商户私钥，pkcs8格式 */
    String RSA_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCs2AKPDyNOG6/NDZ3gx6Tjv0q4/KkRUXfq6MquLf4n7BrgxwN5QKQ/xusngWkigVuageFoz0QY7r81iRaocfk7oQx2VRCOvIl/QZ/JEuk1qRysfDh13UyLsL3zK6QNJ14eDOGXBXB/WaPm/imQTx+h84pACx7BXyyPSC4kccKKcT7mHhxldNgp4Ixn5Qz40ElDG5392eXIEaRyjeGgb1ouchbE831nH9MRYXTk3z1qg5HlVLkbDocOxf1puA3AktjhCaD89GCITgIw+69H/iCNzY7WxPmdOrJAwdZu/2AeoWIcm+wxbO8fUz/6bHAZf9DO7HXGF1gU3SA8wjZtwTBfAgMBAAECggEAY8gIIiJxFURyhwQXYo6eqEPklUp3J+JKQjjF2SwAD3gERENfw/4HVDI3ywSwdxUKlvXn9SPCgkpB5FwR0oiJg10D3sRY3K4HCooN/nyGWU4ZmZHU3mY1LGeKIPnOqTFS6jIxgYfIXByZvF56DC9BabM42qQQyz1wcf8PdEagMl8e63dR7UACZ7bwdv5KNOB5YJKJ4NB1XkKQQ6sNSBxXDIAK1yHrWcUofx4vVvIMul/rlmaHHISu2LgCC15SDVFnTWRzz9kpFStpI7JA70svC3/NJ8cXNNlzpfYRnLA17uvboBHlkIMKUnquW57/LDD3qLTwKDTzLLWkNmRFuPjrYQKBgQDdRqRNvi4LbXsmPlG9/gH8nR3WrmXKU8KRT1TeR0zOKY78LwsdBQoqEnvNl/K2HW6JnylkRn5W9cMnqmm5BFp/KM3LP7SWG22Fzsja/97fyz0pU7seCmwAh/K3IscqahzT5CGgBNH7VrpjslCDkjzjmmbPdmVL8sWnrc38mxUgxwKBgQDH97HL6nsbOmCbdHznUveq3saI3GHBam4u4NXdh5h6OzbEoXyVn4+I5N5v+/mQFL2fzHfO+90nOWcfEn1ZDf0uTRGy/CgWp0EKUleba4JyNEm5S9meweke08uewhB8n3ZQumHfkhxY52eOCHENrPn1b3CZrW4CN0dNmuZy3moLqQKBgH95YdAzvpzwvmBiyH6WpZhc/0KdB6EOrek+dZr0Imgmguv/Qfy+2YMxUMc5QSbvP63i1lqhTclVHc9tGijvPB9DZ4MuuR88v9S1MvsPKttsX1i+lu6QfHW12/rq+ygRJ3heVYy1Gi8cYJZstHuHtLI+Ufo0r9iYDIwz0ZYz1XEjAoGAFixWD7lxayR+/93b176wbccEFrrxMARAMXyG2miltLAKM6WAbgriXJicPaigFKToHecLRe1RqX7I+34OXiiZ8V9kXgdt3o0vPhjBVB+IuGcp4Z0ShXMVCXEMvA7iyx7XfL4eCi9ma0lFh09SUeTjVp7Dypabgy1j/6v0b7pP4fkCgYEAlfa+1cxS7Rvf69HcCXzCywT/sp+N1KX677RXzsVvtssXvGIyKp9F6n1bPKEdITTbaOHx8w9j9dct7+dXxNIEaLabUTguTB5HTvWPPQjHZhwNhacAiSGw1AHKFmzkFA3IplhzjwUeIX5IiWfmP+SmXJC1BopC20vk9X3qk6T/Wno=";
    /** 支付宝回调后台的NotifyUrl */
    String NOTIFY_URL = "http://47.93.9.206/electric/index.php/api/Notify/index.html";
    /** 支付宝公钥 */
    String RSA_PUBLIC = "";
  }

  interface WxPay {
    /** 微信 AppID，在微信开放平台创建应用，并开通支付能力 */
    String APP_ID = "wx3f440ed33ab2e0f2";
    /** 商户号 */
    String WX_SHOP_NUM = "1447967202";
    /** 微信商户密钥 */
    String KEY = "juzhenbaojuzhenbaojuzhenbaojuzhe";
  }
}
