package com.gwt.idworker.handler;

import com.gwt.idworker.config.WorkerIdConfiguration;
import com.gwt.idworker.dto.DecodeIdDTO;
import com.gwt.idworker.utils.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: gewentao
 * @date: 2019/8/23 15:53
 * id解码器
 */

@Component
public class DecodeIdHandler {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-dd-MM hh:mm:ss");

    @Autowired
    private WorkerIdConfiguration configuration;

    public DecodeIdDTO decode(long id) {
        String binaryString = Long.toBinaryString(id);
        long time = Long.valueOf(binaryString.substring(0, (int) (binaryString.length() - Const.TIMESTAMP_LEFT_SHIFT)), 2) + configuration.getStartTimestamp();
        String timeFormat = DATE_FORMAT.format(new Date(time));
        int dataCenterId = Integer.valueOf(binaryString.substring((int) (binaryString.length() - Const.TIMESTAMP_LEFT_SHIFT), (int) (binaryString.length() - Const.DATA_CENTER_ID_SHIFT)), 2);
        int workerId = Integer.valueOf(binaryString.substring((int)(binaryString.length() - Const.DATA_CENTER_ID_SHIFT), (int)(binaryString.length() - Const.WORKER_ID_SHIFT)), 2);
        int sequence = Integer.valueOf(binaryString.substring((int)(binaryString.length() - Const.WORKER_ID_SHIFT), binaryString.length()), 2);
        return new DecodeIdDTO(time, timeFormat, dataCenterId, workerId, sequence);
    }
}
