package com.laravelshao.common.test.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.laravelshao.common.test.dal.entity.Order;
import com.laravelshao.common.test.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * 取消订单定时任务
 *
 * @author qinghua.shao
 * @date 2019/10/4
 * @since 1.0.0
 */
//@ElasticSimpleJob(jobName = "orderCancelJob", cron = "0/15 * * * * ?", shardingTotalCount = 2, overwrite = true)
public class OrderCancelJob implements SimpleJob {

    @Autowired
    private OrderService orderService;

    /**
     * 取消订单执行线程池
     */
    private final static ThreadPoolExecutor CANCEL_ORDER_THREAD_POOL =
            new ThreadPoolExecutor(3, 6, 0L, SECONDS, new LinkedBlockingQueue<>(1000),
                    new ThreadFactoryBuilder().setNameFormat("取消订单执行线程" + "-%d").build());

    @Override
    public void execute(ShardingContext shardingContext) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.SECOND, -30);
        //订单尾号 % 分片总数 == 当前分片项
        List<Order> orders = orderService.getOrder(now, shardingContext.getShardingTotalCount(), shardingContext.getShardingItem());
        if (CollectionUtils.isEmpty(orders)) {
            return;
        }

        for (Order order : orders) {
            CANCEL_ORDER_THREAD_POOL.execute(() -> {
                //更新条件
                Integer orderId = order.getId();
                Date updateTime = order.getUpdateTime();
                //更新内容
                int status = 3;//已取消
                String updateUser = "system";
                Date updateNow = new Date();

                orderService.cancelOrder(orderId, updateTime, status, updateUser, updateNow);
            });
        }
    }
}
