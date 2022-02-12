package com.wm.services.wm;

/* 
    @author Shyam Gupta
    @team   Hotels
*/

import com.wm.exceptions.ExceptionFromWireMockService;
import com.wm.pojos.createStub.request.wmNative.CreateStubNativeRequest;
import io.restassured.response.Response;

public interface WireMockNativeInterface {
    Response addStub(CreateStubNativeRequest createStubNativeRequest) throws ExceptionFromWireMockService;
    Response deleteStub(String mappingId) throws ExceptionFromWireMockService;
    Response deleteStubs() throws ExceptionFromWireMockService;
    Response getStub(String mappingId) throws ExceptionFromWireMockService;
    Response getStubs() throws ExceptionFromWireMockService;
    Response updateStub(CreateStubNativeRequest createStubNativeRequest) throws ExceptionFromWireMockService;
}
