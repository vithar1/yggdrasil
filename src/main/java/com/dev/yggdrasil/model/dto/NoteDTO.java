package com.dev.yggdrasil.model.dto;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoteDTO {

    private Long id;

    private String text;

    private String encryptedText;
}
