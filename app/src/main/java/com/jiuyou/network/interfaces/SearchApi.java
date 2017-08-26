package com.jiuyou.network.interfaces;

import com.jiuyou.network.response.OtherResponse.KeyWordsResponse;
import com.jiuyou.network.response.OtherResponse.SearchResultResponse;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 */
public interface SearchApi {

    /**
     * 获取热词
     *
     * @param token
     * @param imei
     * @param stage_id
     * @param cb
     */
    @FormUrlEncoded
    @POST("/getKeywords.php")
    void getKeywords(@Field("token") String token, @Field("imei") String imei, @Field("stage_id") String stage_id,
                     Callback<KeyWordsResponse> cb);

    /**
     * 获取搜索结果
     *
     * @param token
     * @param imei
     * @param keyword
     * @param cb
     */
    @FormUrlEncoded
    @POST("/getSearchInfo.php")
    void getSearchInfo(@Field("token") String token, @Field("imei") String imei, @Field("keyword") String keyword, @Field("limit") int limit, @Field("offset") int offset,
                       Callback<SearchResultResponse> cb);


}
