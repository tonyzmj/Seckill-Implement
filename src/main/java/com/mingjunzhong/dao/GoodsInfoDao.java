package com.mingjunzhong.dao;

import com.mingjunzhong.bean.GoodsInfoBean;

/**
 * Created by mingjun on 15/10/5.
 */
public interface GoodsInfoDao {

    public GoodsInfoBean getGoodsInfoStockById(int id);

    public int updateGoodsInfoStockById(GoodsInfoBean goodsInfoBean);


}
