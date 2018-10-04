package com.zz.pay.web.interceptor;//package com.zz.pay.web.com.zz.pay.interceptor;
//
//import java.io.PrintWriter;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.alibaba.fastjson.JSON;
//import com.zz.common.component.SpringContextHolder;
//import com.zz.common.model.Result;
//import com.zz.common.model.ResultSupport;
//import com.zz.pay.service.AppTokenService;
//
//public class TokenInterceptor implements HandlerInterceptor {
//	private static final Logger logger = LoggerFactory
//			.getLogger(TokenInterceptor.class);
//
//	@Override
//	public boolean preHandle(HttpServletRequest request,
//			HttpServletResponse response, Object handler) throws Exception {
//		AppTokenService appTokenService = SpringContextHolder
//				.getBean("appTokenServiceImpl");
//		String access_token = request.getParameter("access_token");
//		if (appTokenService.isValid(access_token)) {
//			return true;
//		}
//		Map<String, String> map = new HashMap<String, String>();
//		response.setContentType("application/json;charset=utf-8");
//		PrintWriter pw = response.getWriter();
//		map.put("ret", "-1");
//		map.put("msg", "token已经失效");
//		map.put("ts", String.valueOf(new Date().getTime()));
//		Result<Map<String, String>> resultSupport = new ResultSupport<Map<String, String>>();
//		resultSupport.setMsg("请求成功");
//		resultSupport.setCode("200");
//		resultSupport.setModel(map);
//		pw.print(JSON.toJSONString(resultSupport));
//		return false;
//	}
//
//	@Override
//	public void postHandle(HttpServletRequest request,
//			HttpServletResponse response, Object handler,
//			ModelAndView modelAndView) throws Exception {
//
//	}
//
//	@Override
//	public void afterCompletion(HttpServletRequest request,
//			HttpServletResponse response, Object handler, Exception ex)
//			throws Exception {
//
//	}
//
//	public static void main(String[] args) {
//
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("ret", "-1");
//		map.put("msg", "token已经失效");
//		map.put("ts", String.valueOf(new Date().getTime()));
//		Result<Map<String, String>> resultSupport = new ResultSupport<Map<String, String>>();
//		resultSupport.setMsg("请求成功");
//		resultSupport.setCode("200");
//		resultSupport.setModel(map);
//
//		logger.info("resultSupport:" + JSON.toJSONString(resultSupport));
//	}
//
//}
