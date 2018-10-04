package com.zz.pay.web.interceptor;//package com.zz.pay.web.com.zz.pay.interceptor;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.zz.common.util.encrypt.ApiInterfaceValid;
//
//public class AuthorizationInterceptor implements HandlerInterceptor {
//
//	@Override
//	public boolean preHandle(HttpServletRequest httpServletRequest,
//			HttpServletResponse httpServletResponse, Object o) throws Exception {
//		// WebContext.setRequest(httpServletRequest);
//		// WebContext.setResponse(httpServletResponse);
//		return ApiInterfaceValid.checkPublicParamters(httpServletRequest);
//	}
//
//	@Override
//	public void postHandle(HttpServletRequest httpServletRequest,
//			HttpServletResponse httpServletResponse, Object o,
//			ModelAndView modelAndView) throws Exception {
//
//	}
//
//	@Override
//	public void afterCompletion(HttpServletRequest httpServletRequest,
//			HttpServletResponse httpServletResponse, Object o, Exception e)
//			throws Exception {
//
//	}
//}
