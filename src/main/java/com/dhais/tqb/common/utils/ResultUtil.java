package com.dhais.tqb.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据返回工具类
 * @since 2020-3-18
 * @author fj
 */
public class ResultUtil {

    public static <T> ResultModel<T> success(Integer total ,T rows,String info){
        ResultModel<T> resultModel = new ResultModel<>();
        resultModel.setCode(1)
                .setInfo(info)
                .setTotal(total)
                .setRow(rows);
        return resultModel;
    }
    public static <T> ResultModel<T> success(Long total ,T rows,String info){
        ResultModel<T> resultModel = new ResultModel<>();
        resultModel.setCode(1)
                .setInfo(info)
                .setTotal(total)
                .setRow(rows);
        return resultModel;
    }
    public static <T> ResultModel<T> success(String info){
        ResultModel<T> resultModel = new ResultModel<>();
        resultModel.setCode(1)
                .setInfo(info)
                .setTotal(0)
                .setRow(null);
        return resultModel;
    }
    public static <T> ResultModel<T> fail(Integer total,String info){
        ResultModel<T> resultModel = new ResultModel<>();
        resultModel.setCode(0)
                .setInfo(info)
                .setTotal(total)
                .setRow(null);
        return resultModel;
    }

    public static <T> ResultModel<T> fail(String info){
        ResultModel<T> resultModel = new ResultModel<>();
        resultModel.setCode(0)
                .setInfo(info)
                .setTotal(0)
                .setRow(null);
        return resultModel;
    }

    public static <T> ResultModel<T> notLogin(String info){
        ResultModel<T> resultModel = new ResultModel<>();
        resultModel.setCode(2)
                .setInfo(info)
                .setTotal(0)
                .setRow(null);
        return resultModel;
    }
}
