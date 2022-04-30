package com.hyeonjin.admin.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
// 테스트를 진행할 때 JUnit에 내장된 실행자 외에 다른 실행자(SpringRunner) 실행
// 스프링 부트 테스트와 JUnit 사이의 연결자 역할

@WebMvcTest(controllers = HelloController.class)
// 여러 스프링 테스트 어노테이션 중 Web(Spring MVC)에 집중할 수 있는 어노테이션
// @Controller, @ControllerAdvice 사용 가능
// @Service, @Component, @Repository 등은 사용할 수 없음
// Controller만 사용하기 때문에 선언
public class HelloControllerTest {

    @Autowired
    // 스프링이 관리하는 빈 주입
    private MockMvc mvc;
    // 웹 API를 테스트할 때 사용
    // 스프링 MVC 테스트의 시작지점
    // HTTP GET, POST 등에 대한 API 테스트 가능
    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello"))
                // MockMvc를 통해 /hello 주소로 HTTP GET 요청
                .andExpect(status().isOk())
                // mvc.perform의 결과를 검증
                // HTTP Header의 Status를 검증 (ex. 200, 404, 500)
                .andExpect(content().string(hello));
                // mvc.perform의 결과를 검증
                // 응답 본문의 내용을 검증

    }
}
