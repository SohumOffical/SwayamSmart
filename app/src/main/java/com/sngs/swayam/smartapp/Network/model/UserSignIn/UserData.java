
package com.sngs.swayam.smartapp.Network.model.UserSignIn;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class UserData {

    @SerializedName("authId")
    private String mAuthId;
    @SerializedName("authToken")
    private String mAuthToken;
    @SerializedName("contactNumber")
    private String mContactNumber;
    @SerializedName("name")
    private String mName;
    @SerializedName("otp")
    private Long mOtp;
    @SerializedName("userType")
    private String mUserType;

    public String getAuthId() {
        return mAuthId;
    }

    public void setAuthId(String authId) {
        mAuthId = authId;
    }

    public String getAuthToken() {
        return mAuthToken;
    }

    public void setAuthToken(String authToken) {
        mAuthToken = authToken;
    }

    public String getContactNumber() {
        return mContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        mContactNumber = contactNumber;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Long getOtp() {
        return mOtp;
    }

    public void setOtp(Long otp) {
        mOtp = otp;
    }

    public String getUserType() {
        return mUserType;
    }

    public void setUserType(String userType) {
        mUserType = userType;
    }

}
