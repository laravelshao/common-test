package com.laravelshao.common.test.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.laravelshao.common.core.annotations.ElasticDataflowJob;
import com.laravelshao.common.test.mapper.JdOrderMapper;
import com.laravelshao.common.test.mapper.TmallOrderMapper;
import com.laravelshao.common.test.model.AllOrder;
import com.laravelshao.common.test.model.JdOrder;
import com.laravelshao.common.test.model.TmallOrder;
import com.laravelshao.common.test.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * 拉取第三方订单流式定时任务
 *
 * @author qinghua.shao
 * @date 2019/10/4
 * @since 1.0.0
 */
//@ElasticDataflowJob(jobName = "fetchThirdOrderJob", cron = "0/15 * * * * ?", shardingTotalCount = 2, overwrite = true, streamingProcess = true)
public class FetchThirdOrderJob implements DataflowJob<Object> {

    @Autowired
    private OrderService orderService;

    @Autowired
    private JdOrderMapper jdOrderMapper;

    @Autowired
    private TmallOrderMapper tmallOrderMapper;

    @Override
    public List<Object> fetchData(ShardingContext shardingContext) {
        // 京东订单
        if (shardingContext.getShardingItem() == 0) {
            List<JdOrder> jdOrders = jdOrderMapper.getNotFetchedOrder(5);
            if (!CollectionUtils.isEmpty(jdOrders)) {
                List<Object> jdOrderList = jdOrders.stream().map(jdOrder -> (Object) jdOrder).collect(toList());
                return jdOrderList;
            }
        } else {// 天猫订单
            List<TmallOrder> tmallOrders = tmallOrderMapper.getNotFetchedOrder(5);
            if (!CollectionUtils.isEmpty(tmallOrders)) {
                List<Object> tmallOrderList = tmallOrders.stream().map(tmallOrder -> (Object) tmallOrder).collect(toList());
                return tmallOrderList;
            }
        }
        return null;
    }

    @Override
    public void processData(ShardingContext shardingContext, List<Object> data) {
        // 京东订单
        if (shardingContext.getShardingItem() == 0) {
            if (!CollectionUtils.isEmpty(data)) {
                List<JdOrder> jdOrders = data.stream().map(d -> (JdOrder) d).collect(toList());
                for (JdOrder jdOrder : jdOrders) {
                    AllOrder allOrder = new AllOrder();
                    allOrder.setThirdOrderId(jdOrder.getId());
                    allOrder.setType(0);// 京东订单
                    allOrder.setTotalAmount(jdOrder.getAmount());
                    allOrder.setCreateUser("system");
                    allOrder.setCreateTime(new Date());
                    allOrder.setUpdateUser("system");
                    allOrder.setUpdateTime(new Date());
                    orderService.processJdOrder(allOrder);
                }
            }
        } else {// 天猫订单
            if (!CollectionUtils.isEmpty(data)) {
                List<TmallOrder> tmallOrders = data.stream().map(d -> (TmallOrder) d).collect(toList());
                for (TmallOrder tmallOrder : tmallOrders) {
                    AllOrder allOrder = new AllOrder();
                    allOrder.setThirdOrderId(tmallOrder.getId());
                    allOrder.setType(1);// 天猫订单
                    allOrder.setTotalAmount(tmallOrder.getMoney());
                    allOrder.setCreateUser("system");
                    allOrder.setCreateTime(new Date());
                    allOrder.setUpdateUser("system");
                    allOrder.setUpdateTime(new Date());
                    orderService.processTmallOrder(allOrder);
                }
            }
        }
    }
}
