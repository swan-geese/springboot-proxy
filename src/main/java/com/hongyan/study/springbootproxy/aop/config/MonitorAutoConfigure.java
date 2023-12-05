package com.hongyan.study.springbootproxy.aop.config;

import com.hongyan.study.springbootproxy.aop.MonitorAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MonitorAutoConfigure {

    @Bean
    @ConditionalOnMissingBean
    public MonitorAspect point(){
        return new MonitorAspect();
    }

}
