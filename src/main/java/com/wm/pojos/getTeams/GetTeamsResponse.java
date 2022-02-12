package com.wm.pojos.getTeams;

/* 
    @author Shyam Gupta
    @team   Hotels
*/

import com.wm.model.Teams;
import com.wm.pojos.common.BaseResponse;
import lombok.Data;

import java.util.List;

@Data
public class GetTeamsResponse extends BaseResponse {
    private List<Teams> teams;

    private GetTeamsResponse(){}

    public GetTeamsResponse(List<Teams> teams){
        this.teams = teams;
    }
}
