
package com.sngs.swayam.smartapp.Network.model.BannerList;

import java.util.List;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class BannerListBaseResponse {

    @SerializedName("bannerListResult")
    private List<BannerListResult> mBannerListResult;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("success")
    private Long mSuccess;

    public List<BannerListResult> getBannerListResult() {
        return mBannerListResult;
    }

    public void setBannerListResult(List<BannerListResult> bannerListResult) {
        mBannerListResult = bannerListResult;
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
