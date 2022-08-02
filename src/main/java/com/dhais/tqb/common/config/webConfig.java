package com.dhais.tqb.common.config;

import com.dhais.tqb.common.interceptor.JWTInterceptor;
import com.dhais.tqb.common.utils.PropertiesUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

import static com.dhais.tqb.common.Constants.JWT_ENABLED;


@Configuration
public class webConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //添加拦截器
        List<String> excludePathList = new ArrayList<>();
        excludePathList.add("/swagger-resources/**");
        excludePathList.add("/webjars/**");
        excludePathList.add("/v2/**");
        excludePathList.add("/swagger-ui.html/**");
        excludePathList.add("/static/**");
        excludePathList.add("/user/login");
        if (!PropertiesUtil.getBoolean(JWT_ENABLED, true)) {
            excludePathList.add("/**");
        }
        registry.addInterceptor(new JWTInterceptor())
                .addPathPatterns("/**")
                //放掉某些特定不需要校验token的路由
                .excludePathPatterns(excludePathList.toArray(new String[]{}));

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/static/images/**")
                .addResourceLocations("file:/root/javaService/XD/fileUpload/");
    }

                /*
                因为配置了拦截器，拦截器会先执行，导致跨域失败，所以不把跨域配置写到拦截器，而是写到过滤器
                */
//    public void addCorsMappings(CorsRegistry registry) {
//        //添加映射路径
//        registry.addMapping("/**")
//                //放行哪些原始域
//                .allowedOrigins("*")
//                //是否发送Cookie信息
//                .allowCredentials(true)
//                //放行哪些原始域(请求方式)
//                .allowedMethods("GET","POST", "PUT", "DELETE","OPTIONS")
//                //放行哪些原始域(头部信息)
//                .allowedHeaders("*")
//                //暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）
//                .exposedHeaders("Header1", "Header2");
//    }
}
