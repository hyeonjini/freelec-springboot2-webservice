package com.hyeonjin.admin.config;

// @LoginUser를 사용하기 위한 환경 구성 (@interface LoginUser + LoginUserArgumentResolver)
// 생성된 LoginUserArgumentResolver가 스프링에서 인식될 수 있도록 WebMvcConfigurer에 추가

import com.hyeonjin.admin.config.auth.LoginUserArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final LoginUserArgumentResolver loginUserArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        // HandlerMethodArgument는 항상 WebMvcConfigurer의 addArgumentResolvers()를 통해 추가해야함
        argumentResolvers.add(loginUserArgumentResolver);
    }
}
