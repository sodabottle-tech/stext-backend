package com.sodabottle.stext.service;

import com.sodabottle.stext.models.dtos.GenericDto;

public interface ExternalService {
    <T extends GenericDto> void postMessage(T genericDto);
}
