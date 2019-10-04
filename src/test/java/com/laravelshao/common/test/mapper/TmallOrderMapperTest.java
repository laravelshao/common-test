package com.laravelshao.common.test.mapper;

import com.laravelshao.common.test.model.TmallOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author qinghua.shao
 * @date 2019/10/4
 * @since 1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TmallOrderMapperTest {

    @Autowired
    private TmallOrderMapper tmallOrderMapper;

    /**
     * 测试获取天猫未拉取订单
     */
    @Test
    public void testGetTmallOrder() {
        List<TmallOrder> notFetchedOrder = tmallOrderMapper.getNotFetchedOrder(5);
        System.out.println(notFetchedOrder.size());
    }
}
