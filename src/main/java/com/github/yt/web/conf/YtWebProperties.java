package com.github.yt.web.conf;

import com.github.yt.web.result.BaseResultConfig;
import com.github.yt.web.result.SimpleResultConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author sheng
 */
@Component
@ConfigurationProperties("yt")
@Data
public class YtWebProperties {

	private Web web = new Web();
	private Page page = new Page();
	private Request request = new Request();
	private Result result = new Result();

	@Data
	public static class Web {
		/**
		 * 是否开启 yt-web
		 */
		private boolean enable = true;
	}

	@Data
	public static class Request {
		/**
		 * 是否记录请求日志
		 */
		private boolean requestLog = true;

		private boolean autoSetPageInfo = false;
	}

	@Data
	public static class Result {

		/**
		 * 始终包装异常，优先级高于 packageResponseBody
		 * true：发生异常不判断 全局 packageResponseBody 和 @PackageResponseBody，始终包装异常
		 * false：发生异常判断 全局 packageResponseBody 和 @PackageResponseBody 来确定是否包装异常
		 */
		private boolean alwaysPackageException = false;

		/**
		 * 是否自动包装返回体
		 * 默认为 true
		 */
		private boolean packageResponseBody = true;
		/**
		 * 是否返回异常栈信息
		 */
		private boolean returnStackTrace = false;

		/**
		 * 业务异常返回码
		 */
		private int errorState = 500;

		/**
		 * 返回体配置类
		 */
		private Class<? extends BaseResultConfig> resultConfigClass = SimpleResultConfig.class;
		/**
		 * 排除返回值类型
		 */
		private Class<?>[] ignorePackageResultTypes;

	}

	@Data
	public static class Page {

		/**
		 * 是否转换 page， 将 page 对象转换为 map
		 */
		private boolean convertPage = false;
		/**
		 * 页码
		 */
		private String pageNoName = "pageNo";
		/**
		 * 每页记录数
		 */
		private String pageSizeName = "pageSize";
		/**
		 * 总条数
		 */
		private String pageTotalCountName = "totalCount";
		/**
		 * 数据字段
		 */
		private String pageDataName = "data";

	}

}

