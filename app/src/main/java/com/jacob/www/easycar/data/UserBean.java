package com.jacob.www.easycar.data;

import java.util.List;

/**
 * Created by ASUS-NB on 2017/11/12.
 */

public class UserBean {

    /**
     * icon : string
     * phone : string
     * plateNumberInfo : ["string"]
     * uid : string
     * username : string
     */

    private String icon;
    private String phone;
    private String uid;
    private String username;
    private List<String> plateNumberInfo;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getPlateNumberInfo() {
        return plateNumberInfo;
    }

    public void setPlateNumberInfo(List<String> plateNumberInfo) {
        this.plateNumberInfo = plateNumberInfo;
    }
}
