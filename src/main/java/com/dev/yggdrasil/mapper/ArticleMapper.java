package com.dev.yggdrasil.mapper;

import com.dev.yggdrasil.domain.Article;
import com.dev.yggdrasil.model.dto.ArticleDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = CommentMapper.class)
public interface ArticleMapper extends EntityMapper<ArticleDTO, Article> {

}
