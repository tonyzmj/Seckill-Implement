package com.mingjunzhong.service;

/**
 * Created by mingjun on 15/10/6.
 */

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 异步处理秒杀订单
 */
public interface AsynchronousGoodsInfoHandleService {

    public void handleSeckillOrder(int goodsInfoId, LinkedBlockingQueue goodsOrderQueue);

}
