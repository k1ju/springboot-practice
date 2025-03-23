package me.shinsunyoung.springbootdeveloper.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.shinsunyoung.springbootdeveloper.domain.Article;

@NoArgsConstructor // 기본생성자 추가
@AllArgsConstructor // 모든 필드값을 파라미터로 받는 생성자 추가
@Getter
public class AddArticleRequest {

    private String title;
    private String content;

    public Article toEntity(String author){ // 빌더패턴으로 DTO를 엔티티로 만들어주는 메서드
        return Article.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }

}
