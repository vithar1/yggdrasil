package com.dev.yggdrasil.model.dto;

import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDTO {

    private Long id;

    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String username;

    @Size(max = 255)
    private String email;

    @Size(max = 255)
    private String password;

    private List<Long> authorities;

}
