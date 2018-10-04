package com.zz.pay.web.filter;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class CORSFilter extends OncePerRequestFilter {
	private static Logger logger = LoggerFactory
			.getLogger(CORSFilter.class);
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		if (request.getHeader("Access-Control-Request-Method") != null
				&& "OPTIONS".equals(request.getMethod())) {
			logger.trace("Sending Header....");
			// CORS "pre-flight" request
			response.addHeader("Access-Control-Allow-Methods",
					"GET, POST, PUT, DELETE");
			response.addHeader("Access-Control-Allow-Headers", "Authorization");
			response.addHeader("Access-Control-Allow-Headers", "Content-Type");
			response.addHeader("Access-Control-Max-Age", "1");
			// 响应头 请按照自己需求添加。
			response.addHeader("Access-Control-Allow-Headers",
					"x-requested-with,content-type");
		}
		String servletPath = request.getServletPath();
		logger.info("====================servletPath===================="+servletPath);
		Map<?, ?> requestParams = request.getParameterMap();
		logger.info("过滤器拦截请求，支付宝支付结果通知" + JSON.toJSONString(requestParams));
		filterChain.doFilter(request, response);
	}

}
