package com.dhais.tqb.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dhais.tqb.common.utils.ParamPage;
import com.dhais.tqb.mapper.BannerMapper;
import com.dhais.tqb.model.Banner;
import com.dhais.tqb.model.User;
import com.dhais.tqb.service.IBannerService;
import org.springframework.stereotype.Service;

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
}
