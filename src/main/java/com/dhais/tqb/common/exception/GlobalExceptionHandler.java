package com.dhais.tqb.common.exception;

import com.dhais.tqb.common.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 所有异常报错
     * @param
     * @return
     * @throws Exception
     */
//    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value=Exception.class)
    public Object ServiceExceptionHandler( Exception e)
    {
        if(e instanceof ServiceException){
            logger.info(e.getMessage());
            return ResultUtil.notLogin(e.getMessage());
        }else{
            logger.info(e.getMessage());
            return ResultUtil.notLogin("服务器正在开小差！");
        }
    }
//    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(value=Exception.class)
//    public Object ExceptionHandler( Exception e)
//    {
//        e.printStackTrace();
//        return ResultUtil.fail("系统正在开小差！");
//    }
}