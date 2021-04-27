package com.xxl.job.admin.common;

import java.io.Serializable;

/**
 * jobinfo请求参数
 *
 * @author siguiyang
 */
public class JobInfoParams implements Serializable {
    private static final long serialVersionUID = -3661733218122271723L;

    private int page;
    private int pageSize;
    private int jobGroup;
    private int triggerStatus;
    private String jobDesc;
    private String executorHandler;
    private String author;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(int jobGroup) {
        this.jobGroup = jobGroup;
    }

    public int getTriggerStatus() {
        return triggerStatus;
    }

    public void setTriggerStatus(int triggerStatus) {
        this.triggerStatus = triggerStatus;
    }

    public String getJobDesc() {
        return jobDesc;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }

    public String getExecutorHandler() {
        return executorHandler;
    }

    public void setExecutorHandler(String executorHandler) {
        this.executorHandler = executorHandler;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
