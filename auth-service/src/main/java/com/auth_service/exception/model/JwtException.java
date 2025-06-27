package com.auth_service.exception.model;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public class JwtException extends  RuntimeException{
    private final int status;

    public JwtException(String message, int status){
        super(message);
        this.status = status;
    }
    public  int getStatus(){
        return  status;
    }
}
