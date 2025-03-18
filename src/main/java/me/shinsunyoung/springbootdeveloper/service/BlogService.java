package me.shinsunyoung.springbootdeveloper.service;


import lombok.RequiredArgsConstructor;
import me.shinsunyoung.springbootdeveloper.domain.Article;
import me.shinsunyoung.springbootdeveloper.dto.AddArticleRequest;

import me.shinsunyoung.springbootdeveloper.dto.UpdateArticleRequest;
import me.shinsunyoung.springbootdeveloper.repository.BlogRepositoy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service // 빈으로 등록
public class BlogService {
    private final BlogRepositoy blogRepository;

    //블로그 글 추가 메서드
    public Article save(AddArticleRequest request) {
        return blogRepository.save(request.toEntity());
    }

    public Article findById(long id) {
        return blogRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("not found: " + id));
    }

    public List<Article> findAll(){
        return blogRepository.findAll(); //findAll()은 JPA지원메서드
    }


    @Transactional
    public Article update(long id, UpdateArticleRequest request){
        Article article = blogRepository.findById(id)
                .orElseThrow(() ->new IllegalArgumentException("not found: " + id));

        article.update(request.getTitle(), request.getContent());

        return article;
    }

    public void delete(long id){
        blogRepository.deleteById(id);
    }


}
