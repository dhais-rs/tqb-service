package com.dhais.tqb.common.exception;

import com.dhais.tqb.common.model.HttpCode;

/**
 * All rights Reserved, Designed By Fan Jun
 *
 * @author Fan Jun
 * @version 1.0
 * @since 2022/8/12 12:02
 */
public class NotLoginException extends RuntimeException {
    public NotLoginException() {
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

    public NotLoginException(String message) {
        super(message);
    }

    public NotLoginException(HttpCode httpCode,String message) {
        super(message);
        setHttpCode(httpCode);
    }

    public NotLoginException(HttpCode httpCode, Integer msgCode, String message) {
        super(message);
        setHttpCode(httpCode);
        setMsgCode(msgCode);
    }


    public NotLoginException(String message, Exception e) {
        super(message, e);
        setHttpCode(httpCode);
    }



    public NotLoginException(HttpCode httpCode, String message, Exception e) {
        super(message, e);
        setHttpCode(httpCode);
    }

    public NotLoginException(HttpCode httpCode,Integer msgCode,String message, Exception e) {
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
