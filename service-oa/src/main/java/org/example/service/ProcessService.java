package org.example.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.model.process.Process;
import org.example.vo.process.ProcessFormVo;
import org.example.vo.process.ProcessQueryVo;
import org.example.vo.process.ProcessVo;

import java.util.Map;

public interface ProcessService extends IService<Process> {
    IPage<ProcessVo> index(IPage<ProcessVo> pageParam, ProcessQueryVo processQueryVo);

    /**
     * 通过zip文件流程定义部署
     * @param deployPath zip文件路径
     */
    void PublishByZip(String deployPath);

    /**
     * 提交流程审批（发送审批）
     * @param processFormVo 审批表单内容
     */
    void startUp(ProcessFormVo processFormVo);

    /**
     * 查询当前用户需要审批的流程分页
     * @param pageParam 分页参数
     * @return
     */
    IPage<ProcessVo> findPending(IPage<ProcessVo> pageParam);

    /**
     * 已发起的审批流程
     * @param pageParam
     * @return
     */
    IPage<ProcessVo> findStarted(IPage<ProcessVo> pageParam);

    /**
     * 获取审批详情
     * @param processId
     * @return
     */
    Map<String, Object> show(Long processId);
}
