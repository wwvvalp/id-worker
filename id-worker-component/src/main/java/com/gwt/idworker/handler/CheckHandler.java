package com.gwt.idworker.handler;

import com.gwt.idworker.utils.Const;
import com.gwt.idworker.utils.Tools;
import com.gwt.idworker.utils.ZKClient;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: gewentao
 * @date: 2019/8/26 15:52
 * 服务器时钟回拨校验器
 */

@Component
public class CheckHandler {
    private final Logger logger = LoggerFactory.getLogger(CheckHandler.class);
    @Autowired
    private ZKClient zkClient;

    /**
     * 校验时钟是否回拨
     */
    public String checkTurnBackClock() throws Exception {
        //1、服务启动判断服务器节点父节点是否存在，不存在创建节点（持久节点）
        boolean existNode = zkClient.isExistNode(Const.NODE_PREFIX);
        if (!existNode) {
            boolean result = zkClient.crateNode(Const.NODE_PREFIX, CreateMode.PERSISTENT, "idworker-parent");
            logger.info("【Init.checkTurnBackClock】父节点不存在，创建 result={}", result);
        }
        //2、判断服务器节点（持久节点）是否存在，不存在创建并创建临时节点
        String ip = Tools.getLocalIP();
        List<String> parents = zkClient.getChildren(Const.NODE_PREFIX);
        if (parents  == null) {
            boolean result = zkClient.crateNode(Const.LOCAL_NODE, CreateMode.PERSISTENT_SEQUENTIAL, ip);
            logger.info("【Init.checkTurnBackClock】本机点不存在，创建 result={}", result);
        }
        String parentNode = null;
        for (String parent : parents) {
            String nodeData = zkClient.getNodeData(Const.NODE_PREFIX + "/" + parent);
            if (ip.equals(nodeData)) {
                parentNode = Const.NODE_PREFIX + "/" + parent;
                break;
            }
        }
        if (parentNode  == null) {
            boolean result = zkClient.crateNode(Const.LOCAL_NODE, CreateMode.PERSISTENT_SEQUENTIAL, ip);
            logger.info("【Init.checkTurnBackClock】本机点不存在，创建 result={}", result);
        }
        existNode = zkClient.isExistNode(parentNode+Const.EPHEMERAL_NODE_SUFFIX);
        if (existNode) {
            zkClient.deleteNode(parentNode+Const.EPHEMERAL_NODE_SUFFIX);
        }
        zkClient.crateNode(parentNode+Const.EPHEMERAL_NODE_SUFFIX, CreateMode.EPHEMERAL, Long.toString(System.currentTimeMillis()));
        //3、校验-服务器时间与定时上报时间比较，若小于则服务器时钟回拨，异常报警
        long remoteTime = Long.parseLong(zkClient.getNodeData(parentNode+Const.EPHEMERAL_NODE_SUFFIX));
        if (remoteTime > System.currentTimeMillis()) {
            logger.error("【服务器时钟回拨，请校准时间后启动】");
            throw new IllegalStateException("服务器时钟回拨，请校准时间后启动！！！");
        }
        //4、校验-服务器时间与其他运行服务器平均时间比较，若小于则服务器时钟回拨，异常报警
        List<String> children = zkClient.getChildren(Const.NODE_PREFIX);
        //其他运行服务平均时间
        String finalParentNode = parentNode;
        List<String> runPaths = children.stream().filter(x ->
                !(Const.NODE_PREFIX + "/" + x + "/ephemeral").equals(finalParentNode +Const.EPHEMERAL_NODE_SUFFIX) && zkClient.isExistNode(Const.NODE_PREFIX + "/" + x + "/ephemeral"))
                .collect(Collectors.toList());
        if (Tools.isNotNull(runPaths)) {
            Double averageTime = runPaths.stream().map(x -> Long.valueOf(zkClient.getNodeData(Const.NODE_PREFIX + "/" + x)))
                    .collect(Collectors.toList())
                    .stream().collect(Collectors.averagingDouble(x -> x));
            if (averageTime > System.currentTimeMillis()) {
                logger.error("【服务器时钟回拨，请校准时间后启动】");
                throw new IllegalStateException("服务器时钟回拨，请校准时间后启动！！！");
            }
        }
        return parentNode;
    }
}
