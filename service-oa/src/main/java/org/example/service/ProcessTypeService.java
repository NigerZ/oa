package org.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.model.process.ProcessType;
import org.example.vo.process.ProcessTypeVO;

import java.util.List;

public interface ProcessTypeService extends IService<ProcessType> {

    /**
     * 审批类型分页
     * @param pageParam 分页参数
     * @return
     */
    IPage<ProcessType> index(IPage<ProcessType> pageParam);

    List<ProcessTypeVO> findProcessType();
}
