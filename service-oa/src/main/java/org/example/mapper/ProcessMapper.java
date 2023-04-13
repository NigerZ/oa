package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.example.model.process.Process;
import org.example.vo.process.ProcessQueryVo;
import org.example.vo.process.ProcessVo;

public interface ProcessMapper extends BaseMapper<Process> {

    /**
     * 流程分页
     * @param pageParam 分页参数
     * @param processQueryVo 分页查询条件
     * @return
     */
    IPage<ProcessVo> index(IPage<ProcessVo> pageParam, @Param("vo") ProcessQueryVo processQueryVo);

    /**
     * 获取已经发起流程的流程
     * @param pageParam
     * @param processQueryVo
     * @return
     */
    IPage<ProcessVo> getPage(IPage<ProcessVo> pageParam, @Param("processQueryVo") ProcessQueryVo processQueryVo);
}
