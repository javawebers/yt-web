package com.github.yt.web.query;

import com.github.yt.commons.query.PageQuery;

public class WebQuery implements PageQuery {

    protected Integer pageNo;
    protected Integer pageSize;

    @Override
    public PageQuery<?> makePageNo(Integer pageNo) {
        this.pageNo = pageNo;
        return this;
    }

    @Override
    public PageQuery<?> makePageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    @Override
    public Integer takePageNo() {
        return pageNo;
    }

    @Override
    public Integer takePageSize() {
        return pageSize;
    }
}
