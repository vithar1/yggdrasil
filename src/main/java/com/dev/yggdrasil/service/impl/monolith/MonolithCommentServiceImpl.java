package com.dev.yggdrasil.service.impl.monolith;

import com.dev.yggdrasil.domain.Comment;
import com.dev.yggdrasil.mapper.CommentMapper;
import com.dev.yggdrasil.model.dto.CommentDTO;
import com.dev.yggdrasil.repos.ArticleRepository;
import com.dev.yggdrasil.repos.CommentRepository;
import com.dev.yggdrasil.repos.UserRepository;
import com.dev.yggdrasil.service.CommentService;
import com.dev.yggdrasil.util.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Profile("monolith")
public class MonolithCommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    public List<CommentDTO> findAll() {
        final List<Comment> comments = commentRepository.findAll(Sort.by("id"));
        return comments.stream()
                .map(commentMapper::toDto)
                .toList();
    }

    @Override
    public CommentDTO get(final Long id) {
        return commentRepository.findById(id)
                .map(commentMapper::toDto)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Long create(final CommentDTO commentDTO) {
        final Comment comment = commentMapper.toEntity(commentDTO);
        return commentRepository.save(comment).getId();
    }

    @Override
    public void update(final Long id, final CommentDTO commentDTO) {
        final Comment comment = commentRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        commentMapper.partialUpdate(comment, commentDTO);
        commentRepository.save(comment);
    }

    @Override
    public void delete(final Long id) {
        commentRepository.deleteById(id);
    }
}
