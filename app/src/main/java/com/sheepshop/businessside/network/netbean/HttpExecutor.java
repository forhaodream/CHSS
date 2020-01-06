package com.sheepshop.businessside.network.netbean;

import android.os.Handler;
import android.os.Looper;

import com.sheepshop.businessside.network.manager.NetManager;
import com.sheepshop.businessside.network.netinterface.BaseCallBack;


/**
 * Created by Administrator on 2017/7/31.
 */

public class HttpExecutor {
    private static Handler mHandler = new Handler(Looper.getMainLooper()) ;

    private HttpExecutor() {
    }

    /**
     * 执行一个普通的请求
     * @param httpBean
     * @param callBack
     */
    public static void execute(final HttpBean httpBean, final BaseCallBack callBack) {
        NetManager.getInstance().getFramework().postData(httpBean.getReqBody(), httpBean.getHeader(), httpBean.getUrl(), new BaseCallBack() {
            @Override
            public void onRequesting() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onRequesting();
                    }
                });
            }

            @Override
            public void onSuccess(final ResponseBean data) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        data.setClass(httpBean.getaClass());
                        data.setResDatatype(httpBean.getResDataType());
                        callBack.onSuccess(data);
                    }
                });
//                mHandler.obtainMessage(ONRSUCCESS, new ResponseMsg(callBack, data));
            }

            @Override
            public void onError(final ResponseBean msg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        msg.setClass(httpBean.getaClass());
                        msg.setResDatatype(httpBean.getResDataType());
                        callBack.onError(msg);
                    }
                });
//                mHandler.obtainMessage(ONERROR, new ResponseMsg(callBack, msg));
            }

            @Override
            public void onErrorForOthers(final ResponseBean msg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        msg.setClass(httpBean.getaClass());
                        msg.setResDatatype(httpBean.getResDataType());
                        callBack.onErrorForOthers(msg);
                    }
                });
            }
        });
    }

    /**
     * 执行一个带有缓存的请求
     * @param httpBean
     * @param callBack
     */
    public static void executeWithCache(final HttpBean httpBean, final BaseCallBack callBack) {
        NetManager.getInstance().getFramework().cachePostData(httpBean.getReqBody(), httpBean.getHeader(), httpBean.getUrl(), new BaseCallBack() {
            @Override
            public void onRequesting() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onRequesting();
                    }
                });
            }

            @Override
            public void onSuccess(final ResponseBean data) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        data.setClass(httpBean.getaClass());
                        data.setResDatatype(httpBean.getResDataType());
                        callBack.onSuccess(data);
                    }
                });
//                mHandler.obtainMessage(ONRSUCCESS, new ResponseMsg(callBack, data));
            }

            @Override
            public void onError(final ResponseBean msg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        msg.setClass(httpBean.getaClass());
                        msg.setResDatatype(httpBean.getResDataType());
                        callBack.onError(msg);
                    }
                });
//                mHandler.obtainMessage(ONERROR, new ResponseMsg(callBack, msg));
            }

            @Override
            public void onErrorForOthers(final ResponseBean msg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        msg.setClass(httpBean.getaClass());
                        msg.setResDatatype(httpBean.getResDataType());
                        callBack.onErrorForOthers(msg);
                    }
                });
            }
        });
    }

    /**
     * 取消网络请求
     * @param urls 要取消的网络请求的 路径
     */
    public static void cancleRequest(String ...  urls) {
        NetManager.getInstance().getFramework().cancleReq(urls);
    }

    /**
     * 取消所有网络请求
     */
    public static void cancleAllRequest() {
        NetManager.getInstance().getFramework().cancleAllRequest();

    }
}
