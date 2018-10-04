package com.zz.pay.model;

import java.io.Serializable;

public interface Result<T> extends Serializable {
    String getCode();

    void setCode(String var1);

    void setMsg(String var1);

    String getMsg();

    void setModel(T var1);

    T getModel();
}
