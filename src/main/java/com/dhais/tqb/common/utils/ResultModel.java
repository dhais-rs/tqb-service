package com.dhais.tqb.common.utils;

import java.io.Serializable;

/**
 * All rights Reserved, Designed By Fan Jun
 *
 * @author Fan Jun
 * @version 1.0
 * @since 2022/6/21 15:42
 */
public class ResultModel<T> implements Serializable {
    private int code;
    private String info;
    private long total;
    private T row;
    private final long timestamp = System.currentTimeMillis();

    public int getCode() {
        return code;
    }

    public ResultModel<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getInfo() {
        return info;
    }

    public ResultModel<T> setInfo(String info) {
        this.info = info;
        return this;
    }

    public long getTotal() {
        return total;
    }

    public ResultModel<T> setTotal(long total) {
        this.total = total;
        return this;
    }

    public T getRow() {
        return row;
    }

    public ResultModel<T> setRow(T row) {
        this.row = row;
        return this;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
