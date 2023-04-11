package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.example.model.system.SysUser;
import org.example.vo.system.SysUserQueryVo;

public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 用户查询条件分页
     * @param pageParams
     * @param sysUserQueryVo
     * @return
     */
    IPage<SysUser> index(Page<SysUser> pageParams, @Param("sysUserQueryVo") SysUserQueryVo sysUserQueryVo);
}
