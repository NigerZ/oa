package org.example.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.model.process.Process;
import org.example.vo.process.ProcessFormVo;
import org.example.vo.process.ProcessQueryVo;
import org.example.vo.process.ProcessVo;

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
}
