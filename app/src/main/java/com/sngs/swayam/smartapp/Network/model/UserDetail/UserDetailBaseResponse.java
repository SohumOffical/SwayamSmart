
package com.sngs.swayam.smartapp.Network.model.UserDetail;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class UserDetailBaseResponse {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("success")
    private Long mSuccess;
    @SerializedName("userResult")
    private UserResult mUserResult;

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

    public UserResult getUserResult() {
        return mUserResult;
    }

    public void setUserResult(UserResult userResult) {
        mUserResult = userResult;
    }

}
