package com.jiuyou.alipay.util;

public class OrderInfoHelp2_0 {

    /**
     * App_id
     */
    public static final String APPID = "2017060107396917";
//  public static final String APPID = "2016081901774198";

    /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /** 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1 */
    public static final String RSA2_PRIVATE = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCGbWyz+NypW8vxS59rkZR6MIiCNcGw/rt6EyIfXOef7ef5dVJOH8orsT8Y356MbAZ/2vVlIQ+P6gone6VJ8ZIrtHpYctNBufnHZTPPaoFfZOyCVGn++KjkeYgWoiy6SDB+DxYDyvK/BDsHwlR22L9UNgHPmcN3ba2RFX95opPTQhOxkfoTzM1eDsBOutU0RR3ifSZfSHBBs/fPwU6Do9uhEgyHJHJ2WUanDRg29Bq9RhV+INbXgRA+zXPzwPRPlRcg1gfxL3eIOrj8eu2nbJJDgVn00sRTtxPkblsl3+3NSogIoOiGIUDITDxOWLob/4RffSgkpLpifvAD1RKoSopjAgMBAAECggEADI+1FKwwknLNf+K9JuQXp8NZPjGfw9ZJ1oJrMdsQmdU1VA4ufrZhwetyJfTfb+e4Q8JGJAkO1zfbcw0OyPYV3peixxkTvJSQTp2aVctyA2P+5b7VmMnLnm3s1ZCZ9V5BicYHHJueS1Mt4O+7zM88MbZKPQf1R/ervF7x5n/WqGrkvvnJMsdIDLkEXiRaumvyqw1SKDTLyEahQomaBl4OZIJ+UScQ0wWQ97QbqNsdhDjC6JZ4kcS7cLi2poCuPg//qm3oXmlX5qrPuS5WTUgnuTmv4GcExOiIKAqwpgudvlscLIbt7irKBvskydKlmPp7a1H4d+Chh2dTGb/jdx50YQKBgQDnaUWEfVR8jxmDPtcGssl5nUBkHS6LAlAV1TkNiwtv/aBKzGlhiuCXqFZN73nOvW3QeaodL5kMXonfk/2+NWQVwibX12FqY+xrN3dnALAvy7/w6vRGmHKw2XcY5RoG54fTljvWwW3JJ9sLteNPzDX6x1l5NOlqQ6kF2anCgd43HQKBgQCUtgzpcP6ikw1FdTCFJr4lZi2oxz6usSj10/Q/onp1Oru2ioQjZJl1Fj7tL54l5yto6VFjiD/AGEXyYSDRfmygntPbeoxocEYCsTJd3iWvfo1ck91FjTrPtLuz7P1B8e5ojFmYqWpjUfij7kLxGXC7WQrx6SoddD6mu9oqPBSPfwKBgQDYurRb8JFnExK+4+S5JkshtlAvM1F66ZCd0hPcBkSQ/4XXZ/iuXUYZY5Ro64c2RB2MKdZT/VcmyYfxOIZmVVTGOA7vCY33+Vvb0pfmhTN9oCLE/RgI4hBQZ2tuimtE2iVQ2hxy9rfetJAnOJOLEePgWkIHDbIqTvIHQ6MFQ2IeVQKBgQCNId1NKuyuc+rTcW2T4Hny2m8Yz8cgWYfrDbWvMs8PNKdzjS/suwucgHlF9rwbcrHTBiM019jQwPiZGC7iQx5qrXGYyUw5zdvTsEGBrlOLowobw0TrPiaDex3sm0ybajR9yj050gbirafQRE4JrMA9PTtS7JsIiwruiDd7f7tvlwKBgFn2sBQZ2Cuu3QIZWCcnaYmD6P25XQZfwSUKnZ3WlrUbpOojXC5E1oGQOS2zSExnPIfadaFsjavclkipM0WLHG46UW1q5gn4TFxdhU+A3S1izT8xomkfm4FMHi3LTlean+bZMeS0/LodWleqXp2o4xPyZ00B9Zkyga5wP1PcmvrN";
    public static final String RSA_PRIVATE = "";

//  public static final String RSA_PRIVATE =
//            "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBANrgc9/mQhPMc8F7" +
//            "nvvzWnIcnDXB0+UfOOpe1QSYINN3GY4QFfQBpPM+YQz1a8jodmeJZRXzTjlHuyy+" +
//            "khjDhaEuekZQN4cgCI94RYcf8O7OWdFe++iKgP0hy441G6jrQsBp0/wgvDdEddUN" +
//            "iGiCaOiHC2kV89e4hSDRxn1NoPdvAgMBAAECgYBWbrxTdoIxf1NW3JJzvC3DUjj9" +
//            "oYUjvaikJL7KCaks2KNmzkNdECkrRT7d9yyRMftP95nLiUEirYjkqnzW+p8RxGyV" +
//            "ytwm1wCNPK/5THBeFGBxrIirF4s411vBZdoHk++0O1T8wyKapu1DL5COM2nFyTOB" +
//            "O8U1FAaLNPxYBQ/XAQJBAO0rO32QnDjgHs9UKxyjtfwHPPSkM20Ju+NUF33rPvKL" +
//            "NsemB2bbihFW3tJ5pGMv/6847AOFpA3pm7ZdJg+NuzECQQDsQWjCzk9LHF6lA4BK" +
//            "vlv9TqRuMnBYW8qYjir3zt3YMyTdhK+4ONqv1nQqPoGcGMTi96hdn6cIRFJEkNvH" +
//            "X/SfAkAN46BCRDvB4O4s+iLkNHot3kallRAl7JICSCRwDVzMW3bDzmdUkLrPNSUr" +
//            "clA8Ns1kd5LqyeLHpcymq2HVO1mhAkBKdab91qX5BzzxBvR1Gh/F6vY54N21i2CD" +
//            "yjLSnTkf/aZ9xmbXSdvqlU1RTxfsSYYr/l/q+flbyqSfBYSvBpgDAkAftFHfQufw" +
//            "DJx5XyNSud+EdqFSFqehyj2t1r0GJkd8jsjybm9HjzlNsQsS6gaZeNIYWSr+82Bx" +
//            "82ric/Yf93ZS";

    /**
     * 支付宝公钥
     */
    public static final String RSA_PUBLIC = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhm1ss/jcqVvL8Uufa5GUejCIgjXBsP67ehMiH1znn+3n+XVSTh/KK7E/GN+ejGwGf9r1ZSEPj+oKJ3ulSfGSK7R6WHLTQbn5x2Uzz2qBX2TsglRp/vio5HmIFqIsukgwfg8WA8ryvwQ7B8JUdti/VDYBz5nDd22tkRV/eaKT00ITsZH6E8zNXg7ATrrVNEUd4n0mX0hwQbP3z8FOg6PboRIMhyRydllGpw0YNvQavUYVfiDW14EQPs1z88D0T5UXINYH8S93iDq4/Hrtp2ySQ4FZ9NLEU7cT5G5bJd/tzUqICKDohiFAyEw8Tli6G/+EX30oJKS6Yn7wA9USqEqKYwIDAQAB";

}
