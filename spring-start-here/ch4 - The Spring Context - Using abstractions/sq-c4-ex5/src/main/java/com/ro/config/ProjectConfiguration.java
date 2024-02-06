package com.ro.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.ro.proxy", "com.ro.repository", "com.ro.service"})
//@ComponentScan(basePackages = {"com.ro"})
public class ProjectConfiguration {
}
