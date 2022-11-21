package com.reactor.movie.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MovieInfoClientException.class)
    public ResponseEntity<Map<String, String>> movieInfoClientExceptionHandler(MovieInfoClientException ex) {
        log.error("Excpetion in movieInfoClient : {}", ex.getErrorMsg());
        return ResponseEntity.status(ex.getStatusCode()).body(makeErrorMsg(ex.getErrorMsg()));
    }

    @ExceptionHandler(ReviewClientException.class)
    public ResponseEntity<Map<String, String>> reviewClientExceptionHandler(ReviewClientException ex) {
        log.error("Exception in reviewClient : {}", ex.getErrorMsg());
        return ResponseEntity.status(ex.getStatusCode()).body(makeErrorMsg(ex.getErrorMsg()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> runtimeException(RuntimeException e) {
        Map<String, String> errorMsg = new HashMap();
        errorMsg.put("error", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMsg);
    }

    private Map<String, String> makeErrorMsg(String errorMsg) {
        Map<String, String> map = new HashMap<>();
        map.put("error", errorMsg);
        return map;
    }
}
