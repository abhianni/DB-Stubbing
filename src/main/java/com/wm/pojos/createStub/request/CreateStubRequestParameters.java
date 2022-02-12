package com.wm.pojos.createStub.request;

/* 
    @author Shyam Gupta
    @team   Hotels
*/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wm.pojos.common.BasicAuthCredentialsRequest;
import com.wm.pojos.common.CommonProperties;
import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@ToString
public class CreateStubRequestParameters {

    @JsonProperty(value = "method")
    private String httpMethod;
    /////Only one of url, urlPattern, urlPath or urlPathPattern may be specified.
    private String urlIdentifierType;
    private String url;
    private List<QueryParameter> queryParameters;
    private List<Header> headers;
    @JsonProperty("basicAuthCredentials")
    private BasicAuthCredentialsRequest basicAuthCredentialsRequest;
    private Object cookies;
    private Object bodyPatterns;

}
