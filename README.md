# yt-web

# 介绍
基于spring mvc的前后端交互通用框架，适用于前后端分离系统的后端与前端交互部分。

# 功能
* #### 自动包装返回体
    在controller层直接返回业务对象，返回给前端的json中自动包含异常码信息，其中异常码和描述信息的属性和默认值可在配置文件中指定。
* #### 统一异常处理
    在业务中抛出异常无需在controller中catch并做处理返回给前端。框架自动处理异常，并返回默认异常码和描述信息（可配置）。同时支持在业务抛出异常时指定异常码，和描述信息，通常配置在枚举中，抛出异常指定枚举值。
* #### 分页请求支持（可与yt-mybatis结合使用）
    自动设置分页信息到查询条件类中。可配置pageSize和pageNo参数属性。
* #### 记录请求日志
    可配置在请求返回时输出请求日志，包含请求参数（包含header）和返回结果信息。

# 使用教程
可参考```https://github.com/javawebers/yt-web-example```
* ##  maven引入yt-web
    ```xml
    <dependency>
        <groupId>com.github.javawebers</groupId>
        <artifactId>yt-web</artifactId>
        <version>1.3.1</version>
    </dependency>
    ```
* ##  启用yt-web
    在spring boot的启动类上加 ```@EnableYtWeb``` 如下：
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
***
通过上面配置即可```零配置```使用yt-web的所有功能，下面介绍通过配置修改默认行为。
***
* ##  自动包装返回体
    ```java
    // 自动包装返回体1
    @GetMapping("test1")
    public void testAutoPackage1() {
    }
    // 自动包装返回体2
    @GetMapping("test2")
    public Map testAutoPackage2() {
        Map result = new HashMap<>();
        result.put("key222", "value222");
        return result;
    }
    ```
    分别请求：  
    http://localhost:8080/test1  
    http://localhost:8080/test2  
    * ### 默认
    异常码可异常信息的属性名分别为“errorCode”和“message”，默认值为“0”和“操作成功”。
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
            "key222": "value222"
        }
    }
    ```
    * ### 指定返回体的key和value默认值
        * 实现接口类 ```com.github.yt.web.result.BaseResultConfig``` 可参照 ```com.github.yt.web.result.SimpleResultConfig```.  
        * 在```application.properties```中配置 ```yt.result.class```值指定实现类，如：
        ```properties
        yt.result.class=com.github.yt.web.result.SimpleResultConfig
    
        ```
        * 返回示例
        ```json
        {
            "myErrorCode": "200",
            "myMessage": "my操作成功",
            "myResult": {
                "key222": "value222"
            }
        }
        ```
    * ### 指定默认是否自动包装，默认为自动包装
    ```properties
    yt.result.packageResponseBody=false
    ```
    * ### 方法级别指定是否自动包装
    在类或者方法上加注解 ```@PackageResponseBody(false)``` ，来指定是否自动包装。
    优先级顺序 ```方法>类>全局```。
* ## 统一异常处理
    ```java
    // 统一异常处理，未知异常
    @GetMapping("test3")
    public void testUnknownException() {
        throw new RuntimeException("未知异常");
    }
    // 统一异常处理，已知异常（自定义异常）
    @GetMapping("test4")
    public void testBusinessException() {
        throw new BaseAccidentException(MyBusinessExceptionEnum.CODE_1001, "没有权限");
    }
    ```
    分别请求：  
    http://localhost:8080/test3  
    http://localhost:8080/test4  
    ```json
    {
        "errorCode": 1,
        "message": "系统异常"
    }
    ```
    ```json
    {
        "errorCode": 1001,
        "message": "业务异常，没有权限",
        "result": null
    }
    ```
* ## 分页请求支持
    ```java
    // 分页请求支持
    @GetMapping("test5")
    public Page testPage(Query query) {
        Page page = new Page();
        // 下面内容可结合yt-mybatis进行数据库查询
        // page.initKey()  
        page.initValue(query.takePageNo(), query.takePageSize(), 99, new ArrayList());
        return page;
    }
    ```
     分别请求：  
    http://localhost:8080/test5  
    http://localhost:8080/test5?pageNo=2&pageSize=50  
    ```json
    {
        "errorCode": 0,
        "message": "操作成功",
        "result": {
            "pageNo": 1,
            "pageSize": 10,
            "pageTotalCount": 99,
            "data": []
        }
    }
    ```
    ```json
    {
        "errorCode": 0,
        "message": "操作成功",
        "result": {
            "pageNo": 2,
            "pageSize": 50,
            "pageTotalCount": 99,
            "data": []
        }
    }
    ```
    * ### 分页相关配置
    application.properties中指定
    ```properties
    yt.page.pageNoName=myPageNo
    yt.page.pageSizeName=myPageSize
    ```
    请求： http://localhost:8080/test5?myPageNo=3&myPageSize=100  
    注：结合yt-mybatis后返回结果的pageNo、pageSize、pageTotalCount、data都可以指定。
    ```json
    {
        "errorCode": 0,
        "message": "操作成功",
        "result": {
            "pageNo": 3,
            "pageSize": 100,
            "pageTotalCount": 99,
            "data": []
        }
    }
    ```  
* ## 记录请求日志
    默认通过会打印一条请求日志。格式如下：
    ```text
    2019-03-29 11:27:22.652  INFO 4304 --- [nio-8080-exec-1] c.g.yt.web.log.RequestLogInterceptor     : {"classMethodName":"public com.github.yt.commons.query.Page demo.com.github.yt.web.YtWetDemoApplication.testPage(com.github.yt.commons.query.Query)","error":false,"headerParams":"{\"accept-language\":\"zh-CN,zh;q=0.9,en;q=0.8,zh-TW;q=0.7,es;q=0.6,en-US;q=0.5\",\"cookie\":\"_ga=GA1.1.1696937153.1538101556; jenkins-timestamper-offset=-28800000\",\"host\":\"localhost:8080\",\"upgrade-insecure-requests\":\"1\",\"connection\":\"keep-alive\",\"dnt\":\"1\",\"cache-control\":\"max-age=0\",\"accept-encoding\":\"gzip, deflate, br\",\"user-agent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36\",\"accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\"}","invokingTime":257,"ipAddress":"0:0:0:0:0:0:0:1","requestTime":"2019-03-29 11:27:22.284","responseBody":"{\"errorCode\":0,\"message\":\"操作成功\",\"result\":{\"pageNo\":3,\"pageSize\":100,\"pageTotalCount\":99,\"data\":[]}}","servletPath":"/test5","urlParams":"{\"myPageNo\":[\"3\"],\"myPageSize\":[\"100\"]}","userAgent":"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36"}    
    ```
    可结合elk对```com.github.yt.web.log.RequestLogInterceptor```日志进行收集，json信息格式如下：
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



