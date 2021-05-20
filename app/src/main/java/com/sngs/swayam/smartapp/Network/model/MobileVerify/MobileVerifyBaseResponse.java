
package com.sngs.swayam.smartapp.Network.model.MobileVerify;


import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class MobileVerifyBaseResponse {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("OTP")
    private Long mOTP;
    @SerializedName("success")
    private Long mSuccess;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Long getOTP() {
        return mOTP;
    }

    public void setOTP(Long oTP) {
        mOTP = oTP;
    }

    public Long getSuccess() {
        return mSuccess;
    }

    public void setSuccess(Long success) {
        mSuccess = success;
    }

}
