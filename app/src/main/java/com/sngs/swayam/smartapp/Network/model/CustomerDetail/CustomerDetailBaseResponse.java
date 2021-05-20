
package com.sngs.swayam.smartapp.Network.model.CustomerDetail;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class CustomerDetailBaseResponse {

    @SerializedName("customerResult")
    private CustomerResult mCustomerResult;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("success")
    private Long mSuccess;

    public CustomerResult getCustomerResult() {
        return mCustomerResult;
    }

    public void setCustomerResult(CustomerResult customerResult) {
        mCustomerResult = customerResult;
    }

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
