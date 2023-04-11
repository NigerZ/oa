package org.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.model.system.SysUser;
import org.example.vo.system.SysUserQueryVo;

public interface SysUserService extends IService<SysUser> {
    SysUser getUserByUsername(String username);

    /**
     * 用户查询条件分页
     * @param pageParams
     * @param sysUserQueryVo
     * @return
     */
    IPage<SysUser> index(Page<SysUser> pageParams, SysUserQueryVo sysUserQueryVo);
}
