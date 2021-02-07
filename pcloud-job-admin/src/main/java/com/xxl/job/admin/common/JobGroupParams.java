package com.xxl.job.admin.common;

import java.io.Serializable;

/**
 * 任务组，执行器
 */
public class JobGroupParams implements Serializable {

    private static final long serialVersionUID = 8085735131405913459L;
    private int page;
    private int pageSize;
    private String appname;
    private String title;

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

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
