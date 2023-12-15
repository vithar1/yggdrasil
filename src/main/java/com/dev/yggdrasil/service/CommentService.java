package com.dev.yggdrasil.service;

import com.dev.yggdrasil.model.dto.CommentDTO;

import java.util.List;

public interface CommentService {
    List<CommentDTO> findAll();
    CommentDTO get(final Long id);
    Long create(final CommentDTO commentDTO);
    void update(final Long id, final CommentDTO commentDTO);
    void delete(final Long id);
}
