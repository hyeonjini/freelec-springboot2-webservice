package com.hyeonjin.admin.service.posts;

import com.hyeonjin.admin.domain.posts.Posts;
import com.hyeonjin.admin.domain.posts.PostsRepository;
import com.hyeonjin.admin.web.dto.PostsListResponseDto;
import com.hyeonjin.admin.web.dto.PostsResponseDto;
import com.hyeonjin.admin.web.dto.PostsSaveRequestDto;
import com.hyeonjin.admin.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    public PostsResponseDto findById(Long id){
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc()
                .stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }
}
