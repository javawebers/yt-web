package com.github.yt.web.exception;

/**
 * 业务异常，最终转换为WebBusinessException异常
 * 使用场景：
 * 1、已知业务异异常
 * 2、数据校验
 * 3、结果集异常
 * 4、未知、不可处理的异常可以包装成这个异常
 * <p>
 * 以下我们对是“意外事件”和“错误”的理解
 * <p>
 * 异常条件	                        意外事件	                    错误
 * <p>
 * 认为是（Is considered to be）	    设计的一部分	                难以应付的意外
 * 预期发生（Is expected to happen）	有规律但很少发生	            从不
 * 谁来处理（Who cares about it）	调用方法的上游代码	        需要修复此问题的人员
 * 实例（Examples）	                另一种返回模式	            编程缺陷，硬件故障，配置错误，文件丢失，服务器无法使用
 * 最佳映射（Best Mapping）	        已检查异常	                未检查异常
 *
 * @author liujiasheng
 */
public class WebBusinessException extends RuntimeException implements WebBaseException {

    private static final long serialVersionUID = 8686960428281101225L;

    /**
     * errorCode
     */
    private Object errorCode;

    /**
     * 异常参数
     */
    private Object[] errorParams;

    /**
     * error结果集.
     */
    private Object errorResult;

    public WebBusinessException() {
    }

    public WebBusinessException(int errorCode, String errorMsg, Object... params) {
        this((Object) errorCode, errorMsg, params);
    }

    public WebBusinessException(int errorCode, String errorMsg, Throwable e, Object... params) {
        this((Object) errorCode, errorMsg, e, params);
    }

    public WebBusinessException(String errorCode, String errorMsg, Object... params) {
        this((Object) errorCode, errorMsg, params);
    }

    public WebBusinessException(String errorCode, String errorMsg, Throwable e, Object... params) {
        this((Object) errorCode, errorMsg, e, params);
    }

    private WebBusinessException(Object errorCode, String errorMsg, Object... params) {
        super(ExceptionUtils.getExceptionMessage(errorMsg, params));
        this.errorCode = errorCode;
        this.errorParams = params;
    }

    private WebBusinessException(Object errorCode, String errorMsg, Throwable e, Object... params) {
        super(ExceptionUtils.getExceptionMessage(errorMsg, params), e);
        this.errorCode = errorCode;
        this.errorParams = params;
    }

    public WebBusinessException(Enum<?> errorEnum, Object... params) {
        super(ExceptionUtils.getExceptionMessage(errorEnum, params));
        this.errorCode = ExceptionUtils.getExceptionCode(errorEnum);
        this.errorParams = params;
    }

    public WebBusinessException(Enum<?> errorEnum, Throwable e, Object... params) {
        super(ExceptionUtils.getExceptionMessage(errorEnum, params), e);
        this.errorCode = ExceptionUtils.getExceptionCode(errorEnum);
        this.errorParams = params;
    }

    public WebBusinessException(Object errorResult, Enum<?> errorEnum, Object... params) {
        super(ExceptionUtils.getExceptionMessage(errorEnum, params));
        this.errorCode = ExceptionUtils.getExceptionCode(errorEnum);
        this.errorResult = errorResult;
        this.errorParams = params;
    }

    public WebBusinessException(Object errorResult, Enum<?> errorEnum, Throwable e, Object... params) {
        super(ExceptionUtils.getExceptionMessage(errorEnum, params), e);
        this.errorCode = ExceptionUtils.getExceptionCode(errorEnum);
        this.errorResult = errorResult;
        this.errorParams = params;
    }

    @Override
    public Object getErrorResult() {
        return errorResult;
    }

    @Override
    public Object getErrorCode() {
        return errorCode;
    }


    @Override
    public Object[] getErrorParams() {
        return errorParams;
    }

}
