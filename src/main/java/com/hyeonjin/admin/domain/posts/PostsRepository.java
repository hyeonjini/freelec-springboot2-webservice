package com.hyeonjin.admin.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// Posts 클래스로 Database를 접근하게 해줌
// Entity클래스와 기본 Entity Repository는 반드시 함께 위치
public interface PostsRepository extends JpaRepository<Posts, Long> { //<Entity클래스, PK타입> -> CRUD 메서드 자동 생성

    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();
}

