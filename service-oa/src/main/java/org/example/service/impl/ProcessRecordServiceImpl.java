package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.mapper.ProcessRecordMapper;
import org.example.model.process.ProcessRecord;
import org.example.service.ProcessRecordService;
import org.springframework.stereotype.Service;

@Service
public class ProcessRecordServiceImpl extends ServiceImpl<ProcessRecordMapper, ProcessRecord> implements ProcessRecordService {
}
