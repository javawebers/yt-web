package com.github.yt.web.result;

/**
 * 扩展自动包装返回结果
 *
 * @author liujiasheng
 */
public interface BaseExpandResultBodyHandler {

    /**
     * 扩展返回结果对象
     *
     * @param resultBody 返回结果
     */
    void expandResultBody(HttpResultEntity resultBody);

}
