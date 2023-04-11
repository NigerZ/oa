package org.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import org.example.handler.OAException;
import org.example.jwt.JwtHelper;
import org.example.model.system.SysRole;
import org.example.model.system.SysUser;
import org.example.result.Result;
import org.example.service.SysMenuService;
import org.example.service.SysRoleService;
import org.example.service.SysUserService;
import org.example.utils.MD5;
import org.example.vo.system.LoginVo;
import org.example.vo.system.RouterVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Api(tags = "登录注册模块")
public class IndexController {
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysMenuService sysMenuService;


    @PostMapping("/login")
    public Result<Map> login(@RequestBody LoginVo loginVo) {
        //根据用户名称获取用户信息
        SysUser user = sysUserService.getUserByUsername(loginVo.getUsername());
        if (user == null) {
            //用户不存在
            throw new OAException(201, "用户不存在");
        }
        //比较加密后的密码
        String passwordMD5_db = user.getPassword();
        String passwordMD5_input = MD5.encrypt(loginVo.getPassword());
        if (!passwordMD5_db.equals(passwordMD5_input)) {
            throw new OAException(202, "用户密码不正确");
        }
        //用户是否禁用
        Integer status = user.getStatus();
        if (Integer.valueOf(0).equals(status)) {
            throw new OAException(203, "用户已禁用");
        }
        //获取token
        String token = JwtHelper.createToken(user.getId(), user.getUsername());
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        return Result.ok(map);
    }

    @GetMapping("/info")
    public Result<Map> info(HttpServletRequest request) {
        //获取token
        String token = request.getHeader("header");
        //根据token获取用户id
        Long userId = JwtHelper.getUserId(token);
        //根据用户id获取用户信息
        SysUser user = sysUserService.getById(userId);
        //根据角色获取角色的菜单
        List<RouterVo> routerVoList = sysMenuService.findUserMenuList(user);
        //根据角色获取角色的按钮

        List<String> buttonList = sysMenuService.findUserPermsList(user);
        Map<String, Object> map = new HashMap<>();
        map.put("roles","[admin]");
        map.put("name","admin");
        map.put("avatar","https://oss.aliyuncs.com/aliyun_id_photo_bucket/default_handsome.jpg");
        map.put("buttons", buttonList);
        map.put("routers", routerVoList);
        return Result.ok(map);
    }

}
