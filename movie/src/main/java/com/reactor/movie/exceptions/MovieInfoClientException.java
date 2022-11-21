package com.reactor.movie.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieInfoClientException extends RuntimeException{

    private String errorMsg;
    private Integer statusCode;

    public MovieInfoClientException(String errorMsg, Integer statusCode) {
        super(errorMsg);
        this.errorMsg = errorMsg;
        this.statusCode = statusCode;
    }
}
