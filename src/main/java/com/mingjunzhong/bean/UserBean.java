package com.mingjunzhong.bean;

import lombok.Data;

/**
 * Created by mingjun on 15/10/5.
 */

@Data
public class UserBean {
    private int userId;
    private String contactPhone;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
}
