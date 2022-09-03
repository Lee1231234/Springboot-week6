package com.example.springbootweek6.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
@Builder
public class ResponseErrorDto {
    private String code;
    private String message;

    public ResponseErrorDto(String code, String message){
        this.code = code;
        this.message = message;
    }
}
