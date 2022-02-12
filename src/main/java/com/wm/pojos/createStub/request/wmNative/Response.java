package com.wm.pojos.createStub.request.wmNative;

/* 
    @author Shyam Gupta
    @team   Hotels
*/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wm.constants.FaultResponseEnums;
import lombok.Data;
import lombok.ToString;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@ToString
public class Response {

    @JsonProperty(value = "status")
    private String httpStatusCode;
    private String statusMessage;
    private Map<String, String> headers;
    private Object additionalProxyRequestHeaders;
    @JsonProperty(value = "body")
    private String responseBody;
    private String base64Body;
    private String jsonBody;
    private String bodyFileName;
    @JsonProperty("fault")
    private FaultResponseEnums faultResponseEnums;
    private int fixedDelayMilliseconds;
    private Object transformerParameters;
    private String[] transformers;
}
