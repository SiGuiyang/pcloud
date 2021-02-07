package com.xxl.job.admin.common;

import java.io.Serializable;

/**
 * 执行
 */
public class TriggerJobParam implements Serializable {

    private static final long serialVersionUID = -8660827979857934125L;
    private int id;
    private String executorParam;
    private String addressList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExecutorParam() {
        return executorParam;
    }

    public void setExecutorParam(String executorParam) {
        this.executorParam = executorParam;
    }

    public String getAddressList() {
        return addressList;
    }

    public void setAddressList(String addressList) {
        this.addressList = addressList;
    }
}
