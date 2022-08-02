package com.dhais.tqb.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dhais.tqb.common.Constants;
import com.dhais.tqb.common.exception.ServiceException;
import com.dhais.tqb.common.utils.JWTUtil;
import com.dhais.tqb.common.utils.ParamPage;
import com.dhais.tqb.mapper.UserMapper;
import com.dhais.tqb.model.User;
import com.dhais.tqb.model.vo.LoginInfo;
import com.dhais.tqb.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * All rights Reserved, Designed By Fan Jun
 *
 * @author Fan Jun
 * @version 1.0
 * @since 2022/6/20 16:59
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    /**
     * 管理员登录接口
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public LoginInfo login(User user) {
        LoginInfo result = new LoginInfo();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getUserName,user.getUserName())
                .eq(User::getState,0);
        List<User> flag = list(queryWrapper);
        if(flag!=null&&!flag.isEmpty()){
            User userInfo = flag.get(0);
            String password = SecureUtil.des(Constants.DB_KEY.getBytes()).decryptStr(userInfo.getPassword());
            if (!password.equals(user.getPassword())) {
                throw new ServiceException("用户名或密码错误！");
            }
            userInfo.setPassword(null);
            String token = JWTUtil.getToken(userInfo);
            token = "Bearer:" + token;
            result.setToken(token);
            result.setNickName(userInfo.getNickName());
            return result;
        }else{
            throw new ServiceException("用户名或密码错误！");
        }
    }

    /**
     * 获取用户信息分页数据
     * @param paramPage
     * @return
     */
    @Override
    public IPage<User> getPageList(ParamPage<User> paramPage){
        IPage<User> page = new Page<>(paramPage.getCurrentNum(),paramPage.getSize());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(paramPage.getEntity())) {
            User entity = paramPage.getEntity();
            if (StrUtil.isNotBlank(entity.getNickName())) {
                queryWrapper.lambda().like(User::getNickName,entity.getNickName());
            }
            if (StrUtil.isNotBlank(entity.getUserName())) {
                queryWrapper.lambda().like(User::getUserName,entity.getUserName());
            }
            if (entity.getState()!=null) {
                queryWrapper.lambda().eq(User::getState,entity.getState());
            }
            if (entity.getUserLevel()!=null) {
                queryWrapper.lambda().eq(User::getUserLevel,entity.getUserLevel());
            }
        }
        page = baseMapper.selectPage(page, queryWrapper);
        return page;
    }

    public static void main(String[] args) {
        System.out.println(SecureUtil.des(Constants.DB_KEY.getBytes()).encryptHex("dhais"));
        System.out.println(SecureUtil.des(Constants.DB_KEY.getBytes()).decryptStr("a0f3d6da0a4199fd"));
    }
}
