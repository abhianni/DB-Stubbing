package com.wm.pojos.createStub.request;

/* 
    @author Shyam Gupta
    @team   Hotels
*/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

@JsonInclude(JsonInclude.Include. NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@ToString
public class QueryParameter {
    private String queryParamName;
    private String value;
    private String condition;
    private boolean caseInsensitive;
}
