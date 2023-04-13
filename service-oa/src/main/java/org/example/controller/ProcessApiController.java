package org.example.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.model.process.ProcessTemplate;
import org.example.result.Result;
import org.example.service.ProcessService;
import org.example.service.ProcessTemplateService;
import org.example.service.ProcessTypeService;
import org.example.vo.process.ProcessFormVo;
import org.example.vo.process.ProcessTypeVO;
import org.example.vo.process.ProcessVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = "审批流管理")
@RestController
@RequestMapping(value="/admin/process")
@CrossOrigin
public class ProcessApiController {

    @Autowired
    private ProcessTypeService processTypeService;

    @Autowired
    private ProcessTemplateService processTemplateService;

    @Autowired
    private ProcessService processService;


    @GetMapping("/findProcessType")
    @ApiOperation(value = "获取全部审批分类及模板")
    public Result findProcessType()
    {
        List<ProcessTypeVO> result = processTypeService.findProcessType();
        return Result.ok(result);
    }

    @ApiOperation(value = "获取审批模板")
    @GetMapping("getProcessTemplate/{processTemplateId}")
    public Result getProcessTemplate(@PathVariable Long processTemplateId)
    {
        ProcessTemplate result = processTemplateService.getById(processTemplateId);
        return Result.ok(result);
    }

    @ApiOperation(value = "启动流程")
    @PostMapping("/startUp")
    public Result startUp(@RequestBody ProcessFormVo processFormVo)
    {
        processService.startUp(processFormVo);
        return Result.ok();
    }

    @ApiOperation(value = "待处理")
    @GetMapping("/findPending/{page}/{limit}")
    public Result findPending(@PathVariable Long page, @PathVariable Long limit) {
        IPage<ProcessVo> pageParam = new Page<>(page, limit);
        IPage<ProcessVo> result = processService.findPending(pageParam);
        return Result.ok(result);
    }


    @ApiOperation(value = "已发起")
    @GetMapping("/findStarted/{page}/{limit}")
    public Result findStarted(@PathVariable Long page, @PathVariable Long limit) {
        IPage<ProcessVo> pageParam = new Page<>(page, limit);
        IPage<ProcessVo> result = processService.findStarted(pageParam);
        return Result.ok(result);
    }

    @ApiOperation(value = "获取审批详情")
    @GetMapping("show/{id}")
    public Result show(@PathVariable Long id) {
        Map<String, Object> result = processService.show(id);
        return Result.ok(result);
    }
}
