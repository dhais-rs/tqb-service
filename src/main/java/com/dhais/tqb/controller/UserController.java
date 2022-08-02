package com.dhais.tqb.controller;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dhais.tqb.common.Constants;
import com.dhais.tqb.common.utils.JWTUtil;
import com.dhais.tqb.common.utils.ParamPage;
import com.dhais.tqb.common.utils.ResultModel;
import com.dhais.tqb.common.utils.ResultUtil;
import com.dhais.tqb.model.User;
import com.dhais.tqb.model.vo.LoginInfo;
import com.dhais.tqb.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * All rights Reserved, Designed By Fan Jun
 *
 * @author Fan Jun
 * @version 1.0
 * @since 2022/6/20 15:31
 */
@RestController
@RequestMapping("/user")
@Api(value = "用户接口", tags = "用户接口")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    @ApiOperation(value = "用户登录接口")
    public ResultModel<LoginInfo> login(@RequestBody User user){
        LoginInfo loginInfo = userService.login(user);
        return ResultUtil.success(1,loginInfo,"登录成功！");
    }

    @PostMapping("/page")
    @ApiOperation(value = "用户分页列表")
    public Object page(@RequestBody ParamPage<User> paramPag){
        IPage<User> page = userService.getPageList(paramPag);
        return ResultUtil.success(page.getTotal(),page.getRecords(),"查询成功！");
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改用户信息")
    public Object update(@RequestBody User user){
        User userInfo = JWTUtil.getUserInfo();
        if (Constants.SUPER_ADMIN.equals(userInfo.getUserLevel())||userInfo.getUserName().equals(user.getUserName())) {
            user.setCreator(null);
            user.setCreateBy(null);
            user.setUpdater(userInfo.getNickName());
            user.setUpdateBy(userInfo.getUserName());
            user.setUpdateDate(new Date());
            userService.updateById(user);
            return ResultUtil.success("操作成功！");
        }else {
            return ResultUtil.fail("无操作权限！");
        }
    }

    @PostMapping("/addUser")
    @ApiOperation(value = "添加用户账号")
    public Object addUser(@RequestBody User user){
        User userInfo = JWTUtil.getUserInfo();
        if (Constants.SUPER_ADMIN.equals(userInfo.getUserLevel())) {
            user.setPassword( SecureUtil.des(Constants.DB_KEY.getBytes()).encryptBase64(user.getPassword()));
            user.setState(0);
            user.setCreator(userInfo.getNickName());
            user.setCreateBy(userInfo.getUserName());
            user.setCreateDate(new Date());
            userService.save(user);
            return ResultUtil.success("操作成功！");
        }else {
            return ResultUtil.fail("无操作权限！");
        }
    }

    @PostMapping("/updateState")
    @ApiOperation(value = "修改用户账号")
    public Object updateState(@RequestBody User model){
        User userInfo = JWTUtil.getUserInfo();
        if (Constants.SUPER_ADMIN.equals(userInfo.getUserLevel())) {
            if (userInfo.getId()==model.getId()) {
                return ResultUtil.fail("不能锁定当前用户！");
            }
            User user = new User();
            user.setId(model.getId());
            user.setState(model.getState());
            user.setUpdater(userInfo.getNickName());
            user.setUpdateBy(userInfo.getUserName());
            user.setUpdateDate(new Date());
            userService.updateById(user);
            return ResultUtil.success("操作成功！");
        }else {
            return ResultUtil.fail("无操作权限！");
        }
    }

    @GetMapping("/delUser")
    @ApiOperation(value = "删除用户账号")
    public Object delUser(Integer id){
        User userInfo = JWTUtil.getUserInfo();
        if (Constants.SUPER_ADMIN.equals(userInfo.getUserLevel())) {
            if (userInfo.getId()==id) {
                return ResultUtil.fail("不能删除当前用户！");
            }
            userService.removeById(id);
            return ResultUtil.success("操作成功！");
        }else {
            return ResultUtil.fail("无操作权限！");
        }
    }
}
