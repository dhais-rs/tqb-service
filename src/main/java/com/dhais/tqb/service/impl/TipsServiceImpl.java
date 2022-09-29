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
import com.dhais.tqb.mapper.TipsMapper;
import com.dhais.tqb.model.Tips;
import com.dhais.tqb.model.User;
import com.dhais.tqb.service.ITipsService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TipsServiceImpl extends ServiceImpl<TipsMapper, Tips> implements ITipsService {
    @Override
    public IPage<Tips> getPageList(ParamPage<Tips> paramPage){
        IPage<Tips> page = new Page<>(paramPage.getCurrentNum(),paramPage.getSize());
        QueryWrapper<Tips> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(paramPage.getEntity())) {
            Tips entity = paramPage.getEntity();
            if (StrUtil.isNotBlank(entity.getTipsName())) {
                queryWrapper.lambda().like(Tips::getTipsName,entity.getTipsName());
            }
            if (entity.getStatus()!=null) {
                queryWrapper.lambda().eq(Tips::getStatus,entity.getStatus());
            }
        }
        queryWrapper.lambda().orderByDesc(Tips::getStatus);
        page = baseMapper.selectPage(page, queryWrapper);
        return page;
    }

    @Override
    public void addTips(Tips tips){
        User userInfo = JWTUtil.getUserInfo();
        tips.setCreator(userInfo.getNickName());
        tips.setCreateBy(userInfo.getUserName());
        tips.setCreateDate(new Date());
        if (tips.getStatus()==1) {
            QueryWrapper<Tips> checkWrapper = new QueryWrapper<>();
            checkWrapper.lambda().eq(Tips::getStatus,1);
            Integer flag = baseMapper.selectCount(checkWrapper);
            if (flag>= PropertiesUtil.getInt("tips.max.num")) {
                throw new ServiceException("超过最大展示数量！");
            }
        }
        baseMapper.insert(tips);
    }

    @Override
    public void editTips(Tips tips){
        User userInfo = JWTUtil.getUserInfo();
        tips.setUpdater(userInfo.getNickName());
        tips.setUpdateBy(userInfo.getUserName());
        tips.setUpdateDate(new Date());
        if (tips.getStatus()==1) {
            QueryWrapper<Tips> checkWrapper = new QueryWrapper<>();
            checkWrapper.lambda().eq(Tips::getStatus,1);
            Integer flag = baseMapper.selectCount(checkWrapper);
            if (flag>=PropertiesUtil.getInt("tips.max.num")) {
                Tips oldTips = baseMapper.selectById(tips.getId());
                if (oldTips.getStatus()==0) {
                    throw new ServiceException("超过最大展示数量！");
                }
            }
        }
        baseMapper.updateById(tips);
    }

    @Override
    public void editTipsStatus(Tips tips){
        User userInfo = JWTUtil.getUserInfo();
        Tips model = new Tips();
        model.setId(tips.getId());
        model.setUpdater(userInfo.getNickName());
        model.setUpdateBy(userInfo.getUserName());
        model.setUpdateDate(new Date());
        model.setStatus(tips.getStatus()==null?0:tips.getStatus());
        if (model.getStatus()==1) {
            QueryWrapper<Tips> checkWrapper = new QueryWrapper<>();
            checkWrapper.lambda().eq(Tips::getStatus,1);
            Integer flag = baseMapper.selectCount(checkWrapper);
            if (flag>=PropertiesUtil.getInt("tips.max.num")) {
                Tips oldTips = baseMapper.selectById(tips.getId());
                if (oldTips.getStatus()==0) {
                    throw new ServiceException("超过最大展示数量！");
                }
            }
        }
        baseMapper.updateById(model);
    }
}
