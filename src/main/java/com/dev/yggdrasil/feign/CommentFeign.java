package com.dev.yggdrasil.feign;

import com.dev.yggdrasil.model.dto.CommentDTO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "comment", url = "http://localhost:8081/api/comments")
public interface CommentFeign {
    @GetMapping
    ResponseEntity<List<CommentDTO>> getAllComments();

    @GetMapping("/{id}")
    ResponseEntity<CommentDTO> getComment(@PathVariable(name = "id") final Long id);

    @PostMapping
    ResponseEntity<Long> createComment(@RequestBody @Valid final CommentDTO commentDTO);

    @PutMapping("/{id}")
    ResponseEntity<Long> updateComment(@PathVariable(name = "id") final Long id,
                                              @RequestBody @Valid final CommentDTO commentDTO);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteComment(@PathVariable(name = "id") final Long id);
}
