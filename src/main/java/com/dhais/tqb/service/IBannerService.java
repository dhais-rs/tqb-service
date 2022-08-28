package com.dhais.tqb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dhais.tqb.common.utils.ParamPage;
import com.dhais.tqb.model.Banner;
import com.dhais.tqb.model.User;

/**
 * All rights Reserved, Designed By Fan Jun
 *
 * @author Fan Jun
 * @version 1.0
 * @since 2022/6/20 16:58
 */
public interface IBannerService extends IService<Banner> {

    /**
     * 获取用户信息分页数据
     * @param paramPage
     * @return
     */
    IPage<Banner> getPageList(ParamPage<Banner> paramPage);

    /**
     * 新增banner图片信息
     * @param banner
     */
    void addBanner(Banner banner);

    /**
     * 修改banner图片信息
     * @param banner
     */
    void editBanner(Banner banner);

    /**
     * 修改banner图片状态信息
     * @param banner
     */
    void editBannerStatus(Banner banner);
}
