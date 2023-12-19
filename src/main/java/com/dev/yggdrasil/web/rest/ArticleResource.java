package com.dev.yggdrasil.web.rest;

import com.dev.yggdrasil.model.dto.ArticleDTO;
import com.dev.yggdrasil.service.ArticleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/articles", produces = MediaType.APPLICATION_JSON_VALUE)
public class ArticleResource {

    private final ArticleService articleService;

    public ArticleResource(final ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public ResponseEntity<List<ArticleDTO>> getAllArticles() {
        return ResponseEntity.ok(articleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDTO> getArticle(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(articleService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createArticle(@RequestBody @Valid final ArticleDTO articleDTO) {
        final Long createdId = articleService.create(articleDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateArticle(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final ArticleDTO articleDTO) {
        articleService.update(id, articleDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable(name = "id") final Long id) {
        articleService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
