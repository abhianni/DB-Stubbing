package com.wm.pojos.disableStub;

/* 
    @author Shyam Gupta
    @team   Hotels
*/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wm.pojos.common.BaseResponse;
import lombok.Data;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@ToString
public class DisableStubResponse extends BaseResponse {
    private String successMessage;
    private String uuid;

    public DisableStubResponse(){}

    public DisableStubResponse(String uuid, String successMessage){
        this.successMessage = successMessage;
        this.uuid = uuid;
    }
}
