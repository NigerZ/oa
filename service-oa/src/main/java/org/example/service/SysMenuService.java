package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.model.system.SysMenu;
import org.example.model.system.SysUser;
import org.example.vo.system.AssginMenuVo;
import org.example.vo.system.RouterVo;

import java.util.List;

public interface SysMenuService extends IService<SysMenu> {
    List<RouterVo> findUserMenuList(SysUser user);

    List<String> findUserPermsList(SysUser user);

    void doAssign(AssginMenuVo assginMenuVo);

    List<SysMenu> findSysMenuByRoleId(Long roleId);

    List<RouterVo> findUserMenuList(Long userId);
}
