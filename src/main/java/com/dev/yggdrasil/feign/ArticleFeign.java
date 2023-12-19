package com.dev.yggdrasil.feign;

import com.dev.yggdrasil.model.dto.ArticleDTO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "article", url = "http://localhost:8081/api/articles")
public interface ArticleFeign {
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    ResponseEntity<ArticleDTO> get(@PathVariable(name = "id") final Long id);

    @GetMapping(value = "")
    ResponseEntity<List<ArticleDTO>> getAll();


    @PostMapping
    ResponseEntity<Long> createArticle(@RequestBody ArticleDTO articleDTO);

    @PutMapping("/{id}")
    ResponseEntity<Long> updateArticle(@PathVariable(name = "id") final Long id,
                                              @RequestBody @Valid final ArticleDTO articleDTO);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteArticle(@PathVariable(name = "id") final Long id);
}
