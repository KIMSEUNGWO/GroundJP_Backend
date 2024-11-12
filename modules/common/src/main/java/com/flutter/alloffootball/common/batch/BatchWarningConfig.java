package com.flutter.alloffootball.common.batch;

import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * https://woosungkim0123.github.io/2023_12_17_boot3_2_batch_warn/
 * JobRegistryBeanPostProcessor 빈의 자동 등록 방지
 */
@Configuration
public class BatchWarningConfig {

//    @Bean
//    public static BeanDefinitionRegistryPostProcessor jobRegistryBeanPostProcessorRemover() {
//        return registry -> registry.removeBeanDefinition("jobRegistryBeanPostProcessor");
//    }
}