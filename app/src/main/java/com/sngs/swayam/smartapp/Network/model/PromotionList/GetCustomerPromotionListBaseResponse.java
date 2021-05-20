
package com.sngs.swayam.smartapp.Network.model.PromotionList;

import java.util.List;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class GetCustomerPromotionListBaseResponse {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("promotionListResult")
    private List<PromotionListResult> mPromotionListResult;
    @SerializedName("success")
    private Long mSuccess;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<PromotionListResult> getPromotionListResult() {
        return mPromotionListResult;
    }

    public void setPromotionListResult(List<PromotionListResult> promotionListResult) {
        mPromotionListResult = promotionListResult;
    }

    public Long getSuccess() {
        return mSuccess;
    }

    public void setSuccess(Long success) {
        mSuccess = success;
    }

}
