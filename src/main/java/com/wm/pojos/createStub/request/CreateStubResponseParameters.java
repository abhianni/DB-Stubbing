package com.wm.pojos.createStub.request;

/* 
    @author Shyam Gupta
    @team   Hotels
*/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wm.constants.FaultResponseEnums;
import com.wm.pojos.common.BaseResponse;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@ToString
public class CreateStubResponseParameters {

    @JsonProperty(value = "status")
    private String httpStatusCode;
    private String statusMessage;
    private List<ResponseHeader> headers;
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
