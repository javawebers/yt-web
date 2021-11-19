package com.github.yt.web.exception;

/**
 * 已知异常处理器，支持多实现类
 * 将已知异常转换为WebBaseException(WebBusinessException)
 *
 * @author liujiasheng
 */
public interface WebExceptionConverter {
    /**
     * 已知异常转换为 WebBusinessException，非已知异常不进行处理
     *
     * @param e 已知异常
     * @return WebBusinessException
     */
    Throwable convertToBaseException(Throwable e);
}
