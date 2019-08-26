package com.gwt.idworker;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: gewentao
 * @date: 2019-08-19 22:14
 */

@EnableDubbo
@SpringBootApplication
public class IdWorkerApplication {
    public static void main(String[] args) {
        SpringApplication.run(IdWorkerApplication.class, args);
    }
}
