package com.laravelshao.common.test.listener;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.AbstractDistributeOnceElasticJobListener;
import lombok.extern.slf4j.Slf4j;

/**
 * 自定义分布式作业监听器
 *
 * <p>底层实现存在问题，不能保证所有服务只执行一次
 *
 * @author qinghua.shao
 * @date 2019/10/5
 * @since 1.0.0
 */
@Slf4j
public class MyDistributedListener extends AbstractDistributeOnceElasticJobListener {

    public MyDistributedListener(long startedTimeoutMilliseconds, long completedTimeoutMilliseconds) {
        super(startedTimeoutMilliseconds, completedTimeoutMilliseconds);
    }

    @Override
    public void doBeforeJobExecutedAtLastStarted(ShardingContexts shardingContexts) {
        log.info("分布式监听器方法执行前调用");
    }

    @Override
    public void doAfterJobExecutedAtLastCompleted(ShardingContexts shardingContexts) {
        log.info("分布式监听器方法执行后调用");
    }
}