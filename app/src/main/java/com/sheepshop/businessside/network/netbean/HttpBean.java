package com.sheepshop.businessside.network.netbean;


import java.util.Hashtable;
import java.util.Map;


/**
 * Created by Administrator on 2016/8/3.
 */
public class HttpBean{
    private String reqMethod;//请求方式
    private String url;//请求的路径
    private Class aClass;//生成的数据类型
    private int resDataType;//返回数据的类型
    private Map<String, Object> header;//请求头
    private Map<String, Object> reqBody;//请求体
    public static final int RES_DATATYPE_BEAN = 0xcf;//请求返回的是对象类型
    public static final int RES_DATATYPE_BEANLIST = 0xff;//请求返回的是数组

    public Map<String, Object> getHeader() {
        return header;
    }

    public void setHeader(Map<String, Object> header) {
        this.header = header;
    }

    public Map<String, Object> getReqBody() {
        return reqBody;
    }

    public void setReqBody(Map<String, Object> reqBody) {
        this.reqBody = reqBody;
    }

    public String getReqMethod() {
        return reqMethod;
    }

    public void setReqMethod(String reqMethod) {
        this.reqMethod = reqMethod;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Class getaClass() {
        return aClass;
    }

    public void setaClass(Class aClass) {
        this.aClass = aClass;
    }

    public int getResDataType() {
        return resDataType;
    }

    public void setResDataType(int resDataType) {
        this.resDataType = resDataType;
    }

    public static int getResDatatypeBean() {
        return RES_DATATYPE_BEAN;
    }

    public static int getResDatatypeBeanlist() {
        return RES_DATATYPE_BEANLIST;
    }

    public static class Builder<E> {

        private String url;//请求的路径
        private Class aClass;//生成的数据类型
        private int resDataType;//返回数据的类型
        private Map<String, Object> header;//请求头
        private Map<String, Object> reqBody;//请求体
        private String reqMethod;//请求方式

        public Builder() {
            header = new Hashtable<>();
            reqBody = new Hashtable<>();
//            //添加版本号
//            header.put("api-version",INTERFACE_VERSION);
//            //uid
//            if(UserInfoEntity.isLogin(BaseApplication.getInstance())){
//                reqBody.put("userid", UserPropertyUtils.getUid(BaseApplication.getInstance()));
//            }else {
//                reqBody.put("userid", "");
//            }
//            reqBody.put("token", UserPropertyUtils.getToken(BaseApplication.getInstance()));
//            reqBody.put("jiguangid", UserPropertyUtils.getJPushId());
        }


        public Builder addHeader(String key, Object object) {
            this.header.put(key, object.toString());
            return this;
        }

        public Builder addReqBody(String key, Object object) {
            if (object != null) {
                this.reqBody.put(key, object.toString());
            }else {
                this.reqBody.put(key, "");
            }
            return this;

        }

        public Builder setReqMethod(String reqMethod) {
            this.reqMethod = reqMethod;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setaClass(Class aClass) {
            this.aClass = aClass;
            return this;
        }

        public Builder setResDataType(int resDataType) {
            this.resDataType = resDataType;
            return this;
        }

        public HttpBean build() {
            HttpBean eHttpRequestBean = new HttpBean();
            eHttpRequestBean.url = this.url;
            eHttpRequestBean.aClass = this.aClass;
            eHttpRequestBean.resDataType = this.resDataType;
            eHttpRequestBean.header = this.header;
            eHttpRequestBean.reqBody = this.reqBody;
            eHttpRequestBean.reqMethod = this.reqMethod;
            return eHttpRequestBean;
        }
    }

}
