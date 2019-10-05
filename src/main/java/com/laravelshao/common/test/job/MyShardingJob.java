package com.laravelshao.common.test.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.laravelshao.common.core.annotations.ElasticSimpleJob;
import com.laravelshao.common.core.sharding.MyRoundRobinJobShardingStrategy;
import com.laravelshao.common.test.listener.MyNormalListener;
import lombok.extern.slf4j.Slf4j;


/**
 * 自定义分片策略 job 任务
 *
 * @author qinghua.shao
 * @date 2019/10/5
 * @since 1.0.0
 */
@Slf4j
@ElasticSimpleJob(jobName = "myShardingJob", cron = "0/10 * * * * ?", shardingTotalCount = 10,
        overwrite = true, jobShardingStrategy = MyRoundRobinJobShardingStrategy.class,
        isJobEventTrace = true, jobListeners = {MyNormalListener.class})
public class MyShardingJob implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("当前分片项：{}", shardingContext.getShardingItem());
    }
}
