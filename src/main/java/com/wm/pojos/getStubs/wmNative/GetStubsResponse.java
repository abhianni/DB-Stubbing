package com.wm.pojos.getStubs.wmNative;

/* 
    @author Shyam Gupta
    @team   Hotels
*/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wm.pojos.common.BaseResponse;
import com.wm.pojos.common.Mapping;
import com.wm.pojos.common.Meta;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@ToString
public class GetStubsResponse extends BaseResponse {

    @JsonProperty(value = "meta")
    private Meta meta;

    @JsonProperty("mappings")
    private List<Mapping> mappings;
}
