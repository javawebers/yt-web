package com.github.yt.web.exception;


/**
 * 字定义异常接口
 *
 * @author liujiasheng
 */
public interface WebBaseException {

    /**
     * 获取异常码.
     *
     * @return Object
     */
    Object getErrorCode();

    /**
     * 获取异常参数
     *
     * @return 参数
     */
    Object[] getErrorParams();

    /**
     * 获取异常结果集.
     *
     * @return Object
     */
    Object getErrorResult();

}
