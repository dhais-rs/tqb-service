package com.dhais.tqb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dhais.tqb.common.utils.ParamPage;
import com.dhais.tqb.model.User;
import com.dhais.tqb.model.vo.LoginInfo;

/**
 * All rights Reserved, Designed By Fan Jun
 *
 * @author Fan Jun
 * @version 1.0
 * @since 2022/6/20 16:58
 */
public interface IUserService extends IService<User> {
    /**
     * 管理员登录接口
     * @param user
     * @return
     * @throws Exception
     */
    LoginInfo login(User user);

    /**
     * 获取用户信息分页数据
     * @param paramPage
     * @return
     */
    IPage<User> getPageList(ParamPage<User> paramPage);
}
