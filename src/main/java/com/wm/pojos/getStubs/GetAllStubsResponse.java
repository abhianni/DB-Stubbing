package com.wm.pojos.getStubs;

/* 
    @author Shyam Gupta
    @team   Hotels
*/

import com.wm.pojos.common.BaseResponse;
import lombok.Data;

import java.util.List;

@Data
public class GetAllStubsResponse extends BaseResponse {
    private List<GetStubResponse> getStubResponseList;
}
