package com.jacob.www.easycar.data;

/**
 * Created by ASUS-NB on 2017/11/12.
 */

public class UserBean {

    /**
     * code : 200
     * message : 操作成功
     * data : {"username":"m15340504857","phone":"15340504857","uid":"1510466559729161473","icon":"/user_image/default.jpg"}
     */

    private int code;
    private String message;
    /**
     * username : m15340504857
     * phone : 15340504857
     * uid : 1510466559729161473
     * icon : /user_image/default.jpg
     */

    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String username;
        private String phone;
        private String uid;
        private String icon;

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
}
