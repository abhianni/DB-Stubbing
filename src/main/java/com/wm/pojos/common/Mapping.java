package com.wm.pojos.common;

/* 
    @author Shyam Gupta
    @team   Hotels
*/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wm.pojos.common.Request;
import com.wm.pojos.common.Response;
import lombok.Data;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@ToString
public class Mapping {

    @JsonProperty(value = "id")
    private String id;

    @JsonProperty("request")
    private Request request;

    @JsonProperty(value = "response")
    private Response response;
}
