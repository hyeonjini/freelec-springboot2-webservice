package com.hyeonjin.admin.web;

import com.hyeonjin.admin.config.auth.LoginUser;
import com.hyeonjin.admin.config.auth.dto.SessionUser;
import com.hyeonjin.admin.service.posts.PostsService;
import com.hyeonjin.admin.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        model.addAttribute("posts", postsService.findAllDesc());

//        SessionUser user = (SessionUser) httpSession.getAttribute("user"); @LoginUser 구현을 통한 리팩토링

        if (user != null){
            model.addAttribute("userName", user.getName());
        }
        return "index";
        //머스테치 스타터 덕분에 컨트롤러에서 문자열을 반활할 때 앞의 경로와 뒤의 파일 확장자는 자동으로 지정됨
        // 앞의 경로는 src/main/resources/templates 로 파일 확장자는 .mustache로 붙음
        // return "index" -> src/main/resources/templates/index.mustache 반환
        // View Resolver가 처리하게 됨
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        return "posts-update";
    }
}
