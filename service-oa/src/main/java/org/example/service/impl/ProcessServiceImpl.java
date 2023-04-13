package org.example.service.impl;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import conponent.ThreadLocalComponent;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.StringUtils;
import org.example.mapper.ProcessMapper;
import org.example.model.process.Process;
import org.example.model.process.ProcessRecord;
import org.example.model.process.ProcessTemplate;
import org.example.model.system.SysUser;
import org.example.projectEnum.ProcessRecordEnum;
import org.example.projectEnum.ProcessStatusEnum;
import org.example.service.*;
import org.example.vo.process.ProcessFormVo;
import org.example.vo.process.ProcessQueryVo;
import org.example.vo.process.ProcessVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

@Service
public class ProcessServiceImpl extends ServiceImpl<ProcessMapper, Process> implements ProcessService {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ProcessTemplateService processTemplateService;

    @Autowired
    private ProcessTypeService processTypeService;

    @Autowired
    private ProcessService processService;

    @Autowired
    private ProcessRecordService processRecordService;


    @Override
    public IPage<ProcessVo> index(IPage<ProcessVo> pageParam, ProcessQueryVo processQueryVo)
    {
        IPage<ProcessVo> result = baseMapper.index(pageParam, processQueryVo);
        return result;
    }

    @Override
    public void PublishByZip(String deployPath)
    {
        //获取流程定义的zip文件资源
        InputStream resource = this.getClass().getClassLoader().getResourceAsStream(deployPath);
        ZipInputStream zipInputStream = new ZipInputStream(resource);
        //流程部署
        Deployment deploy = repositoryService.createDeployment()
                .addZipInputStream(zipInputStream)
                .deploy();
    }

    @Override
    public void startUp(ProcessFormVo processFormVo)
    {
        //审批模板id
        Long processTemplateId = processFormVo.getProcessTemplateId();
        //审批类型id
        Long processTypeId = processFormVo.getProcessTypeId();
        //表单值
        String formValues = processFormVo.getFormValues();


        //获取当前登录的用户id和username
        Long userId = ThreadLocalComponent.user_id.get();
        String username = ThreadLocalComponent.username.get();
        SysUser user = sysUserService.getById(userId);

        //流程模板
        ProcessTemplate processTemplate = processTemplateService.getById(processTemplateId);

        Process process = new Process();
        BeanUtils.copyProperties(processFormVo, process);
        //流程code
        String processCode = System.currentTimeMillis() + "";
        process.setUserId(userId)
                .setProcessCode(processCode)
                .setFormValues(formValues)
                .setStatus(ProcessStatusEnum.APPROVAL.getKey());


        //开启流程
        //使用流程id当作业务id
        String businessKey = String.valueOf(process.getId());
        //流程参数
        Map<String, Object> variables = new HashMap<>();

        JSONObject jsonObject = JSON.parseObject(formValues);
        JSONObject formData = jsonObject.getJSONObject("formData");
        Map<String, Object> map = new HashMap<>();

        for (Map.Entry<String, Object> entry : formData.entrySet()) {
            map.put(entry.getKey(), entry.getValue());
        }
        variables.put("data", map);

        //业务key就是流程的id
        ProcessInstance processInstance =
                runtimeService.startProcessInstanceByKey(processTemplate.getProcessDefinitionKey(), businessKey, variables);

        //业务表关联当前流程实例id
        String processInstanceId = processInstance.getId();
        process.setProcessInstanceId(processInstanceId);

        //计算下一个审批人
        List<Task> taskList = this.getCurrentTaskList(processInstanceId);
        if (!CollectionUtils.isEmpty(taskList)) {
            //审批人名称
            ArrayList<String> assigneeList = new ArrayList<>();
            for (Task task : taskList) {
                // 审批人名称
                String assignee = task.getAssignee();
                SysUser assigneeUser = sysUserService.getUserByUsername(assignee);
                assigneeList.add(assigneeUser.getName());
            }
            process.setDescription("等待" + StringUtils.join(assigneeList.toArray(), ",") + "审批");
        }

        //保存流程
        processService.save(process);
        //添加路程操作记录
        processRecordService.record(process.getId(), ProcessRecordEnum.SUCCESS.getCode(), "发起申请");
    }

    @Override
    public IPage<ProcessVo> findPending(IPage<ProcessVo> pageParam)
    {
        //当前登录的用户id和username
        Long currentUserId = ThreadLocalComponent.user_id.get();
        String currentUsername = ThreadLocalComponent.username.get();
        //获取当前用户的任务分页
        TaskQuery taskQuery = taskService.createTaskQuery().taskAssignee(currentUsername)
                .orderByTaskCreateTime().desc();
        long total = taskQuery.list().size();
        int currentPage = (int) (pageParam.getPages() - 1);
        int size = (int) pageParam.getSize();
        List<Task> taskList = taskQuery.listPage(currentPage, size);
        IPage<ProcessVo> resutl = new Page<>(currentPage, size, total);
        List<ProcessVo> processList = new ArrayList<>();
        for (Task item : taskList) {
            //processInstanceId就是processId
            String processInstanceId = item.getProcessInstanceId();
            //获取任务下的所有的流程实例
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processDefinitionId(processInstanceId).singleResult();
            if(processInstance == null) continue;
            //业务key也是processId
            Long businessKey = Long.parseLong(processInstance.getBusinessKey());
            Process process = this.getById(businessKey);
            ProcessVo processVo = new ProcessVo();
            BeanUtils.copyProperties(process, processVo);
            processVo.setTaskId(item.getId());
            processList.add(processVo);
        }
        resutl.setRecords(processList);
        return resutl;
    }

    @Override
    public IPage<ProcessVo> findStarted(IPage<ProcessVo> pageParam)
    {
        ProcessQueryVo processQueryVo = new ProcessQueryVo();
        Long currentUserId = ThreadLocalComponent.user_id.get();
        processQueryVo.setUserId(currentUserId);
        IPage<ProcessVo> result = baseMapper.getPage(pageParam, processQueryVo);
        return result;
    }

    @Override
    public Map<String, Object> show(Long processId) {
        Map<String, Object> result = new HashMap<>();
        //获取流程
        Process process = this.getById(processId);
        //获取流程的审批记录
        List<ProcessRecord> processRecordList = processRecordService.getByProcessId(processId);
        //获取流程的审批模板
        Long processTemplateId = process.getProcessTemplateId();
        ProcessTemplate processTemplate = processTemplateService.getById(processId);
        result.put("process", process);
        result.put("processRecordList", processRecordList);
        result.put("processTemplate", processTemplate);
        //计算当前用户是否可以审批，能够查看详情的用户不是都能审批，审批后也不能重复审批
        
        return result;
    }


    private List<Task> getCurrentTaskList(String processInstanceId) {
        //processInstanceId就是processId
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
        return tasks;
    }

}
