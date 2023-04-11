package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.mapper.SysUserMapper;
import org.example.model.system.SysUser;
import org.example.service.SysUserService;
import org.example.vo.system.SysUserQueryVo;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Override
    public SysUser getUserByUsername(String username) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername, username);

        return baseMapper.selectOne(wrapper);
    }

    @Override
    public IPage<SysUser> index(Page<SysUser> pageParams, SysUserQueryVo sysUserQueryVo) {
        IPage<SysUser> resule = baseMapper.index(pageParams, sysUserQueryVo);
        return resule;
    }
}
