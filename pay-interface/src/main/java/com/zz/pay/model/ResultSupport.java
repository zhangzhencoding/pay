package com.zz.pay.model;

public class ResultSupport<T> implements Result<T> {
    private static final long serialVersionUID = -1802692098484483921L;
    private String code;
    private String msg;
    private T model;

    public ResultSupport() {
    }

    public ResultSupport(String code, String msg, T model) {
        this.code = code;
        this.msg = msg;
        this.model = model;
    }

    public static long getSerialVersionUID() {
        return -1802692098484483921L;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setCode(Integer code) {
        this.code = String.valueOf(code);
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getModel() {
        return this.model;
    }

    public void setModel(T model) {
        this.model = model;
    }

    public String toString() {
        return "ResultSupport{code='" + this.code + '\'' + ", msg='" + this.msg + '\'' + ", model=" + this.model + '}';
    }
}