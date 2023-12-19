package com.dev.yggdrasil.service.impl.rest;

import com.dev.yggdrasil.domain.Comment;
import com.dev.yggdrasil.feign.CommentFeign;
import com.dev.yggdrasil.mapper.CommentMapper;
import com.dev.yggdrasil.model.dto.CommentDTO;
import com.dev.yggdrasil.repos.CommentRepository;
import com.dev.yggdrasil.service.CommentService;
import com.dev.yggdrasil.util.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Profile("rest")
public class RestCommentServiceImpl implements CommentService {

    private final CommentFeign commentFeign;

    @Override
    public List<CommentDTO> findAll() {
        return commentFeign.getAllComments().getBody();
    }

    @Override
    public CommentDTO get(final Long id) {
        return commentFeign.getComment(id).getBody();
    }

    @Override
    public Long create(final CommentDTO commentDTO) {
        return commentFeign.createComment(commentDTO).getBody();
    }

    @Override
    public void update(final Long id, final CommentDTO commentDTO) {
        commentFeign.updateComment(id, commentDTO);
    }

    @Override
    public void delete(final Long id) {
        commentFeign.deleteComment(id);
    }
}
