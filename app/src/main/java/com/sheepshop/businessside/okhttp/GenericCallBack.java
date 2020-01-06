package com.sheepshop.businessside.okhttp;

import com.google.gson.JsonSyntaxException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 通用 CallBack  一般T为Map
 * author: Qi
 * 2018-11-29 下午3:34
 */
public abstract class GenericCallBack<T> implements Callback<T> {


    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if(response.code()==200){
            if (response.body() instanceof Map) {
                Map resMap = (Map) response.body();
                if (resMap.get("Code")!=null && (resMap.get("Code").equals(200D) || "200".equals(resMap.get("Code")))) {
                    onSuccess(response);
                } else {
                    System.out.println("response error,code is " + resMap.get("Code") + " message = "
                            + resMap.get("Msg"));
                    onFailure(call, new RuntimeException("response error,code = " + resMap.get("Code")));
                }
            } else {
                onSuccess(response);
            }
        }else{
            onFailure(call, new RuntimeException("response error,code = " + response.code()));
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if(t instanceof SocketTimeoutException){
            System.out.println("========================Socket超时"+t.getMessage());
        }else if(t instanceof ConnectException){
            System.out.println("========================连接异常"+t.getMessage());
        }else if(t instanceof JsonSyntaxException){
            System.out.println("========================json格式异常"+t.getMessage());
        }else if(t instanceof RuntimeException){
            System.out.println("========================运行时异常"+t.getMessage());
        }else{
            System.out.println("========================其他异常"+t.getMessage());
        }

        onFail(t.getMessage());
    }

    public abstract void onSuccess(Response<T> response);

    public abstract void onFail(String message);

}
