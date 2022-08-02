package com.dhais.tqb.common.exception;

import com.dhais.tqb.common.model.HttpCode;

/**
 * All rights Reserved, Designed By Fan Jun
 *
 * @author Fan Jun
 * @version 1.0
 * @since 2022/6/20 15:36
 */
public class ServiceException extends RuntimeException {
    public ServiceException() {
    }

    private HttpCode httpCode= HttpCode.OK;

    private Integer msgCode = null;

    private HttpCode getHttpCode() {
        return httpCode;
    }

    private void setHttpCode(HttpCode httpCode) {
        this.httpCode = httpCode;
    }

    public void setMsgCode(Integer msgCode) {
        this.msgCode = msgCode;
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(HttpCode httpCode,String message) {
        super(message);
        setHttpCode(httpCode);
    }

    public ServiceException(HttpCode httpCode, Integer msgCode, String message) {
        super(message);
        setHttpCode(httpCode);
        setMsgCode(msgCode);
    }


    public ServiceException(String message, Exception e) {
        super(message, e);
        setHttpCode(httpCode);
    }



    public ServiceException(HttpCode httpCode,String message, Exception e) {
        super(message, e);
        setHttpCode(httpCode);
    }

    public ServiceException(HttpCode httpCode,Integer msgCode,String message, Exception e) {
        super(message, e);
        setHttpCode(httpCode);
        setMsgCode(msgCode);
    }

    public HttpCode getCode() {
        return getHttpCode();
    }

    public Integer getMsgCode() {
        return msgCode;
    }
}
