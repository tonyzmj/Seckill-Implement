package com.mingjunzhong.service;

import com.mingjunzhong.bean.OrderBean;
import com.mingjunzhong.dao.OrderInfoDao;

/**
 * Created by mingjun on 15/10/6.
 */
public interface OrderInfoService {

    public void insertOneOrderRecord(OrderBean orderBean);

}
