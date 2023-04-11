package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.mapper.SysMenuMapper;
import org.example.model.system.SysMenu;
import org.example.model.system.SysRoleMenu;
import org.example.model.system.SysUser;
import org.example.service.SysMenuService;
import org.example.service.SysRoleMenuService;
import org.example.utils.MenuHelper;
import org.example.vo.system.AssginMenuVo;
import org.example.vo.system.RouterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {


    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Override
    public List<RouterVo> findUserMenuList(SysUser user) {
        Long userId = user.getId();
        List<SysMenu> menus = new ArrayList<>();
        if (userId.longValue() == 1) {
            menus = list(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getStatus, 1));
        } else {
            menus = baseMapper.findListByUserId(userId);
        }
        return null;
    }

    @Override
    public List<String> findUserPermsList(SysUser user) {
        return null;
    }




    @Override
    @Transactional(rollbackFor = Exception.class)
    public void doAssign(AssginMenuVo assginMenuVo) {
        //获取角色id
        Long roleId = assginMenuVo.getRoleId();
        //根据角色id删除该角色的所有按钮
        sysRoleMenuService.remove(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, roleId));
        List<Long> menuIdList = assginMenuVo.getMenuIdList();
        List<SysRoleMenu> addModel = new ArrayList<>();
        for (Long menuId : menuIdList) {
            if (StringUtils.isEmpty(menuId)) continue;
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(assginMenuVo.getRoleId());
            sysRoleMenu.setMenuId(menuId);
            addModel.add(sysRoleMenu);
        }
        sysRoleMenuService.saveBatch(addModel);
    }

    @Override
    public List<SysMenu> findSysMenuByRoleId(Long roleId) {
        //获取全部权限
        List<SysMenu> menuList = this.list(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getStatus, 1));

        //根据角色id获取角色权限
        List<SysRoleMenu> roleMenuList = sysRoleMenuService.list(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, roleId));
        //转换给角色id与角色权限对应map对象
        List<Long> roleMenuIdList = roleMenuList.stream().map(item -> item.getMenuId()).collect(Collectors.toList());
        menuList.stream().forEach(item -> {
            if (roleMenuIdList.contains(item.getId())){
                item.setSelect(true);
            } else {
                item.setSelect(false);
            }
        });
        List<SysMenu> menus = MenuHelper.buildTree(menuList);
        return menus;
    }

    @Override
    public List<RouterVo> findUserMenuList(Long userId) {
        return null;
    }
}
