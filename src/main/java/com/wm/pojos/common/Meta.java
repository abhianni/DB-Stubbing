package com.wm.pojos.common;

/* 
    @author Shyam Gupta
    @team   Hotels
*/


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@ToString
public class Meta {

    @JsonProperty(value = "total")
    private int  total;
}
