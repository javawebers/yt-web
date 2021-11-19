package com.github.yt.web.query;

import com.github.yt.web.conf.YtWebProperties;
import com.github.yt.web.util.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * 分页增强
 * 支持可配置分页页码和每页条数
 * 指定如下两个参数
 * 默认是pageNo和pageSize
 * yt.page.pageNoName
 * yt.page.pageSizeName
 *
 * @author liujiasheng
 */
@Aspect
@Order(1000)
@Slf4j
public class QueryControllerAspect {

    private static final Set<Method> METHOD_SET = new HashSet<>();
    private static final Map<Method, Integer> QUERY_METHOD_MAP = new HashMap<>();

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.GetMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PutMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.DeleteMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void allController() {
    }

    @Around(value = "allController() ")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
            Object target = proceedingJoinPoint.getTarget();
            // 获取实现类的方法
            Method currentMethod = target.getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
            // 每个方法只遍历一次
            if (!METHOD_SET.contains(currentMethod)) {
                METHOD_SET.add(currentMethod);
                Class<?>[] classes = methodSignature.getParameterTypes();
                for (int i = 0; i < classes.length; i++) {
                    if (PageQuery.class.isAssignableFrom(classes[i])) {
                        QUERY_METHOD_MAP.put(currentMethod, i);
                    }
                }
            }
            if (QUERY_METHOD_MAP.containsKey(currentMethod)) {
                RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
                HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(requestAttributes)).getRequest();
                YtWebProperties ytWebProperties = SpringContextUtils.getBean(YtWebProperties.class);
                String pageNoStr = request.getParameter(ytWebProperties.getPage().getPageNoName());
                String pageSizeStr = request.getParameter(ytWebProperties.getPage().getPageSizeName());
                PageQuery<?> pageQuery = (PageQuery<?>) proceedingJoinPoint.getArgs()[QUERY_METHOD_MAP.get(currentMethod)];
                int pageSizeNum;
                try {
                    pageSizeNum = (pageSizeStr == null || pageSizeStr.isEmpty()) ? 10 : Integer.parseInt(pageSizeStr);
                } catch (NumberFormatException e) {
                    pageSizeNum = 10;
                }
                int pageNoNum;
                try {
                    pageNoNum = (pageNoStr == null || pageNoStr.isEmpty()) ? 1 : Integer.parseInt(pageNoStr);
                } catch (NumberFormatException e) {
                    pageNoNum = 1;
                }
                pageQuery.makePageNo(pageNoNum);
                pageQuery.makePageSize(pageSizeNum);
            }
        } catch (Exception e) {
            log.warn("设置 Query 的 pageNo、pageSize 异常", e);
        }
        return proceedingJoinPoint.proceed();
    }
}
