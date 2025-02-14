package com.keepersecurity.automator.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

  @GetMapping(value = "/api/greet")
  public ResponseEntity<MyResponse> greet(@RequestParam MyMessages requestName) {
    MyResponse greeting = new MyResponse("Hello, " + requestName.name());
    return ResponseEntity.ok(greeting);
  }
}


record MyMessages(String name) {
}

record MyResponse(String greeting) {
}
