package com.wm.pojos.createStub.request.wmNative;

/* 
    @author Shyam Gupta
    @team   Hotels
*/


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wm.pojos.common.Request;
import lombok.Data;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@ToString
public class CreateStubNativeRequest {
    private String id;
    private String uuid;
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
