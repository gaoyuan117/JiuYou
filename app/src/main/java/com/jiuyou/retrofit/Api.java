package com.jiuyou.retrofit;

import com.jiuyou.model.Areabean;
import com.jiuyou.model.CityBean;
import com.jiuyou.model.CodeBean;
import com.jiuyou.model.CommonBean;
import com.jiuyou.model.MyAdressBean;
import com.jiuyou.model.OrderBean;
import com.jiuyou.model.OrderInfoBean;
import com.jiuyou.model.OrderTimeBean;
import com.jiuyou.model.ProvinceBean;
import com.jiuyou.model.ToPayBean;
import com.jiuyou.model.TuiJianBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by admin on 2017/3/27.
 */

public interface Api {

    //获取省份
    @FormUrlEncoded
    @POST("/api/public/getProvinces")
    Observable<HttpArray<ProvinceBean>> getProvinces(@Field("token") String s);

    //获取城市
    @FormUrlEncoded
    @POST("/api/public/getCities")
    Observable<HttpArray<CityBean>> getCity(@Field("province_id") String province_id);

    //获取县
    @FormUrlEncoded
    @POST("/api/public/getAreas")
    Observable<HttpArray<Areabean>> getArea(@Field("city_id") String city_id);

    //添加收获地址
    @FormUrlEncoded
    @POST("/api/user/addReceiveAddr")
    Observable<HttpResult<CommonBean>> addReceiveAddr(@FieldMap Map<String, Object> map);

    //我的收货地址
    @FormUrlEncoded
    @POST("/api/user/getReceiveAddrs")
    Observable<HttpArray<MyAdressBean>> getReceiveAddrs(@Field("token") String s);

    //设置默认地址
    @FormUrlEncoded
    @POST("/api/user/defaultaReceiveAddr")
    Observable<HttpResult<CommonBean>> defaultaReceiveAddr(@Field("token") String token, @Field("id") String id);

    //删除地址
    @FormUrlEncoded
    @POST("api/user/delReceiveAddr")
    Observable<HttpResult<CommonBean>> delReceiveAddr(@Field("token") String token, @Field("id") String id);

    //编辑地址
    @FormUrlEncoded
    @POST("api/user/editReceiveAddr")
    Observable<HttpResult<CommonBean>> edtReceiveAddr(@FieldMap Map<String, Object> map);

    //编辑地址
    @FormUrlEncoded
    @POST("api/order/getdistance")
    Observable<OrderTimeBean> getDistance(@Field("rec_id") String rec_id);

    //查看全部订单 99全部订单 0 待支付 1 已支付  2 退款  7 配送中 9 已完成
    @FormUrlEncoded
    @POST("api/order/getMyOrders")
    Observable<HttpArray<OrderBean>> getMyOrders(@Field("token") String token, @Field("type") String type);

    //订单详情
    @FormUrlEncoded
    @POST("api/order/order_info")
    Observable<HttpResult<OrderInfoBean>> orderInfo(@Field("token") String token, @Field("order_id") String order_id);

    //订单详情
    @FormUrlEncoded
    @POST("api/order/cancelOrder")
    Observable<HttpResult<CommonBean>> cancelOrder(@Field("token") String token, @Field("order_id") String order_id);

    //申请退款
    @FormUrlEncoded
    @POST("api/order/apply_refund")
    Observable<HttpResult<CommonBean>> returnMoney(@Field("token") String token,
                                                   @Field("order_id") String order_id,
                                                   @Field("refund_desc") String refund_desc);

    //立即支付
    @FormUrlEncoded
    @POST("api/order/quickPayOrder")
    Observable<HttpResult<ToPayBean>> toPay(@Field("token") String token,
                                            @Field("order_id") String order_id,
                                            @Field("pay_type") String pay_type);

    //获取邀请码
    @FormUrlEncoded
    @POST("api/user/myqrcode")
    Observable<HttpResult<CodeBean>> getCode(@Field("token") String token, @Field("driver") String driver);

    //获取推荐
    @FormUrlEncoded
    @POST("/api/user/refereesList")
    Observable<HttpResult<TuiJianBean>> getTuiJian(@Field("token") String token);

    //确认收货
    @FormUrlEncoded
    @POST("/api/order/confirmReceipt")
    Observable<HttpResult<CommonBean>> confirmReceipt(@Field("token") String token, @Field("order_id") String order_id);

    //确认收货
    @FormUrlEncoded
    @POST("/api/order/refuseGoods")
    Observable<HttpResult<CommonBean>> refuseGoods(@Field("token") String token, @Field("order_id") String order_id);
}
