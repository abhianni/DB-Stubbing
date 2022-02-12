package com.wm.pojos.common;

/* 
    @author Shyam Gupta
    @team   Hotels
*/

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ResponseWrapper {

    private Object response;
    private HttpStatus httpStatus;

    public ResponseWrapper(Object o, HttpStatus httpStatus){
        this.response = o;
        this.httpStatus = httpStatus;
    }
}
