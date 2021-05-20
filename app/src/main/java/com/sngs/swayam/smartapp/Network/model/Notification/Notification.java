
package com.sngs.swayam.smartapp.Network.model.Notification;


import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Notification {

    @SerializedName("notiId")
    private String mNotiId;
    @SerializedName("notiMessage")
    private String mNotiMessage;
    @SerializedName("notiType")
    private String mNotiType;
    @SerializedName("notiDate")
    private String mNotiDate;
    @SerializedName("notiEventId")
    private String mNotiEventId;

    public String getNotiId() {
        return mNotiId;
    }

    public void setNotiId(String notiId) {
        mNotiId = notiId;
    }

    public String getNotiMessage() {
        return mNotiMessage;
    }

    public void setNotiMessage(String notiMessage) {
        mNotiMessage = notiMessage;
    }

    public String getNotiType() {
        return mNotiType;
    }

    public void setNotiType(String notiType) {
        mNotiType = notiType;
    }

    public String getmNotiDate() {
        return mNotiDate;
    }

    public void setmNotiDate(String mNotiDate) {
        this.mNotiDate = mNotiDate;
    }

    public String getmNotiEventId() {
        return mNotiEventId;
    }

    public void setmNotiEventId(String mNotiEventId) {
        this.mNotiEventId = mNotiEventId;
    }
}
