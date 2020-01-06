package com.sheepshop.businessside.api;

import com.sheepshop.businessside.base.BaseModel;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AppService {
    //发送验证码
    @FormUrlEncoded
    @POST("sp/user/sendCode")
    Flowable<BaseModel> getSendCode(@Field("mobile") String mobile, @Field("type") String type);
}
