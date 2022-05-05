package com.hyeonjin.admin.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(){
        return "index";
        //머스테치 스타터 덕분에 컨트롤러에서 문자열을 반활할 때 앞의 경로와 뒤의 파일 확장자는 자동으로 지정됨
        // 앞의 경로는 src/main/resources/templates 로 파일 확장자는 .mustache로 붙음
        // return "index" -> src/main/resources/templates/index.mustache 반환
        // View Resolver가 처리하게 됨
    }
}
