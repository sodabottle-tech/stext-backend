package com.sodabottle.stext.models.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@ToString
public class TelegramDto extends GenericDto {
    private String chat_id;
    private String text;
}
