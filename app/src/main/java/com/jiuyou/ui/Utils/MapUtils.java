package com.jiuyou.ui.Utils;


import com.jiuyou.core.AppContext;
import com.jiuyou.network.interfaces.HomeApi;
import com.jiuyou.network.response.JZBResponse.NearByResponse;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MapUtils {

    private static HomeApi getHomeApi() {
        return AppContext.createRequestApi(HomeApi.class);
    }

    //周边商家
    public interface getNearByListener {
        void load(boolean status, NearByResponse info, String message);
    }

    /**
     *周边商家
     */
    public static void getNearBy(float lng,float lat,final getNearByListener listener) {
        HomeApi api = getHomeApi();
        api.nearBy(lng,lat,new Callback<NearByResponse>() {
            @Override
            public void success(NearByResponse cartResponse, Response response) {
                if (cartResponse.getCode()==200) {
                    listener.load(true, cartResponse, "获取信息成功");
                } else {
                    listener.load(false, null, cartResponse.getMessage());
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                listener.load(false, null, "获取信息失败");

            }
        });
    }

}
