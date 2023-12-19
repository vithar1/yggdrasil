package com.dev.yggdrasil.service.impl.rest;

import com.dev.yggdrasil.domain.Article;
import com.dev.yggdrasil.domain.User;
import com.dev.yggdrasil.feign.ArticleFeign;
import com.dev.yggdrasil.mapper.ArticleMapper;
import com.dev.yggdrasil.model.dto.ArticleDTO;
import com.dev.yggdrasil.repos.ArticleRepository;
import com.dev.yggdrasil.service.ArticleService;
import com.dev.yggdrasil.service.impl.UserService;
import com.dev.yggdrasil.util.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Profile("rest")
public class RestArticleServiceImpl implements ArticleService {

    private final ArticleFeign articleFeign;

    @Override
    public List<ArticleDTO> findAll() {
        return articleFeign.getAll().getBody();
    }

    @Override
    public ArticleDTO get(final Long id) {
        return articleFeign.get(id).getBody();
    }

    @Override
    public Long create(final ArticleDTO articleDTO) {
        return articleFeign.createArticle(articleDTO).getBody();
    }

    @Override
    public void update(final Long id, final ArticleDTO articleDTO) {
        articleFeign.updateArticle(id, articleDTO);
    }

    @Override
    public void delete(final Long id) {
        articleFeign.deleteArticle(id);
    }
}
