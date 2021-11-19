package com.github.yt.web.result;

import com.github.yt.web.exception.WebExceptionConverter;
import com.github.yt.web.exception.WebBusinessException;
import com.github.yt.web.enums.YtWebExceptionEnum;

/**
 * 已知异常处理器
 *
 * @author liujiasheng
 */
public class KnowExceptionConverter implements WebExceptionConverter {

    @Override
    public Throwable convertToBaseException(Throwable e) {
        // 不支持的操作
        if (e instanceof UnsupportedOperationException) {
            return new WebBusinessException(YtWebExceptionEnum.CODE_14, e);
        }
        return e;
    }
}
