package com.mingjunzhong.service.impl;

import com.mingjunzhong.bean.OrderBean;
import com.mingjunzhong.dao.OrderInfoDao;
import com.mingjunzhong.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by mingjun on 15/10/6.
 */
@Service
public class OrderInfoServiceImpl implements OrderInfoService {

    @Autowired
    private OrderInfoDao orderInfoDao;

    public void insertOneOrderRecord(OrderBean orderBean) {
        orderInfoDao.insertOneOrderRecord(orderBean);
    }
}
