
package com.sngs.swayam.smartapp.Network.model.City;

import com.google.gson.annotations.SerializedName;

import java.util.List;


@SuppressWarnings("unused")
public class GetCityListBaseResponse {

    @SerializedName("cityListData")
    private List<CityListDatum> mCityListData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("success")
    private Long mSuccess;

    public List<CityListDatum> getCityListData() {
        return mCityListData;
    }

    public void setCityListData(List<CityListDatum> cityListData) {
        mCityListData = cityListData;
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
