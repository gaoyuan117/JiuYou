package com.jiuyou.ui.Utils;


import com.jiuyou.core.AppContext;
import com.jiuyou.network.interfaces.HomeApi;
import com.jiuyou.network.response.JZBResponse.CartResponse;
import com.jiuyou.network.response.JZBResponse.PayResponse;
import com.jiuyou.network.response.JZBResponse.QuickResponse;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CartUtils {

    private static HomeApi getHomeApi() {
        return AppContext.createRequestApi(HomeApi.class);
    }

    //获取购物车列表
    public interface getCartListener {
        void load(boolean status, CartResponse info, String message);
    }

    //购物车增删改综合接口
    public interface getChangeCartListener {
        void load(boolean status, CartResponse info, String message);
    }

    //删除购物车接口
    public interface getdelCartListener {
        void load(boolean status, CartResponse info, String message);
    }

    //是否第一次支付接口
    public interface isFirstPayListener {
        void load(boolean status, PayResponse info, String message);
    }

    //获取订单号接口
    public interface toTradeListener {
        void load(boolean status, PayResponse info, String message);
    }

    //零钱支付接口
    public interface quickListener {
        void load(boolean status, QuickResponse info, String message);
    }

    /**
     * 零钱支付接口
     */
    public static void quick(String token, String order_no, String pwd, final quickListener listener) {
        HomeApi api = getHomeApi();
        api.quickPay(token, order_no, pwd, new Callback<QuickResponse>() {
            @Override
            public void success(QuickResponse quickResponse, Response response) {
                if (quickResponse.getCode() == 200) {
                    listener.load(true, quickResponse, "获取信息成功");
                } else {
                    listener.load(false, null, quickResponse.getMessage());
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                listener.load(false, null, "获取信息失败");

            }
        });
    }

    /**
     * 获取订单号接口
     */
    public static void toTrade(String rec_id, String pickup_mode, String send_time, String pay_type, String token, String[] ids,String send_type, final toTradeListener listener) {
        HomeApi api = getHomeApi();
        api.toTrade(token, rec_id, pickup_mode, send_time, pay_type,send_type, ids, new Callback<PayResponse>() {
            @Override
            public void success(PayResponse cartResponse, Response response) {
                if (cartResponse.getCode() == 200) {
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

    /**
     * 是否第一次支付接口
     */
    public static void isFirstPay(String token, final isFirstPayListener listener) {
        HomeApi api = getHomeApi();
        api.is_first_pay(token, new Callback<PayResponse>() {
            @Override
            public void success(PayResponse cartResponse, Response response) {
                if (cartResponse.getCode() == 200) {
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

    /**
     * 购物车删除接口
     */
    public static void getDelCart(String tooken, String[] cart_id, final getdelCartListener listener) {
        HomeApi api = getHomeApi();
        api.del_cart(tooken, cart_id, new Callback<CartResponse>() {
            @Override
            public void success(CartResponse cartResponse, Response response) {
                if (cartResponse.getCode() == 200) {
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

    /**
     * 购物车增删改综合接口
     */
    public static void getchangeCart(String tooken, String model, String product_id, String quantity, final getChangeCartListener listener) {
        HomeApi api = getHomeApi();
        api.changeCart(tooken, model, product_id, quantity, new Callback<CartResponse>() {
            @Override
            public void success(CartResponse cartResponse, Response response) {
                if (cartResponse.getCode() == 200) {
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

    /**
     * 获取购物车列表
     */
    public static void getCartList(String tooken, String Page, String pageSize, final getCartListener listener) {
        HomeApi api = getHomeApi();
        api.cartList(tooken, Page, pageSize, new Callback<CartResponse>() {
            @Override
            public void success(CartResponse cartResponse, Response response) {
                if (cartResponse.getCode() == 200) {
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
