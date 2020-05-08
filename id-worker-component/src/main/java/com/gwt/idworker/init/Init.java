package com.gwt.idworker.init;

import com.gwt.idworker.executor.TimeUploadExecutor;
import com.gwt.idworker.handler.CheckHandler;
import com.gwt.idworker.handler.SnowflakeIdHandler;
import com.gwt.idworker.utils.Const;
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

@Component
public class Init implements CommandLineRunner {
    private final Logger logger = LoggerFactory.getLogger(Init.class);

    @Autowired
    private CheckHandler checkHandler;

    @Autowired
    private ZKClient zkClient;
    @Autowired
    private SnowflakeIdHandler snowflakeIdHandler;

    @Override
    public void run(String... args) throws Exception {
        logger.info("【Init.onApplicationEvent】-----------init start----------");
        //时钟回拨校验
        String parentNode = checkHandler.checkTurnBackClock();
        String[] split = parentNode.split(Const.LOCAL_NODE);
        snowflakeIdHandler.setWorkerId(Integer.parseInt(split[1]));
        //本地时间定时上传
        TimeUploadExecutor.schedule(zkClient, parentNode);
        logger.info("【Init.onApplicationEvent】-----------init end----------");
    }
}
