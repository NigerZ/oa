package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.example.model.process.ProcessRecord;

import java.util.List;

public interface ProcessRecordMapper extends BaseMapper<ProcessRecord> {
    List<ProcessRecord> getByProcessId(@Param("processId") Long processId);
}
