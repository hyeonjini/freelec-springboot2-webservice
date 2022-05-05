package com.hyeonjin.admin.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

// Posts 클래스로 Database를 접근하게 해줌
// Entity클래스와 기본 Entity Repository는 반드시 함께 위치
public interface PostsRepository extends JpaRepository<Posts, Long> { //<Entity클래스, PK타입> -> CRUD 메서드 자동 생성
}

