package com.sheepshop.businessside.base;

public class Constant {
    public static final String SHARD_URL = Constant.BASE_URL + "download.html";//分享url
    public static final String SHARD_CONTENT = "绵里藏珍，养羊自得。让您足不出户即可体验原汁原味的自然味道。";//分享描述
    public static final String DOWLOAD_URL = Constant.BASE_URL + "download.html";//二维码下载
    public static final String BASE_PIC_URL = Constant.BASE_URL + "profile/upload/";//展示图片路径
    //    public static final String BASE_PIC_URL =  "http://sheepshop.lnhcsk.com/profile/upload/";//测试环境展示图片路径
    public static final String BASE_URL_API = Constant.BASE_URL;//接口相关
    /**
     * 商家版测试环境
     */
    public static final String BASE_URL = "http://testpos.lnhcsk.com";//生产环境服务器地址   TODO 2019年7月1日14:08:28  修改接口host
    public static final String BASE_URL1 = "http://192.168.1.156:8083";//欣阳
    public static final String BASE_URL2 = "http://192.168.1.200:8083";//虹柏
    public static final String SHARE_CODE = "http://register.lnhcsk.com/shop.html";//分享二维码 url生产环境
    public static final String WXAppId = "wxae919bfe032068a9";//生产环境微信ID
    public static final String WXSmallId = "gh_8fdb1daa0744";//微信小程序分享 正式版原始id
    public static final String URL = "https://pos.lnhcsk.com";//正式服务器


    //发送短信验证码
    public static final String URL_SENDCODE = BASE_URL + "/sp/user/sendCode";
    //首页验证码登录
    public static final String URL_SINGIN = BASE_URL + "/sp/user/signIn";
    //获取菜系
    public static final String URL_DIC = BASE_URL + "/sp/check/dic";
    //文件上传接口
    public static final String URL_FILEUPLOAD = BASE_URL + "/sp/check/filesUpload";
    //获取地址  getAreas
    public static final String URL_GETAREAS = BASE_URL + "/sp/basic/getAreas";
    // 店铺信息
    public static final String URL_INFO = BASE_URL + "/sp/detail/info";
    // 修改商铺信息
    public static final String URL_EDITINFO = BASE_URL + "/sp/detail/editInfo";
    // 退券
    public static final String URL_MODIFYCOUPONUSERSTATE = BASE_URL + "/coupon/modifyCouponUserState";
    // 兑换列表
    public static final String URL_SEARCHCOUPONEXCHANGELIST = BASE_URL + "/coupon/searchCouponExchangeList";
    // 代金券开关接口
    public static final String URL_MODIFYCOUPONSTATE = BASE_URL + "/coupon/modifyCouponState";
    // 	代金券详情
    public static final String URL_SEARCHCOUPONDETAIL = BASE_URL + "/coupon/searchCouponDetail";
    // 	新增代金券
    public static final String URL_CREATECOUPON = BASE_URL + "/coupon/createCoupon";
    // 	新增代金券列表/代金券管理列表
    public static final String URL_SEARCHCOUPONLIST = BASE_URL + "/coupon/searchCouponList";


}
