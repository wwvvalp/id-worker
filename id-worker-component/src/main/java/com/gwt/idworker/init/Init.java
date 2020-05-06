package com.gwt.idworker.init;

import com.gwt.idworker.executor.TimeUploadExecutor;
import com.gwt.idworker.handler.CheckHandler;
import com.gwt.idworker.utils.ZKClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * @author: gewentao
 * @date: 2019-08-21 21:27
 * <p>
 * 初始化资源
 */

@Order
public class Init implements CommandLineRunner {
    private final Logger logger = LoggerFactory.getLogger(Init.class);

    @Autowired
    private CheckHandler checkHandler;

    @Autowired
    private ZKClient zkClient;

    @Override
    public void run(String... args) throws Exception {
        logger.info("【Init.onApplicationEvent】-----------init start----------");
        //时钟回拨校验
        checkHandler.checkTurnBackClock();
        //本地时间定时上传
        TimeUploadExecutor.schedule(zkClient);
        logger.info("【Init.onApplicationEvent】-----------init end----------");
    }
}
