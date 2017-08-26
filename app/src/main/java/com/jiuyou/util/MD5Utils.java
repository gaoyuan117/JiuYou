/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014. Ray
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.jiuyou.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5工具类
 */
public class MD5Utils {

    public static String md5(byte[] data) {
//        try {
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            md.update(data, 0, data.length);
//            byte[] digest = md.digest();
//            return convertHashString(digest);
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
        return new String(data);
    }

    private static String convertHashString(byte[] hashBytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : hashBytes) {
            builder.append(Integer.toHexString((b >> 4) & 0xf));
            builder.append(Integer.toHexString(b & 0xf));
        }
        return builder.toString();
    }

    private static MessageDigest digest;

    static{
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            android.util.Log.d("jason", "md5 算法不支持!");
        }
    }

    /**
     * MD5加密
     * @param key
     * @return
     */
    public static String toMD5(String key){
        if(digest == null){
            return String.valueOf(key.hashCode());
        }
        //更新字节
        digest.update(key.getBytes());
        //获取最终的摘要  十进制的  12345678/ABCD1245
        return convert2HexString(digest.digest());
    }

    /**
     * 转为16进制字符串
     * @param bytes
     * @return
     */
    private static String convert2HexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (byte b : bytes) {
            //->8->08
            String hex = Integer.toHexString(0xFF & b);
            if(hex.length() == 1){
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
