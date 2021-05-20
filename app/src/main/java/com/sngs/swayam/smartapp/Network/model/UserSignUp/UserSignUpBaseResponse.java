
package com.sngs.swayam.smartapp.Network.model.UserSignUp;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class UserSignUpBaseResponse {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("success")
    private Long mSuccess;
    @SerializedName("userDetail")
    private UserDetail mUserDetail;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Long getSuccess() {
        return mSuccess;
    }

    public void setSuccess(Long success) {
        mSuccess = success;
    }

    public UserDetail getUserDetail() {
        return mUserDetail;
    }

    public void setUserDetail(UserDetail userDetail) {
        mUserDetail = userDetail;
    }

}
