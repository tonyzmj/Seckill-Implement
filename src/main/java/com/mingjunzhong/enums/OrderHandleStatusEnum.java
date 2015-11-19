package com.mingjunzhong.enums;

/**
 * Created by mingjun on 15/10/6.
 */
public enum OrderHandleStatusEnum {

    NOT(0),
    DOING(1),
    DONE(2);

    public int value;

    private OrderHandleStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
