package com.github.yt.web.query;

import java.util.List;

/**
 * 分页接口类
 * @author sheng
 */
public interface IPage<T> {

    /**
     * 获取页码
     * @return 页码
     */
    int getPageNo();
    /**
     * 获取每页条数
     * @return 每页条数
     */
    int getPageSize();
    /**
     * 获取总记录数
     * @return 总记录数
     */
    int getTotalCount();
    /**
     * 获取分页列表数据
     * @return 分页列表数据
     */
    List<T> getData();

}
