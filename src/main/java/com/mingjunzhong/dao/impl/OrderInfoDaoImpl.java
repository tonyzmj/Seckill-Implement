package com.mingjunzhong.dao.impl;

import com.mingjunzhong.bean.OrderBean;
import com.mingjunzhong.dao.OrderInfoDao;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

/**
 * Created by mingjun on 15/10/6.
 */
public class OrderInfoDaoImpl extends SqlMapClientDaoSupport implements OrderInfoDao {
    public void insertOneOrderRecord(OrderBean orderBean) {
        getSqlMapClientTemplate().insert("insertOneOrder", orderBean);
    }
}
