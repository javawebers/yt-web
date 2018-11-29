package com.github.yt.web.result;

/**
 * 异常工具类
 * @author liujiasheng
 */
public class ExceptionUtils {

    /**
     * 已知异常转换
     * @param e e
     * @return
     */
    public static Exception knownException(Exception e) {
//        if (e instanceof ConstraintViolationException) {
//            // controller校验异常拦截，字段参数异常拦截
//            // ConstraintViolationException是ViolationException的子类
//            ConstraintViolationException cve = (ConstraintViolationException)e;
//            Set<ConstraintViolation<?>> cvSet = cve.getConstraintViolations();
//            List<String> errorMessageList = cvSet.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
//            return new BaseAccidentException(CoreExceptionEnum.CODE_11, e, StringUtils.join(errorMessageList, ","));
//        }  else if (e instanceof BindException) {
//            // controller校验异常拦截，类参数异常拦截
//            BindException be = (BindException)e;
//            List<ObjectError> errorList = be.getAllErrors();
//            List<String> errorMessageList = errorList.stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList());
//            return new BaseAccidentException(CoreExceptionEnum.CODE_11, e, StringUtils.join(errorMessageList, ","));
//        }  else if (e instanceof MethodArgumentNotValidException) {
//            // controller校验异常拦截，RequestBody校验失败
//            MethodArgumentNotValidException manve = (MethodArgumentNotValidException)e;
//            List<ObjectError> errorList = manve.getBindingResult().getAllErrors();
//            List<String> errorMessageList = errorList.stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList());
//            return new BaseAccidentException(CoreExceptionEnum.CODE_11, e, StringUtils.join(errorMessageList, ","));
//        }  else if (e instanceof HttpMessageNotReadableException) {
//            // controller校验异常拦截，RequestBody json转换异常，还没到校验步骤
//            return new BaseAccidentException(CoreExceptionEnum.CODE_12, e, e.getMessage());
//        }  else if (e instanceof MethodArgumentTypeMismatchException) {
//            // 參數異常，RequestBody
//            MethodArgumentTypeMismatchException se = (MethodArgumentTypeMismatchException)e;
//            return new BaseAccidentException(CoreExceptionEnum.CODE_11,  e, "参数:" + se.getName() + ",值:" + se.getValue());
//        } else if (e instanceof DuplicateKeyException) {
//            // 数据库异常，记录已存在
//            return new BaseAccidentException(CoreExceptionEnum.CODE_21, e);
//        } else if (e instanceof DataIntegrityViolationException) {
//            // 数据库异常，数据完整性异常(字段不为空，数据长度限制等)
//            return new BaseAccidentException(CoreExceptionEnum.CODE_22, e);
//        } else if (e instanceof BadSqlGrammarException) {
//            // 数据库异常，脚本语法异常(字段不存在等)
//            return new BaseAccidentException(CoreExceptionEnum.CODE_23, e);
//        }
        return e;
    }

}
