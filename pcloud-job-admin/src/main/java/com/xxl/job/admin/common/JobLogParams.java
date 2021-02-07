package com.xxl.job.admin.common;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 日志
 *
 * @author siguiyang
 */
public class JobLogParams implements Serializable {
    private static final long serialVersionUID = 405027362041949234L;

    private int page;
    private int pageSize;
    private int jobGroup;
    private int jobId;
    private int logStatus;
    private int type;
    private List<Date> filterTime;

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

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public int getLogStatus() {
        return logStatus;
    }

    public void setLogStatus(int logStatus) {
        this.logStatus = logStatus;
    }

    public List<Date> getFilterTime() {
        return filterTime;
    }

    public void setFilterTime(List<Date> filterTime) {
        this.filterTime = filterTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
