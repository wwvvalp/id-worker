package com.gwt.idworker.utils;

/**
 * @author: gewentao
 * @date: 2019/8/23 15:08
 */

public class Const {
    /**
     * 机器id所占的位数
     */
    public static final long WORKER_ID_BITS = 5L;

    /**
     * 数据标识id所占的位数
     */
    public static final long DATA_CENTER_ID_BITS = 5L;

    /**
     * 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
     */
    public static final long MAX_WORKER_ID = -1L ^ (-1L << WORKER_ID_BITS);

    /**
     * 支持的最大数据标识id，结果是31
     */
    public static final long MAX_DATA_CENTER_ID = -1L ^ (-1L << DATA_CENTER_ID_BITS);

    /**
     * 序列在id中占的位数
     */
    public static final long SEQUENCE_BITS = 12L;

    /**
     * 机器ID向左移12位
     */
    public static final long WORKER_ID_SHIFT = SEQUENCE_BITS;

    /**
     * 数据标识id向左移17位(12+5)
     */
    public static final long DATA_CENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;

    /**
     * 时间截向左移22位(5+5+12)
     */
    public static final long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATA_CENTER_ID_BITS;

    /**
     * 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095)
     */
    public static final long SEQUENCE_MASK = -1L ^ (-1L << SEQUENCE_BITS);

    /**
     * 父节点 or 子节点前缀
     */
    public static final String NODE_PREFIX = "/idworker";

    /**
     * 本机节点
     */
    public static final String LOCAL_NODE = NODE_PREFIX + NODE_PREFIX;

    /**
     * 本机临时节点
     */
    public static final String EPHEMERAL_NODE_SUFFIX = "/ephemeral";


}
