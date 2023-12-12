package com.dev.yggdrasil.service;

import com.dev.yggdrasil.domain.Comment;
import com.dev.yggdrasil.domain.User;
import com.dev.yggdrasil.model.dto.CommentDTO;
import com.dev.yggdrasil.repos.CommentRepository;
import com.dev.yggdrasil.repos.UserRepository;
import com.dev.yggdrasil.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public CommentService(final CommentRepository commentRepository,
            final UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    public List<CommentDTO> findAll() {
        final List<Comment> comments = commentRepository.findAll(Sort.by("id"));
        return comments.stream()
                .map(comment -> mapToDTO(comment, new CommentDTO()))
                .toList();
    }

    public CommentDTO get(final Long id) {
        return commentRepository.findById(id)
                .map(comment -> mapToDTO(comment, new CommentDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final CommentDTO commentDTO) {
        final Comment comment = new Comment();
        mapToEntity(commentDTO, comment);
        return commentRepository.save(comment).getId();
    }

    public void update(final Long id, final CommentDTO commentDTO) {
        final Comment comment = commentRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(commentDTO, comment);
        commentRepository.save(comment);
    }

    public void delete(final Long id) {
        commentRepository.deleteById(id);
    }

    private CommentDTO mapToDTO(final Comment comment, final CommentDTO commentDTO) {
        commentDTO.setId(comment.getId());
        commentDTO.setText(comment.getText());
        commentDTO.setUser(comment.getUser() == null ? null : comment.getUser().getId());
        return commentDTO;
    }

    private Comment mapToEntity(final CommentDTO commentDTO, final Comment comment) {
        comment.setText(commentDTO.getText());
        final User user = commentDTO.getUser() == null ? null : userRepository.findById(commentDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        comment.setUser(user);
        return comment;
    }

}
