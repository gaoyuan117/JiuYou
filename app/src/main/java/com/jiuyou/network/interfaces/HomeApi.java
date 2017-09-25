package com.jiuyou.network.interfaces;

import com.jiuyou.network.response.JZBResponse.AboutResponse;
import com.jiuyou.network.response.JZBResponse.AmountResponse;
import com.jiuyou.network.response.JZBResponse.BannerResponse;
import com.jiuyou.network.response.JZBResponse.BannerResponse1;
import com.jiuyou.network.response.JZBResponse.CartResponse;
import com.jiuyou.network.response.JZBResponse.CupResponse;
import com.jiuyou.network.response.JZBResponse.GoodsResponse;
import com.jiuyou.network.response.JZBResponse.HoomeGoodsResponse;
import com.jiuyou.network.response.JZBResponse.NearByResponse;
import com.jiuyou.network.response.JZBResponse.PayResponse;
import com.jiuyou.network.response.JZBResponse.QuickResponse;
import com.jiuyou.network.response.JZBResponse.SearchResultResponse;
import com.jiuyou.network.response.JZBResponse.UserResponse;

import okhttp3.ResponseBody;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 */
public interface HomeApi {

    /**
     * 主页商品
     */
    @FormUrlEncoded
    @POST("/api/product/goodsList")
    void getFGoodsList(@Field("category_id") String category_id, @Field("Page") String Page, @Field("pageSize") String pageSize,
                       Callback<HoomeGoodsResponse> cb);

    /**
     * banner
     */
    @FormUrlEncoded
    @POST("/api/product/banner")
    void getBanner(@Field("") String token, Callback<BannerResponse1> cb);

    /**
     * 搜索结果
     */
    @FormUrlEncoded
    @POST("/api/product/seach")
    void getSearch(@Field("key") String key, Callback<SearchResultResponse> cb);

    /**
     * 商品详情
     */
    @FormUrlEncoded
    @POST("/api/product/goodsDetail")
    void getGoodsinfos(@Field("goods_id") String goods_id, Callback<GoodsResponse> cb);

    /**
     * 商品评价列表
     */
    @FormUrlEncoded
    @POST("/api/order/goodsComment")
    void getCommentList(@Field("product_id") String product_id, @Field("Page") String Page, @Field("pageSize") String pageSize, Callback<GoodsResponse> cb);

    /**
     * 首页分类列表
     */
    @FormUrlEncoded
    @POST("/api/product/categoryList")
    void getCategoryList(@Field("") String token, Callback<BannerResponse> cb);

    /**
     * 商品评价列表
     */
    @FormUrlEncoded
    @POST("/api/order/commentList")
    void getMyCommentList(@Field("token") String token, @Field("Page") String Page, @Field("pageSize") String pageSize, Callback<GoodsResponse> cb);

    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("/api/user/login")
    void getlogin(@Field("user") String user, @Field("pwd") String pwd, @Field("driver") String driver, Callback<GoodsResponse> cb);

    /**
     * 注册
     */
    @FormUrlEncoded
    @POST("/api/user/regist")
    void getRegister(@Field("code") String code, @Field("user") String user, @Field("password") String password, @Field("driver") String driver, @Field("referrer") String referrer, Callback<GoodsResponse> cb);

    /**
     * 密码重置
     */
    @FormUrlEncoded
    @POST("/api/user/resetPassword")
    void getResetPassword(@Field("pass") String pass, @Field("repass") String repass, @Field("user") String user, @Field("code") String code, Callback<GoodsResponse> cb);


    /**
     * 获取短信验证
     */
    @FormUrlEncoded
    @POST("/api/public/verify")
    void getVerify(@Field("user") String user, @Field("type") String type, Callback<GoodsResponse> cb);

    /**
     * 获取充值列表
     */
    @FormUrlEncoded
    @POST("/api/order/rechargeList")
    void getRechargeList(@Field("token") String token, @Field("Page") String Page, @Field("pageSize") String pageSize, Callback<GoodsResponse> cb);

    /**
     * 获取购买历史列表
     */
    @FormUrlEncoded
    @POST("/api/order/buy_history")
    void getBuyHistoryList(@Field("token") String token, @Field("Page") String Page, @Field("pageSize") String pageSize, Callback<GoodsResponse> cb);

    /**
     * 反馈意见
     */
    @FormUrlEncoded
    @POST("/api/user/feedback")
    void getFeedBack(@Field("token") String token, @Field("content") String content, @Field("phone") String phone, Callback<GoodsResponse> cb);

    /**
     * 获取用户信息
     */
    @FormUrlEncoded
    @POST("/api/user/getUserInfo")
    void getUserInfo(@Field("token") String token, Callback<UserResponse> cb);

    /**
     * 头像更新接口
     */
    @FormUrlEncoded
    @POST("/api/user/Updateavatar")
    void Updateavatar(@Field("token") String token, @Field("avatar") String avatar, Callback<UserResponse> cb);

    /**
     * 昵称更新接口
     */
    @FormUrlEncoded
    @POST("/api/user/editNickname")
    void editNickname(@Field("token") String token, @Field("nickname") String nickname, Callback<UserResponse> cb);

    /**
     * 春东号设置接口
     */
    @FormUrlEncoded
    @POST("/api/user/cd_acc")
    void editSetname(@Field("token") String token, @Field("cd_acc") String cd_acc, Callback<UserResponse> cb);

    /**
     * 修改设置支付密码接口
     */
    @FormUrlEncoded
    @POST("/api/user/pay_pwd")
    void setPayPwd(@Field("token") String token, @Field("pwd") String pwd, Callback<UserResponse> cb);

    /**
     * 修改支付密码接口
     */
    @FormUrlEncoded
    @POST("/api/user/editpay")
    void editpay(@Field("token") String token, @Field("user") String user, @Field("code") String code, @Field("pwd") String pwd, Callback<UserResponse> cb);


    /**
     * 修改登录密码接口
     */
    @FormUrlEncoded
    @POST("/api/user/editPassword")
    void setLoginPwd(@Field("token") String token, @Field("oldpwd") String oldpwd, @Field("newpwd") String newpwd, @Field("repwd") String repwd, Callback<UserResponse> cb);

    /**
     * 充值下单接口
     */
    @FormUrlEncoded
    @POST("/api/order/rechargeOrder")
    void rechargeOrder(@Field("driver") String driver,@Field("token") String token, @Field("amount") double amount, @Field("pay_channel") String pay_channel, Callback<AmountResponse> cb);

    /**
     * 关于接口
     */
    @FormUrlEncoded
    @POST("/api/user/about")
    void about(@Field("token") String token, Callback<AboutResponse> cb);

    /**
     * 购物车增删改综合接口
     */
    @FormUrlEncoded
    @POST("/api/order/changeCart")
    void changeCart(@Field("token") String token, @Field("model") String model, @Field("product_id") String product_id, @Field("quantity") String quantity, Callback<CartResponse> cb);

    /**
     * 购物车列表 结算页面
     */
    @FormUrlEncoded
    @POST("/api/order/cartList")
    void cartList(@Field("token") String token, @Field("Page") String Page, @Field("pageSize") String pageSize, Callback<CartResponse> cb);

    /**
     * 删除 购物车
     */
    @FormUrlEncoded
    @POST("/api/order/del_cart")
    void del_cart(@Field("token") String token, @Field("cart_id[]") String[] cart_id, Callback<CartResponse> cb);

    /**
     * 启动页
     */
    @FormUrlEncoded
    @POST("/api/public/boot")
    void boot(@Field("token") String token, Callback<CartResponse> cb);

    /**
     * 是否第一次支付
     */
    @FormUrlEncoded
    @POST("/api/order/is_first_pay")
    void is_first_pay(@Field("token") String token, Callback<PayResponse> cb);

    /**
     * 获取订单号
     */
    @FormUrlEncoded
    @POST("/api/order/toTrade")
    void toTrade(@Field("token") String token,
                 @Field("rec_id") String rec_id,
                 @Field("pickup_mode") String pickup_mode,
                 @Field("send_time") String send_time,
                 @Field("pay_type") String pay_type,
                 @Field("send_type") String send_type,
                 @Field("cart_id[]") String[] cart_id, Callback<PayResponse> cb);

    /**
     * 快捷支付
     */
    @FormUrlEncoded
    @POST("/api/order/quickPay")
    void quickPay(@Field("token") String token, @Field("order_no") String order_no, @Field("pwd") String pwd, Callback<QuickResponse> cb);

    /**
     * 添加评论
     */
    @FormUrlEncoded
    @POST("/api/order/add_comment")
    void add_comment(@Field("token") String token,
                     @Field("content") String content,
                     @Field("order_id") String order_id, Callback<QuickResponse> cb);

    /**
     * 銀聯獲取TN
     */
    @FormUrlEncoded
    @POST("/api/pay/achieveTN")
    void achieveTN(@Field("order_no") String order_no, @Field("type") String type, @Field("txnAmt") String txnAmt, Callback<QuickResponse> cb);

    /**
     * 地图获取商家坐标等信息
     */
    @FormUrlEncoded
    @POST("/api/cupboard/nearBy")
    void nearBy(@Field("lng") float lng, @Field("lat") float lat, Callback<NearByResponse> cb);

    /**
     * 地图 根据订单推荐合适的货柜
     */
    @FormUrlEncoded
    @POST("/api/cupboard/foundCup")
    void foundCup(@Field("lng") float lng, @Field("lat") float lat, @Field("order_no") String order_no, Callback<CupResponse> cb);

    /**
     * 判断春东号是否存在
     */
    @FormUrlEncoded
    @POST("/api/user/isset_cd_acc")
    void isset_cd_acc(@Field("token") String token, Callback<NearByResponse> cb);

    /**
     * 查詢已支付的訂單詳情
     */
    @FormUrlEncoded
    @POST("/api/order/order_info")
    void order_info(@Field("token") String token, @Field("order_no") String order_no, Callback<QuickResponse> cb);

    /**
     * 获取省份列表
     */
    @FormUrlEncoded
    @POST("/api/public/getProvinces")
    void getProvinces(@Field("token") String token, Callback<ResponseBody> cb);


}
