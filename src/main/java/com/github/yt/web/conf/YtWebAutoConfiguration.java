package com.github.yt.web.conf;

import com.github.yt.web.exception.WebExceptionConverter;
import com.github.yt.web.query.QueryControllerAspect;
import com.github.yt.web.result.BaseExpandResultBodyHandler;
import com.github.yt.web.result.KnowExceptionConverter;
import com.github.yt.web.result.PackageResponseBodyAdvice;
import com.github.yt.web.result.ValidatorExceptionConverter;
import com.github.yt.web.util.SpringContextUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * starter
 *
 * @author sheng
 */
@Configuration
@EnableConfigurationProperties(YtWebProperties.class)
@ConditionalOnProperty(name = "yt.web.enable", matchIfMissing = true)
public class YtWebAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public PackageResponseBodyAdvice packageResponseBodyAdvice(YtWebProperties ytWebProperties,
			List<BaseExpandResultBodyHandler> expandResultBodyHandlerList,
			List<WebExceptionConverter> exceptionConverterList) {
		return new PackageResponseBodyAdvice(ytWebProperties, expandResultBodyHandlerList,
				exceptionConverterList);
	}
	@Bean
	@ConditionalOnMissingBean
	public YtWebMvcConfigurer ytWebMvcConfigurer() {
		return new YtWebMvcConfigurer();
	}
	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(name = "yt.request.auto-set-page-info")
	public QueryControllerAspect queryControllerAspect() {
		return new QueryControllerAspect();
	}
	@Bean
	@ConditionalOnMissingBean
	public ValidatorExceptionConverter validatorExceptionConverter() {
		return new ValidatorExceptionConverter();
	}

	@Bean
	@ConditionalOnMissingBean
	public KnowExceptionConverter knowExceptionConverter() {
		return new KnowExceptionConverter();
	}

	@Bean
	@ConditionalOnMissingBean
	public SpringContextUtils ytWebSpringContextUtils() {
		return new SpringContextUtils();
	}



}
