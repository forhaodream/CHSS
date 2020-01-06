package com.sheepshop.businessside.okhttp;

import com.google.gson.JsonSyntaxException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * SSH系统专用CallBack
 *
 * @param <T>
 */
public abstract class SSHCallBack<T extends SSHBaseModel> implements Callback<T> {
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.code() == 200) {
            if (response.body().getCode() == 200) {
                try {
                    onSuccess(response);
                } catch (Exception e) {
                    e.printStackTrace();
                    onFailure(call, new NullPointerException("getView()为null"));
                }
            } else {
                System.out.println("response error,detail = " + response.body().getMsg());
                onFailure(call, new RuntimeException(response.body().getMsg()));
            }
        } else {
            onFailure(call, new RuntimeException("抱歉,网络错误请稍后再试!" + response.code()));
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (t instanceof SocketTimeoutException) {
            System.out.println("========================Socket超时" + t.getMessage());
        } else if (t instanceof ConnectException) {
            System.out.println("========================连接异常" + t.getMessage());
        } else if (t instanceof JsonSyntaxException) {
            System.out.println("========================json格式异常" + t.getMessage());
        } else if (t instanceof NullPointerException) {
            System.out.println("========================空指针异常" + t.getMessage());
        } else if (t instanceof RuntimeException) {
            System.out.println("========================运行时异常" + t.getMessage());
        } else {
            System.out.println("========================其他异常" + t.getMessage());
        }

        onFail(t.getMessage());
    }

    public abstract void onSuccess(Response<T> response) throws Exception;

    public abstract void onFail(String message);

}
