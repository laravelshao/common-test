package com.laravelshao.common.test.controller;

import com.laravelshao.common.core.handler.StrategyHandler;
import com.laravelshao.common.test.strategy.SelectStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shaoqinghua
 * @date 2019/5/11
 * @description
 */
@RestController
public class DemoController {

    @GetMapping("/test")
    public void test() {

        SelectStrategy yanxuan = StrategyHandler.getBean(SelectStrategy.class, "yanxuan");
        String res1 = yanxuan.execute();
        System.out.println(res1);
        SelectStrategy suning = StrategyHandler.getBean(SelectStrategy.class, "suning");
        String res2 = suning.execute();
        System.out.println(res2);
    }

}
