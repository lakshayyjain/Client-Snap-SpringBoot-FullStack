package com.lakshayjain;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingPongController {
    record PingPong(String result){}
    
    @GetMapping("/ping")
    public PingPong getPingPing() {
        return new PingPong("pong");
    }
    
    
}
