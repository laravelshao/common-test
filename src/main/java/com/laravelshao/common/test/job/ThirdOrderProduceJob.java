package com.laravelshao.common.test.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.laravelshao.common.core.annotations.ElasticSimpleJob;
import com.laravelshao.common.test.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 第三方订单生产定时任务
 *
 * @author qinghua.shao
 * @date 2019/10/4
 * @since 1.0.0
 */
@ElasticSimpleJob(jobName = "thirdOrderProduceJob", cron = "0/5 * * * * ?", shardingTotalCount = 1, overwrite = true)
public class ThirdOrderProduceJob implements SimpleJob {

    @Autowired
    private OrderService orderService;

    @Override
    public void execute(ShardingContext shardingContext) {
        orderService.produceThirdOrder();
    }
}
