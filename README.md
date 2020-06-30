# 5分钟上手yt-web！！！

# 介绍
基于spring mvc的前后端交互通用框架，适用于前后端分离系统的后端与前端交互部分。

# 功能
* #### 自动包装返回体
    在 controller 层直接返回业务对象，返回给前端的json中自动包含异常码信息，其中异常码和描述信息的属性和默认值可在配置文件中指定。
* #### 统一异常处理
    在业务中抛出异常无需在 controller 中 catch 并做处理返回给前端。框架自动处理异常，并返回默认异常码和描述信息（可配置）。同时支持在业务抛出异常时指定异常码，和描述信息，通常配置在枚举中，抛出异常指定枚举值。
* #### 分页请求支持（可与yt-mybatis结合使用）
    自动设置分页信息到查询条件类中。可配置pageSize和pageNo参数属性。
* #### 记录请求日志
    可配置在请求返回时输出请求日志，包含请求参数（包含header）和返回结果信息。

# 使用教程
* ##  maven引入yt-web
在`https://mvnrepository.com/artifact/com.github.javawebers/yt-web`找到最新版引入
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
        <version>2.2.0.RELEASE</version>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-aop</artifactId>
        <version>2.2.0.RELEASE</version>
    </dependency>
    <dependency>
        <groupId>com.github.javawebers</groupId>
        <artifactId>yt-web</artifactId>
        <version>1.5.1</version>
    </dependency>
</dependencies>
```
* ##  启用yt-web
* 在spring boot的启动类上加 `@EnableYtWeb` 注解，如下：
```java
@SpringBootApplication
@RestController
@EnableYtWeb
public class YtWetDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(YtWetDemoApplication.class, args);
    }
}
```

* 通过上面配置即可`零配置`使用yt-web的所有功能，下面介绍通过配置修改默认行为。

* ## 自动包装返回体
```java
@GetMapping("autoPackageVoid")
public void autoPackageVoid() {

}

@GetMapping("autoPackageResult")
public Map autoPackageResult() {
    Map result = new HashMap();
    result.put("key1", "value1");
    return result;
}
```

* 分别请求：  
`  http://localhost:8080/autoPackageVoid 
   http://localhost:8080/autoPackageResult  
`  
* 异常码和异常信息的属性名分别为 `errorCode` 和 `message` ，默认值为 `0` 和 `操作成功` 。
```json
{
    "errorCode": 0,
    "message": "操作成功",
    "result": null
}
```
```json
{
    "errorCode": 0,
    "message": "操作成功",
    "result": {
        "key1": "value1"
    }
}
```

* ### 业务发生异常请求
* 定义异常枚举。枚举需要有 `message` 字段，统一异常处理会将 `message` 字段内容返回给前端
```java
package com.github.yt.web.exception;

/**
 * 异常枚举
 *
 * @author liujiasheng
 */
public enum MyBusinessExceptionEnum {
    // business
    CODE_1001("业务异常，{0}"),
    CODE_1002("参数错误:{0}"),
    CODE_1003("参数错误"),
    CODE_1004("已知异常"),
    ;

    public String message;
    public String description;

    MyBusinessExceptionEnum(String message) {
        this.message = message;
    }

    MyBusinessExceptionEnum(String message, String description) {
        this.message = message;
        this.description = description;
    }
}
```

* 业务中抛出异常。抛出的非 `BaseAccidentException` 返回给前端的异常码是 `1` ，异常信息是 `系统异常`。
抛出`BaseAccidentException` ，返回的异常码是枚举的后面数字部分，异常信息是枚举的 `message` 内容，可以动态传参数，如：{0}
```java
@GetMapping("autoPackageException")
public void autoPackageException() {
    throw new RuntimeException("未知异常");
}

@GetMapping("autoPackageException2")
public void autoPackageException2() {
    throw new BaseAccidentException(MyBusinessExceptionEnum.CODE_1004);
}
``` 
* 分别请求：  
`  http://localhost:8080/autoPackageException  
   http://localhost:8080/autoPackageException2  
`
```json
{
    "errorCode": 1,
    "message": "系统异常",
    "result": null
}
```
```json
{
    "errorCode": 1004,
    "message": "已知异常",
    "result": null
}
```
* ### 异常栈返回给前端
* 开发和测试阶段可开启，方便排查问题
* 修改配置 `application.properties`
```properties
yt.result.returnStackTrace=true
```
* 请求：`http://localhost:8080/autoPackageException`
```json
{
    "errorCode": 1,
    "message": "系统异常",
    "result": null,
    "stackTrace": "java.lang.RuntimeException: 未知异常\r\n\tat com.github.yt.web.example.controller.ExampleController.autoPackageException(ExampleController.java:29)\r\n\tat com.github.yt.web.example.controller.ExampleController$$FastClassBySpringCGLIB$$775a7c87.invoke(<generated>)\r\n\tat org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\r\n\tat org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:769)\r\n\tat org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\r\n\tat org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:747)\r\n\tat org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:88)\r\n\tat com.github.yt.web.query.QueryControllerAspect.around(QueryControllerAspect.java:85)\r\n\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n\tat sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\r\n\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\r\n\tat java.lang.reflect.Method.invoke(Method.java:483)\r\n\tat org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethodWithGivenArgs(AbstractAspectJAdvice.java:644)\r\n\tat org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethod(AbstractAspectJAdvice.java:633)\r\n\tat org.springframework.aop.aspectj.AspectJAroundAdvice.invoke(AspectJAroundAdvice.java:70)\r\n\tat org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:175)\r\n\tat org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:747)\r\n\tat org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:93)\r\n\tat org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\r\n\tat org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:747)\r\n\tat org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:689)\r\n\tat com.github.yt.web.example.controller.ExampleController$$EnhancerBySpringCGLIB$$8a4d2151.autoPackageException(<generated>)\r\n\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n\tat sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\r\n\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\r\n\tat java.lang.reflect.Method.invoke(Method.java:483)\r\n\tat org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:190)\r\n\tat org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:138)\r\n\tat org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:106)\r\n\tat org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:888)\r\n\tat org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:793)\r\n\tat org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87)\r\n\tat org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1040)\r\n\tat org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:943)\r\n\tat org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1006)\r\n\tat org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:898)\r\n\tat javax.servlet.http.HttpServlet.service(HttpServlet.java:634)\r\n\tat org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:883)\r\n\tat javax.servlet.http.HttpServlet.service(HttpServlet.java:741)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:231)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\r\n\tat org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:53)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\r\n\tat com.github.yt.web.log.RequestLogFilter.doFilter(RequestLogFilter.java:36)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\r\n\tat org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:100)\r\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\r\n\tat org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:93)\r\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\r\n\tat org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:201)\r\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\r\n\tat org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:202)\r\n\tat org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:96)\r\n\tat org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:526)\r\n\tat org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:139)\r\n\tat org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:92)\r\n\tat org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:74)\r\n\tat org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:343)\r\n\tat org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:367)\r\n\tat org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:65)\r\n\tat org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:860)\r\n\tat org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1591)\r\n\tat org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49)\r\n\tat java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)\r\n\tat java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)\r\n\tat org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)\r\n\tat java.lang.Thread.run(Thread.java:745)\r\n"
}
```
  
* ### 指定返回体的 errorCode 和 message 默认值
* 实现接口类 `com.github.yt.web.result.BaseResultConfig`
```java
package com.github.yt.web.example.result;

import com.github.yt.web.result.BaseResultConfig;

/**
 * http请求返回体的默认实现
 *
 * @author liujiasheng
 */
public class BusinessResultConfig implements BaseResultConfig {

    @Override
    public String getErrorCodeField() {
        return "code";
    }

    @Override
    public String getMessageField() {
        return "msg";
    }

    @Override
    public String getResultField() {
        return "data";
    }

    @Override
    public String getMoreResultField() {
        return "moreResult";
    }

    @Override
    public Object getDefaultSuccessCode() {
        return "200";
    }

    @Override
    public Object getDefaultSuccessMessage() {
        return "操作成功";
    }

    @Override
    public Object getDefaultErrorCode() {
        return "error";
    }

    @Override
    public Object getDefaultErrorMessage() {
        return "系统异常";
    }

    @Override
    public Object convertErrorCode(Object errorCode) {
        return errorCode;
    }
}
```
* 在 `application.properties` 中配置 `yt.result.resultConfigClass` 值指定实现类
```properties
yt.result.resultConfigClass=com.github.yt.web.example.result.BusinessResultConfig
```

* 返回示例
```json
{
    "code": "200",
    "msg": "操作成功",
    "data": {
        "key1": "value1"
    }
}
```

* ### 指定默认是否自动包装，默认为自动包装
```properties
yt.result.packageResponseBody=false
```
* ### 方法和类级别指定是否自动包装
* 在类或者方法上加注解 `@PackageResponseBody` ，来指定是否自动包装。
* 优先级顺序 `方法>类>全局`。

* ## 分页请求参数自动设值支持
* 实现 `PageQuery`
```java
package com.github.yt.web.query;

import com.github.yt.commons.query.PageQuery;

public class WebQuery implements PageQuery<WebQuery> {

    protected Integer pageNo;
    protected Integer pageSize;

    @Override
    public WebQuery makePageNo(Integer pageNo) {
        this.pageNo = pageNo;
        return this;
    }

    @Override
    public WebQuery makePageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    @Override
    public Integer takePageNo() {
        return pageNo;
    }

    @Override
    public Integer takePageSize() {
        return pageSize;
    }
}
```
* 示例
```java
@GetMapping("pageQuery")
public Map pageQuery(WebQuery query) {
    Map<String, Object> result = new HashMap<>();
    result.put("pageNo", query.takePageNo());
    result.put("pageSize", query.takePageSize());
    return result;
}
```
* 分别请求：  
`  http://localhost:8080/pageQuery 
   http://localhost:8080/pageQuery?pageNo=2&pageSize=50
`
```json
{
    "errorCode": 0,
    "message": "操作成功",
    "result": {
        "pageNo": 1,
        "pageSize": 10
    }
}
```
```json
{
    "errorCode": 0,
    "message": "操作成功",
    "result": {
        "pageNo": 2,
        "pageSize": 50
    }
}
```
* ### 分页相关配置
* `application.properties` 中指定
```properties
yt.page.pageNoName=myPageNo
yt.page.pageSizeName=myPageSize
```
* 请求： http://localhost:8080/pageQuery?myPageNo=3&myPageSize=100  
* 注：结合 `yt-mybatis` 后返回结果的 `pageNo、pageSize、pageTotalCount、data` 都可以指定。可以将此处的 `WebQuery` 替换为 `Query` 
```json
{
    "errorCode": 0,
    "message": "操作成功",
    "result": {
        "pageNo": 3,
        "pageSize": 100
    }
}
```  
* ## 记录请求日志
* 默认请求会打印一条请求日志。格式如下：
```text
2019-03-29 11:27:22.652  DEBUG 4304 --- [nio-8080-exec-1] c.g.yt.web.log.RequestLogInterceptor     : {"classMethodName":"public com.github.yt.commons.query.Page demo.com.github.yt.web.YtWetDemoApplication.testPage(com.github.yt.commons.query.Query)","error":false,"headerParams":"{\"accept-language\":\"zh-CN,zh;q=0.9,en;q=0.8,zh-TW;q=0.7,es;q=0.6,en-US;q=0.5\",\"cookie\":\"_ga=GA1.1.1696937153.1538101556; jenkins-timestamper-offset=-28800000\",\"host\":\"localhost:8080\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"dnt\":\"1\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}","invokingTime":257,"ipAddress":"0:0:0:0:0:0:0:1","requestTime":"2019-03-29 11:27:22.284","responseBody":"{\"errorCode\":0,\"message\":\"操作成功\",\"result\":{\"pageNo\":3,\"pageSize\":100,\"pageTotalCount\":99,\"data\":[]}}","servletPath":"/test5","urlParams":"{\"myPageNo\":[\"3\"],\"myPageSize\":[\"100\"]}","userAgent":"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36"}    
```
* 可结合elk对```com.github.yt.web.log.RequestLogInterceptor```日志进行收集，json信息格式如下：
```json
{
    "classMethodName": "public com.github.yt.commons.query.Page demo.com.github.yt.web.YtWetDemoApplication.testPage(com.github.yt.commons.query.Query)",
    "error": false,
    "headerParams": "{\"accept-language\":\"zh-CN,zh;q=0.9,en;q=0.8,zh-TW;q=0.7,es;q=0.6,en-US;q=0.5\",\"cookie\":\"_ga=GA1.1.1696937153.1538101556; jenkins-timestamper-offset=-28800000\",\"host\":\"localhost:8080\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"dnt\":\"1\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}",
    "invokingTime": 257,
    "ipAddress": "0:0:0:0:0:0:0:1",
    "requestTime": "2019-03-29 11:27:22.284",
    "responseBody": "{\"errorCode\":0,\"message\":\"操作成功\",\"result\":{\"pageNo\":3,\"pageSize\":100,\"pageTotalCount\":99,\"data\":[]}}",
    "servletPath": "/test5",
    "urlParams": "{\"myPageNo\":[\"3\"],\"myPageSize\":[\"100\"]}",
    "userAgent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36"
}
```
* ### 请求日志相关配置
application.properties中指定
```properties
## 是否记录请求日志
yt.request.requestLog=true
## 是否记录post请求body中的内容
yt.request.requestLogBody=false
```



