
package com.sngs.swayam.smartapp.Network.model.AdvertismentList;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class AdvertismentListBaseResponse {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("promotionBannerListResult")
    private List<PromotionBannerListResult> mPromotionBannerListResult;
    @SerializedName("success")
    private Long mSuccess;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<PromotionBannerListResult> getPromotionBannerListResult() {
        return mPromotionBannerListResult;
    }

    public void setPromotionBannerListResult(List<PromotionBannerListResult> promotionBannerListResult) {
        mPromotionBannerListResult = promotionBannerListResult;
    }

    public Long getSuccess() {
        return mSuccess;
    }

    public void setSuccess(Long success) {
        mSuccess = success;
    }

}
