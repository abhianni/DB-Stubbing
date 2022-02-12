package com.wm.pojos.common;

/* 
    @author Shyam Gupta
    @team   Hotels
*/

import lombok.Data;

@Data
public class Error {
    private String code;
    private String message;

    private Error(){}
    public Error(String code, String message){
        this.code = code;
        this.message = message;
    }
}
