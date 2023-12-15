package com.dev.yggdrasil.service.impl.monolith;

import com.dev.yggdrasil.domain.Article;
import com.dev.yggdrasil.domain.User;
import com.dev.yggdrasil.mapper.ArticleMapper;
import com.dev.yggdrasil.model.dto.ArticleDTO;
import com.dev.yggdrasil.repos.ArticleRepository;
import com.dev.yggdrasil.service.ArticleService;
import com.dev.yggdrasil.service.impl.UserService;
import com.dev.yggdrasil.util.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Profile("monolith")
public class MonolithArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final UserService userService;
    private final ArticleMapper articleMapper;

    @Override
    public List<ArticleDTO> findAll() {
        final List<Article> articles = articleRepository.findAll(Sort.by("id"));
        return articles.stream()
                .map(articleMapper::toDto)
                .toList();
    }

    @Override
    public ArticleDTO get(final Long id) {
        return articleRepository.findById(id)
                .map(articleMapper::toDto)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Long create(final ArticleDTO articleDTO) {
        User user = userService.getCurrentUser();
        final Article article = articleMapper.toEntity(articleDTO);
        article.setUser(user);
        return articleRepository.save(article).getId();
    }

    @Override
    public void update(final Long id, final ArticleDTO articleDTO) {
        final Article article = articleRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        articleMapper.partialUpdate(article, articleDTO);
        articleRepository.save(article);
    }

    @Override
    public void delete(final Long id) {
        articleRepository.deleteById(id);
    }
}
