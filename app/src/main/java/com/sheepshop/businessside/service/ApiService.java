package com.sheepshop.businessside.service;

import com.sheepshop.businessside.entity.ArgsBean;
import com.sheepshop.businessside.entity.BankListBean;
import com.sheepshop.businessside.entity.BeanRespReqEmpty;
import com.sheepshop.businessside.entity.CenterHomeBean;
import com.sheepshop.businessside.entity.CenterStateBean;
import com.sheepshop.businessside.entity.CouponDetailBean;
import com.sheepshop.businessside.entity.CouponListBean;
import com.sheepshop.businessside.entity.EvaluateBean;
import com.sheepshop.businessside.entity.ExchangeListBean;
import com.sheepshop.businessside.entity.FilesUpload;
import com.sheepshop.businessside.entity.GoodsDetailBean;
import com.sheepshop.businessside.entity.GoodsKindBean;
import com.sheepshop.businessside.entity.GoodsLabelBean;
import com.sheepshop.businessside.entity.GoodsListTypeBean;
import com.sheepshop.businessside.entity.InfoBean;
import com.sheepshop.businessside.entity.OfferingBean;
import com.sheepshop.businessside.entity.PackageClassListBean;
import com.sheepshop.businessside.entity.PackageGoodsClassListBean;
import com.sheepshop.businessside.entity.ProvinceBean;
import com.sheepshop.businessside.entity.SendCodeBean;
import com.sheepshop.businessside.entity.ShopAuditBean;
import com.sheepshop.businessside.entity.ShopFirstInfoBean;
import com.sheepshop.businessside.entity.ShopStateBean;
import com.sheepshop.businessside.entity.StoreTypeBean;
import com.sheepshop.businessside.entity.SuccessBean;
import com.sheepshop.businessside.entity.UserInfoBean;
import com.sheepshop.businessside.okhttp.BaseResp;
import com.sheepshop.businessside.ui.bean.LoginBean;

import java.io.File;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by CH
 * on 2019/11/8 19:40
 */
public interface ApiService {
    /**
     * 发送验证码
     */
    @FormUrlEncoded
    @POST("/sp/user/sendCode")
    Call<BaseResp<BeanRespReqEmpty>> sendCode(@Field("mobile") String mobile, @Field("type") String type);

    /**
     * 验证码登录
     */
    @FormUrlEncoded
    @POST("/sp/user/signIn")
    Call<BaseResp<LoginBean>> login(@Field("mobile") String phone, @Field("code") String code, @Field("sendCode") String sendCode);

    /**
     * 修改手机号码
     */
    @FormUrlEncoded
    @POST("/sp/user/changeUserMobile")
    Call<BaseResp<BeanRespReqEmpty>> modify(@Field("mobile") String phone,
                                            @Field("buId") String buId,
                                            @Field("buToken") String buToken,
                                            @Field("code") String code);

    /**
     * 获取用户信息
     */
    @FormUrlEncoded
    @POST("/sp/user/getUserInfo")
    Call<BaseResp<UserInfoBean>> getUserInfo(@Field("buId") String buId, @Field("buToken") String buToken);

    /**
     * 获取省列表
     */
    @FormUrlEncoded
    @POST("/sp/basic/getAreas")
    Call<BaseResp<List<ProvinceBean>>> getProvince(@Field("cId") int cId);

    /**
     * 获取市列表
     */
    @FormUrlEncoded
    @POST("/sp/basic/getAreas")
    Call<BaseResp<List<ProvinceBean>>> getCity(@Field("pId") int pId);

    /**
     * 获取区列表
     */
    @FormUrlEncoded
    @POST("/sp/basic/getAreas")
    Call<BaseResp<List<ProvinceBean>>> getArea(@Field("cId") int cId);

    /**
     * 获取店铺类型
     */
    @FormUrlEncoded
    @POST("/sp/check/dic")
    Call<BaseResp<List<StoreTypeBean>>> storeType(@Field("type") String type);

    /**
     * 验券
     * couponUseNumber 码
     */
    @GET("/coupon/doVerification")
    Call<BaseResp<BeanRespReqEmpty>> doVerification(@Query("couponUseNumber") String couponUseNumber, @Query("businessId") String businessId);

    /**
     * 店铺首页数据
     */
    @FormUrlEncoded
    @POST("/sp/detail/firstPage")
    Call<BaseResp<ShopFirstInfoBean>> shopFirstInfo(@Field("buToken") String buToken, @Field("buId") String buId);

    /**
     * 店铺开关状态
     */
    @FormUrlEncoded
    @POST("/sp/detail/switchState")
    Call<BaseResp<ShopStateBean>> switchState(@Field("bdId") String bdId, @Field("buToken") String buToken, @Field("buId") String buId);

    /**
     * 代金券管理列表
     */
    @FormUrlEncoded
    @POST("/coupon/searchCouponList")
    Call<BaseResp<CouponListBean>> searchCouponList(@Field("searchType") String searchType,
                                                    @Field("first") int first,
                                                    @Field("rows") int rows,
                                                    @Field("businessId") String businessId,
                                                    @Field("timeType") String timeType);

    /**
     * 代金券开关接口
     */
    @FormUrlEncoded
    @POST("/coupon/modifyCouponState")
    Call<OfferingBean> modifyCouponState(@Field("couponId") String couponId,
                                         @Field("businessId") String businessId,
                                         @Field("isOpen") String isOpen);

    /**
     * 新增代金券
     */
    @FormUrlEncoded
    @POST("/coupon/createCoupon")
    Call<BaseResp<BeanRespReqEmpty>> createCoupon(@Field("couponId") String couponId,
                                                  @Field("businessId") String businessId);

    /**
     * 兑换列表
     */
    @FormUrlEncoded
    @POST("/coupon/searchCouponExchangeList")
    Call<BaseResp<ExchangeListBean>> searchExchangeList(@Field("first") int first,
                                                        @Field("rows") int rows,
                                                        @Field("businessId") String businessId,
                                                        @Field("isBack") String isBack);

    /**
     * 代金券详情
     */
    @FormUrlEncoded
    @POST("/coupon/searchCouponDetail")
    Call<BaseResp<CouponDetailBean>> couponDetail(@Field("couponId") String couponId, @Field("searchType") String searchType, @Field("businessId") String businessId);

    /**
     * 确认返券
     */
    @FormUrlEncoded
    @POST("/coupon/modifyCouponUserState")
    Call<BaseResp<BeanRespReqEmpty>> userState(@Field("couponUserId") String couponUserId);


    /**
     * 店铺信息（修改页面用）
     */
    @FormUrlEncoded
    @POST("/sp/detail/info")
    Call<BaseResp<InfoBean>> info(@Field("bdId") String bdId);

    /**
     * 全部评价
     */
    @FormUrlEncoded
    @POST("/sp/detail/commentList")
    Call<BaseResp<EvaluateBean>> evaluate(@Field("pageSize") String pageSize, @Field("bdId") String bdId, @Field("endId") String endId, @Field("type") String type);

    /**
     * 修改商铺信息
     */
    @FormUrlEncoded
    @POST("/sp/detail/editInfo")
    Call<BaseResp<BeanRespReqEmpty>> editInfo(@Field("bdId") String bdId
            , @Field("bdPhone") String bdPhone
            , @Field("bdOpentime") String bdOpentime
            , @Field("bdClosetime") String bdClosetime
            , @Field("bdLogo") String bdLogo
            , @Field("bdDetailpic") String bdDetailpic
            , @Field("bdMainpic") String bdMainpic
            , @Field("bdDemo") String bdDemo);

    /**
     * 文件上传接口
     */
    @Multipart
    @POST("/sp/check/filesUpload")
    Call<FilesUpload> upload(@Part List<MultipartBody.Part> files);


    /**
     * 审核中-店铺信息
     */
    @FormUrlEncoded
    @POST("/sp/check/checkInfoDetail")
    Call<BaseResp<ShopAuditBean>> shopAudit(@Field("bdId") String bdId, @Field("buToken") String buToken, @Field("buId") String buId);

    /**
     * 获取银行
     */
    @GET("/center/check/bankList")
    Call<BaseResp<List<BankListBean>>> bankList();


    /**
     * 新增店铺信息
     */
    @FormUrlEncoded
    @POST("/sp/check/addStore")
    Call<BaseResp<BeanRespReqEmpty>> addStore(
            @Field("bcName") String bcName
            , @Field("bcOwner") int bcOwner
            , @Field("bcStorekeeper") String bcStorekeeper
            , @Field("bcAreacode") String bcAreacode
            , @Field("bcAddress") String bcAddress
            , @Field("bcLongitude") String bcLongitude
            , @Field("bcLatitude") String bcLatitude
            , @Field("bcLogo") String bcLogo
            , @Field("bcMainpic") String bcMainpic
            , @Field("bcDetailpic") String bcDetailpic
            , @Field("bcPhone") String bcPhone
            , @Field("bcOpentime") String bcOpentime
            , @Field("bcClosetime") String bcClosetime
            , @Field("bcCuisineId") int bcCuisineId
            , @Field("bcIdpreUrl") String bcIdpreUrl
            , @Field("bcIdbackUrl") String bcIdbackUrl
            , @Field("bcLicenseUrl") String bcLicenseUrl
            , @Field("bcExequaturUrl") String bcExequaturUrl
            , @Field("bcDemo") String bcDemo);

    /**
     * 新增运营中心
     */
    @FormUrlEncoded
    @POST("/center/check/addCenter")
    Call<BaseResp<BeanRespReqEmpty>> addCenter(
            @Field("occName") String occName
            , @Field("occOwner") String occOwner
            , @Field("occKeepername") String occKeepername
            , @Field("occAreacode") String occAreacode
            , @Field("occAddress") String occAddress
            , @Field("occLongitude") String occLongitude
            , @Field("occLatitude") String occLatitude
            , @Field("occLogo") String occLogo
            , @Field("occPhone") String occPhone
            , @Field("occOpentime") String occOpentime
            , @Field("occClosetime") String occClosetime
            , @Field("occBank") String occBank
            , @Field("occBankcard") String occBankcard
            , @Field("occIdpreUrl") String occIdpreUrl
            , @Field("occIdbackUrl") String occIdbackUrl
            , @Field("occLicenseUrl") String occLicenseUrl
            , @Field("occExequaturUrl") String occExequaturUrl
            , @Field("occDistance") String occDistance
            , @Field("occIsCity") String occIsCity
            , @Field("occContain") String occContain);

    /**
     * 获取运营中心首页数据
     */
    @FormUrlEncoded
    @POST("/center/detail/firstPage")
    Call<BaseResp<CenterHomeBean>> getCenterHome(@Field("buToken") String buToken, @Field("buId") String buId);

    /**
     * 修改运营中心营业状态
     */
    @FormUrlEncoded
    @POST("/center/detail/switchCenterState")
    Call<BaseResp<CenterStateBean>> getCenterState(@Field("ocdId") String ocdId, @Field("buToken") String buToken, @Field("buId") String buId);

    ////////////////////////////////////////////////////套餐//////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 套餐分类列表
     */
    @GET("/package/other/getPackageClassList")
    Call<BaseResp<List<PackageClassListBean>>> getPackageClassList();

    /**
     * 获取菜品分类列表(菜品管理界面)
     */
    @FormUrlEncoded
    @POST("/package/packageGoodsInfo/getPackageGoodsClassList")
    Call<BaseResp<List<PackageGoodsClassListBean>>> getPackageGoodsClassList(@Field("odId") String odId);

    /**
     * 根据种类与状态获取菜品列表
     */
    @FormUrlEncoded
    @POST("/package/packageGoodsInfo/getGoodsListByType")
    Call<BaseResp<GoodsListTypeBean>> getGoodsListByType(@Field("odId") String odId, @Field("type") String type, @Field("classId") String classId);

    /**
     * 编辑菜品库存
     */
    @FormUrlEncoded
    @POST("/package/packageGoodsInfo/editStock")
    Call<BaseResp<BeanRespReqEmpty>> editStock(@Field("id") String id
            , @Field("odId") String odId
            , @Field("stockStatus") String stockStatus
            , @Field("stockNum") String stockNum
            , @Field("stockAutoStatus") String stockAutoStatus
            , @Field("maxStockNum") String maxStockNum);

    /**
     * 菜品种类列表(通用分类查询)
     */
    @GET("/package/other/getPackageGoodsClassList")
    Call<BaseResp<List<GoodsKindBean>>> getPackageGoodsClassList();

    /**
     * 操作菜品上下架
     */
    @FormUrlEncoded
    @POST("/package/packageGoodsInfo/editReleaseStatus")
    Call<BaseResp<BeanRespReqEmpty>> editReleaseStatus(@Field("odId") String odId, @Field("pgiId") String pgiId, @Field("isRelease") String isRelease);

    /**
     * 菜品单位列表
     */
    @GET("/package/other/getPackageArgsList")
    Call<ArgsBean> getPackageArgsList();

    /**
     * 添加菜品
     */
    @FormUrlEncoded
    @POST("/package/packageGoodsInfo/addGoodsInfo")
    Call<BaseResp<BeanRespReqEmpty>> addGoodsInfo(
            @Field("odId") String odId,
            @Field("packageClassId") String packageClassId,
            @Field("goodsClassId") String goodsClassId,
            @Field("name") String name,
            @Field("details") String details,
            @Field("showUrl") String showUrl,
            @Field("mainUrl") String mainUrl,
            @Field("money") String money,
            @Field("originalPrice") String originalPrice,
            @Field("attr") String attr,
            @Field("arg") String arg,
            @Field("stockStatus") String stockStatus,
            @Field("stockNum") String stockNum,
            @Field("stockAutoStatus") String stockAutoStatus,
            @Field("maxStockNum") String maxStockNum,
            @Field("boxNum") String boxNum,
            @Field("boxTotalPrice") String boxTotalPrice,
            @Field("isRelease") String isRelease,
            @Field("isRecommend") String isRecommend,
            @Field("labelIds") String labelIds);

    /**
     * 编辑菜品
     */
    @FormUrlEncoded
    @POST("/package/packageGoodsInfo/editGoodsInfo")
    Call<BaseResp<BeanRespReqEmpty>> editGoodsInfo(
            @Field("id") String id,
            @Field("odId") String odId,
            @Field("packageClassId") String packageClassId,
            @Field("goodsClassId") String goodsClassId,
            @Field("name") String name,
            @Field("details") String details,
            @Field("showUrl") String showUrl,
            @Field("mainUrl") String mainUrl,
            @Field("money") String money,
            @Field("originalPrice") String originalPrice,
            @Field("attr") String attr,
            @Field("arg") String arg,
            @Field("stockStatus") String stockStatus,
            @Field("stockNum") String stockNum,
            @Field("stockAutoStatus") String stockAutoStatus,
            @Field("maxStockNum") String maxStockNum,
            @Field("boxNum") String boxNum,
            @Field("boxTotalPrice") String boxTotalPrice,
            @Field("isRelease") String isRelease,
            @Field("isRecommend") String isRecommend,
            @Field("labelIds") String labelIds);

    /**
     * 菜品标签列表
     */
    @GET("/package/other/getPackageGoodsLabelList")
    Call<BaseResp<List<GoodsLabelBean>>> getPackageGoodsLabelList();

    /**
     * 获取菜品详细信息
     */
    @FormUrlEncoded
    @POST("/package/packageGoodsInfo/getGoodsInfo")
    Call<BaseResp<GoodsDetailBean>> getGoodsInfo(@Field("odId") String odId, @Field("pgiId") String pgiId);


    /**
     * 删除菜品
     */
    @FormUrlEncoded
    @POST("/package/packageGoodsInfo/deleteGoods")
    Call<BaseResp<BeanRespReqEmpty>> deleteGoods(@Field("odId") String odId, @Field("pgiId") String pgiId);

    /**
     * 编辑菜品排序
     */
    @FormUrlEncoded
    @POST("/package/packageGoodsInfo/editSort")
    Call<BaseResp<BeanRespReqEmpty>> editSort(@Field("odId") String odId, @Field("json") String json);

    /**
     * 获取全部菜品排序列表
     */
    @FormUrlEncoded
    @POST("/package/packageGoodsInfo/getSortGoodsClassList")
    Call<BaseResp<List<PackageGoodsClassListBean>>> getSortGoodsClassList(@Field("odId") String odId);

}
