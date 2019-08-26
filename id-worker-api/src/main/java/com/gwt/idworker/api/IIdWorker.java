package com.gwt.idworker.api;

import com.gwt.idworker.common.Result;
import com.gwt.idworker.dto.DecodeIdDTO;

/**
 * @author: gewentao
 * @date: 2019/8/21 14:19
 * 接口类
 */

public interface IIdWorker {

    /**
     * 生成ID
     * @return ID
     */
    Result<Long> generateId();

    /**
     * ID解码
     * @param id
     * @return Result<DecodeIdDTO>
     */
    Result<DecodeIdDTO> decodeId(Long id);
}
