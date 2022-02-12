package com.wm.pojos.common;

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
public class CommonProperties {
    private String equalTo;
    private String contains;
    private boolean caseInsensitive;
}
