package com.dev.yggdrasil.feign;

import com.dev.yggdrasil.model.dto.NoteDTO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;


@FeignClient(value = "note", url = "http://localhost:8082/api/notes")
public interface NoteFeign {
    @GetMapping
    ResponseEntity<NoteDTO> getNoteForUser();


//    @PatchMapping ("/code/{key}")
    @RequestMapping(method = RequestMethod.PATCH,  value = "/code/{key}")
    ResponseEntity<NoteDTO> code(@PathVariable(name = "key") final String key,
                                        @RequestBody final NoteDTO noteDTO);


    @PatchMapping ("/schedule/{key}")
    ResponseEntity<Void> schedule(@PathVariable(name = "key") final String key,
                                         @RequestBody LocalTime localTime);

    @PatchMapping ("/decode/remove")
    ResponseEntity<Void> decodeRemove();

    @RequestMapping(method = RequestMethod.PATCH,  value = "/decode/{key}")
    ResponseEntity<NoteDTO> decode(@PathVariable(name = "key") final String key,
                                          @RequestBody final NoteDTO noteDTO);

    @PostMapping
    ResponseEntity<Long> createNote(@RequestBody @Valid final NoteDTO noteDTO);

    @PutMapping("/{id}")
    ResponseEntity<Long> updateNote(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final NoteDTO noteDTO);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteNote(@PathVariable(name = "id") final Long id);
}
