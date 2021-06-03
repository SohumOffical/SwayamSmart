package com.sngs.swayam.smartapp.Network.apiinterface;



import com.sngs.swayam.smartapp.Network.WebUtlis.Links;
import com.sngs.swayam.smartapp.Network.model.AdvertismentList.AdvertismentListBaseResponse;
import com.sngs.swayam.smartapp.Network.model.Area.GetAreaListBaseResponse;
import com.sngs.swayam.smartapp.Network.model.BannerList.BannerListBaseResponse;
import com.sngs.swayam.smartapp.Network.model.BaseResponse;
import com.sngs.swayam.smartapp.Network.model.Category.GetCategoryListBaseResponse;
import com.sngs.swayam.smartapp.Network.model.City.GetCityListBaseResponse;
import com.sngs.swayam.smartapp.Network.model.CustomerDetail.CustomerDetailBaseResponse;
import com.sngs.swayam.smartapp.Network.model.MobileVerify.MobileVerifyBaseResponse;
import com.sngs.swayam.smartapp.Network.model.Notification.NotificationBaseResponse;
import com.sngs.swayam.smartapp.Network.model.PromotionList.GetCustomerPromotionListBaseResponse;
import com.sngs.swayam.smartapp.Network.model.ServiceProvider.GetServiceProviderBaseResponse;
import com.sngs.swayam.smartapp.Network.model.State.GetStateListBaseResponse;
import com.sngs.swayam.smartapp.Network.model.UserDetail.UserDetailBaseResponse;
import com.sngs.swayam.smartapp.Network.model.UserSignIn.UserSignInBaseResponse;
import com.sngs.swayam.smartapp.Network.model.UserSignUp.UserSignUpBaseResponse;

import java.util.HashMap;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIInterface {

    //Swayam Smart
    @POST(Links.User_SignUp)
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<UserSignUpBaseResponse> postUserSignUp(@Body HashMap<String, String> mBodyMap);

    //Swayam Smart
    @POST(Links.User_SignIn)
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<UserSignInBaseResponse> postUserSignIn(@Body HashMap<String, String> mBodyMap);

    //Mobile Verify
    @POST(Links.Mobile_Verify)
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<MobileVerifyBaseResponse> postMobileVerify(@Body HashMap<String, String> mBodyMap);

    @Multipart
    @POST(Links.Edit_User_Profile)
    Call<BaseResponse> postEditUserProfile(@Part(Links.Header.Auth_ID) RequestBody auth_id,
                                           @Part(Links.Header.Auth_Token) RequestBody auth_token,
                                           @Part(Links.Header.User_Type) RequestBody user_type,
                                           @Part(Links.EditUserProfile.User_Name) RequestBody user_name,
                                           @Part(Links.EditUserProfile.User_ContactNumber) RequestBody User_ContactNumber,
                                           @Part(Links.EditUserProfile.User_Email) RequestBody User_Email,
                                           @Part(Links.EditUserProfile.User_Gender) RequestBody User_Gender,
                                           @Part(Links.EditUserProfile.User_BirthDate) RequestBody User_BirthDate,
                                           @Part(Links.EditUserProfile.User_State) RequestBody User_State,
                                           @Part(Links.EditUserProfile.User_City) RequestBody User_City,
                                           @Part(Links.EditUserProfile.User_Area) RequestBody User_Area,
                                           @Part MultipartBody.Part service_File);


    //Get State List
    @POST(Links.Get_State_List)
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<GetStateListBaseResponse> postGetStateList(@Body HashMap<String, String> mBodyMap);

    //Get City List
    @POST(Links.Get_City_List)
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<GetCityListBaseResponse> postGetCityList(@Body HashMap<String, String> mBodyMap);

    //Get Area List
    @POST(Links.Get_Area_List)
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<GetAreaListBaseResponse> postGetAreaList(@Body HashMap<String, String> mBodyMap);



    //Advertisment List
    @POST(Links.Get_Advertisement_List)
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<AdvertismentListBaseResponse> postAdvertisementList(@Body HashMap<String, String> mBodyMap);


    //User Promotion List
    @POST(Links.Get_UserPromotionDetail_List)
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<GetCustomerPromotionListBaseResponse> postUserPromotionDetailList(@Body HashMap<String, String> mBodyMap);


    //Banner List
    @POST(Links.Get_Banner_List)
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<BannerListBaseResponse> postBannerList(@Body HashMap<String, String> mBodyMap);

    //User Detail
    @POST(Links.Get_User_Detail)
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<UserDetailBaseResponse> postUserDetail(@Body HashMap<String, String> mBodyMap);

    //purchase promation
    @POST(Links.User_Purchase_Promotion)
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<BaseResponse> postUserPurchasePromotion(@Body HashMap<String, String> mBodyMap);

    //View Promotion
    @POST(Links.View_Promotion)
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<BaseResponse> postViewPromotion(@Body HashMap<String, String> mBodyMap);

    //Attribute List
    @POST(Links.Get_Customer_Detail)
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<CustomerDetailBaseResponse> postCustomerDetail(@Body HashMap<String, String> mBodyMap);

    //Query Detail
    @POST(Links.Add_Promotion_Query)
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<BaseResponse> postAddPromotionQuery(@Body HashMap<String, String> mBodyMap);

    //Promotion LikeDislike
    @POST(Links.Promotion_Like_Dislike)
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<BaseResponse> postPromotionLikeDislike(@Body HashMap<String, String> mBodyMap);

    //contact us
    @POST(Links.Contact_US)
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<BaseResponse> postContactUS(@Body HashMap<String, String> mBodyMap);

    @POST(Links.Get_Notification_List)
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<NotificationBaseResponse> postGetNotificationList(@Body HashMap<String, String> mBodyMap);


    //Service Provider List
    @POST(Links.Get_Service_Provider_List)
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<GetServiceProviderBaseResponse> postGetServiceProviderList(@Body HashMap<String, String> mBodyMap);


    //Category List
    @POST(Links.Get_Category_List)
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<GetCategoryListBaseResponse> postGetCategoryList(@Body HashMap<String, String> mBodyMap);

}
