package com.wm.services.misc;

/* 
    @author Shyam Gupta
    @team   Hotels
*/


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ResponseDelayService {

    @Value("${add.hard.coded.delay}")
    private boolean isResponseDelay;

    @Value("${hard.coded.delay.in.each.request.in.milliseconds}")
    private String delayIntervalInMilliSeconds;

    public void addResponseDelay(){
        try {
            if(isResponseDelay) {
                Thread.sleep(Long.parseLong(delayIntervalInMilliSeconds));
            }
        } catch (InterruptedException e) {
            return;
        }
    }
}
