
package com.sngs.swayam.smartapp.Network.model.State;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class StateListDatum {

    @SerializedName("stateId")
    private String mStateId;
    @SerializedName("stateName")
    private String mStateName;

    boolean is_selected;

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

    public boolean isIs_selected() {
        return is_selected;
    }

    public void setIs_selected(boolean is_selected) {
        this.is_selected = is_selected;
    }
}
