package com.zz.pay.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * @description:
 */
@Component
@EnableWebMvc
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	private static final Logger logger = LoggerFactory
			.getLogger(SwaggerConfig.class);
	
	@Value("${swagger.flag}")
	public String swaggerFlag;

	@Bean
	public Docket createRestApi() {
		logger.info("=============================swaggerFlag============="+swaggerFlag);
		
		
		if ("true".equals(swaggerFlag)) {
			return new Docket(DocumentationType.SWAGGER_2)
					.apiInfo(apiInfo())
					.select()
					.apis(RequestHandlerSelectors
							.basePackage("com.zz.pay.web.com.zz.pay.controller"))
					.paths(PathSelectors.any()).build();
		}
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors
						.basePackage("com.zz.pay.web.xxx.com.zz.pay.filter"))
				.paths(PathSelectors.any()).build();
	}

	private ApiInfo apiInfo() {
		// if ("true".equals(swaggerFlag)) {
		return new ApiInfoBuilder().title("验签及支付接口RESTful APIs")
				.termsOfServiceUrl("www.xxxcom").contact("by-shanlin")
				.version("1.1").build();
		// }
		// return null;
	}
}
