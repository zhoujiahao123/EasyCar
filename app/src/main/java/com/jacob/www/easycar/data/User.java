package com.jacob.www.easycar.data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

import java.util.List;

/**
 *
 * @author ASUS-NB
 * @date 2017/11/12
 */
@Entity
public class User {

    /**
     * icon : string
     * phone : string
     * plateNumberInfo : ["string"]
     * uid : string
     * username : string
     */
    @Id
    private Long id;
    private String icon;
    private String phone;
    private String uid;
    private String username;
    private String plateNums;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlateNums() {
        return plateNums;
    }

    public void setPlateNums(String plateNums) {
        this.plateNums = plateNums;
    }

    @Transient
    private List<String> plateNumberInfo;

    @Generated(hash = 1122088771)
    public User(Long id, String icon, String phone, String uid, String username,
            String plateNums) {
        this.id = id;
        this.icon = icon;
        this.phone = phone;
        this.uid = uid;
        this.username = username;
        this.plateNums = plateNums;
    }

    @Generated(hash = 586692638)
    public User() {
    }

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
