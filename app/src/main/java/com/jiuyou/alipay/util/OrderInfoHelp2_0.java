package com.jiuyou.alipay.util;

public class OrderInfoHelp2_0 {

    /**
     * App_id
     */
    public static final String APPID = "2017080708079914";
//  public static final String APPID = "2016081901774198";

    /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /** 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1 */
    public static final String RSA2_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCLZX7ERLr9kT+iK5MiVyHsx9ZrzNe6Wai7sErh0tU76dPEeNmnOLxzpU9rZEzbpcohvQhUfLgV4khGJJJ+QBx5QGhaMIj10aLyeYDlHjJ5z3kP380jwmLQuW2GDZhes27WfrDtWl5NmBH0M3sW+DI14alg7hAIch1llDPhzRmSVDtX49SZK6+JyklIiwcW65GtFcRm6a5iXONEAOAmFRn2ZYQEsBcyNUDe3nmuY9cWG2mTYpEdjWPDbH/7tfT5Hmt72aRi/yvkkIax6YEgC3rAQ81IvJCPGVe2GJde1xLrHyYV/cIX88gSzTL1mmmBC33rCG0mRWDYCz9JnQU5qXOzAgMBAAECggEBAIIR0peRCmpQfe/0ePYyF5W5Ynp0SVe5EZkQhs7wy8d8B/3sRiui5TWDqveqP400xcd1e7BaL+s7mfTZa1gium7pinWdlC7LziAy//LKmo7O6UfGrMQRPeNFIs1TIjo7c9DxpmXTTOtaWmZjIxfrv+5HAqVPfeZjlMqT31Vy68l9JtaeJ6d/NMJiqDHQLh/p/rc74wodlclCVGNuPCe0tXuuxzORvs9xcicFZFn0UKwW6oySeTP4FtyE2BE4Rf5ughVCmN1UFEvVzbx3WaF5ccsnvsWmomYmZlZ5RiQ//uFSHXCK4tdifgV0Tlt/eTT2sPUMD8OShosM1UZ8cAzMNAECgYEA6bBUfM8Qnq6V9+emhVBSvKt+YYGPzDKGNbKww61eJ9ugCj8UYBvssq2josAYxCsRMznuK6zsCUaPk8aK0R7q581plOdrjej6/txZCC/qkVUTrGBZHwb75AI5f1JM1ZCnYrsyq+4FO/aUfMJjfYqP4KQd8IldHlhdzKm3w2YNVrECgYEAmLSIhKSRXowRtipm/EhwP13HgJw0pIk5SIll8toIrIKS04Qhdy6686HtOneAE/z1fMxn6eaT6Xjcs9oBphoD0m9Sh6Vzy2VfCQfHTdUfW4jSC9HcPivHZYQMhV8rHTJ56WN3R5YEKM/JcOA8Dz44NQGJWD50eZElNTB/41hhkaMCgYA/CuLACNxi8pZimovByzmGzHz7sQgp/jdWpS4qm8qa1hUGrp3Aj8f7K/zJGymTQksRlr856RyEJBT3uMgaQkz8291v3j6VuhR8TNV37/VH37u+9P8V77xkdnUsc/dmZe/CIt44o62lz4+fWJGefJLz91JcYbJNTWmAnQiGHjHbAQKBgG9gfZIBVeXX07NCCgtH6lbNV41+YQcA7z7wt+MBFAJ+2ghb/uymsMSx/gz+y/PINGTp0YDQJGHNPWwQPdOgddkuSMuW6XYYYdB8U+l44H4qtMjaMAFIfQ6V2WwNlayd/D01xvH3gOEjbJFfe27CuswuarZqFxnzu2eoEB8Jf2BxAoGAHCNnEdCYAvZaLvXwiw7M+8NT1cKxHR+VGopEHLWQVqasgSnlGErcPEskiBNU/L/uaK2Wl4oRWgETL1U5okzhWKV8IJ+2GBKDU7JlbAnGVuHcnp9b1apEwuzmPbm+OEz4xP/h8CNgIaC+mNz5h+uW3XcLztJvIxVA7L2WSFz7B6Y=";
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
