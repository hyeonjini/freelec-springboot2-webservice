package com.hyeonjin.admin.service.posts;

import com.hyeonjin.admin.domain.posts.PostsRepository;
import com.hyeonjin.admin.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {

    // @Autowired 안써도됨 -> 생성자로 주입받는 방식 사용
    // Lombok의 RequiredArgsConstructor가 생성자 자동 생성해줌
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }
}
