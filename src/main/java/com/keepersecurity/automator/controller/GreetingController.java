package com.keepersecurity.automator.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.saml2.provider.service.authentication.Saml2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

  @GetMapping(value = "/api/greet")
  public ResponseEntity<MyResponse> greet(
      @AuthenticationPrincipal Saml2AuthenticatedPrincipal principal) {
    MyResponse greeting = new MyResponse("Hello, " + principal.getName());
    return ResponseEntity.ok(greeting);
  }
}


record MyMessages(String name) {
}

record MyResponse(String greeting) {
}
