package com.example.springbootweek6.Dto.Request;


import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequestDto {

    @NotBlank
    @Pattern(regexp = "[a-zA-Z\\d]*")
    private String nickname;
    @NotBlank
    @Pattern(regexp = "[a-zA-Z\\d]*")
    private String username;
    @NotBlank
    @Size(min = 8, max = 20)
    @Pattern(regexp = "[a-zA-Z\\d]*${8,20}")
    private String password;

    @NotBlank
    private String passwordConfirm;
}
