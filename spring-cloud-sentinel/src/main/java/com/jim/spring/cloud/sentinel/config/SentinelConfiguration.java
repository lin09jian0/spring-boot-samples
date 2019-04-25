package com.jim.spring.cloud.sentinel.config;

import com.alibaba.csp.sentinel.adapter.servlet.callback.WebCallbackManager;
import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jim lin
 * @date 2019/4/24.
 */
@Configuration
public class SentinelConfiguration {

    @Bean
    public WebCallbackManager webCallbackManager(){
        WebCallbackManager webCallbackManager = new WebCallbackManager();
        WebCallbackManager.setUrlBlockHandler(new MyUrlBlockHandler());
        return webCallbackManager;
    }

    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }
}
