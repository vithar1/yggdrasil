package com.dev.yggdrasil.mapper;

import com.dev.yggdrasil.domain.Comment;
import com.dev.yggdrasil.model.dto.CommentDTO;
import com.dev.yggdrasil.repos.ArticleRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {ArticleRepository.class})
public interface CommentMapper extends EntityMapper<CommentDTO, Comment> {
    @Override
    @Mapping(target = "user.id", source = "user")
    Comment toEntity(CommentDTO dto);

    @Override
    @Mapping(target = "user", source = "user.id")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "article", ignore = true)
    CommentDTO toDto(Comment entity);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "article", ignore = true)
    void partialUpdate(@MappingTarget Comment entity, CommentDTO dto);
}
