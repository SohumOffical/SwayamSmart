
package com.sngs.swayam.smartapp.Network.model.Area;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class AreaListDatum {

    @SerializedName("areaId")
    private String mAreaId;
    @SerializedName("areaName")
    private String mAreaName;
    @SerializedName("cityId")
    private String mCityId;
    @SerializedName("cityName")
    private String mCityName;
    @SerializedName("stateId")
    private String mStateId;
    @SerializedName("stateName")
    private String mStateName;

    boolean is_selected;

    public boolean isIs_selected() {
        return is_selected;
    }

    public void setIs_selected(boolean is_selected) {
        this.is_selected = is_selected;
    }

    public String getAreaId() {
        return mAreaId;
    }

    public void setAreaId(String areaId) {
        mAreaId = areaId;
    }

    public String getAreaName() {
        return mAreaName;
    }

    public void setAreaName(String areaName) {
        mAreaName = areaName;
    }

    public String getCityId() {
        return mCityId;
    }

    public void setCityId(String cityId) {
        mCityId = cityId;
    }

    public String getCityName() {
        return mCityName;
    }

    public void setCityName(String cityName) {
        mCityName = cityName;
    }

    public String getStateId() {
        return mStateId;
    }

    public void setStateId(String stateId) {
        mStateId = stateId;
    }

    public String getStateName() {
        return mStateName;
    }

    public void setStateName(String stateName) {
        mStateName = stateName;
    }

}
