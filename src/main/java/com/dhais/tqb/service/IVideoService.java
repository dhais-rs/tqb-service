package com.dhais.tqb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dhais.tqb.common.utils.ParamPage;
import com.dhais.tqb.model.Video;

/**
 * All rights Reserved, Designed By Fan Jun
 *
 * @author Fan Jun
 * @version 1.0
 * @since 2022/6/20 16:58
 */
public interface IVideoService extends IService<Video> {

    /**
     * 获取视频信息分页列表
     * @param paramPage
     * @return
     */
    IPage<Video> getPageList(ParamPage<Video> paramPage);

    /**
     * 新增视频信息
     * @param video
     */
    void addVideo(Video video);

    /**
     * 修改视频信息
     * @param video
     */
    void editVideo(Video video);

    /**
     * 修改视频信息
     * @param video
     */
    void editVideoStatus(Video video);
}
