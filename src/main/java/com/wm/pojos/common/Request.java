package com.wm.pojos.common;

/* 
    @author Shyam Gupta
    @team   Hotels
*/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wm.pojos.createStub.request.wmNative.QueryNativeParam;
import lombok.Data;
import lombok.ToString;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@ToString
public class Request {
    @JsonProperty(value = "method")
    private String httpMethod;
    /////Only one of url, urlPattern, urlPath or urlPathPattern may be specified.
    private String url;
    private String urlPath;
    private String urlPathPattern;
    private String urlPattern;
    private Map<String, QueryNativeParam> queryParameters;
    private Map<String, QueryNativeParam> headers;
//    private Object headers;
    @JsonProperty("basicAuthCredentials")
    private BasicAuthCredentialsRequest basicAuthCredentialsRequest;
    private Object cookies;
    private Object bodyPatterns;

}
