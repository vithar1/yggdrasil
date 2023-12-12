package com.dev.yggdrasil.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CommentDTO {

    private Long id;

    @NotNull
    @Size(max = 2096)
    private String text;

    @NotNull
    private Long user;

}
