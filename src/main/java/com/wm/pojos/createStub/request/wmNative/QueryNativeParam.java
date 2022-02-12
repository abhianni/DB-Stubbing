package com.wm.pojos.createStub.request.wmNative;

/* 
    @author Shyam Gupta
    @team   Hotels
*/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include. NON_NULL)
@Data
@ToString
public class QueryNativeParam {
    private String contains=null;
    private String equalTo=null;
    private boolean caseInsensitive;

}
