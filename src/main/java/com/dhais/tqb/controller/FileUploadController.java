package com.dhais.tqb.controller;

import com.dhais.tqb.common.utils.ResultModel;
import com.dhais.tqb.common.utils.ResultUtil;
import com.dhais.tqb.service.IFileUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * All rights Reserved, Designed By Fan Jun
 *
 * @author Fan Jun
 * @version 1.0
 * @since 2022/8/9 17:26
 */
@RestController
@RequestMapping("/file")
@Api(value = "文件上传接口", tags = "文件上传接口")
public class FileUploadController {
    @Autowired
    private IFileUploadService fileUploadService;

    @PostMapping("/img/upload")
    @ApiOperation("图片上传接口")
    public ResultModel<?> imgUpload(@RequestParam(required = true) MultipartFile file){
        String url = fileUploadService.fileUpload(file,0);
        return ResultUtil.success(1,url,"保存成功！");
    }

    @PostMapping("/video/upload")
    @ApiOperation("视频上传接口")
    public ResultModel<?> videoUpload(@RequestParam(required = true) MultipartFile file){
        String url = fileUploadService.fileUpload(file,0);
        return ResultUtil.success(1,url,"保存成功！");
    }
}
