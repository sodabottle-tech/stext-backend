package com.sodabottle.stext.models.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PushOverDto {
    private String token;
    private String user;
    private String title;
    private String message;
    private String device;
}
