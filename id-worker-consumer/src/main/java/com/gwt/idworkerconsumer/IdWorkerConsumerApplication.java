package com.gwt.idworkerconsumer;

import com.gwt.idworker.api.IIdWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.context.annotation.Bean;

@EnableAutoConfiguration
public class IdWorkerConsumerApplication {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Reference(version = "${demo.service.version}")
    private IIdWorker idWorker;

    public static void main(String[] args) {
        SpringApplication.run(IdWorkerConsumerApplication.class, args);
    }

    @Bean
    public ApplicationRunner runner() {
        return args -> {
            for (int i=0;i<4000;i++) {
                new Thread(()->{
                    Long start = System.currentTimeMillis();
                    Long id = idWorker.generateId().getData();
                    logger.info("【idWorker.generateId()】ID={}, 耗时={}", id, System.currentTimeMillis()-start);
                    logger.info("【idWorker.decodeId()】ID={}", idWorker.decodeId(id).getData());
                }).start();
            }
            Thread.sleep(10000);

        };
    }
}
