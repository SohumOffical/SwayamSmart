
package com.sngs.swayam.smartapp.Network.model.UserSignUp;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class UserDetail {

    @SerializedName("authId")
    private Long mAuthId;
    @SerializedName("authToken")
    private String mAuthToken;
    @SerializedName("userContactNumber")
    private String mUserContactNumber;
    @SerializedName("userName")
    private String mUserName;
    @SerializedName("userType")
    private String mUserType;

    public Long getAuthId() {
        return mAuthId;
    }

    public void setAuthId(Long authId) {
        mAuthId = authId;
    }

    public String getAuthToken() {
        return mAuthToken;
    }

    public void setAuthToken(String authToken) {
        mAuthToken = authToken;
    }

    public String getUserContactNumber() {
        return mUserContactNumber;
    }

    public void setUserContactNumber(String userContactNumber) {
        mUserContactNumber = userContactNumber;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getUserType() {
        return mUserType;
    }

    public void setUserType(String userType) {
        mUserType = userType;
    }

}
