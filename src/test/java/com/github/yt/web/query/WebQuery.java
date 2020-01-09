package com.github.yt.web.query;

import com.github.yt.commons.query.PageQuery;

public class WebQuery implements PageQuery<WebQuery> {

    protected Integer pageNo;
    protected Integer pageSize;

    @Override
    public WebQuery makePageNo(Integer pageNo) {
        this.pageNo = pageNo;
        return this;
    }

    @Override
    public WebQuery makePageSize(Integer pageSize) {
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
