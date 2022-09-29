package com.dhais.tqb.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dhais.tqb.common.utils.ParamPage;
import com.dhais.tqb.common.utils.ResultUtil;
import com.dhais.tqb.model.Tips;
import com.dhais.tqb.service.ITipsService;
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
@RequestMapping("/tips")
@Api(value = "提示信息接口", tags = "提示信息接口")
public class TipsController {

    @Autowired
    private ITipsService tipsService;

    @PostMapping("/page")
    @ApiOperation(value = "提示信息分页列表")
    public Object page(@RequestBody ParamPage<Tips> paramPag){
        IPage<Tips> page = tipsService.getPageList(paramPag);
        return ResultUtil.success(page.getTotal(),page.getRecords(),"查询成功！");
    }

    @PostMapping("/addTips")
    @ApiOperation(value = "新增提示信息")
    public Object addTips(@RequestBody Tips tips){
        tipsService.addTips(tips);
        return ResultUtil.success("保存成功！");
    }

    @PostMapping("/editTips")
    @ApiOperation(value = "修改提示信息")
    public Object editTips(@RequestBody Tips tips){
        tipsService.editTips(tips);
        return ResultUtil.success("修改成功！");
    }

    @PostMapping("/editTipsStatus")
    @ApiOperation(value = "修改提示信息状态")
    public Object editTipsStatus(@RequestBody Tips tips){
        tipsService.editTipsStatus(tips);
        return ResultUtil.success("修改成功！");
    }

    @PostMapping("/delTips")
    @ApiOperation(value = "删除提示信息")
    public Object delTips(@RequestBody Tips tips){
        tipsService.removeById(tips.getId());
        return ResultUtil.success("删除成功！");
    }


}
