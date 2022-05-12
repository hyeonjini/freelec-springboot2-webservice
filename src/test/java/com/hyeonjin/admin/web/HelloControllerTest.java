package com.hyeonjin.admin.web;

import com.hyeonjin.admin.config.auth.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
@RunWith(SpringRunner.class)
// 테스트를 진행할 때 JUnit에 내장된 실행자 외에 다른 실행자(SpringRunner) 실행
// 스프링 부트 테스트와 JUnit 사이의 연결자 역할

@WebMvcTest(
        controllers = HelloController.class,
        excludeFilters = {
                @ComponentScan.Filter( // 스캔 대상에서 SecurityConfig 제거
                        type= FilterType.ASSIGNABLE_TYPE,
                        classes = SecurityConfig.class
                )
        }
)
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
    @WithMockUser(roles="USER")
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
    @WithMockUser(roles="USER")
    @Test
    public void helloDto가_리턴된다() throws Exception{
        String name = "hello";
        int amount = 1000;

        mvc.perform(
                    get("/hello/dto")
                            .param("name", name) // API를 테스트할 때 사용될 요청 파라미터 설정 (String만 허용)
                            .param("amount", String.valueOf(amount)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name", is(name))) //JSON 응답값을 필드별로 검증할 수 있는 메서드, $를 기준으로 필드명 명시
                    .andExpect(jsonPath("$.amount", is(amount)));

    }
}
