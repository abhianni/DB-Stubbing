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
public class RequestBodyParameters {

    private boolean isRequestBodyPassed;
    private String criteria;
    private String requestBody;
    private String ignoreOrder;
    private String ignoreExtraElements;
}
