package com.example.demoapi.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/random/api/v1")
public class RandomController {

    @GetMapping("/")
    public String getValue(){
        double doubleValue = Math.random();
        int result = (int)(doubleValue*10000);
        String hostName = "";
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            hostName = inetAddress.getHostName();
        } catch (UnknownHostException e) {
            hostName="N/A";
        }
        return String.format("Number: %d, from %s", result, hostName);
    }
    
}
