package com.dev.yggdrasil.feign;

import com.dev.yggdrasil.model.dto.NoteDTO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "note", url = "http://localhost:8082/api/notes")
public interface NoteFeign {
    @GetMapping
    ResponseEntity<NoteDTO> getNoteForUser();

    @PostMapping
    ResponseEntity<Long> createNote(@RequestBody @Valid final NoteDTO noteDTO);

    @PutMapping("/{id}")
    ResponseEntity<Long> updateNote(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final NoteDTO noteDTO);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteNote(@PathVariable(name = "id") final Long id);
}
