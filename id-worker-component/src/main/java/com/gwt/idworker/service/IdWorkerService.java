package com.gwt.idworker.service;

import com.gwt.idworker.api.IIdWorker;
import com.gwt.idworker.common.Result;
import com.gwt.idworker.dto.DecodeIdDTO;
import com.gwt.idworker.handler.DecodeIdHandler;
import com.gwt.idworker.handler.SnowflakeIdHandler;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: gewentao
 * @date: 2019/8/21 15:14
 */

@Service(version = "${demo.service.version}")
public class IdWorkerService implements IIdWorker {

    @Autowired
    private SnowflakeIdHandler idHandler;

    @Autowired
    private DecodeIdHandler decodeIdHandler;

    /**
     * 生成ID
     * @return ID
     */
    @Override
    public Result<Long> generateId() {
        long id = idHandler.nextId();
        return Result.buildSuccess(id);
    }

    /**
     * ID解码
     * @param id
     * @return Result<DecodeIdDTO>
     */
    @Override
    public Result<DecodeIdDTO> decodeId(Long id) {
        DecodeIdDTO decode = decodeIdHandler.decode(id);
        return Result.buildSuccess(decode);
    }
}
