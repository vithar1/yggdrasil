package com.dev.yggdrasil.service;

import com.dev.yggdrasil.domain.Article;
import com.dev.yggdrasil.domain.Comment;
import com.dev.yggdrasil.domain.User;
import com.dev.yggdrasil.model.dto.ArticleDTO;
import com.dev.yggdrasil.repos.ArticleRepository;
import com.dev.yggdrasil.repos.CommentRepository;
import com.dev.yggdrasil.repos.UserRepository;
import com.dev.yggdrasil.util.NotFoundException;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public List<ArticleDTO> findAll() {
        final List<Article> articles = articleRepository.findAll(Sort.by("id"));
        return articles.stream()
                .map(article -> mapToDTO(article, new ArticleDTO()))
                .toList();
    }

    public ArticleDTO get(final Long id) {
        return articleRepository.findById(id)
                .map(article -> mapToDTO(article, new ArticleDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ArticleDTO articleDTO) {
        User user = userService.getCurrentUser();
        final Article article = new Article();
        mapToEntity(articleDTO, article);
        article.setUser(user);
        return articleRepository.save(article).getId();
    }

    public void update(final Long id, final ArticleDTO articleDTO) {
        final Article article = articleRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(articleDTO, article);
        articleRepository.save(article);
    }

    public void delete(final Long id) {
        articleRepository.deleteById(id);
    }

    private ArticleDTO mapToDTO(final Article article, final ArticleDTO articleDTO) {
        articleDTO.setId(article.getId());
        articleDTO.setTitle(article.getTitle());
        articleDTO.setText(article.getText());
        articleDTO.setCreatedDate(article.getCreatedDate());
        articleDTO.setTimeToUnderstand(article.getTimeToUnderstand());
        articleDTO.setLastEditTime(article.getLastEditTime());
        articleDTO.setComments(article.getComments() == null ? null : article.getComments().getId());
        return articleDTO;
    }

    private Article mapToEntity(final ArticleDTO articleDTO, final Article article) {
        article.setTitle(articleDTO.getTitle());
        article.setText(articleDTO.getText());
        article.setCreatedDate(articleDTO.getCreatedDate());
        article.setTimeToUnderstand(articleDTO.getTimeToUnderstand());
        article.setLastEditTime(articleDTO.getLastEditTime());
        final Comment comments = articleDTO.getComments() == null ? null : commentRepository.findById(articleDTO.getComments())
                .orElseThrow(() -> new NotFoundException("comments not found"));
        article.setComments(comments);
//        final User user = articleDTO.getUser() == null ? null : userRepository.findById(articleDTO.getUser())
//                .orElseThrow(() -> new NotFoundException("user not found"));
//        article.setUser(user);
        return article;
    }

}
