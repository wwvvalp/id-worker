package com.gwt.idworker.config;

import com.gwt.idworker.handler.SnowflakeIdHandler;
import com.gwt.idworker.utils.ZKClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author: gewentao
 * @date: 2019-08-21 21:54
 * 配置类
 */

@PropertySource(value = "classpath:snowflakeid.properties")
@ConfigurationProperties(prefix = "snowflakeid")
@Configuration
public class WorkerIdConfiguration {
    /**
     * workId 支持0~31
     */
    private long workId;

    /**
     * dataCenterId 支持0~31
     */
    private long dataCenterId;

    /**
     * startTimestamp 起始时间戳，一经确定不可修改
     */
    private long startTimestamp;

    public long getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(long startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public long getWorkId() {
        return workId;
    }

    public void setWorkId(long workId) {
        this.workId = workId;
    }

    public long getDataCenterId() {
        return dataCenterId;
    }

    public void setDataCenterId(long dataCenterId) {
        this.dataCenterId = dataCenterId;
    }

    @Bean
    SnowflakeIdHandler snowflakeIdHandler() {
        return new SnowflakeIdHandler(workId, dataCenterId, startTimestamp);
    }

    @Bean
    ZKClient zkClient() {
        return new ZKClient("gwt.com:2181");
    }
}
