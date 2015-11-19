package com.mingjunzhong.service;

import com.mingjunzhong.bean.GoodsInfoBean;
import com.mingjunzhong.bean.OrderBean;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by mingjun on 15/10/5.
 */
public interface GoodsInfoService {

    /**
     * 根据商品id，获取商品的信息
     *
     * @param id
     * @return
     */
    public GoodsInfoBean getGoodsInfoStockById(int id);

    /**
     * 更新商品的库存
     *
     * @param goodsInfoBean
     * @return
     */
    public int updateGoodsInfoStockById(GoodsInfoBean goodsInfoBean);

    /**
     * 由于是集群部署，每台机器获取一定数量的秒杀商品数据（一般采取每台机器均分）
     *
     * @param id
     * @return
     */
    public ConcurrentHashMap<Integer, GoodsInfoBean> getGoodsInfoSecSkill(int id);

    /**
     * 根据商品获取装载待处理的goodsInfo的阻塞队列
     *
     * @param id
     * @return
     */
    public LinkedBlockingQueue<OrderBean> getQueueOfNeedHandleGoodsOrder(int id);

}
