
package com.sngs.swayam.smartapp.Network.model.Category;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class CategoryListDatum {

    @SerializedName("categoryId")
    private String mCategoryId;
    @SerializedName("categoryName")
    private String mCategoryName;
    @SerializedName("serviceId")
    private String mServiceId;
    @SerializedName("serviceName")
    private String mServiceName;

    boolean is_selected;

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

    public String getServiceId() {
        return mServiceId;
    }

    public void setServiceId(String serviceId) {
        mServiceId = serviceId;
    }

    public String getServiceName() {
        return mServiceName;
    }

    public void setServiceName(String serviceName) {
        mServiceName = serviceName;
    }

    public boolean isIs_selected() {
        return is_selected;
    }

    public void setIs_selected(boolean is_selected) {
        this.is_selected = is_selected;
    }
}
