package com.reactor.movie.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;

public class MovieInfoServerException extends RuntimeException{

    private String errorMsg;
    private Integer statusCode;

    public MovieInfoServerException(String errorMsg, Integer statusCode) {
        super(errorMsg);
        this.errorMsg = errorMsg;
        this.statusCode = statusCode;
    }
}
