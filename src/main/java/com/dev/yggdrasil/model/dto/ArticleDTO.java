package com.dev.yggdrasil.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String title;

    @NotNull
    private String text;

    @NotNull
    private LocalDate createdDate;

    private Integer timeToUnderstand;

    private LocalDate lastEditTime;

    private Long comments;

}
