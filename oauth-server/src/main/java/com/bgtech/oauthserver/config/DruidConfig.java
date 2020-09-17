package com.bgtech.oauthserver.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author HuangJiefeng
 * @date 2020/9/17 0017 9:04
 */
@Configuration
public class DruidConfig {
    /**
     * 将自定义的 Druid 数据源添加到容器中，不再让 Spring Boot 自动创建
     * 这样做的目的是：绑定全局配置文件中的 druid 数据源属性到 com.alibaba.druid.pool.DruidDataSource
     * 从而让它们生效
     * @ConfigurationProperties(prefix = "spring.datasource")：作用就是将 全局配置文件中 前缀为 spring.datasource
     * 的属性值注入到 com.alibaba.druid.pool.DruidDataSource 的同名参数中
     *
     * @return
     */
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druidDataSource() {
        return new DruidDataSource();
    }
}

