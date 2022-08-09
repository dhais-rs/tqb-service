package com.dhais.tqb.controller;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dhais.tqb.common.Constants;
import com.dhais.tqb.common.utils.JWTUtil;
import com.dhais.tqb.common.utils.ParamPage;
import com.dhais.tqb.common.utils.ResultModel;
import com.dhais.tqb.common.utils.ResultUtil;
import com.dhais.tqb.model.Banner;
import com.dhais.tqb.model.User;
import com.dhais.tqb.model.vo.LoginInfo;
import com.dhais.tqb.service.IBannerService;
import com.dhais.tqb.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * All rights Reserved, Designed By Fan Jun
 *
 * @author Fan Jun
 * @version 1.0
 * @since 2022/6/20 15:31
 */
@RestController
@RequestMapping("/banner")
@Api(value = "轮播图信息接口", tags = "轮播图信息接口")
public class BannerController {

    @Autowired
    private IBannerService bannerService;

    @PostMapping("/page")
    @ApiOperation(value = "用户分页列表")
    public Object page(@RequestBody ParamPage<Banner> paramPag){
        IPage<Banner> page = bannerService.getPageList(paramPag);
        return ResultUtil.success(page.getTotal(),page.getRecords(),"查询成功！");
    }


}
