package com.laravelshao.common.test.listener;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import lombok.extern.slf4j.Slf4j;

/**
 * 自定义普通作业监听器
 *
 * @author qinghua.shao
 * @date 2019/10/5
 * @since 1.0.0
 */
@Slf4j
public class MyNormalListener implements ElasticJobListener {
    @Override
    public void beforeJobExecuted(ShardingContexts shardingContexts) {
        log.info("任务：{} 执行前调用", shardingContexts.getJobName());
    }

    @Override
    public void afterJobExecuted(ShardingContexts shardingContexts) {
        log.info("任务：{} 执行后调用", shardingContexts.getJobName());
    }
}
