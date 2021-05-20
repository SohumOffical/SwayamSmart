
package com.sngs.swayam.smartapp.Network.model.Area;

import com.google.gson.annotations.SerializedName;

import java.util.List;


@SuppressWarnings("unused")
public class GetAreaListBaseResponse {

    @SerializedName("areaListData")
    private List<AreaListDatum> mAreaListData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("success")
    private Long mSuccess;

    public List<AreaListDatum> getAreaListData() {
        return mAreaListData;
    }

    public void setAreaListData(List<AreaListDatum> areaListData) {
        mAreaListData = areaListData;
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
