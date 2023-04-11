package org.example.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.result.Result;
import org.example.service.ProcessService;
import org.example.vo.process.ProcessQueryVo;
import org.example.vo.process.ProcessVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.example.model.process.Process;

@Api(tags = "审批流管理")
@RestController
@RequestMapping(value = "/admin/process")
public class ProcessController {

    @Autowired
    private ProcessService processService;


    @ApiOperation(value = "获取分页列表")
    @GetMapping("{page}/{limit}")
    public Result index(
            @PathVariable Long page,
            @PathVariable Long limit,
            ProcessQueryVo processQueryVo)
    {
        IPage<ProcessVo> pageParam = new Page(page, limit);
        IPage<ProcessVo> result = processService.index(pageParam, processQueryVo);
        return Result.ok(result);
    }


}
