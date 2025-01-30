package com.giuseppemarket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.giuseppemarket.service.IPingService;

@RestController
@RequestMapping("/ping")
@RequiredArgsConstructor
public class PingController {
    private final IPingService pingService;

    @GetMapping
    private ResponseEntity<String> ping(){
        return ResponseEntity.ok(pingService.getPong());
    }

}
