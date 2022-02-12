package com.wm.services.wm;

import com.wm.constants.HTTP_METHOD;
import com.wm.exceptions.ExceptionFromWireMockService;
import com.wm.pojos.createStub.request.wmNative.CreateStubNativeRequest;
import com.wm.services.http.HttpService;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


/* 
    @author Shyam Gupta
    @team   Hotels
*/

@Service
public class WireMockNativeService implements WireMockNativeInterface{

    @Value("${wiremock.host}")
    private String wireMockHost;

    @Value("${wiremock.port}")
    private String wireMockPort;

    @Autowired
    private HttpService httpService;

    private static final String baseCommonPath = "__admin/mappings/";

    @Override
    public Response addStub(CreateStubNativeRequest createStubNativeRequest) throws ExceptionFromWireMockService {
        RequestSpecification requestSpecification = httpService.getRequestSpecificationWithRequestBody(getBaseUri(), baseCommonPath, createStubNativeRequest, ContentType.JSON);
        try{
            return httpService.executeAndGetResponse(HTTP_METHOD.POST, requestSpecification);
        } catch (Exception e){
            throw new ExceptionFromWireMockService(e.getMessage());
        }
    }

    @Override
    public Response deleteStubs() throws ExceptionFromWireMockService {
        RequestSpecification requestSpecification = httpService.getRequestSpecificationWithOutRequestBody(getBaseUri(), baseCommonPath, ContentType.JSON);
        try{
            return httpService.executeAndGetResponse(HTTP_METHOD.DELETE, requestSpecification);
        } catch (Exception e){
                throw new ExceptionFromWireMockService(e.getMessage());
        }
    }

    @Override
    public Response deleteStub(String mappingId) throws ExceptionFromWireMockService {
        String basePath = baseCommonPath.concat("{stubMappingId}");
        try{
            RequestSpecification requestSpecification = httpService.getRequestSpecificationWithOutRequestBody(getBaseUri(), basePath, getPathParameters(mappingId), ContentType.JSON);
            return httpService.executeAndGetResponse(HTTP_METHOD.DELETE, requestSpecification);
        } catch (Exception e){
            throw new ExceptionFromWireMockService(e.getMessage());
        }
    }

    @Override
    public Response getStubs() throws ExceptionFromWireMockService {
        RequestSpecification requestSpecification = httpService.getRequestSpecificationWithOutRequestBody(getBaseUri(), baseCommonPath, ContentType.JSON);
        try{
            return httpService.executeAndGetResponse(HTTP_METHOD.GET, requestSpecification);
        } catch (Exception e){
        throw new ExceptionFromWireMockService(e.getMessage());
        }
    }

    @Override
    public Response getStub(String mappingId) throws ExceptionFromWireMockService {
        String basePath = baseCommonPath.concat("{stubMappingId}");
        RequestSpecification requestSpecification = httpService.getRequestSpecificationWithOutRequestBody(getBaseUri(), basePath, getPathParameters(mappingId), ContentType.JSON);
        try{
            return httpService.executeAndGetResponse(HTTP_METHOD.GET, requestSpecification);
        } catch (Exception e){
            throw new ExceptionFromWireMockService(e.getMessage());
        }
    }

    @Override
    public Response updateStub(CreateStubNativeRequest createStubNativeRequest) throws ExceptionFromWireMockService {
        String basePath = baseCommonPath.concat("{stubMappingId}");
        RequestSpecification requestSpecification = httpService.getRequestSpecificationWithRequestBody(getBaseUri(), basePath, createStubNativeRequest, getPathParameters(createStubNativeRequest.getUuid()), ContentType.JSON);
        try{
            return httpService.executeAndGetResponse(HTTP_METHOD.PUT, requestSpecification);
        } catch (Exception e){
            throw new ExceptionFromWireMockService(e.getMessage());
        }
    }

    public String getWireMockHost(){
        return getBaseUri();
    }

    private String getBaseUri(){
        if(wireMockPort.equalsIgnoreCase("80")){
            return wireMockHost;
        }
        return wireMockHost.concat(":").concat(wireMockPort);
    }

    private Map<String, String> getPathParameters(String mappingId){
        Map<String, String> map = new HashMap<>();
        map.put("stubMappingId", mappingId);
        return map;
    }
}
