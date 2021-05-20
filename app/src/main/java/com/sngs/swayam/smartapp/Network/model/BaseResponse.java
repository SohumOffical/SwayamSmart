
package com.sngs.swayam.smartapp.Network.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class BaseResponse {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("success")
    private Long mSuccess;

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

}
