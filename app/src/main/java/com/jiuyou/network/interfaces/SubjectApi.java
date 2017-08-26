package com.jiuyou.network.interfaces;

import com.jiuyou.network.response.OtherResponse.SecondInfoResponse;
import com.jiuyou.network.response.OtherResponse.SourceInfoResponse;
import com.jiuyou.network.response.OtherResponse.ThirdInfoResponse;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 */
public interface SubjectApi {

    /**
     *返回一级科目信息接口
     * @param token
     * @param imei
     * @param stage_id
     * @param cb
     */
    @FormUrlEncoded
    @POST("/getSourceInfo.php")
    void getSourceInfo(@Field("tooken") String token, @Field("imei") String imei, @Field("stage_id") String stage_id, @Field("object_name") String object_name,
                       Callback<SourceInfoResponse> cb);
    /**
     *返回二级科目信息接口
     * @param token
     * @param imei
     * @param cb
     */
    @FormUrlEncoded
    @POST("/getSecondCatalog.php")
    void getSecondCatalog(@Field("tooken") String token, @Field("imei") String imei, @Field("first_catalog") String first_catalog,
                          Callback<SecondInfoResponse> cb);
    /**
     *返回三级科目信息接口
     * @param token
     * @param imei
     * @param cb
     */
    @FormUrlEncoded
    @POST("/getThirdCatalog.php")
    void getThirdCatalog(@Field("tooken") String token, @Field("imei") String imei, @Field("second_catalog") String second_catalog,
                         Callback<ThirdInfoResponse> cb);



}
