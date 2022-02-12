package com.wm.pojos.createStub.request;

/* 
    @author Shyam Gupta
    @team   Hotels
*/


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@ToString
public class CreateStubRequest {
    private String id;
    private String uuid;
    private String team;
    private String stubMappingName;
    private boolean persistent;
    private int priority;
    private String scenarioName;
    private String requiredScenarioState;
    private String newScenarioState;
    private CreateStubRequestParameters requestParameters;
    private CreateStubResponseParameters responseParameters;
}
