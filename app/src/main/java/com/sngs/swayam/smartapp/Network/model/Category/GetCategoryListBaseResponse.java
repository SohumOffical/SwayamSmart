
package com.sngs.swayam.smartapp.Network.model.Category;

import com.google.gson.annotations.SerializedName;

import java.util.List;


@SuppressWarnings("unused")
public class GetCategoryListBaseResponse {

    @SerializedName("categoryListData")
    private List<CategoryListDatum> mCategoryListData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("success")
    private Long mSuccess;

    public List<CategoryListDatum> getCategoryListData() {
        return mCategoryListData;
    }

    public void setCategoryListData(List<CategoryListDatum> categoryListData) {
        mCategoryListData = categoryListData;
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
