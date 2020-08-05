package com.github.yt.web.result;

import com.github.yt.commons.exception.BaseAccidentException;
import com.github.yt.commons.exception.BaseExceptionConverter;
import com.github.yt.commons.util.YtStringUtils;
import com.github.yt.web.YtWebExceptionEnum;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 参数校验异常处理器
 *
 * @author liujiasheng
 */
@Component
public class ValidatorExceptionConverter implements BaseExceptionConverter {

    @Override
    public Throwable convertToBaseException(Throwable e) {
        if (e instanceof ConstraintViolationException) {
            // controller校验异常拦截，字段参数异常拦截
            // ConstraintViolationException是ViolationException的子类
            ConstraintViolationException cve = (ConstraintViolationException) e;
            Set<ConstraintViolation<?>> cvSet = cve.getConstraintViolations();
            List<String> errorMessageList = cvSet.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
            return new BaseAccidentException(YtWebExceptionEnum.CODE_11, e, YtStringUtils.join(errorMessageList, ", "));
        } else if (e instanceof BindException) {
            // controller校验异常拦截，类参数异常拦截
            BindException be = (BindException) e;
            List<ObjectError> errorList = be.getAllErrors();
            List<String> errorMessageList = errorList.stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList());
            return new BaseAccidentException(YtWebExceptionEnum.CODE_11, e, YtStringUtils.join(errorMessageList, ", "));
        } else if (e instanceof MethodArgumentNotValidException) {
            // controller校验异常拦截，RequestBody校验失败
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;
            List<ObjectError> errorList = exception.getBindingResult().getAllErrors();
            List<String> errorMessageList = errorList.stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList());
            return new BaseAccidentException(YtWebExceptionEnum.CODE_11, e, YtStringUtils.join(errorMessageList, ", "));
        } else if (e instanceof HttpMessageNotReadableException) {
            // controller校验异常拦截，RequestBody json转换异常，还没到校验步骤
            return new BaseAccidentException(YtWebExceptionEnum.CODE_12, e, e.getMessage());
        } else if (e instanceof MethodArgumentTypeMismatchException) {
            // 參數異常，RequestBody
            MethodArgumentTypeMismatchException se = (MethodArgumentTypeMismatchException) e;
            return new BaseAccidentException(YtWebExceptionEnum.CODE_11, e, "参数:" + se.getName() + ", 值:" + se.getValue());
        } else if (e instanceof MaxUploadSizeExceededException) {
            // 上传文件超过最大限制
            return new BaseAccidentException(YtWebExceptionEnum.CODE_13, e);
        } else if (e instanceof MissingServletRequestPartException) {
            // 缺少必要参数
            MissingServletRequestPartException missingServletRequestPartException = (MissingServletRequestPartException) e;
            return new BaseAccidentException(YtWebExceptionEnum.CODE_11, e, "缺少必要参数 " + missingServletRequestPartException.getRequestPartName());
        }
        return e;
    }
}
