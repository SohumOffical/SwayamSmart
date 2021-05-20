
package com.sngs.swayam.smartapp.Network.model.CustomerDetail;


import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class CategoryResult {

    @SerializedName("categoryId")
    private String mCategoryId;
    @SerializedName("categoryName")
    private String mCategoryName;

    public String getCategoryId() {
        return mCategoryId;
    }

    public void setCategoryId(String categoryId) {
        mCategoryId = categoryId;
    }

    public String getCategoryName() {
        return mCategoryName;
    }

    public void setCategoryName(String categoryName) {
        mCategoryName = categoryName;
    }

}
