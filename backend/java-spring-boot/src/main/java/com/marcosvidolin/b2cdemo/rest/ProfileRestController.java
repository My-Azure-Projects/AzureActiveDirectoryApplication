package com.marcosvidolin.b2cdemo.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileRestController {

    @GetMapping("/profile")
    public ResponseEntity<?> index(@AuthenticationPrincipal Jwt jwt) {
        String name = jwt.getClaim("given_name");
        return ResponseEntity.ok("{\"name\": \"" + name + "\"}");
    }

}
