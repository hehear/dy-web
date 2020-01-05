package com.dy.u.report.config;

import com.dy.u.report.properties.ReportProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @description 报表模块配置类
 * @author dxy
 * @date 2020-01-04
 */
@Configuration
@EnableConfigurationProperties(ReportProperties.class)
public class ReportConfig {
}
