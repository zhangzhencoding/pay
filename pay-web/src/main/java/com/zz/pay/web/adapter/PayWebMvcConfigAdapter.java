package com.zz.pay.web.adapter;

import com.zz.pay.web.interceptor.AliPayInterceptor;
import com.zz.pay.web.interceptor.CharacterEncodInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class PayWebMvcConfigAdapter extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(new CharacterEncodInterceptor()).addPathPatterns("/unionpay/**");
        registry.addInterceptor(new AliPayInterceptor()).addPathPatterns("/alipay/**");
        super.addInterceptors(registry);
    }
}
