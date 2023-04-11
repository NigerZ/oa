package org.example.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.model.process.ProcessType;
import org.example.result.Result;
import org.example.service.ProcessTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/process/processType")
@Api(value = "审批类型", tags = "审批类型")
public class ProcessTypeController {

    @Autowired
    private ProcessTypeService processTypeService;



    @ApiOperation(value = "获取分页列表")
    @GetMapping("{page}/{limit}")
    public Result index(@PathVariable Long page,
                        @PathVariable Long limit) {
        IPage<ProcessType> pageParam = new Page<>(page, limit);
        IPage<ProcessType> result = processTypeService.index(pageParam);
    return Result.ok(result);
    }

    @ApiOperation(value = "获取")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        return Result.ok(processTypeService.getById(id));
    }


    @ApiOperation(value = "新增")
    @PostMapping("save")
    public Result save(@RequestBody ProcessType processType) {
        return Result.ok(processTypeService.save(processType));
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        return Result.ok(processTypeService.removeById(id));
    }
}
