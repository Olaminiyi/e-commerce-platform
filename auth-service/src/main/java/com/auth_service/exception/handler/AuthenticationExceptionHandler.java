package com.auth_service.exception.handler;
import com.auth_service.exception.model.AuthenticationException;
import com.auth_service.exception.model.AuthenticationExceptionDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AuthenticationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException e){
        AuthenticationExceptionDetail details = AuthenticationExceptionDetail.builder()
                .message(e.getMessage())
                .status(e.getStatus())
                .build();
        return new ResponseEntity<>(details, HttpStatusCode.valueOf(e.getStatus()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception e){
        AuthenticationExceptionDetail details = AuthenticationExceptionDetail.builder()
                .message("internal server error")
                .status(500)
                .build();
        return new ResponseEntity<>(details, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
