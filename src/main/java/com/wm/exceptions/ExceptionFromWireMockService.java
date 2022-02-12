package com.wm.exceptions;

/* 
    @author Shyam Gupta
    @team   Hotels
*/

import lombok.Data;

@Data
public class ExceptionFromWireMockService extends Exception{

    private String message;

    public ExceptionFromWireMockService(String message){
        this.message=message;
    }
}
