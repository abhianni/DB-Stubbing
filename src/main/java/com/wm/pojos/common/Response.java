package com.wm.pojos.common;

/* 
    @author Shyam Gupta
    @team   Hotels
*/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wm.constants.FaultResponseEnums;
import lombok.Data;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@ToString
public class Response extends BaseResponse{

    private int lower;
    private String type;
    private int upper;
    @JsonProperty(value = "status")
    private String httpStatusCode;
    private String statusMessage;
    private Object headers;
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
