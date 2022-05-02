package com.hyeonjin.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // JPA Auditing 활성화
@SpringBootApplication
// 스프링 부트의 자동 설정, 수프링 Bean 읽기와 생성 모두 자동으로 설정
// @SpringBootApplication이 있는 위치부터 설정을 읽어가기 때문에 이 클래스는 항상 프로젝트의 최상단에 위치
public class Application {
    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
        // WAS 실행, 내장 WAS
        // 외부에 별도 WAS를 두지 않고 애플리케이션을 실행할 때 내부에서 WAS 실행
        // 톰캣을 설치할 필요가 없음
        // Jar파일로 실항만 하면 됨
    }
}
