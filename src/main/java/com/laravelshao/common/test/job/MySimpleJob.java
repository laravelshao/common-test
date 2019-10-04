package com.laravelshao.common.test.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.laravelshao.common.core.annotations.ElasticSimpleJob;
import lombok.extern.slf4j.Slf4j;

/**
 * 自定义 simple job 任务
 *
 * @author qinghua.shao
 * @date 2019/10/4
 * @since 1.0.0
 */
@Slf4j
//@ElasticSimpleJob(jobName = "mySimpleJob", cron = "0/5 * * * * ?", shardingTotalCount = 2, overwrite = true)
public class MySimpleJob implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("当前分片为：{}, 总分片数为：{}", shardingContext.getShardingItem(), shardingContext.getShardingTotalCount());
    }
}
