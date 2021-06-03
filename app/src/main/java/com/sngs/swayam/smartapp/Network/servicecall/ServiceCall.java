package com.sngs.swayam.smartapp.Network.servicecall;

import android.content.Context;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;

import com.sngs.swayam.smartapp.Network.HashMapLog;
import com.sngs.swayam.smartapp.Network.WebUtlis.Links;
import com.sngs.swayam.smartapp.Network.apiinterface.APIInterface;
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
import com.sngs.swayam.smartapp.Network.remotes.APIClient;

import java.io.File;
import java.util.HashMap;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

public class ServiceCall  extends AppCompatActivity {


    //Swayam Smart
    public static Call<UserSignUpBaseResponse> callUserSignUp(Context context,
                                                              String device_id, String fcm_token,
                                                              String user_Type,
                                                              String user_Name,
                                                              String user_Contact_Number,
                                                              String Email,
                                                              String Gender, String Birthdate,
                                                              String State, String City, String Area) {

        HashMap<String, String> mBodyMap = new HashMap<String, String>();
        mBodyMap.put(Links.Header.Device_Id, device_id);
        mBodyMap.put(Links.Header.Reg_Id,fcm_token);
        mBodyMap.put(Links.UserSignUp.User_Type,user_Type);
        mBodyMap.put(Links.UserSignUp.User_Name,user_Name);
        mBodyMap.put(Links.UserSignUp.User_Contact_Number,user_Contact_Number);
        mBodyMap.put(Links.UserSignUp.User_Email,Email);
        mBodyMap.put(Links.UserSignUp.User_Gender,Gender);
        mBodyMap.put(Links.UserSignUp.User_BirthDate,Birthdate);
        mBodyMap.put(Links.UserSignUp.User_State,State);
        mBodyMap.put(Links.UserSignUp.User_City,City);
        mBodyMap.put(Links.UserSignUp.User_Area,Area);

        HashMapLog.getHashMapLog("callUserSignUp", mBodyMap);

        return APIClient.getClient().create(APIInterface.class).postUserSignUp(mBodyMap);
    }

    //Edit User Profile
    public static Call<BaseResponse> callEditUserProfile(Context context,
                                                         String auth_id,
                                                         String auth_token,
                                                         String user_type, String user_name,
                                                         String user_contactnumber,
                                                         String Email,
                                                         String Gender, String Birthdate,
                                                         String State, String City, String Area, File img) {

        MediaType mediaType = MediaType.parse("multipart/form-data");
        MultipartBody.Part img_File = null;

        if(img!=null)
        {
            RequestBody requestBody = RequestBody.create(mediaType,img);
            img_File = MultipartBody.Part.createFormData(Links.EditUserProfile.User_Profile_Pic,
                    Uri.fromFile(img).toString(), requestBody);
        }

        RequestBody authid = RequestBody.create(MediaType.parse("text/plain"), auth_id);
        RequestBody authtoken = RequestBody.create(MediaType.parse("text/plain"),auth_token);
        RequestBody usertype = RequestBody.create(MediaType.parse("text/plain"),user_type);
        RequestBody username = RequestBody.create(MediaType.parse("text/plain"),user_name);
        RequestBody usercontactnumber = RequestBody.create(MediaType.parse("text/plain"),user_contactnumber);
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"),Email);
        RequestBody gender = RequestBody.create(MediaType.parse("text/plain"),Gender);
        RequestBody birthdate = RequestBody.create(MediaType.parse("text/plain"),Birthdate);
        RequestBody state = RequestBody.create(MediaType.parse("text/plain"),State);
        RequestBody city = RequestBody.create(MediaType.parse("text/plain"),City);
        RequestBody area = RequestBody.create(MediaType.parse("text/plain"),Area);

        return APIClient.getClient().create(APIInterface.class).postEditUserProfile(
                authid,
                authtoken,
                usertype,
                username,
                usercontactnumber,
                email,
                gender,
                birthdate,
                state,
                city,
                area,
                img_File);
    }

    //Swayam Smart
    public static Call<UserSignInBaseResponse> callUserSignIn(Context context,String device_id,
                                                              String fcm_token,
                                                              String user_Type,
                                                              String user_Contact_Number) {

        HashMap<String, String> mBodyMap = new HashMap<String, String>();
        mBodyMap.put(Links.Header.Device_Id, device_id);
        mBodyMap.put(Links.Header.Reg_Id,fcm_token);
        mBodyMap.put(Links.UserSignIn.User_Type,user_Type);
        mBodyMap.put(Links.UserSignIn.User_Contact_Number,user_Contact_Number);

        HashMapLog.getHashMapLog("callUserSignIn", mBodyMap);

        return APIClient.getClient().create(APIInterface.class).postUserSignIn(mBodyMap);
    }

    //Mobile Verify
    public static Call<MobileVerifyBaseResponse> callMobileVerify(Context context, String user_type, String contact_number) {

        HashMap<String, String> mBodyMap = new HashMap<String, String>();
        mBodyMap.put(Links.MobileVerifyDetail.User_Type,user_type);
        mBodyMap.put(Links.MobileVerifyDetail.Contact_Number,contact_number);

        HashMapLog.getHashMapLog("callMobileVerify", mBodyMap);

        return APIClient.getClient().create(APIInterface.class).postMobileVerify(mBodyMap);
    }



    //Get State List
    public static Call<GetStateListBaseResponse> callGetStateList(Context context, String auth_id, String auth_token, String user_type) {

        HashMap<String, String> mBodyMap = new HashMap<String, String>();
        mBodyMap.put(Links.Header.Auth_ID,auth_id);
        mBodyMap.put(Links.Header.Auth_Token,auth_token);
        mBodyMap.put(Links.Header.User_Type,user_type);

        HashMapLog.getHashMapLog("callGetStateList", mBodyMap);

        return APIClient.getClient().create(APIInterface.class).postGetStateList(mBodyMap);
    }

    //Get City List
    public static Call<GetCityListBaseResponse> callGetCityList(Context context, String auth_id, String auth_token, String user_type,
                                                                String state_id) {

        HashMap<String, String> mBodyMap = new HashMap<String, String>();
        mBodyMap.put(Links.Header.Auth_ID,auth_id);
        mBodyMap.put(Links.Header.Auth_Token,auth_token);
        mBodyMap.put(Links.Header.User_Type,user_type);
        mBodyMap.put(Links.City_List_Detail.state_Id,state_id);

        HashMapLog.getHashMapLog("callGetCityList", mBodyMap);

        return APIClient.getClient().create(APIInterface.class).postGetCityList(mBodyMap);
    }

    //Get City List
    public static Call<GetAreaListBaseResponse> callGetAreaList(Context context, String auth_id, String auth_token, String user_type,
                                                                String state_id, String city_id) {

        HashMap<String, String> mBodyMap = new HashMap<String, String>();
        mBodyMap.put(Links.Header.Auth_ID,auth_id);
        mBodyMap.put(Links.Header.Auth_Token,auth_token);
        mBodyMap.put(Links.Header.User_Type,user_type);
        mBodyMap.put(Links.Area_List_Detail.state_Id,state_id);
        mBodyMap.put(Links.Area_List_Detail.city_Id,city_id);

        HashMapLog.getHashMapLog("callGetAreaList", mBodyMap);

        return APIClient.getClient().create(APIInterface.class).postGetAreaList(mBodyMap);
    }


    //Advertisment List
    public static Call<AdvertismentListBaseResponse> callAdvertismentList(Context context, String auth_id, String auth_token, String user_type) {

        HashMap<String, String> mBodyMap = new HashMap<String, String>();
        mBodyMap.put(Links.Header.Auth_ID,auth_id);
        mBodyMap.put(Links.Header.Auth_Token,auth_token);
        mBodyMap.put(Links.Header.User_Type,user_type);

        HashMapLog.getHashMapLog("callGetBannerList", mBodyMap);

        return APIClient.getClient().create(APIInterface.class).postAdvertisementList(mBodyMap);
    }


    //User Promotion List
    public static Call<GetCustomerPromotionListBaseResponse> callUserPromotionList(Context context, String auth_id,
                                                                                   String auth_token, String user_type,
                                                                                   String category_Id) {

        HashMap<String, String> mBodyMap = new HashMap<String, String>();
        mBodyMap.put(Links.Header.Auth_ID,auth_id);
        mBodyMap.put(Links.Header.Auth_Token,auth_token);
        mBodyMap.put(Links.Header.User_Type,user_type);
        mBodyMap.put(Links.Promotion_Detail.category_Id,category_Id);

        HashMapLog.getHashMapLog("callUserPromotionList", mBodyMap);

        return APIClient.getClient().create(APIInterface.class).postUserPromotionDetailList(mBodyMap);
    }


    //Banner List
    public static Call<BannerListBaseResponse> callGetBannerList(Context context, String auth_id, String auth_token, String user_type) {

        HashMap<String, String> mBodyMap = new HashMap<String, String>();
        mBodyMap.put(Links.Header.Auth_ID,auth_id);
        mBodyMap.put(Links.Header.Auth_Token,auth_token);
        mBodyMap.put(Links.Header.User_Type,user_type);

        HashMapLog.getHashMapLog("callGetBannerList", mBodyMap);

        return APIClient.getClient().create(APIInterface.class).postBannerList(mBodyMap);
    }



    //User Detail List
    public static Call<UserDetailBaseResponse> callUserDetail(Context context, String auth_id, String auth_token, String user_type) {

        HashMap<String, String> mBodyMap = new HashMap<String, String>();
        mBodyMap.put(Links.Header.Auth_ID,auth_id);
        mBodyMap.put(Links.Header.Auth_Token,auth_token);
        mBodyMap.put(Links.Header.User_Type,user_type);

        HashMapLog.getHashMapLog("callUserDetail", mBodyMap);

        return APIClient.getClient().create(APIInterface.class).postUserDetail(mBodyMap);
    }

    //User Detail List
    public static Call<BaseResponse> callUserPurchaseDetail(Context context, String auth_id,
                                                              String auth_token, String user_type,
                                                    String promotion_Id) {

        HashMap<String, String> mBodyMap = new HashMap<String, String>();
        mBodyMap.put(Links.Header.Auth_ID,auth_id);
        mBodyMap.put(Links.Header.Auth_Token,auth_token);
        mBodyMap.put(Links.Header.User_Type,user_type);
        mBodyMap.put(Links.Purchase_Promotion_Detail.Promotion_Id,promotion_Id);

        HashMapLog.getHashMapLog("callUserPurchaseDetail", mBodyMap);

        return APIClient.getClient().create(APIInterface.class).postUserPurchasePromotion(mBodyMap);
    }

    //View Promotion
    public static Call<BaseResponse> callViewPromotion(Context context, String auth_id, String auth_token, String user_type, String promotion_Id) {

        HashMap<String, String> mBodyMap = new HashMap<String, String>();
        mBodyMap.put(Links.Header.Auth_ID,auth_id);
        mBodyMap.put(Links.Header.Auth_Token,auth_token);
        mBodyMap.put(Links.Header.User_Type,user_type);
        mBodyMap.put(Links.ViewPromotion.Promotion_Id,promotion_Id);

        HashMapLog.getHashMapLog("callViewPromotion", mBodyMap);

        return APIClient.getClient().create(APIInterface.class).postViewPromotion(mBodyMap);
    }

    //Customer Detail
    public static Call<CustomerDetailBaseResponse> callCustomerDetail(Context context, String auth_id,
                                                                      String auth_token,
                                                                      String user_type,String customer_Id) {

        HashMap<String, String> mBodyMap = new HashMap<String, String>();
        mBodyMap.put(Links.Header.Auth_ID,auth_id);
        mBodyMap.put(Links.Header.Auth_Token,auth_token);
        mBodyMap.put(Links.Header.User_Type,user_type);
        mBodyMap.put(Links.CustomerDetail.Customer_Id,customer_Id);


        HashMapLog.getHashMapLog("callCustomerDetail", mBodyMap);

        return APIClient.getClient().create(APIInterface.class).postCustomerDetail(mBodyMap);
    }

    //Query Detail
    public static Call<BaseResponse> callAddPromotionQuery(Context context, String auth_id,
                                                          String auth_token,
                                                          String user_type,String promotion_Id,
                                                           String customer_Id,String query) {

        HashMap<String, String> mBodyMap = new HashMap<String, String>();
        mBodyMap.put(Links.Header.Auth_ID,auth_id);
        mBodyMap.put(Links.Header.Auth_Token,auth_token);
        mBodyMap.put(Links.Header.User_Type,user_type);
        mBodyMap.put(Links.PromotionQueryDetail.Promotion_Id,promotion_Id);
        mBodyMap.put(Links.PromotionQueryDetail.Customer_Id,customer_Id);
        mBodyMap.put(Links.PromotionQueryDetail.Query,query);

        HashMapLog.getHashMapLog("callAddPromotionQuery", mBodyMap);

        return APIClient.getClient().create(APIInterface.class).postAddPromotionQuery(mBodyMap);
    }

    //Promotion LikeDislike
    public static Call<BaseResponse> callPromotionLikeDislike(Context context, String auth_id,
                                                           String auth_token,
                                                           String user_type,String promotion_Id,
                                                           String likeDislike) {

        HashMap<String, String> mBodyMap = new HashMap<String, String>();
        mBodyMap.put(Links.Header.Auth_ID,auth_id);
        mBodyMap.put(Links.Header.Auth_Token,auth_token);
        mBodyMap.put(Links.Header.User_Type,user_type);
        mBodyMap.put(Links.PromotionLikeDislikeDetail.Promotion_Id,promotion_Id);
        mBodyMap.put(Links.PromotionLikeDislikeDetail.Like_Dislike,likeDislike);

        HashMapLog.getHashMapLog("callPromotionLikeDislike", mBodyMap);

        return APIClient.getClient().create(APIInterface.class).postPromotionLikeDislike(mBodyMap);
    }

    public static Call<BaseResponse> callContactUS(Context context, String auth_id, String auth_token,
                                                   String user_type,String name,
                                                   String mobile_no,String email,String message) {

        HashMap<String, String> mBodyMap = new HashMap<String, String>();
        mBodyMap.put(Links.Header.Auth_ID,auth_id);
        mBodyMap.put(Links.Header.Auth_Token,auth_token);
        mBodyMap.put(Links.Header.User_Type,user_type);
        mBodyMap.put(Links.ContactUSDetail.Name,name);
        mBodyMap.put(Links.ContactUSDetail.Contact_Number,mobile_no);
        mBodyMap.put(Links.ContactUSDetail.Email,email);
        mBodyMap.put(Links.ContactUSDetail.Message,message);

        HashMapLog.getHashMapLog("callContactUS", mBodyMap);

        return APIClient.getClient().create(APIInterface.class).postContactUS(mBodyMap);
    }

    public static Call<NotificationBaseResponse> callGetNotificationList(Context context, String auth_id, String auth_token,
                                                                         String user_type) {

        HashMap<String, String> mBodyMap = new HashMap<String, String>();
        mBodyMap.put(Links.Header.Auth_ID,auth_id);
        mBodyMap.put(Links.Header.Auth_Token,auth_token);
        mBodyMap.put(Links.Header.User_Type,user_type);

        HashMapLog.getHashMapLog("callGetNotificationList", mBodyMap);

        return APIClient.getClient().create(APIInterface.class).postGetNotificationList(mBodyMap);
    }

    //Service Provider List
    public static Call<GetServiceProviderBaseResponse> callGetServiceProviderList(Context context, String auth_id, String auth_token, String user_type) {

        HashMap<String, String> mBodyMap = new HashMap<String, String>();
        mBodyMap.put(Links.Header.Auth_ID,auth_id);
        mBodyMap.put(Links.Header.Auth_Token,auth_token);
        mBodyMap.put(Links.Header.User_Type,user_type);

        HashMapLog.getHashMapLog("callGetServiceProviderList", mBodyMap);

        return APIClient.getClient().create(APIInterface.class).postGetServiceProviderList(mBodyMap);
    }

    //Get Category List
    public static Call<GetCategoryListBaseResponse> callGetCategoryList(Context context, String auth_id, String auth_token, String user_type,
                                                                        String service_id) {

        HashMap<String, String> mBodyMap = new HashMap<String, String>();
        mBodyMap.put(Links.Header.Auth_ID,auth_id);
        mBodyMap.put(Links.Header.Auth_Token,auth_token);
        mBodyMap.put(Links.Header.User_Type,user_type);
        mBodyMap.put(Links.GetCategoryListDetail.Service_Id,service_id);

        HashMapLog.getHashMapLog("callGetCategoryList", mBodyMap);

        return APIClient.getClient().create(APIInterface.class).postGetCategoryList(mBodyMap);
    }

}
