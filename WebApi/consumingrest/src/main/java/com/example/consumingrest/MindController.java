package com.example.consumingrest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/mindcontroller")
public class MindController {
    @PostMapping
    public ResponseEntity<String> postTest(@RequestBody String censorBody) {
                
    }
}
