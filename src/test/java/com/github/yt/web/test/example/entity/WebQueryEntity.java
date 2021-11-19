package com.github.yt.web.test.example.entity;

public class WebQueryEntity {

    protected Integer pageNo;
    protected Integer pageSize;

    public Integer getPageNo() {
        return pageNo;
    }

    public WebQueryEntity setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
        return this;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public WebQueryEntity setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }
}
