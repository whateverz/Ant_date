package com.ant.antdate.http;

public class HttpResult<T> extends lib.frame.module.http.HttpResult<T> {

    private T data;
    private int state;
    private int code;
    private String message;

    @Override
    public T getResults() {
        return data;
    }

    @Override
    public String getUserMsg() {
        return "";
    }

    @Override
    public boolean isSuccess() {
        return code == 1000;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return message;
    }

    @Override
    public boolean isNeedlogin() {
        return false;
    }
}
