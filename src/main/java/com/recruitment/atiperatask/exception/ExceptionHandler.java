package com.recruitment.atiperatask.exception;

import com.recruitment.atiperatask.dto.ErrorMessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler({
            UserNotFoundException.class,
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessageDto handleExceptions(Exception e) {
        return new ErrorMessageDto(HttpStatus.NOT_FOUND.value() ,e.getMessage());
    }
}
