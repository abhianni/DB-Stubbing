package com.wm.pojos.enableStub;

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
public class EnableStubResponse extends BaseResponse {
    private String successMessage;
    private String oldUuid;
    private String newUuid;

    public EnableStubResponse(){}

    public EnableStubResponse(String oldUuid, String newUuid, String successMessage){
        this.successMessage = successMessage;
        this.oldUuid = oldUuid;
        this.newUuid = newUuid;
    }
}
