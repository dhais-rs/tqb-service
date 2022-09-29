package com.dhais.tqb.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dhais.tqb.common.exception.ServiceException;
import com.dhais.tqb.common.utils.JWTUtil;
import com.dhais.tqb.common.utils.ParamPage;
import com.dhais.tqb.common.utils.PropertiesUtil;
import com.dhais.tqb.mapper.BannerMapper;
import com.dhais.tqb.model.Banner;
import com.dhais.tqb.model.User;
import com.dhais.tqb.service.IBannerService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * All rights Reserved, Designed By Fan Jun
 *
 * @author Fan Jun
 * @version 1.0
 * @since 2022/6/20 16:59
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper,Banner> implements IBannerService {
    /**
     * 获取用户信息分页数据
     * @param paramPage
     * @return
     */
    @Override
    public IPage<Banner> getPageList(ParamPage<Banner> paramPage){
        IPage<Banner> page = new Page<>(paramPage.getCurrentNum(),paramPage.getSize());
        QueryWrapper<Banner> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(paramPage.getEntity())) {
            Banner entity = paramPage.getEntity();
            if (StrUtil.isNotBlank(entity.getBannerName())) {
                queryWrapper.lambda().like(Banner::getBannerName,entity.getBannerName());
            }
            if (entity.getStatus()!=null) {
                queryWrapper.lambda().eq(Banner::getStatus,entity.getStatus());
            }
        }
        queryWrapper.lambda().orderByDesc(Banner::getStatus);
        page = baseMapper.selectPage(page, queryWrapper);
        return page;
    }

    /**
     * 新增banner图片信息
     * @param banner
     */
    @Override
    public void addBanner(Banner banner){
        User userInfo = JWTUtil.getUserInfo();
        banner.setCreator(userInfo.getNickName());
        banner.setCreateBy(userInfo.getUserName());
        banner.setCreateDate(new Date());
        if (banner.getStatus()==1) {
            QueryWrapper<Banner> checkWrapper = new QueryWrapper<>();
            checkWrapper.lambda().eq(Banner::getStatus,1);
            Integer flag = baseMapper.selectCount(checkWrapper);
            if (flag>=PropertiesUtil.getInt("banner.max.num")) {
                throw new ServiceException("超过最大轮播数量，请修改状态！");
            }
        }
        baseMapper.insert(banner);
    }

    /**
     * 修改banner图片信息
     * @param banner
     */
    @Override
    public void editBanner(Banner banner){
        User userInfo = JWTUtil.getUserInfo();
        banner.setUpdater(userInfo.getNickName());
        banner.setUpdateBy(userInfo.getUserName());
        banner.setUpdateDate(new Date());
        if (banner.getStatus()==1) {
            QueryWrapper<Banner> checkWrapper = new QueryWrapper<>();
            checkWrapper.lambda().eq(Banner::getStatus,1);
            Integer flag = baseMapper.selectCount(checkWrapper);
            if (flag>=PropertiesUtil.getInt("banner.max.num")) {
                Banner oldBanner = baseMapper.selectById(banner.getId());
                if (oldBanner.getStatus()==0) {
                    throw new ServiceException("超过最大轮播数量，请修改状态！");
                }
            }
        }
        baseMapper.updateById(banner);
    }

    /**
     * 修改banner图片状态信息
     * @param banner
     */
    @Override
    public void editBannerStatus(Banner banner){
        User userInfo = JWTUtil.getUserInfo();
        Banner model = new Banner();
        model.setId(banner.getId());
        model.setUpdater(userInfo.getNickName());
        model.setUpdateBy(userInfo.getUserName());
        model.setUpdateDate(new Date());
        model.setStatus(banner.getStatus()==null?0:banner.getStatus());
        if (model.getStatus()==1) {
            QueryWrapper<Banner> checkWrapper = new QueryWrapper<>();
            checkWrapper.lambda().eq(Banner::getStatus,1);
            Integer flag = baseMapper.selectCount(checkWrapper);
            if (flag>= PropertiesUtil.getInt("banner.max.num")) {
                Banner oldBanner = baseMapper.selectById(banner.getId());
                if (oldBanner.getStatus()==0) {
                    throw new ServiceException("超过最大轮播数量，请修改状态！");
                }
            }
        }
        baseMapper.updateById(model);
    }
}
