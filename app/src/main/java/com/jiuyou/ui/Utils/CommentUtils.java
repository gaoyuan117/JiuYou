package com.jiuyou.ui.Utils;


import com.jiuyou.core.AppContext;
import com.jiuyou.network.interfaces.HomeApi;
import com.jiuyou.network.response.JZBResponse.GoodsResponse;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 */
public class CommentUtils {
    
    private static HomeApi getHomeApi() {
        return AppContext.createRequestApi(HomeApi.class);
    }

    //获取宝贝评价监听
    public interface getCommentListListener {
        void load(boolean status, GoodsResponse info, String message);
    }


    /**
     *获取评价列表
     */
    public static void getCommentList(String product_id,String Page,String pageSize,final getCommentListListener listener) {
        HomeApi api = getHomeApi();
        api.getCommentList(product_id,Page,pageSize, new Callback<GoodsResponse>() {
            @Override
            public void success(GoodsResponse goodsResponse, Response response) {
                if (goodsResponse.getCode()==200) {
                    listener.load(true, goodsResponse, "获取信息成功");
                } else {
                    listener.load(false, null, goodsResponse.getMessage());
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                listener.load(false, null, "获取信息失败");

            }
        });
    }

    /**
     *获取评价列表
     */
    public static void getMyCommentList(String token,String Page,String pageSize,final getCommentListListener listener) {
        HomeApi api = getHomeApi();
        api.getMyCommentList(token,Page,pageSize, new Callback<GoodsResponse>() {
            @Override
            public void success(GoodsResponse goodsResponse, Response response) {
                if (goodsResponse.getCode()==200) {
                    listener.load(true, goodsResponse, "获取信息成功");
                } else {
                    listener.load(false, null, goodsResponse.getMessage());
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                listener.load(false, null, "获取信息失败");

            }
        });
    }



}
