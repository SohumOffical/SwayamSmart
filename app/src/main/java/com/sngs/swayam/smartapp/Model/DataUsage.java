package com.sngs.swayam.smartapp.Model;

public class DataUsage {

    String data_usage_type;
    boolean is_selected;

    public DataUsage(String data_usage_type, boolean is_selected) {
        this.data_usage_type = data_usage_type;
        this.is_selected = is_selected;
    }

    public String getData_usage_type() {
        return data_usage_type;
    }

    public void setData_usage_type(String data_usage_type) {
        this.data_usage_type = data_usage_type;
    }

    public boolean isIs_selected() {
        return is_selected;
    }

    public void setIs_selected(boolean is_selected) {
        this.is_selected = is_selected;
    }
}
