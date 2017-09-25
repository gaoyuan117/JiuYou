package com.jiuyou.ui.Utils;


import android.util.Log;

import com.jiuyou.core.AppContext;
import com.jiuyou.network.interfaces.HomeApi;
import com.jiuyou.network.response.JZBResponse.BannerResponse;
import com.jiuyou.network.response.JZBResponse.BannerResponse1;
import com.jiuyou.network.response.JZBResponse.GoodsResponse;
import com.jiuyou.network.response.JZBResponse.HoomeGoodsResponse;
import com.jiuyou.network.response.JZBResponse.SearchResultResponse;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 */
public class GoodsUtils {
    
    private static HomeApi getHomeApi() {
        return AppContext.createRequestApi(HomeApi.class);
    }

    //获取首页商品信息监听
    public interface getInfosListener {
        void load(boolean status, HoomeGoodsResponse info, String message);
    }
    //获取商品详情信息监听
    public interface getGoodsInfosListener {
        void load(boolean status, GoodsResponse info, String message);
    }
    //获取首页商品信息监听
    public interface getSearchResultListener {
        void load(boolean status, SearchResultResponse info, String message);
    }
    //获取首页横幅信息监听
    public interface getHomeBannerListener {
        void load(boolean status, BannerResponse1 info, String message);
    }
    //获取搜索结果信息监听
    public interface getSearchResaultListener {
        void load(boolean status, SearchResultResponse info, String message);
    }
    //获取搜索结果信息监听
    public interface getCategoryListener {
        void load(boolean status, BannerResponse info, String message);
    }


    //获取首页横幅
    public static void getHomeBanner(final getHomeBannerListener listener) {
        HomeApi api = getHomeApi();
        api.getBanner("", new Callback<BannerResponse1>() {
            @Override
            public void success(BannerResponse1 homeBanner, Response response) {
                if (homeBanner.getCode()==200) {
                    listener.load(true, homeBanner, "获取首页信息成功");
                } else {
                    listener.load(false, null, homeBanner.getMessage());
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                listener.load(false, null, "获取信息失败");
            }
        });
    }
    //获取首页信息

    /**
     *
     * @param category_id  分類
     * @param Page         頁碼
     * @param pageSize     一頁顯示多少
     * @param listener     數據監聽
     */
    public static void getHomeInfos(String category_id,String Page,String pageSize,final getInfosListener listener) {
        HomeApi api = getHomeApi();
        api.getFGoodsList(category_id,Page,pageSize,new Callback<HoomeGoodsResponse>(){
            @Override
            public void success(HoomeGoodsResponse goodsResponse, Response response) {
                if (goodsResponse.getCode()==200) {
                    listener.load(true, goodsResponse, "获取首页信息成功");
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
     *获取搜索结果
     */
    public static void getSearchResultInfos(String key,final getSearchResultListener listener) {
        HomeApi api = getHomeApi();
       api.getSearch(key, new Callback<SearchResultResponse>() {
           @Override
           public void success(SearchResultResponse searchResultResponse, Response response) {
               if (searchResultResponse.getCode()==200) {
                   listener.load(true, searchResultResponse, "获取首页信息成功");
               } else {
                   listener.load(false, null, searchResultResponse.getMessage());
               }
           }

           @Override
           public void failure(RetrofitError retrofitError) {
               listener.load(false, null, "获取信息失败");

           }
       });
    }

    /**
     *获取商品详情
     */
    public static void getGoodsInfos(String goods_id,final getGoodsInfosListener listener) {
        HomeApi api = getHomeApi();
        api.getGoodsinfos(goods_id, new Callback<GoodsResponse>() {
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
     *获取首页分类详情
     */
    public static void getCategory(String tooken,final getCategoryListener listener) {
        HomeApi api = getHomeApi();
        api.getCategoryList(tooken, new Callback<BannerResponse>() {
            @Override
            public void success(BannerResponse bannerResponse, Response response) {
                if (bannerResponse.getCode()==200) {
                    listener.load(true, bannerResponse, "获取信息成功");
                } else {
                    listener.load(false, null, bannerResponse.getMessage());
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                listener.load(false, null, "获取信息失败");

            }
        });
    }



}
