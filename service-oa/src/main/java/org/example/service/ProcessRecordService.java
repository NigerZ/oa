package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.model.process.ProcessRecord;

import java.util.List;

public interface ProcessRecordService extends IService<ProcessRecord> {


    /**
     * 添加流程记录
     * @param processId 流程id
     * @param status 流程状态
     * @param description 流程描述
     */
    void record(Long processId, Integer status, String description);

    /**
     * 根据流程id获取流程审批记录
     * @param processId 流程id
     * @return
     */
    List<ProcessRecord> getByProcessId(Long processId);
}
