package com.mingjunzhong.service.impl;

import com.mingjunzhong.bean.OrderBean;
import com.mingjunzhong.enums.OrderHandleStatusEnum;
import com.mingjunzhong.service.AsynchronousGoodsInfoHandleService;
import com.mingjunzhong.service.OrderInfoService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by mingjun on 15/10/6.
 */
@Service
@Log4j
public class AsynchronousGoodsInfoHandleServiceImpl implements AsynchronousGoodsInfoHandleService {

    @Autowired
    private OrderInfoService orderInfoService;

    //某个商品生成的订单处理处理状态：key：商品id，value：处理状态，null为未处理，1为正在处理，2为处理完毕
    private static ConcurrentHashMap<Integer, Integer> goodsInfoOrderHandleStatusMap =
            new ConcurrentHashMap<Integer, Integer>();

    public void handleSeckillOrder(int goodsInfoId, LinkedBlockingQueue goodsOrderQueue) {
        if (goodsInfoOrderHandleStatusMap.get(goodsInfoId) == null) {
            synchronized (this) {
                Integer startStatus = new Integer(1);
                goodsInfoOrderHandleStatusMap.put(goodsInfoId, startStatus);
                new ConsumerOrderBeanTask(goodsOrderQueue, goodsInfoOrderHandleStatusMap, goodsInfoId, orderInfoService).start();
            }
        }
    }

    private static class ConsumerOrderBeanTask extends Thread {
        private LinkedBlockingQueue<OrderBean> linkedBlockingQueue;
        private int goodsInfoId;
        private ConcurrentHashMap<Integer, Integer> goodsInfoOrderHandleStatusMap;
        private OrderInfoService orderInfoService;

        public ConsumerOrderBeanTask(LinkedBlockingQueue<OrderBean> linkedBlockingQueue,
                                     ConcurrentHashMap<Integer, Integer> goodsInfoOrderHandleStatusMap,
                                     int goodsInfoId, OrderInfoService orderInfoService) {
            this.linkedBlockingQueue = linkedBlockingQueue;
            this.goodsInfoId = goodsInfoId;
            this.goodsInfoOrderHandleStatusMap = goodsInfoOrderHandleStatusMap;
            this.orderInfoService = orderInfoService;
        }

        public void run() {
            while (linkedBlockingQueue.size() > 0) {
                OrderBean orderBean = linkedBlockingQueue.poll();
               // log.info("orderBean: " + orderBean);
                orderInfoService.insertOneOrderRecord(orderBean);
            }
           // log.info(String.format("goodsId:%d 秒杀订单处理完毕", goodsInfoId));
            goodsInfoOrderHandleStatusMap.put(goodsInfoId, OrderHandleStatusEnum.DONE.value);
        }
    }


}
