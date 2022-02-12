package com.wm.pojos.deleteStub;

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
public class DeleteStubResponse extends BaseResponse {
    private String successMessage;
    private String uuid;

    public DeleteStubResponse(){}

    public DeleteStubResponse(String uuid, String successMessage){
        this.successMessage = successMessage;
        this.uuid = uuid;
    }
}
