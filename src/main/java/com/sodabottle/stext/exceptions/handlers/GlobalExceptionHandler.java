package com.sodabottle.stext.exceptions.handlers;

import com.sodabottle.stext.models.dtos.PushOverDto;
import com.sodabottle.stext.service.AsyncPushOverService;
import com.sodabottle.stext.utils.LogUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private AsyncPushOverService pushOverService;

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<Object> handleConflict(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Exception while Handling";

        String stackTrace = ExceptionUtils.getStackTrace(ex);
        pushOverService.postMessage(
                PushOverDto.builder().title("----- Exception Occured -----")
                        .message(stackTrace).build());
        LogUtils.logMessage(stackTrace, log, LogUtils.LogState.ERROR);

        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}
