package com.laravelshao.common.test.service;

import com.laravelshao.common.test.mapper.AllOrderMapper;
import com.laravelshao.common.test.mapper.JdOrderMapper;
import com.laravelshao.common.test.mapper.OrderMapper;
import com.laravelshao.common.test.mapper.TmallOrderMapper;
import com.laravelshao.common.test.model.AllOrder;
import com.laravelshao.common.test.model.JdOrder;
import com.laravelshao.common.test.model.Order;
import com.laravelshao.common.test.model.TmallOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 订单服务类
 *
 * @author qinghua.shao
 * @date 2019/10/4
 * @since 1.0.0
 */
@Slf4j
@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private AllOrderMapper allOrderMapper;

    @Autowired
    private JdOrderMapper jdOrderMapper;

    @Autowired
    private TmallOrderMapper tmallOrderMapper;

    public int insertOrder() {
        Order order = new Order();
        order.setAmount(BigDecimal.TEN);
        order.setReceiveName("zhangsan");
        order.setReceiveAddress("上海徐汇区");
        order.setReceiveMobile("12300001111");
        order.setStatus(1);//未支付
        order.setCreateUser("zhangsan");
        order.setCreateTime(new Date());
        order.setUpdateUser("zhangsan");
        order.setUpdateTime(new Date());
        int i = orderMapper.insertSelective(order);
        return i;
    }

    /**
     * 获取指定时间未支付订单列表
     *
     * @param now
     * @param shardingTotalCount
     * @param shardingItem
     * @return
     */
    public List<Order> getOrder(Calendar now, int shardingTotalCount, int shardingItem) {
        return orderMapper.getOrder(now.getTime(), shardingTotalCount, shardingItem);
    }

    /**
     * 取消订单
     *
     * @param orderId
     * @param updateTime
     * @param status
     * @param updateUser
     * @param updateNow
     */
    public void cancelOrder(Integer orderId, Date updateTime, int status, String updateUser, Date updateNow) {
        orderMapper.cancelOrder(orderId, updateTime, status, updateUser, updateNow);
    }

    /**
     * 生产第三方订单
     */
    public void produceThirdOrder() {
        for (int i = 0; i < 5; i++) {
            Random random = new Random();
            int randomInt = random.nextInt(2);
            //京东订单
            if (randomInt == 0) {
                log.info("插入京东订单");
                JdOrder jdOrder = new JdOrder();
                jdOrder.setStatus(0);//未抓取
                jdOrder.setAmount(BigDecimal.TEN);
                jdOrder.setCreateUser("jdUser");
                jdOrder.setCreateTime(new Date());
                jdOrder.setUpdateUser("jdUser");
                jdOrder.setUpdateTime(new Date());
                jdOrderMapper.insertSelective(jdOrder);
            } else {//天猫订单
                log.info("插入天猫订单");
                TmallOrder tmallOrder = new TmallOrder();
                tmallOrder.setOrderStatus(0);//未抓取
                tmallOrder.setMoney(new BigDecimal(100));
                tmallOrder.setCreateUser("tmallUser");
                tmallOrder.setCreateTime(new Date());
                tmallOrder.setUpdateUser("tmallUser");
                tmallOrder.setUpdateTime(new Date());
                tmallOrderMapper.insertSelective(tmallOrder);
            }
        }
    }

    /**
     * 处理京东订单
     *
     * @param allOrder
     */
    @Transactional
    public void processJdOrder(AllOrder allOrder) {
        allOrderMapper.insertSelective(allOrder);
        JdOrder jdOrder = new JdOrder();
        jdOrder.setId(allOrder.getThirdOrderId());
        jdOrder.setStatus(1);//已抓取
        jdOrder.setUpdateUser("system");
        jdOrder.setUpdateTime(new Date());
        jdOrderMapper.updateByPrimaryKeySelective(jdOrder);
    }

    /**
     * 处理天猫订单
     *
     * @param allOrder
     */
    @Transactional
    public void processTmallOrder(AllOrder allOrder) {
        allOrderMapper.insertSelective(allOrder);
        TmallOrder tmallOrder = new TmallOrder();
        tmallOrder.setId(allOrder.getThirdOrderId());
        tmallOrder.setOrderStatus(1);//已抓取
        tmallOrder.setUpdateUser("system");
        tmallOrder.setUpdateTime(new Date());
        tmallOrderMapper.updateByPrimaryKeySelective(tmallOrder);
    }
}
