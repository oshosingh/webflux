package com.reactor.movie.exceptions;

public class ReviewServerException extends RuntimeException{

    private String errorMsg;
    private Integer statusCode;

    public ReviewServerException(String errorMsg, Integer statusCode) {
        this.errorMsg = errorMsg;
        this.statusCode = statusCode;
    }
}
