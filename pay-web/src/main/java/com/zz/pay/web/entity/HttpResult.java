package com.zz.pay.web.entity;

public class HttpResult {
    private Integer status;
    private String data;

    public HttpResult(Integer status,String data){
        this.status = status;
        this.data = data;
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
