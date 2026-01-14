package com.turkcell.phisingproject.handler;
import com.turkcell.phisingproject.data.Response;
import com.turkcell.phisingproject.data.ResponseCode;
import com.turkcell.phisingproject.exception.DataAccessException;
import com.turkcell.phisingproject.exception.FileCreationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(value = DataAccessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response<Object> handleDataAccessException(DataAccessException ex) {
        log.error("DataAccessException occurred: {}", ex.getMessage(), ex);
        return Response.builder()
                .code(ResponseCode.ERROR.getValue())
                .message("Data access/process error.")
                .time(LocalDateTime.now())
                .build();
    }
    @ExceptionHandler(value = FileCreationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response<Object> handleFileCreationException(FileCreationException ex) {
        log.error("FileCreationException occurred: {}", ex.getMessage(), ex);
        return Response.builder()
                .code(ResponseCode.ERROR.getValue())
                .message("File error: " + ex.getMessage())
                .time(LocalDateTime.now())
                .build();
    }
    @ExceptionHandler(value = RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response<Object> handleRuntimeException(RuntimeException ex) {
        log.error("Unexpected Runtime Exception: {}", ex.getMessage(), ex);
        return Response.builder()
                .code(ResponseCode.ERROR.getValue())
                .message("Unexpected error!")
                .time(LocalDateTime.now())
                .build();
    }

}
