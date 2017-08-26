package com.jiuyou.core.request;

import com.jiuyou.global.BaseApp;
import com.jiuyou.util.RetrofitUtils;


// 制造服务请求的工厂类
public class ServerReqFactory {


    // 创建服务请求对象
    public <T> T createRequestApi(Class<T> cls) {
        return RetrofitUtils.createApi(BaseApp.getApplication(), cls);
    }

}
