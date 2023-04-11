package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.example.model.process.ProcessType;
import org.example.vo.process.ProcessTypeVO;

import java.util.List;

public interface ProcessTypeMapper extends BaseMapper<ProcessType> {


    List<ProcessTypeVO> findProcessType();
}
