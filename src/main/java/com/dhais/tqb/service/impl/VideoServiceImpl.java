package com.dhais.tqb.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dhais.tqb.common.utils.JWTUtil;
import com.dhais.tqb.common.utils.ParamPage;
import com.dhais.tqb.mapper.VideoMapper;
import com.dhais.tqb.model.User;
import com.dhais.tqb.model.Video;
import com.dhais.tqb.service.IVideoService;
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
public class VideoServiceImpl extends ServiceImpl<VideoMapper,Video> implements IVideoService {
    /**
     * 获取用户信息分页数据
     * @param paramPage
     * @return
     */
    @Override
    public IPage<Video> getPageList(ParamPage<Video> paramPage){
        IPage<Video> page = new Page<>(paramPage.getCurrentNum(),paramPage.getSize());
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(paramPage.getEntity())) {
            Video entity = paramPage.getEntity();
            if (StrUtil.isNotBlank(entity.getVideoName())) {
                queryWrapper.lambda().like(Video::getVideoName,entity.getVideoName());
            }
            if (entity.getStatus()!=null) {
                queryWrapper.lambda().eq(Video::getStatus,entity.getStatus());
            }
        }
        queryWrapper.lambda().orderByDesc(Video::getStatus);
        page = baseMapper.selectPage(page, queryWrapper);
        return page;
    }

    /**
     * 新增Video图片信息
     * @param video
     */
    @Override
    public void addVideo(Video video){
        User userInfo = JWTUtil.getUserInfo();
        video.setCreator(userInfo.getNickName());
        video.setCreateBy(userInfo.getUserName());
        video.setCreateDate(new Date());
        baseMapper.insert(video);
    }

    /**
     * 修改Video图片信息
     * @param video
     */
    @Override
    public void editVideo(Video video){
        User userInfo = JWTUtil.getUserInfo();
        video.setUpdater(userInfo.getNickName());
        video.setUpdateBy(userInfo.getUserName());
        video.setUpdateDate(new Date());
        baseMapper.updateById(video);
    }

    /**
     * 修改Video图片状态信息
     * @param video
     */
    @Override
    public void editVideoStatus(Video video){
        User userInfo = JWTUtil.getUserInfo();
        Video model = new Video();
        model.setId(video.getId());
        model.setUpdater(userInfo.getNickName());
        model.setUpdateBy(userInfo.getUserName());
        model.setUpdateDate(new Date());
        model.setStatus(video.getStatus()==null?0:video.getStatus());
        baseMapper.updateById(model);
    }
}
