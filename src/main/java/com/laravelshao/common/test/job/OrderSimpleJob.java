package com.laravelshao.common.test.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.laravelshao.common.core.annotations.ElasticSimpleJob;
import com.laravelshao.common.test.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 订单生成 simple 任务
 *
 * @author qinghua.shao
 * @date 2019/10/4
 * @since 1.0.0
 */
@Slf4j
//@ElasticSimpleJob(jobName = "orderSimpleJob", cron = "0/5 * * * * ?", shardingTotalCount = 1, overwrite = true)
public class OrderSimpleJob implements SimpleJob {

    @Autowired
    private OrderService orderService;

    @Override
    public void execute(ShardingContext shardingContext) {

        for (int i = 0; i < 10; i++) {
            orderService.insertOrder();
        }
    }
}
