package com.wm.services.helpers;

/* 
    @author Shyam Gupta
    @team   Hotels
*/

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wm.constants.UrlIdentifierType;
import com.wm.helpers.DataConversion;
import com.wm.model.Stub;
import com.wm.model.Teams;
import com.wm.pojos.common.Request;
import com.wm.pojos.createStub.request.*;
import com.wm.pojos.createStub.request.wmNative.CreateStubNativeRequest;
import com.wm.pojos.createStub.request.wmNative.QueryNativeParam;
import com.wm.pojos.createStub.request.wmNative.Response;
import com.wm.pojos.createStub.response.CreateStubResponse;
import com.wm.pojos.getStubs.GetAllStubsResponse;
import com.wm.pojos.getStubs.GetStubResponse;
import com.wm.pojos.getTeams.GetTeamsResponse;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.*;

public final class WMStubbingServiceHelper {

    public static CreateStubNativeRequest getCreateStubNativeRequest(CreateStubRequest createStubRequest){
        CreateStubNativeRequest createStubNativeRequest = new CreateStubNativeRequest();
        createStubNativeRequest.setRequest(getRequest(createStubRequest));
        createStubNativeRequest.setResponse(getResponse(createStubRequest.getResponseParameters()));
        createStubNativeRequest.setScenarioName(createStubRequest.getScenarioName());
        createStubNativeRequest.setStubMappingName(createStubRequest.getStubMappingName());
        createStubNativeRequest.setPriority(createStubRequest.getPriority());
        createStubNativeRequest.setNewScenarioState(createStubRequest.getNewScenarioState());
        createStubNativeRequest.setRequiredScenarioState(createStubRequest.getRequiredScenarioState());
        return createStubNativeRequest;
    }

    public static CreateStubNativeRequest getUpdateStubNativeRequest(CreateStubRequest createStubRequest){
        CreateStubNativeRequest createStubNativeRequest = new CreateStubNativeRequest();
        createStubNativeRequest.setUuid(createStubRequest.getUuid());
        createStubNativeRequest.setRequest(getRequest(createStubRequest));
        createStubNativeRequest.setResponse(getResponse(createStubRequest.getResponseParameters()));
        createStubNativeRequest.setScenarioName(createStubRequest.getScenarioName());
        createStubNativeRequest.setStubMappingName(createStubRequest.getStubMappingName());
        createStubNativeRequest.setPriority(createStubRequest.getPriority());
        createStubNativeRequest.setNewScenarioState(createStubRequest.getNewScenarioState());
        createStubNativeRequest.setRequiredScenarioState(createStubRequest.getRequiredScenarioState());
        return createStubNativeRequest;
    }

    private static Request getRequest(CreateStubRequest createStubRequest){
        Request request= new Request();
        request.setHttpMethod(createStubRequest.getRequestParameters().getHttpMethod());
        request.setBasicAuthCredentialsRequest(createStubRequest.getRequestParameters().getBasicAuthCredentialsRequest());
        request.setBodyPatterns(createStubRequest.getRequestParameters().getBodyPatterns());
        request.setQueryParameters(getQueryParameters(createStubRequest.getRequestParameters().getQueryParameters()));
        request.setHeaders(getRequestHeaders(createStubRequest.getRequestParameters().getHeaders()));
        request.setCookies(createStubRequest.getRequestParameters().getCookies());
        setUrl(request, createStubRequest); //setting url based upon identifier
        return request;
    }

    private static Response getResponse(CreateStubResponseParameters createStubResponseParameters){
        Response response = new Response();
        response.setHttpStatusCode(createStubResponseParameters.getHttpStatusCode());
        response.setHeaders(getResponseHeaders(createStubResponseParameters.getHeaders()));
        response.setAdditionalProxyRequestHeaders(createStubResponseParameters.getAdditionalProxyRequestHeaders());
        response.setResponseBody(createStubResponseParameters.getResponseBody());
        response.setBase64Body(createStubResponseParameters.getBase64Body());
        response.setJsonBody(createStubResponseParameters.getJsonBody());
        response.setBodyFileName(createStubResponseParameters.getBodyFileName());
        response.setFaultResponseEnums(createStubResponseParameters.getFaultResponseEnums());
        response.setFixedDelayMilliseconds(createStubResponseParameters.getFixedDelayMilliseconds());
        response.setTransformerParameters(createStubResponseParameters.getTransformerParameters());
        response.setTransformers(createStubResponseParameters.getTransformers());
        return response;
    }

    private static Map<String, String> getResponseHeaders(List<ResponseHeader> responseHeaderList){
        Map<String, String> map = new HashMap<>();
        if((null != responseHeaderList) && !responseHeaderList.isEmpty()){
            for(ResponseHeader responseHeader: responseHeaderList){
                map.put(responseHeader.getName(), responseHeader.getValue());
            }
        }
        return map;
    }

    private static Map<String, QueryNativeParam> getQueryParameters(List<QueryParameter> queryParameterList){
        Map<String, QueryNativeParam> nativeParamMap = new HashMap<>();
        if(!CollectionUtils.isEmpty(queryParameterList)){
            for(QueryParameter queryParameter: queryParameterList){
                QueryNativeParam queryNativeParam = new QueryNativeParam();
                if(queryParameter.getCondition().equalsIgnoreCase("contains")){
                    queryNativeParam.setContains(queryParameter.getValue());
                } else if(queryParameter.getCondition().equalsIgnoreCase("equalTo")){
                    queryNativeParam.setEqualTo(queryParameter.getValue());
                }
                queryNativeParam.setCaseInsensitive(queryParameter.isCaseInsensitive());
                nativeParamMap.put(queryParameter.getQueryParamName(), queryNativeParam);
            }
        }
        return nativeParamMap;
    }

    private static Map<String, QueryNativeParam> getRequestHeaders(List<Header> requestHeaderList){
        Map<String, QueryNativeParam> nativeParamMap = new HashMap<>();
        if(!CollectionUtils.isEmpty(requestHeaderList)){
            for(Header header: requestHeaderList){
                QueryNativeParam queryNativeParam = new QueryNativeParam();
                if(header.getCondition().equalsIgnoreCase("contains")){
                    queryNativeParam.setContains(header.getValue());
                } else if(header.getCondition().equalsIgnoreCase("equalTo")){
                    queryNativeParam.setEqualTo(header.getValue());
                }
                queryNativeParam.setCaseInsensitive(header.isCaseInsensitive());
                nativeParamMap.put(header.getHeaderName(), queryNativeParam);
            }
        }
        return nativeParamMap;
    }

    private static void setUrl(Request request, CreateStubRequest createStubRequest){
        switch (createStubRequest.getRequestParameters().getUrlIdentifierType()){
            case (UrlIdentifierType.URL):
                request.setUrl(createStubRequest.getRequestParameters().getUrl());
                break;
            case (UrlIdentifierType.URL_PATH):
                request.setUrlPath(createStubRequest.getRequestParameters().getUrl());
                break;
            case (UrlIdentifierType.URL_PATH_PATTERN):
                request.setUrlPathPattern(createStubRequest.getRequestParameters().getUrl());
                break;
            case (UrlIdentifierType.URL_PATTERN):
                request.setUrlPattern(createStubRequest.getRequestParameters().getUrl());
                break;
        }
    }

    public static Stub getStub(CreateStubRequest createStubRequest, CreateStubResponse createStubResponse){
        Stub stub = new Stub();
        stub.setUuid(createStubResponse.getUuid());
        stub.setStubMappingName(createStubResponse.getStubMappingName());
        stub.setPriority(createStubResponse.getPriority());
        stub.setScenarioName(createStubResponse.getScenarioName());
        stub.setRequest(DataConversion.convertObjectToJson(createStubResponse.getRequest()));
        stub.setResponse(DataConversion.convertObjectToJson(createStubResponse.getResponse()));
        stub.setTeam(createStubRequest.getTeam());
        stub.setUrlIdentifierType(createStubRequest.getRequestParameters().getUrlIdentifierType());
        stub.setUrl(createStubRequest.getRequestParameters().getUrl());
        stub.setCreateRequest(DataConversion.convertObjectToJson(createStubRequest));
        stub.setCreateResponse(DataConversion.convertObjectToJson(createStubResponse));
        stub.setHost(createStubResponse.getMockedServerHost());
        return stub;
    }

    public static GetAllStubsResponse getAllStubsResponse(List<Stub> stubs) throws JsonProcessingException {
        GetAllStubsResponse getAllStubsResponse = new GetAllStubsResponse();
        List<GetStubResponse> getStubResponses = new ArrayList<>();
        for(Stub stub: stubs){
            getStubResponses.add(getStubResponse(stub));
        }
        getAllStubsResponse.setGetStubResponseList(getStubResponses);
        return getAllStubsResponse;
    }

//    public static GetTeamsResponse getTeamsResponse(List<Teams> teams) throws JsonProcessingException {
//        GetTeamsResponse teamsResponse = new GetTeamsResponse();
//        List<String> list = new ArrayList<>();
//        for(Teams t: teams){
//            list.add(t.getName());
//        }
//        teamsResponse.setTeams(list);
//        return teamsResponse;
//    }

    public static GetAllStubsResponse getStubResponseUsingUUID(Stub stub) throws JsonProcessingException {
        GetAllStubsResponse getAllStubsResponse = new GetAllStubsResponse();
        List<GetStubResponse> getStubResponses = new ArrayList<>();
        getStubResponses.add(getStubResponse(stub));
        getAllStubsResponse.setGetStubResponseList(getStubResponses);
        return getAllStubsResponse;
    }

    private static GetStubResponse getStubResponse(Stub stub) throws JsonProcessingException {
        GetStubResponse getStubResponse = new GetStubResponse();
        getStubResponse.setCreateStubRequest((CreateStubRequest)DataConversion.jsonToObject(stub.getCreateRequest(), CreateStubRequest.class));
        getStubResponse.setCreateStubResponse((CreateStubResponse) DataConversion.jsonToObject(stub.getCreateResponse(), CreateStubResponse.class));
        getStubResponse.setActive(isEnabled(stub.getActive()));
        getStubResponse.setRequestBodyParameters(getRequestBodyParameters((CreateStubRequest)DataConversion.jsonToObject(stub.getCreateRequest(), CreateStubRequest.class)));
        getStubResponse.setHost(stub.getHost());
        getStubResponse.setCreatedOn(getDateAndTime_Chennai_Kolkata_Mumbai_UTC(stub.getCreatedOn()));
        getStubResponse.setUpdatedOn(getDateAndTime_Chennai_Kolkata_Mumbai_UTC(stub.getUpdatedOn()));
        return getStubResponse;
    }

    public static String getDateAndTime_Chennai_Kolkata_Mumbai_UTC(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

    private static RequestBodyParameters getRequestBodyParameters(CreateStubRequest createStubRequest){
        RequestBodyParameters requestBodyParameters = new RequestBodyParameters();
        if(createStubRequest.getRequestParameters().getBodyPatterns() == null){
            return requestBodyParameters;
        }
        requestBodyParameters.setRequestBodyPassed(true);
        Object o = createStubRequest.getRequestParameters().getBodyPatterns();
        List list = (List) o;
        Map<String, String> map = (Map)list.get(0);
        for(Map.Entry<String, String> entry: map.entrySet()){
            if(entry.getKey().equalsIgnoreCase("contains") || entry.getKey().equalsIgnoreCase("equalToJson") || entry.getKey().equalsIgnoreCase("equalToXml")){
                requestBodyParameters.setCriteria(entry.getKey());
                requestBodyParameters.setRequestBody(entry.getValue());
            } else if(entry.getKey().equalsIgnoreCase("ignoreArrayOrder")) {
                requestBodyParameters.setIgnoreOrder(entry.getValue());
            } else if(entry.getKey().equalsIgnoreCase("ignoreExtraElements")){
                requestBodyParameters.setIgnoreExtraElements(entry.getValue());
            }
        }
        return requestBodyParameters;
    }

    private static boolean isEnabled(int identifier){
        return identifier == 1;
    }

}
