package com.zhq.exclusivememory.api;


import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Huiqiang Zhang
 * on 2019/1/19.
 */

public interface ApiService {

    @POST("notifications/login/verifications")
    @FormUrlEncoded
    Observable<Response<Object>> sendSmsCodeForRegister(@Field("phone") String phone);
}
