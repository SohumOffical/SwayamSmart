
package com.sngs.swayam.smartapp.Network.model.AdvertismentList;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class PromotionBannerListResult {

    @SerializedName("advertisement")
    private String mAdvertisement;
    @SerializedName("advertisementId")
    private String mAdvertisementId;

    public String getAdvertisement() {
        return mAdvertisement;
    }

    public void setAdvertisement(String advertisement) {
        mAdvertisement = advertisement;
    }

    public String getAdvertisementId() {
        return mAdvertisementId;
    }

    public void setAdvertisementId(String advertisementId) {
        mAdvertisementId = advertisementId;
    }

}
