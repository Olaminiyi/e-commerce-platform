package com.auth_service.exception.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@AllArgsConstructor
public class AuthenticationException extends  RuntimeException{
    private final int status;

    public AuthenticationException(String message, int status){
        super(message);
        this.status = status;
    }
    public  int getStatus(){
        return  status;
    }
}
