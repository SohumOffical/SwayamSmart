
package com.sngs.swayam.smartapp.Network.model.ServiceProvider;

import com.google.gson.annotations.SerializedName;

import java.util.List;


@SuppressWarnings("unused")
public class GetServiceProviderBaseResponse {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("serviceListData")
    private List<ServiceListDatum> mServiceListData;
    @SerializedName("success")
    private Long mSuccess;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<ServiceListDatum> getServiceListData() {
        return mServiceListData;
    }

    public void setServiceListData(List<ServiceListDatum> serviceListData) {
        mServiceListData = serviceListData;
    }

    public Long getSuccess() {
        return mSuccess;
    }

    public void setSuccess(Long success) {
        mSuccess = success;
    }

}
