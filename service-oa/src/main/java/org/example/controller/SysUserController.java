package org.example.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.model.system.SysUser;
import org.example.result.Result;
import org.example.service.SysUserService;
import org.example.vo.system.SysUserQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/system/sysUser")
@Api(tags = "用户管理模块")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/{page}/{limit}")
    @ApiOperation("用户条件分页查询")
    public Result index(@PathVariable Long page, @PathVariable Long limit, SysUserQueryVo sysUserQueryVo) {
        Page<SysUser> pageParams = new Page<>(page, limit);
        IPage<SysUser> pageModel = sysUserService.index(pageParams, sysUserQueryVo);
        return Result.ok(pageModel);
    }

    @GetMapping("/get/{id}")
    @ApiOperation("根据id获取用户信息")
    public Result get(@PathVariable Long id) {
        SysUser sysUser = sysUserService.getById(id);
        return Result.ok(sysUser);
    }

    @PostMapping("save")
    @ApiOperation("保存用户信息")
    public Result save(@RequestBody SysUser user) {
        sysUserService.save(user);
        return Result.ok();
    }

    @PostMapping("remove/{id}")
    @ApiOperation("删除用户信息")
    public Result remove(@PathVariable Long id) {
        sysUserService.removeById(id);
        return Result.ok();
    }

    @PostMapping("update")
    @ApiOperation("更新用户信息")
    public Result update(@RequestBody SysUser user) {
        sysUserService.updateById(user);
        return Result.ok();
    }

}
