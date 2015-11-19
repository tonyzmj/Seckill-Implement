package com.mingjunzhong.dao.impl;

import com.mingjunzhong.bean.GoodsInfoBean;
import com.mingjunzhong.dao.GoodsInfoDao;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

/**
 * Created by mingjun on 15/10/5.
 */
public class GoodsInfoDaoImpl extends SqlMapClientDaoSupport implements GoodsInfoDao {
    public GoodsInfoBean getGoodsInfoStockById(int id) {
        Object result = getSqlMapClientTemplate().queryForObject("getGoodsInfoStockById", id);
        return (GoodsInfoBean) result;
    }

    public int updateGoodsInfoStockById(GoodsInfoBean goodsInfoBean) {
        Object result = getSqlMapClientTemplate().update("updateGoodsInfoStockById", goodsInfoBean);
        return (Integer) result;
    }
}
