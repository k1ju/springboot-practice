package me.shinsunyoung.springbootdeveloper.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter //게터 메서드 생성
@NoArgsConstructor(access= AccessLevel.PROTECTED) //접근제어자가 PROTECTED인 생성자생성
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    //생성자 위에 빌더어노테이션 사용하면 빌더 패턴으로 인스턴스 생성가능.
    //빌더패턴으로 인스턴스 생성 시 필드매핑이 보여서 가독성 증가.
    @Builder
    public Article(String title, String content){
        this.title = title;
        this.content = content;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
}
