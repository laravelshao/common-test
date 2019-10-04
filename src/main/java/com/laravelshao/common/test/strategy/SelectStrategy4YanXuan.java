package com.laravelshao.common.test.strategy;

import com.laravelshao.common.core.annotations.Strategy;
import org.springframework.stereotype.Component;

/**
 * @author shaoqinghua
 * @date 2019/5/11
 * @description
 */
@Component
@Strategy(superClass = SelectStrategy.class, strategyCode = "yanxuan", description = "严选策略实现")
public class SelectStrategy4YanXuan implements SelectStrategy {


    @Override
    public String execute() {
        return "严选策略实现";
    }
}
