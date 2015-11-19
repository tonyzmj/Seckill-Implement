package com.mingjunzhong.test;

import com.mingjunzhong.bean.GoodsInfoBean;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by mingjun on 15/10/5.
 */
public class Test extends AbstractTest {

    @org.junit.Test
    public void test() {
        GoodsInfoBean goodsInfoBean = new GoodsInfoBean();
        goodsInfoBean.setId(1);
        goodsInfoBean.setCanBuyNum(10);
        ConcurrentHashMap<Integer, GoodsInfoBean> goodsInfoBeanConcurrentHashMap = new ConcurrentHashMap<Integer, GoodsInfoBean>();
        goodsInfoBeanConcurrentHashMap.put(1, goodsInfoBean);

        goodsInfoBeanConcurrentHashMap.get(1).setCanBuyNum(9);
        System.out.println(goodsInfoBean==goodsInfoBeanConcurrentHashMap.get(1));
    }
}
