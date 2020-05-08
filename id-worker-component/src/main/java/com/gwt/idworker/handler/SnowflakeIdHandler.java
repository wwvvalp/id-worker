package com.gwt.idworker.handler;

import com.gwt.idworker.utils.Const;

/**
 * @author: gewentao
 * @date: 2019/8/21 15:21
 * id生成器
 */

public class SnowflakeIdHandler {

    private long startTimestamp;

    /**
     * 工作机器ID(0~31)
     */
    private long workerId;

    public void setWorkerId(long workerId) {
        this.workerId = workerId;
    }

    /**
     * 数据中心ID(0~31)
     */
    private long dataCenterId;

    /**
     * 毫秒内序列(0~4095)
     */
    private long sequence = 0L;

    /**
     * 上次生成ID的时间截
     */
    private long lastTimestamp = -1L;

    /**
     * 构造函数
     *
     * @param workerId     工作ID (0~31)
     * @param dataCenterId 数据中心ID (0~31)
     */
    public SnowflakeIdHandler(long workerId, long dataCenterId, long startTimestamp) {
        if (workerId > Const.MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException(String.format("workerId 不能大于 %s or 小于 0", Const.MAX_WORKER_ID));
        }
        if (dataCenterId > Const.MAX_DATA_CENTER_ID || dataCenterId < 0) {
            throw new IllegalArgumentException(String.format("dataCenterId workerId 不能大于 %s or 小于 0", Const.MAX_DATA_CENTER_ID));
        }
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
        this.startTimestamp = startTimestamp;
    }

    /**
     * 获得下一个ID (该方法是线程安全的)
     *
     * @return SnowflakeId
     */
    public synchronized long nextId() {
        long timestamp = getCurrentTimeMillis();
        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("时钟回拨.  拒绝生成新id %s 毫秒！！！", lastTimestamp - timestamp));
        }
        //如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & Const.SEQUENCE_MASK;
            //毫秒内序列溢出
            if (sequence == 0) {
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        //时间戳改变，毫秒内序列重置
        else {
            sequence = 0L;
        }
        //上次生成ID的时间截
        lastTimestamp = timestamp;
        //移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - startTimestamp) << Const.TIMESTAMP_LEFT_SHIFT)
                | (dataCenterId << Const.DATA_CENTER_ID_SHIFT)
                | (workerId << Const.WORKER_ID_SHIFT)
                | sequence;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = getCurrentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = getCurrentTimeMillis();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     *
     * @return 当前时间(毫秒)
     */
    protected long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }
}
