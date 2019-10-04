package com.laravelshao.common.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author qinghua.shao
 * @date 2019/10/4
 * @since 1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;


    /**
     * 添加订单测试
     */
    @Test
    public void testInsertOrder() {
        orderService.insertOrder();
    }

    /**
     * 生产第三放订单测试
     */
    @Test
    public void testProduceThirdOrder() {
        orderService.produceThirdOrder();
    }

}
