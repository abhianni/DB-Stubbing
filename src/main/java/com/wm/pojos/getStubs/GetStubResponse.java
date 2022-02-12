package com.wm.pojos.getStubs;

/* 
    @author Shyam Gupta
    @team   Hotels
*/

import com.wm.pojos.common.BaseResponse;
import com.wm.pojos.createStub.request.CreateStubRequest;
import com.wm.pojos.createStub.request.RequestBodyParameters;
import com.wm.pojos.createStub.response.CreateStubResponse;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class GetStubResponse extends BaseResponse {
    private CreateStubRequest createStubRequest;
    private CreateStubResponse createStubResponse;
    private RequestBodyParameters requestBodyParameters;
    private boolean isEnabled;
    private boolean isActive;
    private String host;
    private String createdOn;
    private String updatedOn;
}
