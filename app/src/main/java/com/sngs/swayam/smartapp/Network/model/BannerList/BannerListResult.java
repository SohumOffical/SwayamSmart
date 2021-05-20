
package com.sngs.swayam.smartapp.Network.model.BannerList;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class BannerListResult {

    @SerializedName("banner")
    private String mBanner;
    @SerializedName("bannerId")
    private String mBannerId;

    public String getBanner() {
        return mBanner;
    }

    public void setBanner(String banner) {
        mBanner = banner;
    }

    public String getBannerId() {
        return mBannerId;
    }

    public void setBannerId(String bannerId) {
        mBannerId = bannerId;
    }

}
