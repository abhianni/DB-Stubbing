package com.wm.model;

/* 
    @author Shyam Gupta
    @team   Hotels
*/

import com.wm.constants.UrlIdentifierType;
import lombok.Data;

import java.util.Date;

@Data
public class Stub {
    private String uuid;
    private String stubMappingName;
    private String url;
    private String urlIdentifierType;
    private int priority;
    private String scenarioName;
    private String request;
    private String response;
    private Date createdOn;
    private Date updatedOn;
    private String team;
    private String createRequest;
    private String createResponse;
    private int active;
    private String host;
}
