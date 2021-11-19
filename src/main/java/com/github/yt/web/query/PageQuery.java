package com.github.yt.web.query;

/**
 * 分页查询条件
 * controller 设置分页信息和 mapper 使用分页信息的粘合剂
 *
 * @author sheng
 */
public interface PageQuery<T extends PageQuery<?>> {

    /**
     * 设置 pageNo
     *
     * @param pageNo 页码
     * @return 当前实例
     */
    T makePageNo(Integer pageNo);

    /**
     * 设置 pageSize
     *
     * @param pageSize 每页数据大小
     * @return 当前实例
     */
    T makePageSize(Integer pageSize);

    /**
     * 获取 pageNo
     *
     * @return pageNo
     */
    Integer takePageNo();

    /**
     * 获取 pageSize
     *
     * @return pageSize
     */
    Integer takePageSize();

}
