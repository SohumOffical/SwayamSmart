
package com.sngs.swayam.smartapp.Network.model.UserSignIn;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class UserSignInBaseResponse {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("success")
    private Long mSuccess;
    @SerializedName("userData")
    private UserData mUserData;

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

    public UserData getUserData() {
        return mUserData;
    }

    public void setUserData(UserData userData) {
        mUserData = userData;
    }

}
