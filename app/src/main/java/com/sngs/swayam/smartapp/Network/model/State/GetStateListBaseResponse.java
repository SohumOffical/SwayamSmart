
package com.sngs.swayam.smartapp.Network.model.State;

import com.google.gson.annotations.SerializedName;

import java.util.List;


@SuppressWarnings("unused")
public class GetStateListBaseResponse {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("stateListData")
    private List<StateListDatum> mStateListData;
    @SerializedName("success")
    private Long mSuccess;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<StateListDatum> getStateListData() {
        return mStateListData;
    }

    public void setStateListData(List<StateListDatum> stateListData) {
        mStateListData = stateListData;
    }

    public Long getSuccess() {
        return mSuccess;
    }

    public void setSuccess(Long success) {
        mSuccess = success;
    }

}
