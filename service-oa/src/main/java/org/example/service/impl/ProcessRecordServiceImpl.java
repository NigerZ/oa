package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import conponent.ThreadLocalComponent;
import org.example.mapper.ProcessRecordMapper;
import org.example.model.process.ProcessRecord;
import org.example.model.system.SysUser;
import org.example.service.ProcessRecordService;
import org.example.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessRecordServiceImpl extends ServiceImpl<ProcessRecordMapper, ProcessRecord> implements ProcessRecordService {


    @Autowired
    private SysUserService sysUserService;

    @Override
    public void record(Long processId, Integer status, String description) {
        ProcessRecord processRecord = new ProcessRecord();
        //获取当前登录的userid和username
        Long userId = ThreadLocalComponent.user_id.get();
        // SysUser currentUser = sysUserService.getById(userId);
        String username = ThreadLocalComponent.username.get();
        processRecord.setOperateUserId(userId)
                .setOperateUser(username)
                .setProcessId(processId)
                .setDescription(description)
                .setStatus(status);

        this.save(processRecord);
    }

    @Override
    public List<ProcessRecord> getByProcessId(Long processId) {
        List<ProcessRecord> result = baseMapper.getByProcessId(processId);
        return result;
    }
}
