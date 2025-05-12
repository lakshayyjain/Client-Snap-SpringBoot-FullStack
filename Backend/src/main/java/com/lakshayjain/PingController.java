package com.lakshayjain;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

    record PingPong(String result){}
    @GetMapping("/pong")
    public PingPong getPingPing() {
        return new PingPong("ping");
    }
}