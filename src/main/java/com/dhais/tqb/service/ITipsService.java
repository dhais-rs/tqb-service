package com.dhais.tqb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dhais.tqb.common.utils.ParamPage;
import com.dhais.tqb.model.Tips;

public interface ITipsService extends IService<Tips> {
    IPage<Tips> getPageList(ParamPage<Tips> paramPage);

    void addTips(Tips tips);

    void editTips(Tips tips);

    void editTipsStatus(Tips tips);
}
