package com.mingjunzhong.bean;

import lombok.Data;

/**
 * Created by mingjun on 15/10/5.
 */
@Data
public class OrderBean {

    private int orderId;

    private int userId;

    private int goodsInfoId;

    private int buyNum;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGoodsInfoId() {
        return goodsInfoId;
    }

    public void setGoodsInfoId(int goodsInfoId) {
        this.goodsInfoId = goodsInfoId;
    }

    public int getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(int buyNum) {
        this.buyNum = buyNum;
    }
}
