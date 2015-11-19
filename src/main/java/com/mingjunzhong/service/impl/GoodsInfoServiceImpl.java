package com.mingjunzhong.service.impl;

import com.mingjunzhong.bean.GoodsInfoBean;
import com.mingjunzhong.bean.OrderBean;
import com.mingjunzhong.dao.GoodsInfoDao;
import com.mingjunzhong.service.GoodsInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by mingjun on 15/10/5.
 */
@Service
public class GoodsInfoServiceImpl implements GoodsInfoService {

    private final static int SERVERNUM = 1;//集群服务器的机器总数，一般是可配置的数据

    private static ConcurrentHashMap<Integer, GoodsInfoBean> goodsInfoBeanConcurrentHashMap =
            new ConcurrentHashMap<Integer, GoodsInfoBean>();

    private static ConcurrentHashMap<Integer, LinkedBlockingQueue<OrderBean>> goodsInfoOrderQueueMap =
            new ConcurrentHashMap<Integer, LinkedBlockingQueue<OrderBean>>();

    @Autowired
    private GoodsInfoDao goodsInfoDao;

    public GoodsInfoBean getGoodsInfoStockById(int id) {
        return goodsInfoDao.getGoodsInfoStockById(id);
    }

    public int updateGoodsInfoStockById(GoodsInfoBean goodsInfoBean) {
        return goodsInfoDao.updateGoodsInfoStockById(goodsInfoBean);
    }

    public ConcurrentHashMap<Integer, GoodsInfoBean> getGoodsInfoSecSkill(int id) {
        GoodsInfoBean goodsInfoBean = null;
        //如果不想采取加锁，可以提供将所有的秒杀商品加载放到static静态块加载出来
        if (goodsInfoBeanConcurrentHashMap.get(id) == null) {
            synchronized (this) {
                if (goodsInfoBeanConcurrentHashMap.get(id) == null) {
                    //此处获取原始库存
                    goodsInfoBean = goodsInfoDao.getGoodsInfoStockById(id);
                    int size = goodsInfoBean.getOriginStock() / SERVERNUM;
                    int remain = goodsInfoBean.getOriginStock() % SERVERNUM;
                    goodsInfoBean.setCanBuyNum(size);
                    goodsInfoDao.updateGoodsInfoStockById(goodsInfoBean);
                    //库存不能均分，存在某台机器多分配一部分
                    if (remain != 0) {
                        goodsInfoBean.setCanBuyNum(remain);
                        //这里存在竞争
                        int row = goodsInfoDao.updateGoodsInfoStockById(goodsInfoBean);
                        if (row > 0) {//此台服务器抢到多余的库存
                            goodsInfoBean.setCanBuyNum(size + remain);
                        }

                    }
                    goodsInfoBeanConcurrentHashMap.put(id, goodsInfoBean);
                }
            }
        }

        return goodsInfoBeanConcurrentHashMap;
    }

    public LinkedBlockingQueue<OrderBean> getQueueOfNeedHandleGoodsOrder(int id) {
        if (goodsInfoOrderQueueMap.get(id) == null) {
            synchronized (this) {
                if (goodsInfoOrderQueueMap.get(id) == null) {
                    goodsInfoOrderQueueMap.put(id, new LinkedBlockingQueue<OrderBean>());
                }
            }
        }
        return goodsInfoOrderQueueMap.get(id);
    }
}
