package com.sodabottle.stext.models.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PushOverDto extends GenericDto {
    private String title;
    private String message;
    private String device;
}
