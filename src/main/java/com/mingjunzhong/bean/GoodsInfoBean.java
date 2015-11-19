package com.mingjunzhong.bean;

import lombok.Data;

/**
 * Created by mingjun on 15/10/5.
 */
@Data
public class GoodsInfoBean {
    private int id;
    private String name;
    private int latestStock;
    private int originStock;
    private int canBuyNum;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLatestStock() {
        return latestStock;
    }

    public void setLatestStock(int latestStock) {
        this.latestStock = latestStock;
    }

    public int getOriginStock() {
        return originStock;
    }

    public void setOriginStock(int originStock) {
        this.originStock = originStock;
    }

    public int getCanBuyNum() {
        return canBuyNum;
    }

    public void setCanBuyNum(int canBuyNum) {
        this.canBuyNum = canBuyNum;
    }

}
