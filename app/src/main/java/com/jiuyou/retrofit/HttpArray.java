package com.jiuyou.retrofit;

import java.util.List;

/**
 * Created by admin on 2017/3/27.
 *
 */

public class HttpArray<T> {
    public int code;
    public String message;
    public List<T> data;
}
