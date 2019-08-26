package com.gwt.idworker.dto;

import java.io.Serializable;

/**
 * @author: gewentao
 * @date: 2019/8/21 14:30
 * ID解码DTO
 */

public class DecodeIdDTO implements Serializable {

    private static final long serialVersionUID = 2882018353814669140L;
    /**
     * 生成时间
     */
    private Long time;

    /**
     * 生成时间格式化
     */
    private String timeFormat;

    /**
     * 工作ID
     */
    private Integer workerId;

    /**
     * 数据中心ID
     */
    private Integer dataCenterId;

    /**
     * 生成序列
     */
    private Integer sequence;

    public DecodeIdDTO() {
        //do nothing
    }

    public DecodeIdDTO(Long time, String timeFormat, Integer workerId, Integer dataCenterId, Integer sequence) {
        this.time = time;
        this.timeFormat = timeFormat;
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
        this.sequence = sequence;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Integer getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Integer workerId) {
        this.workerId = workerId;
    }

    public Integer getDataCenterId() {
        return dataCenterId;
    }

    public void setDataCenterId(Integer dataCenterId) {
        this.dataCenterId = dataCenterId;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }

    @Override
    public String toString() {
        return "DecodeIdDTO{" +
                "time=" + time +
                ", timeFormat='" + timeFormat + '\'' +
                ", workerId=" + workerId +
                ", dataCenterId=" + dataCenterId +
                ", sequence=" + sequence +
                '}';
    }
}
