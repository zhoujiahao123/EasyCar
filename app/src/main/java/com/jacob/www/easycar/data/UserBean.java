package com.jacob.www.easycar.data;

/**
 * Created by ASUS-NB on 2017/11/12.
 */

public class UserBean {

    private String username;
    private String phone;
    private String uid;
    private String icon;

    @Override
    public String toString() {
        return "UserBean{" +
                "username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", uid='" + uid + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
