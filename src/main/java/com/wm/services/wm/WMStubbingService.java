package com.wm.services.wm;

/* 
    @author Shyam Gupta
    @team   Hotels
*/

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wm.constants.ErrorCodes;
import com.wm.exceptions.ExceptionFromWireMockService;
import com.wm.helpers.DataConversion;
import com.wm.mappers.StubsDatabaseMapper;
import com.wm.mappers.TeamsDatabaseMapper;
import com.wm.model.Stub;
import com.wm.model.Teams;
import com.wm.pojos.common.BaseResponse;
import com.wm.pojos.common.Error;
import com.wm.pojos.common.ResponseWrapper;
import com.wm.pojos.createStub.request.CreateStubRequest;
import com.wm.pojos.createStub.request.wmNative.CreateStubNativeRequest;
import com.wm.pojos.createStub.response.CreateStubResponse;
import com.wm.pojos.deleteStub.DeleteStubResponse;
import com.wm.pojos.disableStub.DisableStubResponse;
import com.wm.pojos.enableStub.EnableStubResponse;
import com.wm.pojos.getStub.GetStubResponse;
import com.wm.pojos.getStubs.GetAllStubsResponse;
import com.wm.pojos.getTeams.GetTeamsResponse;
import com.wm.services.helpers.WMStubbingServiceHelper;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class WMStubbingService {

    @Autowired
    private WireMockNativeService wireMockNativeService;

    @Autowired
    private StubsDatabaseMapper stubsDatabaseMapper;

    @Autowired
    private TeamsDatabaseMapper teamsDatabaseMapper;

    public ResponseEntity<BaseResponse> createStub(CreateStubRequest createStubRequest)  {
        CreateStubNativeRequest createStubNativeRequest = WMStubbingServiceHelper.getCreateStubNativeRequest(createStubRequest);
        Response response;
        try {
            response = wireMockNativeService.addStub(createStubNativeRequest);
        } catch (ExceptionFromWireMockService exceptionFromWireMockService) {
            return returnFailureResponseForNativeWireMockService(exceptionFromWireMockService.getMessage());
        }
        ResponseWrapper responseWrapper = wrapCreateStubResponse(response);
        if(response.getStatusCode() == 201){
            addStubInTable(createStubRequest, (CreateStubResponse) responseWrapper.getResponse());
        }
        return new ResponseEntity<>((BaseResponse) responseWrapper.getResponse(), responseWrapper.getHttpStatus());
    }

    public ResponseEntity<BaseResponse> updateStub(CreateStubRequest createStubRequest)  {
        CreateStubNativeRequest createStubNativeRequest = WMStubbingServiceHelper.getUpdateStubNativeRequest(createStubRequest);
        if(!doesSubExistInDatabase(createStubRequest.getUuid())){
            return createStub(createStubRequest);
        }

        if(!isActiveInDatabase(createStubRequest.getUuid())){
            BaseResponse baseResponse = new BaseResponse();
            Error error = new Error(ErrorCodes.INACTIVE_STUB.getErrorCode(), ErrorCodes.INACTIVE_STUB.getMessage());
            baseResponse.setError(error);
            return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
        }

        //handling it of in case if stub is active in Database but is not active in wire mock. In this case we always get a new UUID
        try {
            if(!isStubRunning(createStubRequest.getUuid())) {
                Response response = null;
                try {
                    response = wireMockNativeService.addStub(createStubNativeRequest);
                } catch (ExceptionFromWireMockService exceptionFromWireMockService) {
                    return returnFailureResponseForNativeWireMockService(exceptionFromWireMockService.getMessage());
                }
                if (response.getStatusCode() != 201) {
                    BaseResponse baseResponse = new BaseResponse();
                    Error error = new Error(ErrorCodes.EXCEPTION_WHILE_UPDATING_STUB_IN_WIREMOCK.getErrorCode(), ErrorCodes.EXCEPTION_WHILE_UPDATING_STUB_IN_WIREMOCK.getMessage());
                    baseResponse.setError(error);
                    return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
                } else{
                    CreateStubResponse createStubResponse = response.as(CreateStubResponse.class);
                    createStubResponse.setMockedServerHost(wireMockNativeService.getWireMockHost());
                    deleteStub(createStubRequest.getUuid());
                    addStubInTable(createStubRequest, createStubResponse);
                    return new ResponseEntity<BaseResponse>(createStubResponse, HttpStatus.OK);
                }
            }
        } catch (ExceptionFromWireMockService exceptionFromWireMockService) {
            return returnFailureResponseForNativeWireMockService(exceptionFromWireMockService.getMessage());
        }
        Response response = null;
        try {
            response = wireMockNativeService.updateStub(createStubNativeRequest);
        } catch (ExceptionFromWireMockService exceptionFromWireMockService) {
            return returnFailureResponseForNativeWireMockService(exceptionFromWireMockService.getMessage());
        }
        ResponseWrapper responseWrapper = wrapUpdateStubResponse(response, createStubNativeRequest.getUuid());
        if(response.getStatusCode() == 200){
            updateStubInTable(createStubRequest, (CreateStubResponse) responseWrapper.getResponse());
        }
        return new ResponseEntity<BaseResponse>((BaseResponse) responseWrapper.getResponse(), responseWrapper.getHttpStatus());
    }

    public ResponseEntity<BaseResponse> getAllStubMappings() throws JsonProcessingException {
        BaseResponse baseResponse;
        List<Stub> stubList = stubsDatabaseMapper.findAll();
        if(stubList.isEmpty()){
            baseResponse = new BaseResponse();
            Error error = new Error(ErrorCodes.STUBS_NOT_FOUND.getErrorCode(), ErrorCodes.STUBS_NOT_FOUND.getMessage());
            return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
        }
        GetAllStubsResponse getAllStubsResponse = WMStubbingServiceHelper.getAllStubsResponse(stubList);
        return new ResponseEntity<BaseResponse>(getAllStubsResponse, HttpStatus.OK);
    }

    public ResponseEntity<BaseResponse> getStubsUsingTeam(String team) throws JsonProcessingException {
        BaseResponse baseResponse;
        List<Stub> stubList;
        stubList = team.equalsIgnoreCase("all") ? stubsDatabaseMapper.findAll() : stubsDatabaseMapper.findAllForTeam(team);
        if(stubList.isEmpty()){
            baseResponse = new BaseResponse();
            Error error = new Error(ErrorCodes.STUBS_NOT_FOUND.getErrorCode(), ErrorCodes.STUBS_NOT_FOUND.getMessage());
            baseResponse.setError(error);
            return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
        }
        GetAllStubsResponse getAllStubsResponse = WMStubbingServiceHelper.getAllStubsResponse(stubList);
        getAllStubsResponse.getGetStubResponseList().sort((d1,d2) -> d1.getUpdatedOn().compareTo(d2.getUpdatedOn()));
        Collections.reverse(getAllStubsResponse.getGetStubResponseList());
        return new ResponseEntity<BaseResponse>(getAllStubsResponse, HttpStatus.OK);
    }

    public ResponseEntity<BaseResponse> getTeams(String team) throws JsonProcessingException {
        BaseResponse baseResponse;
        List<Teams> stubList;

        if(team.equalsIgnoreCase("all")){
            stubList = teamsDatabaseMapper.findAll();
        } else {
            if(team.equalsIgnoreCase("active")){
                stubList = teamsDatabaseMapper.find(1);
            } else{
                stubList = teamsDatabaseMapper.find(0);
            }
        }
        if(stubList.isEmpty()){
            baseResponse = new BaseResponse();
            Error error = new Error(ErrorCodes.TEAMS_NOT_FOUND.getErrorCode(), ErrorCodes.TEAMS_NOT_FOUND.getMessage().concat("[" + team + "]"));
            baseResponse.setError(error);
            return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
        }
        GetTeamsResponse teamsResponse = new GetTeamsResponse(stubList);

        teamsResponse.getTeams().sort((d1,d2) -> d1.getOrder().compareTo(d2.getOrder()));
        return new ResponseEntity<BaseResponse>(teamsResponse, HttpStatus.OK);
    }

    public ResponseEntity<BaseResponse> updateNative(CreateStubNativeRequest createStubNativeRequest)  {
        Response response = null;
        try {
            response = wireMockNativeService.updateStub(createStubNativeRequest);
        } catch (ExceptionFromWireMockService exceptionFromWireMockService) {
            return returnFailureResponseForNativeWireMockService(exceptionFromWireMockService.getMessage());
        }
        ResponseWrapper responseWrapper = wrapUpdateStubResponse(response, createStubNativeRequest.getUuid());
        return new ResponseEntity<BaseResponse>((BaseResponse) responseWrapper.getResponse(), responseWrapper.getHttpStatus());
    }

    public ResponseEntity<BaseResponse> getStub(String uuid) throws JsonProcessingException {
        BaseResponse baseResponse;
        Stub stub;
        stub = stubsDatabaseMapper.findStub(uuid);
        if (stub == null) {
            baseResponse = new BaseResponse();
            Error error = new Error(ErrorCodes.STUBS_NOT_FOUND.getErrorCode(), ErrorCodes.STUBS_NOT_FOUND.getMessage());
            baseResponse.setError(error);
            return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
        }
        GetAllStubsResponse getAllStubsResponse = WMStubbingServiceHelper.getStubResponseUsingUUID(stub);
        return new ResponseEntity<BaseResponse>(getAllStubsResponse, HttpStatus.OK);
    }

    public ResponseEntity<BaseResponse> deleteStub (String mappingId)  {
        if (doesSubExistInDatabase(mappingId)) {
            stubsDatabaseMapper.deleteStub(mappingId);
        }
        try {
            if (isStubRunning(mappingId)) {
                try {
                    wireMockNativeService.deleteStub(mappingId);
                } catch (ExceptionFromWireMockService exceptionFromWireMockService) {
                    return returnFailureResponseForNativeWireMockService(exceptionFromWireMockService.getMessage());
                }
            }
        } catch (ExceptionFromWireMockService exceptionFromWireMockService) {
            return returnFailureResponseForNativeWireMockService(exceptionFromWireMockService.getMessage());
        }
        return new ResponseEntity<BaseResponse>(getDeleteStubResponse(mappingId), HttpStatus.OK);
    }

    public ResponseEntity<BaseResponse> disableStub(String mappingId) throws JsonProcessingException {
        if(!doesSubExistInDatabase(mappingId)){
            BaseResponse baseResponse = new BaseResponse();
            Error error = new Error(ErrorCodes.STUB_NOT_FOUND.getErrorCode(), ErrorCodes.STUB_NOT_FOUND.getMessage());
            baseResponse.setError(error);
            return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
        }
        stubsDatabaseMapper.disAbleStub(mappingId);
        try {
            if(isStubRunning(mappingId)) {
                try {
                    wireMockNativeService.deleteStub(mappingId);
                } catch (ExceptionFromWireMockService exceptionFromWireMockService) {
                    return returnFailureResponseForNativeWireMockService(exceptionFromWireMockService.getMessage());
                }
            }
        } catch (ExceptionFromWireMockService exceptionFromWireMockService) {
            return returnFailureResponseForNativeWireMockService(exceptionFromWireMockService.getMessage());
        }
        updateUpdatedOnTimeStampInTable(mappingId);
        return new ResponseEntity<BaseResponse>(getDisableStubResponse(mappingId), HttpStatus.OK);
    }

    public ResponseEntity<BaseResponse> enableStub(String mappingId) throws JsonProcessingException {
        if(!doesSubExistInDatabase(mappingId)){
            BaseResponse baseResponse = new BaseResponse();
            Error error = new Error(ErrorCodes.STUB_NOT_FOUND.getErrorCode(), ErrorCodes.STUB_NOT_FOUND.getMessage());
            baseResponse.setError(error);
            return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
        }
        stubsDatabaseMapper.activateStub(mappingId);
        updateUpdatedOnTimeStampInTable(mappingId);

        //handling it of in case if stub is active in Database but is not active in wire mock. In this case we always get a new UUID
        try {
            if(!isStubRunning(mappingId)) {
                CreateStubRequest createStubRequest = (CreateStubRequest)DataConversion.jsonToObject(stubsDatabaseMapper.findStub(mappingId).getCreateRequest(), CreateStubRequest.class);
                Response response = null;
                try {
                    response = wireMockNativeService.addStub(WMStubbingServiceHelper.getCreateStubNativeRequest(createStubRequest));
                } catch (ExceptionFromWireMockService exceptionFromWireMockService) {
                    return returnFailureResponseForNativeWireMockService(exceptionFromWireMockService.getMessage());
                }
                CreateStubResponse createStubResponse = response.as(CreateStubResponse.class);
                updateTableAfterEnable(mappingId, createStubResponse);
                return new ResponseEntity<BaseResponse>(getEnableStubResponse(mappingId, createStubResponse.getUuid()), HttpStatus.OK);
                //it generates a new UUID...update it in the table and redirect the user to search page again
                /*
                **/
            } else{
                updateUpdatedOnTimeStampInTable(mappingId);
                return new ResponseEntity<BaseResponse>(getEnableStubResponse(mappingId, mappingId), HttpStatus.OK);
            }
        } catch (ExceptionFromWireMockService exceptionFromWireMockService) {
            return returnFailureResponseForNativeWireMockService(exceptionFromWireMockService.getMessage());

        }
    }

    private <T> ResponseWrapper wrapResponse(Response response, Class<T> tClass){
        BaseResponse baseResponse = new BaseResponse();
        if(response.getStatusCode() == 404){
            Error error = new Error(ErrorCodes.STUB_NOT_FOUND.getErrorCode(), ErrorCodes.STUB_NOT_FOUND.getMessage());
            baseResponse.setError(error);
        } else if(response.getStatusCode() != 200){
            Error error = new Error(ErrorCodes.NOT_AN_EXPECTED_STATUS_CODE.getErrorCode(), ErrorCodes.NOT_AN_EXPECTED_STATUS_CODE.getMessage(). concat(" [" + response.getStatusCode() + "]"));
            baseResponse.setError(error);
        }else {
            baseResponse = (BaseResponse)response.as(tClass);
        }
        return new ResponseWrapper(baseResponse, HttpStatus.OK);
    }

    private <T> ResponseWrapper wrapGetStubResponse(Response response, String mappingId){
        BaseResponse baseResponse = new BaseResponse();
        if(response.getStatusCode() == 404){
            Error error = new Error(ErrorCodes.STUB_NOT_FOUND.getErrorCode(), ErrorCodes.STUB_NOT_FOUND.getMessage().concat(" [") + mappingId + "]" );
            baseResponse.setError(error);
        } else if(response.getStatusCode() != 200){
            Error error = new Error(ErrorCodes.NOT_AN_EXPECTED_STATUS_CODE.getErrorCode(), ErrorCodes.NOT_AN_EXPECTED_STATUS_CODE.getMessage(). concat(" [" + response.getStatusCode() + "]"));
            baseResponse.setError(error);
        }else {
            baseResponse = (BaseResponse)response.as(GetStubResponse.class);
        }
        return new ResponseWrapper(baseResponse, HttpStatus.OK);
    }

    private <T> ResponseWrapper wrapCreateStubResponse(Response response){
        CreateStubResponse createStubResponse = new CreateStubResponse();
        if(response.getStatusCode() != 201){
            Error error = new Error(ErrorCodes.NOT_AN_EXPECTED_STATUS_CODE.getErrorCode(), ErrorCodes.NOT_AN_EXPECTED_STATUS_CODE.getMessage().concat(" [") + response.getStatusCode() + "]" );
            createStubResponse.setError(error);
        } else if(response.getStatusCode() == 201){
            createStubResponse = (CreateStubResponse)response.as(CreateStubResponse.class);
            createStubResponse.setMockedServerHost(wireMockNativeService.getWireMockHost());

        }
        return new ResponseWrapper(createStubResponse, HttpStatus.OK);
    }

    private <T> ResponseWrapper wrapUpdateStubResponse(Response response, String mappingId){
        CreateStubResponse createStubResponse = new CreateStubResponse();
        if(response.getStatusCode() == 404){
            Error error = new Error(ErrorCodes.STUB_NOT_FOUND.getErrorCode(), ErrorCodes.STUB_NOT_FOUND.getMessage().concat(" [") + mappingId + "]" );
            createStubResponse.setError(error);
        } else if(response.getStatusCode() == 200){
            createStubResponse = (CreateStubResponse)response.as(CreateStubResponse.class);
            createStubResponse.setMockedServerHost(wireMockNativeService.getWireMockHost());
        } else{
            Error error = new Error(ErrorCodes.NOT_AN_EXPECTED_STATUS_CODE.getErrorCode(), ErrorCodes.NOT_AN_EXPECTED_STATUS_CODE.getMessage().concat(" [") + response.getStatusCode() + "]" );
            createStubResponse.setError(error);
        }
        return new ResponseWrapper(createStubResponse, HttpStatus.OK);
    }

    private DeleteStubResponse getDeleteStubResponse(String mappingId){
        DeleteStubResponse deleteStubResponse = new DeleteStubResponse();
        deleteStubResponse.setUuid(mappingId);
        deleteStubResponse.setSuccessMessage("Deleted Successfully");
        return deleteStubResponse;
    }

    private DisableStubResponse getDisableStubResponse(String mappingId){
        DisableStubResponse disableStubResponse = new DisableStubResponse();
        disableStubResponse.setUuid(mappingId);
        disableStubResponse.setSuccessMessage("Disabled Successfully");
        return disableStubResponse;
    }

    private EnableStubResponse getEnableStubResponse(String oldMappingId, String newMappingId){
        EnableStubResponse enableStubResponse = new EnableStubResponse();
        enableStubResponse.setOldUuid(oldMappingId);
        enableStubResponse.setNewUuid(newMappingId);
        enableStubResponse.setSuccessMessage("Enabled Successfully");
        return enableStubResponse;
    }

    private void addStubInTable(CreateStubRequest createStubRequest, CreateStubResponse createStubResponse){
        if(createStubRequest.isPersistent()){
            Stub stub = WMStubbingServiceHelper.getStub(createStubRequest, createStubResponse);
            stubsDatabaseMapper.insert(stub);
        }
    }

    private void updateStubInTable(CreateStubRequest createStubRequest, CreateStubResponse createStubResponse){
        if(createStubRequest.isPersistent()){
            Stub stub = WMStubbingServiceHelper.getStub(createStubRequest, createStubResponse);
            stubsDatabaseMapper.update(stub);
        }
    }

    private void updateUpdatedOnTimeStampInTable(String uuid){
        Stub stub = stubsDatabaseMapper.findStub(uuid);
        stubsDatabaseMapper.update(stub);
    }

    private void updateTableAfterEnable(String oldUuid, CreateStubResponse createStubResponse){
        Stub stub = stubsDatabaseMapper.findStub(oldUuid);
        stubsDatabaseMapper.deleteStub(oldUuid);
        stub.setUuid(createStubResponse.getUuid());
        stub.setCreateResponse(DataConversion.convertObjectToJson(createStubResponse));
        stubsDatabaseMapper.insert(stub);
    }

    private boolean doesSubExistInDatabase(String uuid){
        Stub stub = stubsDatabaseMapper.findStub(uuid);
        return stub != null;
    }

    private boolean isActiveInDatabase(String uuid){
        Stub stub = stubsDatabaseMapper.findStub(uuid);
        return stub.getActive() == 1;
    }

    private boolean isStubRunning(String uuid) throws ExceptionFromWireMockService {
        Response response = null;
        response = wireMockNativeService.getStub(uuid);
        return response.getStatusCode() == 200;
    }

    private ResponseEntity<BaseResponse> returnFailureResponseForNativeWireMockService(String errorMessage){
        BaseResponse baseResponse = new BaseResponse();
        Error error = new Error(ErrorCodes.EXCEPTION_WHILE_CALLING_NATIVE_WIREMOCK_SERVICE.getErrorCode(),
                ErrorCodes.EXCEPTION_WHILE_CALLING_NATIVE_WIREMOCK_SERVICE.getMessage().concat(errorMessage).concat("Wiremock host: " + wireMockNativeService.getWireMockHost()));
        baseResponse.setError(error);
        return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
    }
}
