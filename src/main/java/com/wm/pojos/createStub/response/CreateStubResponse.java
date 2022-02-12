package com.wm.pojos.createStub.response;

/* 
    @author Shyam Gupta
    @team   Hotels
*/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wm.pojos.common.BaseResponse;
import com.wm.pojos.common.Request;
import com.wm.pojos.createStub.request.CreateStubResponseParameters;
import com.wm.pojos.createStub.request.wmNative.Response;
import lombok.Data;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@ToString
public class CreateStubResponse extends BaseResponse {
    private String id;
    private String uuid;
    private String mockedServerHost;
    @JsonProperty("name")
    private String stubMappingName;
    private boolean persistent;
    private int priority;
    private String scenarioName;
    private String requiredScenarioState;
    private String newScenarioState;

    private Request request;
    private Response response;
}
