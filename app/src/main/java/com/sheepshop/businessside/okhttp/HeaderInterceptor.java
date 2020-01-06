package com.sheepshop.businessside.okhttp;


import java.io.IOException;
import org.apache.commons.lang3.StringUtils;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 对头信息进行统一处理
 *
 */
public class HeaderInterceptor implements Interceptor {
    private String token = null;

    public HeaderInterceptor(String token) {
        this.token = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("Accept","application/json");
        if(!StringUtils.isEmpty(token)){
            builder.addHeader("access_token", token);
        }
        return chain.proceed(builder.build());
    }
}
