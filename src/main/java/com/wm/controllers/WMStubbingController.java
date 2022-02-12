package com.wm.controllers;

/* 
    @author Shyam Gupta
    @team   Hotels
*/


import com.fasterxml.jackson.core.JsonProcessingException;
import com.wm.pojos.common.BaseResponse;
import com.wm.pojos.createStub.request.CreateStubRequest;
import com.wm.services.misc.ResponseDelayService;
import com.wm.services.wm.WMStubbingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("wm")
public class WMStubbingController {

    @Autowired
    private WMStubbingService wmStubbingService;

//    to be removed
    @Autowired
    private ResponseDelayService responseDelayService;


    @PostMapping("stub/create")
    public ResponseEntity<BaseResponse> createStub(@RequestBody CreateStubRequest createStubRequest) {
        responseDelayService.addResponseDelay();
        return wmStubbingService.createStub(createStubRequest);
    }

    @GetMapping("stub/getAll")
    public ResponseEntity<BaseResponse> getStubs() throws JsonProcessingException {
        responseDelayService.addResponseDelay();
        return wmStubbingService.getAllStubMappings();
    }

    @PutMapping("stub/update")
    public ResponseEntity<BaseResponse> updateStub(@RequestBody CreateStubRequest createStubRequest) {
        responseDelayService.addResponseDelay();
        return wmStubbingService.updateStub(createStubRequest);
    }

    @GetMapping("stub/get/{uuid}")
    public ResponseEntity<BaseResponse> getStub(@PathVariable("uuid") String uuid) throws JsonProcessingException {
        responseDelayService.addResponseDelay();
        return wmStubbingService.getStub(uuid);
    }

    @GetMapping("stub/get")
    public ResponseEntity<BaseResponse> getStubUsingTeam(@RequestParam("team") String team) throws JsonProcessingException {
        responseDelayService.addResponseDelay();
        return wmStubbingService.getStubsUsingTeam(team);
    }

    @PutMapping("stub/disable/{uuid}")
    public ResponseEntity<BaseResponse> disAbleStub(@PathVariable("uuid") String uuid) throws JsonProcessingException {
        responseDelayService.addResponseDelay();
        return wmStubbingService.disableStub(uuid);
    }

    @PutMapping("stub/enable/{uuid}")
    public ResponseEntity<BaseResponse> enableStub(@PathVariable("uuid") String uuid) throws JsonProcessingException {
        responseDelayService.addResponseDelay();
        return wmStubbingService.enableStub(uuid);
    }

    @DeleteMapping("stub/{uuid}")
    public ResponseEntity<BaseResponse> deleteStub(@PathVariable("uuid") String uuid)  {
        responseDelayService.addResponseDelay();
        return wmStubbingService.deleteStub(uuid);
    }

    @GetMapping("stub/get-teams")
    public ResponseEntity<BaseResponse> getTeams(@RequestParam("team") String team) throws JsonProcessingException {
        responseDelayService.addResponseDelay();
        return wmStubbingService.getTeams(team);
    }
}
