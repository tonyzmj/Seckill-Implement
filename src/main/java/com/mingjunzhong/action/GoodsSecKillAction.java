package com.mingjunzhong.action;

import com.mingjunzhong.bean.GoodsInfoBean;
import com.mingjunzhong.bean.OrderBean;
import com.mingjunzhong.bean.UserBean;
import com.mingjunzhong.service.AsynchronousGoodsInfoHandleService;
import com.mingjunzhong.service.GoodsInfoService;
import com.opensymphony.xwork2.ActionSupport;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by mingjun on 15/10/5.
 */
public class GoodsSecKillAction extends ActionSupport {

    @Setter
    @Getter
    private int userId;//用户id

    @Setter
    @Getter
    private int goodsId;//商品id

    private final static String FAIL = "fail";

    @Autowired
    private GoodsInfoService goodsInfoService;

    @Autowired
    private AsynchronousGoodsInfoHandleService asynchronousGoodsInfoHandleService;

    @Override
    public String execute() throws Exception {
        ConcurrentHashMap<Integer, GoodsInfoBean> goodsInfoBeanConcurrentHashMap =
                goodsInfoService.getGoodsInfoSecSkill(goodsId);
        while (true) {
            GoodsInfoBean goodsInfoBeanOld = goodsInfoBeanConcurrentHashMap.get(goodsId);
            //单台机器最新库存大于0，尝试购买
            int latestStock = goodsInfoBeanOld.getCanBuyNum();
            if (latestStock > 0) {
                GoodsInfoBean goodsInfoBeanNew = new GoodsInfoBean();
                BeanUtils.copyProperties(goodsInfoBeanOld, goodsInfoBeanNew);
                goodsInfoBeanNew.setCanBuyNum(latestStock - 1);
                goodsInfoBeanNew.setLatestStock(latestStock - 1);//canBuyStock和latestStock是一个意思，多定义了
                //判断是否成功秒杀，核心在此
                boolean isSuccessSecSkill = goodsInfoBeanConcurrentHashMap.
                        replace(goodsId, goodsInfoBeanOld, goodsInfoBeanNew);

                if (isSuccessSecSkill) {//成功秒杀
                    LOG.info(String.format("成功秒杀，userId:%d, goodsName:%s, goodsId:%d, buyNum:%s,latestStock:%d", userId, goodsInfoBeanOld.getName(), goodsInfoBeanOld.getId(), 1, goodsInfoBeanNew.getLatestStock()));
                    OrderBean orderBean = new OrderBean();
                    orderBean.setUserId(userId);
                    orderBean.setGoodsInfoId(goodsId);
                    orderBean.setBuyNum(1);//每个人秒杀一件
                    //后面将orderBean放进队列，慢慢插入数据库
                    goodsInfoService.getQueueOfNeedHandleGoodsOrder(goodsId).add(orderBean);
                    return SUCCESS;

                } else {//手速慢了点，继续秒杀
                    //LOG.info(String.format("userId=%d没有抢到哦，手速慢了点", userId));
                    continue;
                }
            } else {//已无库存
                //LOG.info(String.format("userId=%d没有抢到哦，因为没有库存了", userId));
                //开始处理数据
                asynchronousGoodsInfoHandleService.handleSeckillOrder(goodsId, goodsInfoService.getQueueOfNeedHandleGoodsOrder(goodsId));
                return FAIL;
            }
        }
    }
}
