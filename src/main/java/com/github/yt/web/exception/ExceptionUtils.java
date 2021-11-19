package com.github.yt.web.exception;

import java.lang.reflect.Field;
import java.text.MessageFormat;

/**
 * @author liujiasheng
 */
public class ExceptionUtils {

    /**
     * 获取异常码.
     *
     * @param errorEnum errorEnum
     * @return Object
     */
    public static Object getExceptionCode(Enum<?> errorEnum) {
        Object errorCode;
        try {
            Field codeField = errorEnum.getClass().getDeclaredField("code");
            codeField.setAccessible(true);
            errorCode = codeField.get(errorEnum);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            errorCode = errorEnum.name();
        }
        return errorCode;
    }

    /**
     * 获取异常消息.
     *
     * @param errorEnum errorEnum
     * @param params    params
     * @return String
     */
    public static String getExceptionMessage(Enum<?> errorEnum, Object... params) {
        try {
            Field messageField = errorEnum.getClass().getDeclaredField("message");
            messageField.setAccessible(true);
            String errorMessage = (String) messageField.get(errorEnum);
            return MessageFormat.format(errorMessage, params);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException("获取异常枚举中 message 属性异常", e);
        }
    }

    /**
     * 获取异常消息.
     *
     * @param errorMsg errorMsg
     * @param params   params
     * @return String
     */
    public static String getExceptionMessage(String errorMsg, Object... params) {
        return MessageFormat.format(errorMsg, params);
    }

    /**
     * 获取异常描述
     *
     * @param errorEnum errorEnum
     * @return 异常描述
     */
    public static String getExceptionDescription(Enum<?> errorEnum) {
        String exceptionDescription = null;
        try {
            Field descriptionField = errorEnum.getClass().getDeclaredField("description");
            descriptionField.setAccessible(true);
            exceptionDescription = (String) descriptionField.get(errorEnum);
        } catch (IllegalAccessException | NoSuchFieldException ignored) {
        }
        return exceptionDescription;
    }

}
