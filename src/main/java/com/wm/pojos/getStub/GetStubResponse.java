package com.wm.pojos.getStub;

/* 
    @author Shyam Gupta
    @team   Hotels
*/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wm.pojos.common.BaseResponse;
import com.wm.pojos.common.Request;
import com.wm.pojos.common.Response;
import lombok.Data;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@ToString
public class GetStubResponse extends BaseResponse {
    private String id;
    private String uuid;
    private Request request;
    private Response response;
}
