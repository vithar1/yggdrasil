package com.dev.yggdrasil.service;

import com.dev.yggdrasil.model.dto.ArticleDTO;

import java.util.List;

public interface ArticleService {
    List<ArticleDTO> findAll();
    ArticleDTO get(final Long id);
    Long create(final ArticleDTO articleDTO);
    void update(final Long id, final ArticleDTO articleDTO);
    void delete(final Long id);
}
