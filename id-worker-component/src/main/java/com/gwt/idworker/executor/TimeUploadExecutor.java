package com.gwt.idworker.executor;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.gwt.idworker.utils.Const;
import com.gwt.idworker.utils.Tools;
import com.gwt.idworker.utils.ZKClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author: gewentao
 * @date: 2019/8/26 15:58
 * 定时上传本地时间
 */

public class TimeUploadExecutor implements Runnable {
    private static ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("time-upload-executor-%d").build();
    private static ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(Runtime.getRuntime().availableProcessors() * 2 + 1, threadFactory);
    private static final Logger logger = LoggerFactory.getLogger(TimeUploadExecutor.class);

    /**
     * 3s执行一次
     */
    private static final int NEXT_TIME = 3;

    private ZKClient zkClient;

    public TimeUploadExecutor(ZKClient zkClient) {
        this.zkClient = zkClient;
    }

    public static void schedule(ZKClient zkClient) {
        executor.submit(new TimeUploadExecutor(zkClient));
    }

    @Override
    public void run() {
        try {
            long lastTime = Long.valueOf(zkClient.getNodeData(Const.LOCAL_NODE));
            long localTime = System.currentTimeMillis();
            if (lastTime > localTime) {
                logger.error("【TimeUploadExecutor】服务器(ip:{})发生时钟回拨，请及时校准服务器时间！！！", Tools.getLocalIP());
                throw new IllegalStateException(Tools.getLocalIP() + "::服务器时钟发生回拨，请及时校准服务器时间！！！");
            }
            zkClient.updateNodeData(Const.LOCAL_NODE, String.valueOf(localTime));
            logger.info("【TimeUploadExecutor】localTime={}", localTime);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.schedule(this, NEXT_TIME, TimeUnit.SECONDS);
        }
    }
}
