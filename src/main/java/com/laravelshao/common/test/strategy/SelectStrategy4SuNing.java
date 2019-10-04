package com.laravelshao.common.test.strategy;

import com.laravelshao.common.core.annotations.Strategy;
import org.springframework.stereotype.Component;

/**
 * @author shaoqinghua
 * @date 2019/5/11
 * @description
 */
@Component
@Strategy(superClass = SelectStrategy.class, strategyCode = "suning", description = "苏宁策略实现")
public class SelectStrategy4SuNing implements SelectStrategy {

    @Override
    public String execute() {
        return "苏宁策略实现";
    }
}
