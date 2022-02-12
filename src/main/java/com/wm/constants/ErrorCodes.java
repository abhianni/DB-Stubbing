package com.wm.constants;

/* 
    @author Shyam Gupta
    @team   Hotels
*/

import lombok.Data;

public enum ErrorCodes{
    NOT_AN_EXPECTED_STATUS_CODE("100", "Response code not 200"),
    STUB_NOT_FOUND("101", "Stub not found in database."),
    STUBS_NOT_FOUND("102", "Stubs not found in database"),
    EXCEPTION_WHILE_ADDING_STUB("103", "Exception while adding stub in wiremock."),
    INACTIVE_STUB("104", "Stub is inactive. Please activate it before updating."),
    EXCEPTION_WHILE_UPDATING_STUB_IN_WIREMOCK("105", "Exception while updating stub in wiremock."),
    EXCEPTION_WHILE_CALLING_NATIVE_WIREMOCK_SERVICE("106", "Exception from native wiremock service."),
    TEAMS_NOT_FOUND("107", "Teams not found in database meeting the required criteria.");



    private String errorCode;
    private String message;

    ErrorCodes(String errorCode, String message){
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
