package com.dhais.tqb.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dhais.tqb.common.utils.ParamPage;
import com.dhais.tqb.common.utils.ResultUtil;
import com.dhais.tqb.model.Video;
import com.dhais.tqb.service.IVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * All rights Reserved, Designed By Fan Jun
 *
 * @author Fan Jun
 * @version 1.0
 * @since 2022/6/20 15:31
 */
@RestController
@RequestMapping("/video")
@Api(value = "轮播图信息接口", tags = "轮播图信息接口")
public class VideoController {

    @Autowired
    private IVideoService videoService;

    @PostMapping("/page")
    @ApiOperation(value = "轮播图信息分页列表")
    public Object page(@RequestBody ParamPage<Video> paramPag){
        IPage<Video> page = videoService.getPageList(paramPag);
        return ResultUtil.success(page.getTotal(),page.getRecords(),"查询成功！");
    }

    @PostMapping("/addVideo")
    @ApiOperation(value = "新增轮播图信息")
    public Object addVideo(@RequestBody Video video){
        videoService.addVideo(video);
        return ResultUtil.success("保存成功！");
    }

    @PostMapping("/editVideo")
    @ApiOperation(value = "修改轮播图信息")
    public Object editVideo(@RequestBody Video video){
        videoService.editVideo(video);
        return ResultUtil.success("修改成功！");
    }

    @PostMapping("/editVideoStatus")
    @ApiOperation(value = "修改banner图片状态信息")
    public Object editVideoStatus(@RequestBody Video video){
        videoService.editVideoStatus(video);
        return ResultUtil.success("修改成功！");
    }

    @PostMapping("/delVideo")
    @ApiOperation(value = "删除banner图片状态信息")
    public Object delVideo(@RequestBody Video video){
        videoService.removeById(video.getId());
        return ResultUtil.success("删除成功！");
    }


}
