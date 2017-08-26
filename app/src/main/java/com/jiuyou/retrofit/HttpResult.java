package com.jiuyou.retrofit;

/**
 * Created by admin on 2017/3/27.
 */

public class HttpResult<T> {
    public int code;
    public String message;
    public T data;
}
