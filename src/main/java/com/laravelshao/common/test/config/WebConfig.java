package com.laravelshao.common.test.config;

import com.laravelshao.common.test.handler.ObservableReturnValueHandler;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * WEB 配置
 *
 * @author qinghua.shao
 * @date 2020/1/26
 * @since 1.0.0
 */
@Configuration
@ComponentScan(basePackages = {"com.laravelshao.common.test.*"})
public class WebConfig extends WebMvcConfigurationSupport {

    @Override
    protected void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        super.addReturnValueHandlers(returnValueHandlers);
        returnValueHandlers.add(new ObservableReturnValueHandler());
    }

    //@Override
    //protected void addResourceHandlers(ResourceHandlerRegistry registry) {
    //    super.addResourceHandlers(registry);
    //    registry.addResourceHandler("swagger-ui.html")
    //            .addResourceLocations("classpath:/META-INF/resources/");
    //
    //    registry.addResourceHandler("/webjars/**")
    //            .addResourceLocations("classpath:/META-INF/resources/webjars/");
    //
    //}
}
