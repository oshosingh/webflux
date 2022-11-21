package com.reactor.movie.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewClientException extends RuntimeException{

    private String errorMsg;
    private Integer statusCode;

    public ReviewClientException(String errorMsg, Integer statusCode) {
        super(errorMsg);
        this.errorMsg = errorMsg;
        this.statusCode = statusCode;
    }


}
